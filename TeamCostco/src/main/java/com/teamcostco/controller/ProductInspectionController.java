package main.java.com.teamcostco.controller;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import main.java.com.teamcostco.model.Product;
import main.java.com.teamcostco.model.database.DatabaseUtil;
import main.java.com.teamcostco.view.panels.ProductInspectionPanel;

/**
 * 제품 검수 컨트롤러 클래스
 */
public class ProductInspectionController extends PanelController<ProductInspectionPanel> {

    private List<Product> products; // 제품 리스트를 저장할 리스트
    private List<String> categories; // 카테고리 리스트를 저장할 리스트

    /**
     * 생성자
     */
    public ProductInspectionController() {
        products = new ArrayList<>(); // 제품 리스트 초기화
        loadTableData(); // 테이블 데이터 로드 메서드 호출하여 테이블 초기화

        // 검수 확인 버튼의 ActionListener 설정
        view.checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processCheckedItems(); // 검수 확인된 항목 처리 메서드 호출
            }
        });

        // 불량 처리 버튼의 ActionListener 설정
        view.defectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDefectDialog(); // 불량 처리 다이얼로그 표시 메서드 호출
            }
        });

        categories = retrieveCategoriesFromDatabase(); // 데이터베이스에서 카테고리 목록을 가져오는 메서드 호출하여 카테고리 리스트 초기화
        for (String item : categories) {
            view.categoryComboBox.addItem(item); // 카테고리 리스트의 각 항목을 콤보박스에 추가
        }

        // 검색 필드와 카테고리 콤보박스의 ActionListener 설정
        ActionListener updateTableListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productNumber = view.productNumberField.getText(); // 검색할 제품 번호 가져오기
                String selectedCategory = (String) view.categoryComboBox.getSelectedItem(); // 선택된 카테고리 가져오기
                List<Product> searchedProducts = fetchDataFromDatabase(productNumber, selectedCategory); // 데이터베이스에서 제품 목록 가져오기
                updateTable(searchedProducts); // 테이블 업데이트 메서드 호출하여 테이블 갱신
                view.checkButton.setEnabled(!searchedProducts.isEmpty()); // 검색 결과에 따라 검수 확인 버튼 활성/비활성 설정
            }
        };

        view.productNumberField.addActionListener(updateTableListener); // 검색 필드에 ActionListener 추가
        view.categoryComboBox.addActionListener(updateTableListener); // 카테고리 콤보박스에 ActionListener 추가
    }

    /**
     * 데이터베이스에서 카테고리 목록을 가져오는 메서드
     * 
     * @return 카테고리 목록
     */
    private List<String> retrieveCategoriesFromDatabase() {
        List<String> categories = new ArrayList<>(); // 카테고리 목록을 저장할 리스트 생성
        categories.add("전체"); // "전체" 항목 추가

        try (Connection conn = DatabaseUtil.getConnection(); 
             Statement stmt = conn.createStatement(); 
             ResultSet rs = stmt.executeQuery("SELECT main_name FROM maincategory")) {

            while (rs.next()) {
                categories.add(rs.getString("main_name")); // 리스트에 카테고리 추가
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // 예외 처리
        }

        return categories; // 카테고리 목록 반환
    }

    /**
     * 테이블 데이터 로드 메서드
     */
    private void loadTableData() {
        products.clear(); // 기존 제품 리스트 초기화
        view.allProducts = fetchDataFromDatabase(null, "전체"); // 전체 제품 데이터 로드
        updateTable(view.allProducts); // 테이블 업데이트 메서드 호출하여 테이블 초기화
    }

    /**
     * 데이터베이스에서 데이터 가져오기 메서드
     * 
     * @param productcode 검색할 제품 코드
     * @param mainid      선택된 카테고리
     * @return 데이터베이스에서 가져온 제품 리스트
     */
    private List<Product> fetchDataFromDatabase(String productcode, String mainid) {
        products.clear(); // 기존 제품 리스트 초기화
        String query = "SELECT product_code, mc.main_name AS MAIN_NAME, product_name FROM product INNER JOIN maincategory mc using(main_id) WHERE 1=1";

        if (productcode != null && !productcode.isEmpty()) {
            query += " AND PRODUCT_CODE LIKE ?";
        }
        if (mainid != null && !"전체".equals(mainid)) {
            query += " AND MAIN_NAME = ?";
        }

        try (Connection conn = DatabaseUtil.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(query)) {
            int parameterIndex = 1;

            if (productcode != null && !productcode.isEmpty()) {
                ps.setString(parameterIndex++, "%" + productcode + "%");
            }
            if (mainid != null && !"전체".equals(mainid)) {
                ps.setString(parameterIndex++, mainid);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setProduct_code(rs.getString("PRODUCT_CODE"));
                    product.setMain_id(rs.getString("MAIN_NAME"));
                    product.setProduct_name(rs.getString("PRODUCT_NAME"));
                    products.add(product); // 제품 리스트에 추가
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // 데이터베이스 오류 메시지 표시
            JOptionPane.showMessageDialog(null, "데이터베이스에서 데이터를 가져오는 중 오류가 발생했습니다.", "데이터베이스 오류", JOptionPane.ERROR_MESSAGE);
        }

        return products; // 가져온 제품 리스트 반환
    }

    /**
     * 테이블 업데이트 메서드
     * 
     * @param products 업데이트할 제품 리스트
     */
    public void updateTable(List<Product> products) {
        view.tableModel.setRowCount(0); // 기존 데이터 제거
        for (Product product : products) {
            view.tableModel.addRow(new Object[] { 
                false, // 검수 확인 체크박스 기본값 false
                product.getProduct_code(), 
                product.getMain_id(), 
                product.getProduct_name() 
            });
        }
    }

    /**
     * 검수 확인된 항목 처리 메서드
     */
    private void processCheckedItems() {
        DefaultTableModel model = (DefaultTableModel) view.table.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            boolean isSelected = (Boolean) model.getValueAt(i, 0);
            if (isSelected) {
                // 선택된 항목 정보 가져오기
                String productcode = (String) model.getValueAt(i, 1);
                String mainid = (String) model.getValueAt(i, 2);
                String productName = (String) model.getValueAt(i, 3);

                // 선택된 항목 정보 콘솔에 출력
                System.out.println("검수완료: [제품번호: " + productcode + ", 품목: " + mainid + ", 제품명: " + productName + "]");
            }
        }
    }

    /**
     * 불량 처리 다이얼로그 표시 메서드
     */
    private void showDefectDialog() {
        DefaultTableModel model = (DefaultTableModel) view.table.getModel(); // 테이블 모델 가져오기
        JPanel defectPanel = new JPanel(new GridBagLayout()); // 불량 처리 다이얼로그 패널 생성
        GridBagConstraints gbc = new GridBagConstraints(); // GridBagLayout 사용을 위한 constraints 생성
        gbc.insets = new Insets(5, 5, 5, 5); // 패딩 설정

        String[] defectOptions = { "환불", "반품", "폐기" }; // 불량 처리 옵션 배열
        JLabel defectLabel = new JLabel("불량 처리 옵션:"); // 라벨 생성
        JComboBox<String> defectComboBox = new JComboBox<>(defectOptions); // 불량 처리 옵션 콤보박스 생성

        gbc.gridx = 0;
        gbc.gridy = 0;
        defectPanel.add(defectLabel, gbc); // 불량 처리 옵션 라벨 패널에 추가

        gbc.gridx = 1;
        gbc.gridy = 0;
        defectPanel.add(defectComboBox, gbc); // 불량 처리 옵션 콤보박스 패널에 추가

        JLabel reasonLabel = new JLabel("사유:"); // 사유 라벨 생성
        JTextArea reasonArea = new JTextArea(5, 20); // 사유 입력 텍스트 에어리어 생성

        gbc.gridx = 0;
        gbc.gridy = 1;
        defectPanel.add(reasonLabel, gbc); // 사유 라벨 패널에 추가

        gbc.gridx = 1;
        gbc.gridy = 1;
        defectPanel.add(reasonArea, gbc); // 사유 입력 텍스트 에어리어 패널에 추가

        // 불량 처리 다이얼로그 표시
        int option = JOptionPane.showConfirmDialog(view, defectPanel, "불량 처리", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        // OK 버튼 클릭 시 처리
        if (option == JOptionPane.OK_OPTION) {
            String defectOption = (String) defectComboBox.getSelectedItem(); // 선택된 불량 처리 옵션 가져오기
            String reason = reasonArea.getText(); // 입력된 사유 가져오기
            processDefectedItems(defectOption, reason); // 불량 처리 항목 처리 메서드 호출
        }
    }

    /**
     * 불량 처리 항목 처리 메서드
     * 
     * @param defectOption 불량 처리 옵션
     * @param reason       불량 처리 사유
     */
    private void processDefectedItems(String defectOption, String reason) {
        DefaultTableModel model = (DefaultTableModel) view.table.getModel(); // 테이블 모델 가져오기
        StringBuilder message = new StringBuilder(); // 메시지를 저장할 StringBuilder 생성

        // 테이블 모든 행에 대해 처리
        for (int i = 0; i < model.getRowCount(); i++) {
            boolean isSelected = (Boolean) model.getValueAt(i, 0); // 검수 확인 체크박스 상태 가져오기
            if (isSelected) {
                // 선택된 항목 정보 가져오기
                String productcode = (String) model.getValueAt(i, 1);
                String mainid = (String) model.getValueAt(i, 2);
                String productName = (String) model.getValueAt(i, 3);

                // 메시지에 불량 처리 정보 추가
                message.append("불량확인: [제품번호: ").append(productcode).append(", 품목: ").append(mainid)
                        .append(", 제품명: ").append(productName).append(", 처리옵션: ").append(defectOption)
                        .append(", 사유: ").append(reason).append("]\n");
            }
        }

        // 불량 처리 완료 메시지 다이얼로그 표시
        JOptionPane.showMessageDialog(view, message.toString(), "불량 처리 완료", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * 날짜 형식을 포맷하는 메서드
     * 
     * @param date 날짜 객체
     * @return 포맷된 날짜 문자열
     */
    private String formatDate(java.util.Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 날짜 포맷 설정
        return sdf.format(date); // 포맷된 날짜 문자열 반환
    }

    @Override
    public String toString() {
        return "제품검수"; // 객체를 문자열로 표현하는 메서드
    }
}
