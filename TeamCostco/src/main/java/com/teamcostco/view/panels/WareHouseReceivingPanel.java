package main.java.com.teamcostco.view.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import main.utils.Constants;

public class WareHouseReceivingPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    public JPanel dataPanel;
    public JButton saveButton;
    public JTextField receivingQuantityField;

    public WareHouseReceivingPanel() {
        setLayout(new BorderLayout());

        // 데이터 표시 패널 설정
        dataPanel = new JPanel();
        dataPanel.setLayout(new GridLayout(0, 1));
        dataPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
        add(dataPanel, BorderLayout.CENTER);

        // 입고수량 입력 필드
        receivingQuantityField = new JTextField();
        receivingQuantityField.setFont(new Font("맑은 고딕", Font.PLAIN, 16));

        // 하단 패널 설정
        JPanel bottomPanel = new JPanel(new BorderLayout());

        // 저장 버튼 설정
        saveButton = new JButton("저장");
        saveButton.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
        saveButton.setFocusPainted(false);
        saveButton.setBackground(Constants.BUTTON_BACKGROUND_COLOR);
        saveButton.setForeground(Color.WHITE);
        saveButton.setBorderPainted(false);
        bottomPanel.add(saveButton, BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.SOUTH);
    }

	public JPanel getDataPanel() {
		return dataPanel;
	}

	public void setDataPanel(JPanel dataPanel) {
		this.dataPanel = dataPanel;
	}

	public JButton getSaveButton() {
		return saveButton;
	}

	public void setSaveButton(JButton saveButton) {
		this.saveButton = saveButton;
	}

	public JTextField getReceivingQuantityField() {
		return receivingQuantityField;
	}

	public void setReceivingQuantityField(JTextField receivingQuantityField) {
		this.receivingQuantityField = receivingQuantityField;
	}
    
    
}