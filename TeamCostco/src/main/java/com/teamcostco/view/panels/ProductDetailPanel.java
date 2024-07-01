package main.java.com.teamcostco.view.panels;

import javax.swing.*;
import main.utils.Constants;
import java.awt.*;

public class ProductDetailPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel productIdLabel, productCodeLabel, productNameLabel, mainIdLabel, mediumIdLabel, smallIdLabel;
	private JLabel purchasePriceLabel, sellingPriceLabel, appropriateInventoryLabel, currentInventoryLabel,
			activeInventoryLabel;

	private JTextField adjustmentTextField;
	private JButton btnAdjustRequest;

	public ProductDetailPanel() {
		setLayout(new BorderLayout());
		setBounds(new Rectangle(0, 0, 480, 640));

		initializeLabels(); // 라벨 초기화 메소드 호출

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
		gbc.insets = new Insets(5, 5, 5, 5);

		String[] labels = { "상품 ID:", "상품코드:", "상품명:", "대분류:", "중분류:", "소분류:", "매입가:", "판매가:", "적정재고:", "현재재고:",
				"활성재고:" };
		JLabel[] valueLabels = { productIdLabel, productCodeLabel, productNameLabel, mainIdLabel, mediumIdLabel,
				smallIdLabel, purchasePriceLabel, sellingPriceLabel, appropriateInventoryLabel, currentInventoryLabel,
				activeInventoryLabel };

		for (int i = 0; i < labels.length; i++) {
			gbc.gridy = i;

			gbc.gridx = 0;
			gbc.weightx = 0.3;
			panel.add(new JLabel(labels[i]), gbc);

			gbc.gridx = 1;
			gbc.weightx = 0.7;
			valueLabels[i].setBorder(
					BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
							BorderFactory.createEmptyBorder(2, 2, 2, 2)));
			panel.add(valueLabels[i], gbc);
		}

		return panel;
	}

	private JPanel createAdjustmentPanel() {
	    JPanel panel = new JPanel(new GridBagLayout());
	    panel.setBorder(BorderFactory.createCompoundBorder(
	        BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY),
	        BorderFactory.createEmptyBorder(10, 10, 10, 10)
	    ));

	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.fill = GridBagConstraints.HORIZONTAL;
	    gbc.insets = new Insets(5, 5, 5, 5);

	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.gridwidth = 1;  // 전체 너비를 사용하도록 변경
	    gbc.weightx = 1.0;  // 수평 방향으로 모든 여유 공간을 사용

	    btnAdjustRequest = new JButton("조정요청");
	    btnAdjustRequest.setBackground(Constants.BUTTON_BACKGROUND_COLOR);
	    btnAdjustRequest.setForeground(Constants.BUTTON_FOREGROUND_COLOR);
	    btnAdjustRequest.setPreferredSize(new Dimension(0, 40));  // 버튼의 높이를 40픽셀로 설정
	    panel.add(btnAdjustRequest, gbc);

	    return panel;
	}

	private JPanel createPanel() {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		return panel;
	}

	private void addLabel(JPanel panel, String labelText) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = panel.getComponentCount();
		panel.add(new JLabel(labelText), gbc);
	}

	private void addValueLabel(JPanel panel, JLabel label) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = panel.getComponentCount();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		panel.add(label, gbc);
	}

	// Getter 메소드들
	public JTextField getAdjustmentTextField() {
		return adjustmentTextField;
	}

	public JButton getBtnAdjustRequest() {
		return btnAdjustRequest;
	}

	public JLabel getProductIdLabel() {
		return productIdLabel;
	}

	public JLabel getProductCodeLabel() {
		return productCodeLabel;
	}

	public JLabel getProductNameLabel() {
		return productNameLabel;
	}

	public JLabel getMainIdLabel() {
		return mainIdLabel;
	}

	public JLabel getMediumIdLabel() {
		return mediumIdLabel;
	}

	public JLabel getSmallIdLabel() {
		return smallIdLabel;
	}

	public JLabel getPurchasePriceLabel() {
		return purchasePriceLabel;
	}

	public JLabel getSellingPriceLabel() {
		return sellingPriceLabel;
	}

	public JLabel getAppropriateInventoryLabel() {
		return appropriateInventoryLabel;
	}

	public JLabel getCurrentInventoryLabel() {
		return currentInventoryLabel;
	}

	public JLabel getActiveInventoryLabel() {
		return activeInventoryLabel;
	}
}