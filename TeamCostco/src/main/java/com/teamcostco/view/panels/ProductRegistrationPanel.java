package main.java.com.teamcostco.view.panels;


import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import main.java.com.teamcostco.model.database.DatabaseUtil;
import main.java.com.teamcostco.view.textfields.JPlaceholderTextField;

public class ProductRegistrationPanel extends JPanel {

    public static final long serialVersionUID = 1L;
    private JTextField textFieldProductCode;
    private JTextField textFieldProductName;
    private JTextField textFieldPurchasePrice;
    private JTextField textFieldSellingPrice;
    private JComboBox<String> comboBoxLargeCategory;
    private JComboBox<String> comboBoxMediumCategory;
    private JComboBox<String> comboBoxSmallCategory;
    private JButton initializationBtn;
    private JButton productRegistrationBtn;
    
    /**
     * Create the panel.
     */
    public ProductRegistrationPanel() {
    	
        setBounds(new Rectangle(0, 0, 480, 640));
        setLayout(null);

        JLabel productCodeLabel = new JLabel("상품코드");
        productCodeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        productCodeLabel.setBounds(12, 67, 90, 40);
        add(productCodeLabel);

        textFieldProductCode = new JPlaceholderTextField("ex) PRID00000001");
        textFieldProductCode.setBounds(114, 68, 354, 40);
        add(textFieldProductCode);
        textFieldProductCode.setColumns(10);

        JLabel largeClassificationLabel = new JLabel("대분류");
        largeClassificationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        largeClassificationLabel.setBounds(12, 117, 90, 40);
        add(largeClassificationLabel);

        comboBoxLargeCategory = new JComboBox<>();
        comboBoxLargeCategory.setBounds(114, 118, 354, 40);
        add(comboBoxLargeCategory);

        JLabel middleClassificationLabel = new JLabel("중분류");
        middleClassificationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        middleClassificationLabel.setBounds(12, 167, 90, 40);
        add(middleClassificationLabel);

        comboBoxMediumCategory = new JComboBox<>();
        comboBoxMediumCategory.setBounds(114, 168, 354, 40);
        add(comboBoxMediumCategory);

        JLabel smallClassificationLabel = new JLabel("소분류");
        smallClassificationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        smallClassificationLabel.setBounds(12, 218, 90, 40);
        add(smallClassificationLabel);

        comboBoxSmallCategory = new JComboBox<>();
        comboBoxSmallCategory.setBounds(114, 218, 354, 40);
        add(comboBoxSmallCategory);

        initializationBtn = new JButton("초기화");
        initializationBtn.setForeground(new Color(255, 255, 255));
        initializationBtn.setBackground(new Color(6, 127, 196));
        initializationBtn.setBounds(12, 503, 225, 67);
        add(initializationBtn);

        productRegistrationBtn = new JButton("등록");
        productRegistrationBtn.setForeground(new Color(255, 255, 255));
        productRegistrationBtn.setBackground(new Color(6, 127, 196));
        productRegistrationBtn.setBounds(243, 503, 225, 67);
        add(productRegistrationBtn);
        
        JLabel purchasePriceLabel = new JLabel("구입가");
        purchasePriceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        purchasePriceLabel.setBounds(276, 350, 174, 35);
        add(purchasePriceLabel);
        
        JLabel sellingPriceLabel = new JLabel("판매가");
        sellingPriceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        sellingPriceLabel.setBounds(30, 350, 174, 35);
        add(sellingPriceLabel);
        
        textFieldPurchasePrice = new JTextField();
        textFieldPurchasePrice.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldPurchasePrice.setBounds(276, 410, 174, 41);
        add(textFieldPurchasePrice);
        textFieldPurchasePrice.setColumns(10);
        
        textFieldSellingPrice = new JTextField();
        textFieldSellingPrice.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldSellingPrice.setColumns(10);
        textFieldSellingPrice.setBounds(30, 410, 174, 41);
        add(textFieldSellingPrice);
        
        JLabel productLabel = new JLabel("상품");
        productLabel.setHorizontalAlignment(SwingConstants.CENTER);
        productLabel.setBounds(12, 268, 90, 40);
        add(productLabel);
        
        textFieldProductName = new JPlaceholderTextField("ex) 신라면");
        textFieldProductName.setColumns(10);
        textFieldProductName.setBounds(114, 268, 354, 40);
        add(textFieldProductName);
    }

	public JButton getInitializationBtn() {
		return initializationBtn;
	}

	public JButton getproductRegistrationBtn() {
		return productRegistrationBtn;
	}

	public JTextField getTextFieldProductCode() {
		return textFieldProductCode;
	}
	
	public JTextField gettextFieldProductName() {
		return textFieldProductName;
	}

	public JTextField getTextFieldPurchasePrice() {
		return textFieldPurchasePrice;
	}

	public JTextField getTextFieldSellingPrice() {
		return textFieldSellingPrice;
	}

	public JComboBox<String> getComboBoxLargeCategory() {
		return comboBoxLargeCategory;
	}

	public JComboBox<String> getComboBoxMediumCategory() {
		return comboBoxMediumCategory;
	}

	public JComboBox<String> getComboBoxSmallCategory() {
		return comboBoxSmallCategory;
	}	
}
