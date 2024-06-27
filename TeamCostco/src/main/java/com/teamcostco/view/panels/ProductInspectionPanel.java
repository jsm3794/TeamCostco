package main.java.com.teamcostco.view.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import main.java.com.teamcostco.model.Product;
import main.java.com.teamcostco.model.database.DatabaseUtil;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductInspectionPanel extends JPanel {
    public JTextField productNumberField; // 제품 번호 입력 필드
    public JComboBox<String> categoryComboBox; // 카테고리 선택 콤보박스
    public JTable table; // 제품 목록 표시 테이블
    public DefaultTableModel tableModel; // 테이블 모델
    public JButton checkButton; // 검수 확인 버튼
    public JButton defectButton; // 불량 제품 버튼

    public List<Product> allProducts; // 전체 제품 목록을 저장할 리스트

    public ProductInspectionPanel() {
        setLayout(new BorderLayout());

        // 상단 패널 (제품번호 입력 필드 및 카테고리 선택)
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(2, 1));

        productNumberField = new JTextField();
        productNumberField.setToolTipText("제품번호 입력");
        searchPanel.add(productNumberField);

        // 카테고리 목록을 데이터베이스에서 가져와서 콤보박스에 설정
        categoryComboBox = new JComboBox<String>();
        searchPanel.add(categoryComboBox);

        add(searchPanel, BorderLayout.NORTH); // 상단 패널을 BorderLayout의 NORTH에 추가

        // 테이블 초기화 및 설정
        String[] columnNames = {"검수확인", "제품번호", "품목", "제품명"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return Boolean.class; // 첫 번째 열(검수확인)은 체크박스 형태로 설정
                }
                return super.getColumnClass(columnIndex);
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0; // 첫 번째 열(검수확인)만 수정 가능하도록 설정
            }
        };
        table = new JTable(tableModel); // 테이블 모델을 기반으로 테이블 생성

        // 테이블을 스크롤 패널에 넣고 BorderLayout의 CENTER에 추가
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // 하단 패널 (버튼)
        JPanel buttonPanel = new JPanel();
        checkButton = new JButton("검수확인");
        defectButton = new JButton("불량확인");

        buttonPanel.add(checkButton);
        buttonPanel.add(defectButton);
        add(buttonPanel, BorderLayout.SOUTH); // 하단 패널을 BorderLayout의 SOUTH에 추가

        // 초기 버튼 상태 설정
        checkButton.setEnabled(true); // 검수확인 버튼은 초기에 활성화 상태로 설정
    }

 
}
