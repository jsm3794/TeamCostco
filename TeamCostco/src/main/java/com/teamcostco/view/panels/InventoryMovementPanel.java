package main.java.com.teamcostco.view.panels;

import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import main.utils.Constants;

import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

public class InventoryMovementPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textFieldProductName;
	private JTextField textFieldOutgoingQuantity;
	private JComboBox<String> comboBoxSmallCategory;
	private JButton searchBtn; 
	private JButton movementBtn;
	private JLabel currentQuantity;

	/**
	 * Create the panel.
	 */
	public InventoryMovementPanel() {

		setBounds(new Rectangle(0, 0, 480, 640));
		setLayout(null);

		JLabel smallClassificationLabel = new JLabel("검색결과");
		smallClassificationLabel.setHorizontalAlignment(SwingConstants.CENTER);
		smallClassificationLabel.setBounds(12, 159, 90, 40);
		add(smallClassificationLabel);

		comboBoxSmallCategory = new JComboBox<>();
		comboBoxSmallCategory.setBounds(114, 159, 354, 40);
		add(comboBoxSmallCategory);

		movementBtn = new JButton("확인");
		movementBtn.setForeground(new Color(255, 255, 255));
		movementBtn.setBackground(new Color(6, 127, 196));
		movementBtn.setBounds(12, 503, 456, 67);
		add(movementBtn);

		JLabel outgoingQuantityLabel = new JLabel("출고수량");
		outgoingQuantityLabel.setHorizontalAlignment(SwingConstants.CENTER);
		outgoingQuantityLabel.setBounds(276, 350, 174, 35);
		add(outgoingQuantityLabel);

		JLabel currentQuantityLabel = new JLabel("현재재고");
		currentQuantityLabel.setHorizontalAlignment(SwingConstants.CENTER);
		currentQuantityLabel.setBounds(30, 350, 174, 35);
		add(currentQuantityLabel);

		textFieldOutgoingQuantity = new JTextField();
		textFieldOutgoingQuantity.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldOutgoingQuantity.setBounds(276, 410, 174, 41);
		add(textFieldOutgoingQuantity);
		textFieldOutgoingQuantity.setColumns(10);

		JLabel productLabel = new JLabel("상품명");
		productLabel.setHorizontalAlignment(SwingConstants.CENTER);
		productLabel.setBounds(12, 83, 90, 40);
		add(productLabel);

		textFieldProductName = new JTextField();
		textFieldProductName.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldProductName.setColumns(10);
		textFieldProductName.setBounds(114, 84, 241, 40);
		add(textFieldProductName);

		currentQuantity = new JLabel();
		currentQuantity.setBorder(new LineBorder(new Color(172, 172, 172)));
		currentQuantity.setBackground(new Color(255, 255, 255));
		currentQuantity.setOpaque(true);
		currentQuantity.setHorizontalAlignment(SwingConstants.CENTER);
		currentQuantity.setBounds(30, 410, 174, 41);
		add(currentQuantity);

		searchBtn = new JButton("검색");
		searchBtn.setBackground(Constants.BUTTON_BACKGROUND_COLOR);
		searchBtn.setForeground(Constants.BUTTON_FOREGROUND_COLOR);
		searchBtn.setBounds(367, 83, 101, 40);
		add(searchBtn);
	}

	public JTextField getTextFieldProductName() {
		return textFieldProductName;
	}

	public JTextField getTextFieldOutgoingQuantity() {
		return textFieldOutgoingQuantity;
	}

	public JComboBox<String> getComboBoxSmallCategory() {
		return comboBoxSmallCategory;
	}

	public JButton getSearchBtn() {
		return searchBtn;
	}
	
	public JButton getMovementBtn() {
		return movementBtn;
	}

	public JLabel getCurrentQuantity() {
		return currentQuantity;
	}
}
