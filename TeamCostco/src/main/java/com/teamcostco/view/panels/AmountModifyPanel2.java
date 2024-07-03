package main.java.com.teamcostco.view.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import main.utils.Constants;

public class AmountModifyPanel2 extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel productIdLabel, productCodeLabel, productNameLabel, mainIdLabel, mediumIdLabel, smallIdLabel;
	private JLabel purchasePriceLabel, sellingPriceLabel, appropriateInventoryLabel, currentInventoryLabel,
			activeInventoryLabel;

	private JTextField defectAmountField;

	private JComboBox<String> adjustmentReasonComboBox;
	private JButton btnAdjustRequest;

	private static String[] modification_reason = new String[] { "DAM001 - 파손 (Damage)", "DEF002 - 결함 (Defective)",
			"EXP003 - 유통기한 경과 (Expired)", "MIS004 - 잘못된 사양 (Mismatched Specification)", "CON005 - 오염 (Contaminated)", };

	public AmountModifyPanel2() {
		setLayout(new BorderLayout());
		setBounds(new Rectangle(0, 0, 480, 640));

		initializeLabels();

		JPanel infoPanel = createInfoPanel();
		JScrollPane scrollPane = new JScrollPane(infoPanel);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());

		JPanel adjustmentPanel = createAdjustmentPanel();

		add(scrollPane, BorderLayout.CENTER);
		add(adjustmentPanel, BorderLayout.SOUTH);
	}

	private void initializeLabels() {
		productIdLabel = new JLabel();
		productCodeLabel = new JLabel();
		productNameLabel = new JLabel();
		mainIdLabel = new JLabel();
		mediumIdLabel = new JLabel();
		smallIdLabel = new JLabel();
		purchasePriceLabel = new JLabel();
		sellingPriceLabel = new JLabel();
		appropriateInventoryLabel = new JLabel();
		currentInventoryLabel = new JLabel();
		activeInventoryLabel = new JLabel();
	}

	private JPanel createInfoPanel() {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(4, 4, 3, 4);

		String[] labels = { "상품 ID:", "상품코드:", "상품명:", "대분류:", "중분류:", "소분류:", "매입가:", "판매가:", "적정수량:", "대기수량:",
				"활성수량:" };
		JComponent[] valueComponents = { productIdLabel, productCodeLabel, productNameLabel, mainIdLabel, mediumIdLabel,
				smallIdLabel, purchasePriceLabel, sellingPriceLabel, appropriateInventoryLabel, currentInventoryLabel,
				activeInventoryLabel };

		for (int i = 0; i < labels.length; i++) {
			gbc.gridy = i;

			gbc.gridx = 0;
			gbc.weightx = 0.3;
			panel.add(new JLabel(labels[i]), gbc);

			gbc.gridx = 1;
			gbc.weightx = 0.7;
			JComponent component = valueComponents[i];
			component.setBorder(
					BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
							BorderFactory.createEmptyBorder(2, 2, 2, 2)));
			panel.add(component, gbc);

		}

		return panel;
	}

	private JPanel createAdjustmentPanel() {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);

		// 조정사유
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.anchor = GridBagConstraints.WEST;
		JLabel adjustmentReasonLabel = new JLabel("조정사유:");
		adjustmentReasonLabel.setPreferredSize(new Dimension(80, 25));
		panel.add(adjustmentReasonLabel, gbc);

		gbc.gridx = 1;
		gbc.weightx = 1;
		adjustmentReasonComboBox = new JComboBox<>(modification_reason);
		panel.add(adjustmentReasonComboBox, gbc);

		// 차감수량
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0;
		JLabel deductionAmountLabel = new JLabel("차감수량:");
		deductionAmountLabel.setPreferredSize(new Dimension(80, 25));
		panel.add(deductionAmountLabel, gbc);

		gbc.gridx = 1;
		gbc.weightx = 1;
		defectAmountField = new JTextField();
		panel.add(defectAmountField, gbc);

		// 조정요청 버튼
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.weightx = 1;
		btnAdjustRequest = new JButton("조정요청");
		btnAdjustRequest.setBackground(Constants.BUTTON_BACKGROUND_COLOR);
		btnAdjustRequest.setForeground(Constants.BUTTON_FOREGROUND_COLOR);
		btnAdjustRequest.setPreferredSize(new Dimension(0, 25));
		panel.add(btnAdjustRequest, gbc);

		return panel;
	}

	public JTextField getDefectAmountField() {
		return defectAmountField;
	}

	public void setDefectAmountField(JTextField deductionAmountField) {
		this.defectAmountField = deductionAmountField;
	}

	public JLabel getProductIdLabel() {
		return productIdLabel;
	}

	public void setProductIdLabel(JLabel productIdLabel) {
		this.productIdLabel = productIdLabel;
	}

	public JLabel getProductCodeLabel() {
		return productCodeLabel;
	}

	public void setProductCodeLabel(JLabel productCodeLabel) {
		this.productCodeLabel = productCodeLabel;
	}

	public JLabel getProductNameLabel() {
		return productNameLabel;
	}

	public void setProductNameLabel(JLabel productNameLabel) {
		this.productNameLabel = productNameLabel;
	}

	public JLabel getMainIdLabel() {
		return mainIdLabel;
	}

	public void setMainIdLabel(JLabel mainIdLabel) {
		this.mainIdLabel = mainIdLabel;
	}

	public JLabel getMediumIdLabel() {
		return mediumIdLabel;
	}

	public void setMediumIdLabel(JLabel mediumIdLabel) {
		this.mediumIdLabel = mediumIdLabel;
	}

	public JLabel getSmallIdLabel() {
		return smallIdLabel;
	}

	public void setSmallIdLabel(JLabel smallIdLabel) {
		this.smallIdLabel = smallIdLabel;
	}

	public JLabel getPurchasePriceLabel() {
		return purchasePriceLabel;
	}

	public void setPurchasePriceLabel(JLabel purchasePriceLabel) {
		this.purchasePriceLabel = purchasePriceLabel;
	}

	public JLabel getSellingPriceLabel() {
		return sellingPriceLabel;
	}

	public void setSellingPriceLabel(JLabel sellingPriceLabel) {
		this.sellingPriceLabel = sellingPriceLabel;
	}

	public JLabel getAppropriateInventoryLabel() {
		return appropriateInventoryLabel;
	}

	public void setAppropriateInventoryLabel(JLabel appropriateInventoryLabel) {
		this.appropriateInventoryLabel = appropriateInventoryLabel;
	}

	public JLabel getCurrentInventoryLabel() {
		return currentInventoryLabel;
	}

	public void setCurrentInventoryLabel(JLabel currentInventoryLabel) {
		this.currentInventoryLabel = currentInventoryLabel;
	}

	public JLabel getActiveInventoryLabel() {
		return activeInventoryLabel;
	}

	public void setActiveInventoryLabel(JLabel activeInventoryLabel) {
		this.activeInventoryLabel = activeInventoryLabel;
	}

	public JComboBox<String> getAdjustmentReasonComboBox() {
		return adjustmentReasonComboBox;
	}

	public void setAdjustmentReasonComboBox(JComboBox<String> adjustmentReasonComboBox) {
		this.adjustmentReasonComboBox = adjustmentReasonComboBox;
	}

	public JButton getBtnAdjustRequest() {
		return btnAdjustRequest;
	}

	public void setBtnAdjustRequest(JButton btnAdjustRequest) {
		this.btnAdjustRequest = btnAdjustRequest;
	}

	public static String[] getModification_reason() {
		return modification_reason;
	}

	public static void setModification_reason(String[] modification_reason) {
		AmountModifyPanel2.modification_reason = modification_reason;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}