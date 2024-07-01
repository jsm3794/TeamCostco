package main.java.com.teamcostco.view.panels;

import javax.swing.*;
import java.awt.*;

public class OrderListPanel extends JPanel {

    public JTextField startDateField;
    public JTextField endDateField;
    public JTextField itemNumberField;
    public JTextField supplierField;
    
    public JPanel resultPanel;
    public JPanel searchPanel;
    public JLabel dateLabel;
    public JPanel datePanel;
    public ButtonGroup buttonGroup;
    public JRadioButton waitingwarehousing;
    public JRadioButton completedwarehousing;

    public JLabel supplierLabel;
    public JButton searchButton;

    public OrderListPanel() {
        setLayout(new BorderLayout());
        initializeComponents();
        setupLayout();
    }

    private void initializeComponents() {
        startDateField = new JTextField(11);
        endDateField = new JTextField(11);
        itemNumberField = new JTextField(10);
        supplierField = new JTextField(10);

        resultPanel = new JPanel();
        searchPanel = new JPanel(new GridBagLayout());
        datePanel = new JPanel(new FlowLayout());

        dateLabel = new JLabel("발주일자");
        supplierLabel = new JLabel("거래처명");

        buttonGroup = new ButtonGroup();
        waitingwarehousing = new JRadioButton("입고대기");
        completedwarehousing = new JRadioButton("입고완료");

        searchButton = new JButton("검색");
    }

    private void setupLayout() {
        setupSearchPanel();
        setupResultPanel();

        add(searchPanel, BorderLayout.NORTH);
        add(new JScrollPane(resultPanel), BorderLayout.CENTER);
    }

    private void setupSearchPanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        addToSearchPanel(dateLabel, gbc, 0, 0);
        setupDatePanel();
        addToSearchPanel(datePanel, gbc, 1, 0);

        addToSearchPanel(new JLabel("품번"), gbc, 0, 1);
        addToSearchPanel(itemNumberField, gbc, 1, 1);

        addToSearchPanel(supplierLabel, gbc, 0, 2);
        addToSearchPanel(supplierField, gbc, 1, 2);

        setupRadioButtons(gbc);
        setupSearchButton(gbc);
    }

    private void setupDatePanel() {
        datePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 0, 0);

        gbc.weightx = 0.5;
        gbc.gridx = 0;
        startDateField.setHorizontalAlignment(SwingConstants.CENTER);
        datePanel.add(startDateField, gbc);

        gbc.weightx = 0;
        gbc.gridx = 1;
        datePanel.add(new JLabel("~"), gbc);

        gbc.weightx = 0.5;
        gbc.gridx = 2;
        endDateField.setHorizontalAlignment(SwingConstants.CENTER);
        datePanel.add(endDateField, gbc);
    }

    private void setupRadioButtons(GridBagConstraints gbc) {
        // 라디오 버튼을 위한 새 패널 생성 (오른쪽 정렬)
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        
        waitingwarehousing.setSelected(true);
        buttonGroup.add(waitingwarehousing);
        buttonGroup.add(completedwarehousing);

        // 라디오 버튼을 새 패널에 추가
        radioPanel.add(waitingwarehousing);
        radioPanel.add(completedwarehousing);

        // 라디오 패널을 위한 GridBagConstraints 수정
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;  // 오른쪽 정렬
        gbc.fill = GridBagConstraints.HORIZONTAL;  // 수평으로 채우기
        gbc.weightx = 1.0;  // 수평 여유 공간 모두 사용

        searchPanel.add(radioPanel, gbc);
    }

    private void setupSearchButton(GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;
        searchPanel.add(searchButton, gbc);
        searchButton.setBackground(new Color(6, 127, 196));
        searchButton.setForeground(Color.WHITE);
    }

    private void addToSearchPanel(Component component, GridBagConstraints gbc, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        searchPanel.add(component, gbc);
    }

    private void setupResultPanel() {
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
    }
}