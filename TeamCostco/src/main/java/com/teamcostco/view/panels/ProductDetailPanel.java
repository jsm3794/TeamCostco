package main.java.com.teamcostco.view.panels;

import javax.swing.*;
import main.utils.Constants;
import java.awt.*;

public class ProductDetailPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JLabel CategoryLabel;
    private JTextField textField;
    private JButton btnAdjustRequest;
    private JLabel mainName;
    private JLabel productName;
    private JLabel productCode;
    private JLabel purchase_price;
    private JLabel selling_price;
    private JLabel appropriate_inventory;
    private JLabel current_inventory;
    private JLabel dateOf_Receipt;
    private JLabel main_id;

    public ProductDetailPanel() {
        setBounds(new Rectangle(0, 0, 480, 640));
        setLayout(null);

        JLabel CodeLabel = new JLabel("상품코드");
        CodeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        CodeLabel.setBounds(12, 44, 78, 28);
        add(CodeLabel);

        CategoryLabel = new JLabel("대분류");
        CategoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        CategoryLabel.setBounds(12, 82, 78, 28);
        add(CategoryLabel);

        JLabel nameLabel = new JLabel("상품명");
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setBounds(12, 120, 78, 28);
        add(nameLabel);

        JLabel sellingLabel = new JLabel("판매가");
        sellingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        sellingLabel.setBounds(243, 173, 225, 28);
        add(sellingLabel);

        JLabel purchaseLabel = new JLabel("매입가");
        purchaseLabel.setHorizontalAlignment(SwingConstants.CENTER);
        purchaseLabel.setBounds(12, 173, 225, 28);
        add(purchaseLabel);

        JLabel properInventoryLabel = new JLabel("적정재고");
        properInventoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        properInventoryLabel.setBounds(12, 257, 225, 28);
        add(properInventoryLabel);

        JLabel currentInventoryLabel = new JLabel("현재재고");
        currentInventoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        currentInventoryLabel.setBounds(243, 257, 225, 28);
        add(currentInventoryLabel);

        JLabel warehousingDateLabel = new JLabel("입고날짜");
        warehousingDateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        warehousingDateLabel.setBounds(12, 341, 78, 28);
        add(warehousingDateLabel);

        JLabel positionLabel = new JLabel("적재위치");
        positionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        positionLabel.setBounds(12, 385, 78, 28);
        add(positionLabel);

        textField = new JTextField();
        textField.setBounds(12, 437, 456, 128);
        add(textField);
        textField.setColumns(10);

        btnAdjustRequest = new JButton("조정요청");
        btnAdjustRequest.setBounds(12, 575, 456, 55);
        btnAdjustRequest.setBackground(Constants.BUTTON_BACKGROUND_COLOR);
        add(btnAdjustRequest);

        mainName = new JLabel();
        mainName.setBounds(130, 89, 247, 28);
        add(mainName);

        productCode = new JLabel();
        productCode.setBounds(130, 44, 247, 28);
        add(productCode);

        purchase_price = new JLabel();
        purchase_price.setBounds(68, 201, 115, 36);
        add(purchase_price);

        selling_price = new JLabel();
        selling_price.setBounds(295, 201, 115, 36);
        add(selling_price);

        appropriate_inventory = new JLabel();
        appropriate_inventory.setBounds(68, 282, 115, 36);
        add(appropriate_inventory);

        current_inventory = new JLabel();
        current_inventory.setBounds(295, 282, 115, 36);
        add(current_inventory);

        dateOf_Receipt = new JLabel();
        dateOf_Receipt.setBounds(130, 341, 233, 28);
        add(dateOf_Receipt);

        main_id = new JLabel();
        main_id.setBounds(130, 385, 233, 28);
        add(main_id);
    }

    public JTextField getTextField() {
        return textField;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public JLabel getCategoryLabel() {
        return CategoryLabel;
    }

    public JLabel getMainName() {
        return mainName;
    }
    
    public JLabel getProductName() {
        return mainName;
    }

    public JLabel getProductCode() {
        return productCode;
    }

    public JLabel getPurchase_price() {
        return purchase_price;
    }

    public JLabel getSelling_price() {
        return selling_price;
    }

    public JLabel getAppropriate_inventory() {
        return appropriate_inventory;
    }

    public JLabel getCurrent_inventory() {
        return current_inventory;
    }

    public JLabel getDateOf_Receipt() {
        return dateOf_Receipt;
    }

    public JLabel getMain_id() {
        return main_id;
    }

    public JButton getBtnAdjustRequest() {
        return btnAdjustRequest; // 버튼 반환
    }
}
