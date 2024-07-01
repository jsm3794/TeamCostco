package main.java.com.teamcostco.view.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import main.utils.Constants;
import main.utils.CustomNumberFormatter;

public class WarehouseReceivingPanel extends JPanel {

	public static final long serialVersionUID = 1L;
	public JLabel processingDateLabel;
	public JButton saveButton;
	public JLabel orderNumberLabel;
	public JLabel categoryLabel;
	public JLabel productNameLabel;
	public JLabel warehouseLabel;
	public JLabel orderQuantityLabel;
	public JLabel remainingQuantityLabel;
	public JLabel currentDateLabel;
	public JLabel productIdLabel;
	public JLabel order_quantity;
	public JLabel remaining_capacity;
	public JFormattedTextField textField;

	public WarehouseReceivingPanel() {
		setLayout(null);
		setSize(new Dimension(480, 640));
		setBackground(new Color(240, 240, 240)); // 배경색 설정

		// 공통 스타일 메서드
		Consumer<JLabel> stylizeLabel = (label) -> {
			label.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
			label.setForeground(new Color(50, 50, 50));
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
		};

		Consumer<JLabel> stylizeDataLabel = (label) -> {
			label.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
			label.setForeground(new Color(80, 80, 80));
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)),
					BorderFactory.createEmptyBorder(0, 10, 0, 0)));
		};

		processingDateLabel = createLabel("처리일자", 12, 20, 83, 45);
		stylizeLabel.accept(processingDateLabel);

		saveButton = new JButton("저장");
		saveButton.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		saveButton.setBackground(Constants.BUTTON_BACKGROUND_COLOR);
		saveButton.setForeground(Constants.BUTTON_FOREGROUND_COLOR);
		saveButton.setBounds(0, 521, 480, 69);
		saveButton.setBorder(BorderFactory.createEmptyBorder());
		add(saveButton);

		orderNumberLabel = createLabel("발주번호", 12, 90, 83, 45);
		stylizeLabel.accept(orderNumberLabel);

		categoryLabel = createLabel("카테고리", 12, 160, 83, 45);
		stylizeLabel.accept(categoryLabel);

		productNameLabel = createLabel("상품명", 12, 230, 83, 45);
		stylizeLabel.accept(productNameLabel);

		orderQuantityLabel = createLabel("발주수량", 12, 300, 83, 45);
		stylizeLabel.accept(orderQuantityLabel);

		remainingQuantityLabel = createLabel("잔량", 12, 440, 83, 45);
		stylizeLabel.accept(remainingQuantityLabel);

		currentDateLabel = createLabel("", 125, 21, 329, 45);
		stylizeDataLabel.accept(currentDateLabel);

		productIdLabel = createLabel("", 125, 90, 329, 45);
		stylizeDataLabel.accept(productIdLabel);

		categoryLabel = createLabel("", 125, 160, 329, 45);
		stylizeDataLabel.accept(categoryLabel);

		productNameLabel = createLabel("", 125, 230, 329, 45);
		stylizeDataLabel.accept(productNameLabel);

		order_quantity = createLabel("", 125, 300, 329, 45);
		stylizeDataLabel.accept(order_quantity);

		remaining_capacity = createLabel("", 125, 440, 329, 45);
		stylizeDataLabel.accept(remaining_capacity);

		JLabel receivingQuantityLabel = createLabel("입고수량", 12, 370, 83, 45);
		stylizeLabel.accept(receivingQuantityLabel);

		CustomNumberFormatter cnf = CustomNumberFormatter.createFormatter();

		textField = new JFormattedTextField(cnf);
		textField.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setBounds(107, 370, 347, 45);
		textField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)),
				BorderFactory.createEmptyBorder(0, 10, 0, 10)));
		add(textField);
		textField.setColumns(10);
	}

	public JTextField getTextField() {
		return textField;
	}

	private JLabel createLabel(String text, int x, int y, int width, int height) {
		JLabel label = new JLabel(text);
		label.setBounds(x, y, width, height);
		add(label);
		return label;
	}

	public JButton getSaveButton() {
		return saveButton;
	}

	public JLabel getProcessingDateLabel() {
		return processingDateLabel;
	}

	public JLabel getProductIdLabel() {
		return productIdLabel;
	}

	public JLabel getCategoryLabel() {
		return categoryLabel;
	}

	public JLabel getProductNameLabel() {
		return productNameLabel;
	}

	public JLabel getWarehouseLabel() {
		return warehouseLabel;
	}

	public JLabel getOrderQuantityLabel() {
		return orderQuantityLabel;
	}

	public JLabel getRemainingQuantityLabel() {
		return remainingQuantityLabel;
	}
}
