package main.java.com.teamcostco.view.panels;


import javax.swing.*;

import main.java.com.teamcostco.model.database.DatabaseUtil;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class InventoryDetailPanel extends JPanel {

    public InventoryDetailPanel(String productName) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.anchor = GridBagConstraints.WEST;

        // 데이터베이스에서 데이터를 가져옴
        Product product = fetchProductFromDatabase(productName);

        // 데이터를 GUI에 표시
        if (product != null) {
            int row = 0;
            addLabelAndValue(this, gbc, "상품명", product.getName(), row++);
            addLabelAndValue(this, gbc, "카테고리", product.getCategory(), row++);
            addLabelAndValue(this, gbc, "수량", String.valueOf(product.getQuantity()), row++);
            addLabelAndValue(this, gbc, "입고날짜", product.getEntryDate(), row++);
            addLabelAndValue(this, gbc, "판매가격", "₩ " + product.getPrice(), row++);
            addLabelAndValue(this, gbc, "상품코드", product.getProductCode(), row++);
            addLabelAndValue(this, gbc, "적재위치", product.getStorageLocation(), row++);
        } else {
            add(new JLabel("데이터를 불러오는 데 실패했습니다."));
        }
        
        

        // 수정 요청 버튼 추가
        JButton modifyButton = new JButton("수정요청");
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(modifyButton, gbc);
    }

    private void addLabelAndValue(JPanel panel, GridBagConstraints gbc, String labelText, String valueText, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        panel.add(new JLabel(valueText), gbc);
    }

    private GridBagConstraints createGbc(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.anchor = GridBagConstraints.WEST;
        return gbc;
    }

    private Product fetchProductFromDatabase(String productName) {
        String query = "SELECT p.product_name, c.categori_name, s.amount, p.expiredate, s.storage_id " +
                       "FROM Product p " +
                       "JOIN Categori c ON p.categori_id = c.categori_id " +
                       "JOIN Storage s ON p.product_id = s.product_id " +
                       "WHERE p.product_name = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtil.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, productName);
            rs = ps.executeQuery();

            if (rs.next()) {
                String name = rs.getString("product_name");
                String category = rs.getString("categori_name");
                int quantity = rs.getInt("amount");
                String entryDate = rs.getString("expiredate");
                double price = 5200; // 임시 가격
                String productCode = "CP_220564";
                String storageLocation = rs.getString("storage_id");

                return new Product(name, category, quantity, entryDate, price, productCode, storageLocation);
            } else {
                System.out.println("No data found for the given product name.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseUtil.close(conn, ps, rs);
        }

        return null;
    }

    private static void createAndShowGUI(String productName) {
        JFrame frame = new JFrame("상품 정보");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(480, 640);
        frame.setLocationRelativeTo(null);

        InventoryDetailPanel inventoryDetail = new InventoryDetailPanel(productName);
        frame.add(inventoryDetail, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI("로션"));
    }
}

class Product {
    private String name;
    private String category;
    private int quantity;
    private String entryDate;
    private double price;
    private String productCode;
    private String storageLocation;

    public Product(String name, String category, int quantity, String entryDate, double price, String productCode, String storageLocation) {
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.entryDate = entryDate;
        this.price = price;
        this.productCode = productCode;
        this.storageLocation = storageLocation;
    }

    // Getter methods
    public String getName() { return name; }
    public String getCategory() { return category; }
    public int getQuantity() { return quantity; }
    public String getEntryDate() { return entryDate; }
    public double getPrice() { return price; }
    public String getProductCode() { return productCode; }
    public String getStorageLocation() { return storageLocation; }
}



