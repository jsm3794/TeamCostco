package main.java.com.teamcostco.view.panels;

import javax.swing.*;
import main.java.com.teamcostco.model.database.DatabaseUtil;
import main.java.com.teamcostco.model.Product;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JPanel;
import java.awt.Rectangle;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import main.utils.Constants;

import javax.swing.JTextField;
import javax.swing.JButton;

public class ProductDetailPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel CategoryLabel;
	private JTextField textField;
	private JLabel productCode;
	private JLabel largeCategory;
	private JLabel productName;
	private JLabel sellingPrice;
	private JLabel purchasePrice;
	private JLabel properInventory;
	private JLabel currentInventory;
	private JLabel warehousingDate;
	private JLabel loadingPosition;


	public ProductDetailPanel() {
		setBounds(new Rectangle(0, 0, 480, 640));
		setLayout(null);

		JLabel CodeLabel = new JLabel("상품코드");
		CodeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		CodeLabel.setBounds(12, 44, 78, 28);
		add(CodeLabel);

		JLabel CategoryLabel = new JLabel("대분류");
		CategoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
		CategoryLabel.setBounds(12, 82, 78, 28);
		add(CategoryLabel);

		JLabel nameLabel = new JLabel("상품명");
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setBounds(12, 120, 78, 28);
		add(nameLabel);

		productCode = new JLabel("상품코드 보여질 예정");
		productCode.setHorizontalAlignment(SwingConstants.CENTER);
		productCode.setBounds(102, 44, 366, 28);
		add(productCode);

		largeCategory = new JLabel("대분류 보여질 예정");
		largeCategory.setHorizontalAlignment(SwingConstants.CENTER);
		largeCategory.setBounds(102, 82, 366, 28);
		add(largeCategory);

		productName = new JLabel("상품명 보여질 예정");
		productName.setHorizontalAlignment(SwingConstants.CENTER);
		productName.setBounds(102, 120, 366, 28);
		add(productName);

		JLabel sellingLabel = new JLabel("판매가");
		sellingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		sellingLabel.setBounds(243, 173, 225, 28);
		add(sellingLabel);

		JLabel purchaseLabel = new JLabel("매입가");
		purchaseLabel.setHorizontalAlignment(SwingConstants.CENTER);
		purchaseLabel.setBounds(12, 173, 225, 28);
		add(purchaseLabel);

		sellingPrice = new JLabel("판매가 보여질 예정");
		sellingPrice.setHorizontalAlignment(SwingConstants.CENTER);
		sellingPrice.setBounds(12, 211, 225, 28);
		add(sellingPrice);

		purchasePrice = new JLabel("매입가 보여질 예정");
		purchasePrice.setHorizontalAlignment(SwingConstants.CENTER);
		purchasePrice.setBounds(243, 211, 225, 28);
		add(purchasePrice);

		JLabel properInventoryLabel = new JLabel("적정재고");
		properInventoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
		properInventoryLabel.setBounds(12, 257, 225, 28);
		add(properInventoryLabel);

		JLabel currentInventoryLabel = new JLabel("현재재고");
		currentInventoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
		currentInventoryLabel.setBounds(243, 257, 225, 28);
		add(currentInventoryLabel);

		properInventory = new JLabel("적정재고 보여질 예정");
		properInventory.setHorizontalAlignment(SwingConstants.CENTER);
		properInventory.setBounds(12, 295, 225, 28);
		add(properInventory);

		currentInventory = new JLabel("현재재고 보여질 예정");
		currentInventory.setHorizontalAlignment(SwingConstants.CENTER);
		currentInventory.setBounds(243, 295, 225, 28);
		add(currentInventory);

		JLabel warehousingDateLabel = new JLabel("입고날짜");
		warehousingDateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		warehousingDateLabel.setBounds(12, 341, 78, 28);
		add(warehousingDateLabel);

		warehousingDate = new JLabel("입고날짜 보여질 예정");
		warehousingDate.setHorizontalAlignment(SwingConstants.CENTER);
		warehousingDate.setBounds(102, 341, 366, 28);
		add(warehousingDate);

		JLabel positionLabel = new JLabel("적재위치");
		positionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		positionLabel.setBounds(12, 385, 78, 28);
		add(positionLabel);

		loadingPosition = new JLabel("적재위치 보여질 예정");
		loadingPosition.setHorizontalAlignment(SwingConstants.CENTER);
		loadingPosition.setBounds(102, 385, 366, 28);
		add(loadingPosition);

		textField = new JTextField();
		textField.setBounds(12, 437, 456, 128);
		add(textField);
		textField.setColumns(10);

		JButton btnNewButton = new JButton("조정요청");
		btnNewButton.setBounds(12, 575, 456, 55);
		btnNewButton.setBackground(Constants.BUTTON_BACKGROUND_COLOR);
		add(btnNewButton);

	}

	public JTextField getTextField() {
		return textField;
	}
	//상품코드
	public JLabel getProductCode() {
		return productCode;
	}

	//상품이름
	public JLabel getProductName() {
		return productName;
	}
	//판매가
	public JLabel getSellingPrice() {
		return sellingPrice;
	}
	//구매가
	public JLabel getPurchasePrice() {
		return purchasePrice;
	}
	//적정재고
	public JLabel getProperInventory() {
		return properInventory;
	}
	//현재재고
	public JLabel getCurrentInventory() {
		return currentInventory;
	}
	//입고날짜
	public JLabel getWarehousingDate() {
		return warehousingDate;
	}
	//적재위치
	public JLabel getloadingPosition() {
		return loadingPosition;
	}
	//대분류
	public JLabel getCategoryLabel() {
		return CategoryLabel;
		
	}
	
	public JTextField getBtnAdjustRequest() {
		// TODO Auto-generated method stub
		return null;
	}


}
