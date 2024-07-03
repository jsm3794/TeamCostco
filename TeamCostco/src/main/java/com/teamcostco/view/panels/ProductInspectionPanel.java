package main.java.com.teamcostco.view.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import main.java.com.teamcostco.model.Productinspection;
import main.utils.Constants;

public class ProductInspectionPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    public JTextField productNumberField;
    public JComboBox<String> categoryComboBox;
    public JPanel resultPanel;
    public JButton checkButton;

    public List<Productinspection> allProductinspection;
    private Map<Integer, JCheckBox> checkBoxMap = new HashMap<>();

    public ProductInspectionPanel() {
        setLayout(new BorderLayout());

        // 상단 패널 (제품번호 입력 필드 및 카테고리 선택)
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(2, 1));

        productNumberField = new JTextField();
        productNumberField.setToolTipText("제품번호 입력");
        searchPanel.add(productNumberField);

        categoryComboBox = new JComboBox<String>();
        searchPanel.add(categoryComboBox);

        add(searchPanel, BorderLayout.NORTH);

        // 중앙 패널 (결과 표시)
        resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(resultPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);

        // 하단 패널 (버튼)
        JPanel buttonPanel = new JPanel();
        checkButton = new JButton("검수확인");

        checkButton.setBackground(Constants.BUTTON_BACKGROUND_COLOR);
        checkButton.setForeground(Constants.BUTTON_FOREGROUND_COLOR);
        buttonPanel.add(checkButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // 초기 버튼 상태 설정
        checkButton.setEnabled(true);
    }

    public void updateProductInspectionList(List<Productinspection> inspections) {
        resultPanel.removeAll();
        checkBoxMap.clear();
        for (Productinspection inspection : inspections) {
            JPanel entryPanel = createEntryPanel(inspection);
            resultPanel.add(entryPanel);
            resultPanel.add(Box.createRigidArea(new Dimension(0, 10))); // 패널 사이 간격
        }
        resultPanel.revalidate();
        resultPanel.repaint();
    }

    private JPanel createEntryPanel(Productinspection data) {
        JPanel entryPanel = new JPanel();
        entryPanel.setLayout(new BorderLayout());

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        // 폰트 크기 설정
        Font labelFont = new Font("맑은 고딕", Font.PLAIN, 16);

        // 라벨 생성 및 설정
        JLabel productCodeLabel = new JLabel("제품코드: " + data.getProductcode());
        JLabel categoryLabel = new JLabel("카테고리: " + data.getMain_name());
        JLabel productNameLabel = new JLabel("제품명: " + data.getProduct_name());
        JLabel orderQuantityLabel = new JLabel("주문수량: " + data.getOrderquantity());
        JLabel checkQuantityLabel = new JLabel("검수수량: " + data.getCheckquantity());
        JLabel dateOfReceiptLabel = new JLabel("입고일자: " + data.getDateofreceipt());

        JLabel[] labels = { productCodeLabel, categoryLabel, productNameLabel, 
                            orderQuantityLabel, checkQuantityLabel, dateOfReceiptLabel };

        for (JLabel label : labels) {
            label.setFont(labelFont);
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            infoPanel.add(label);
        }

        // 체크박스 추가
        JCheckBox checkBox = new JCheckBox();
        checkBox.setAlignmentY(Component.TOP_ALIGNMENT);
        checkBoxMap.put(data.getPRODUCT_INSPECTION_ID(), checkBox);

        entryPanel.add(infoPanel, BorderLayout.CENTER);
        entryPanel.add(checkBox, BorderLayout.WEST);

        entryPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        entryPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, entryPanel.getPreferredSize().height));
        entryPanel.setPreferredSize(new Dimension(460, entryPanel.getPreferredSize().height));

        // 체크박스에 대한 액션 리스너 추가
        checkBox.addActionListener(e -> {
            // 체크박스 상태가 변경될 때마다 호출됨
            System.out.println("체크박스 상태 변경: " + data.getPRODUCT_INSPECTION_ID() + " - " + checkBox.isSelected());
        });

        // 패널 클릭 이벤트 처리
        entryPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() != checkBox) {
                    checkBox.setSelected(!checkBox.isSelected());
                    System.out.println("패널 클릭으로 체크박스 상태 변경: " + data.getProductcode() + " - " + checkBox.isSelected());
                }
            }
        });

        return entryPanel;
    }

    public List<Integer> getSelectedProductCodes() {
        List<Integer> selectedCodes = new ArrayList<>();
        for (Entry<Integer, JCheckBox> entry : checkBoxMap.entrySet()) {
            if (entry.getValue().isSelected()) {
                selectedCodes.add(entry.getKey());
                System.out.println("선택된 제품 코드: " + entry.getKey()); // 디버깅 출력
            }
        }
        System.out.println("총 선택된 제품 수: " + selectedCodes.size()); // 디버깅 출력
        return selectedCodes;
    }
    
    public void printCheckBoxStatus() {
        System.out.println("현재 체크박스 상태:");
        for (Entry<Integer, JCheckBox> entry : checkBoxMap.entrySet()) {
            System.out.println("제품 코드: " + entry.getKey() + ", 선택 여부: " + entry.getValue().isSelected());
        }
    }
    
}
