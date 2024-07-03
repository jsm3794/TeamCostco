package main.java.com.teamcostco.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import main.java.com.teamcostco.MainForm;
import main.java.com.teamcostco.model.CategoryModel;
import main.java.com.teamcostco.model.Product;
import main.java.com.teamcostco.model.database.DatabaseUtil;
import main.java.com.teamcostco.view.panels.ProductDetailPanel;

public class ProductDetailController extends PanelController<ProductDetailPanel> {
    private Product product;
    private CategoryModel.MainCategory mainCategory;
    private CategoryModel.MediumCategory mediumCategory;
    private CategoryModel.SmallCategory smallCategory;
    
    public ProductDetailController(Product product) {
        this.product = product; 
        initControl();
        fetchData();
    }

    private void fetchData() {
        try (Connection conn = DatabaseUtil.getConnection()) {
            String query = "SELECT p.PRODUCT_ID, p.PRODUCT_CODE, p.PRODUCT_NAME, "
                + "p.MAIN_ID, mc.Main_name AS main_name, "
                + "p.MEDIUM_ID, mec.Medium_name AS medium_name, "
                + "p.SMALL_ID, sc.Small_name AS small_name, "
                + "p.PURCHASE_PRICE, p.SELLING_PRICE, p.APPROPRIATE_INVENTORY, "
                + "p.CURRENT_INVENTORY, p.ACTIVE_INVENTORY "
                + "FROM product p "
                + "JOIN MainCategory mc ON p.MAIN_ID = mc.Main_id "
                + "JOIN MediumCategory mec ON p.MEDIUM_ID = mec.Medium_id "
                + "JOIN SmallCategory sc ON p.SMALL_ID = sc.Small_id "
                + "WHERE p.product_code = ?";
            
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, product.getProduct_code());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                product.setProduct_id(rs.getInt("PRODUCT_ID"));
                product.setProduct_code(rs.getString("PRODUCT_CODE"));
                product.setProduct_name(rs.getString("PRODUCT_NAME"));
                product.setPurchase_price(rs.getDouble("PURCHASE_PRICE"));
                product.setSelling_price(rs.getDouble("SELLING_PRICE"));
                product.setAppropriate_inventory(rs.getInt("APPROPRIATE_INVENTORY"));
                product.setCurrent_inventory(rs.getInt("CURRENT_INVENTORY"));
                product.setActive_inventory(rs.getInt("ACTIVE_INVENTORY"));
                
                mainCategory = new CategoryModel().new MainCategory(rs);
                mediumCategory = new CategoryModel().new MediumCategory(rs);
                smallCategory = new CategoryModel().new SmallCategory(rs);
                
                updateView();
            } else {
                System.out.println("상품코드를 찾지 못하였습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateView() {
        view.getProductIdLabel().setText(String.valueOf(product.getProduct_id()));
        view.getProductCodeLabel().setText(product.getProduct_code());
        view.getProductNameLabel().setText(product.getProduct_name());
        view.getMainIdLabel().setText(mainCategory.getMain_name());
        view.getMediumIdLabel().setText(mediumCategory.getMedium_name());
        view.getSmallIdLabel().setText(smallCategory.getSmall_name());
        view.getPurchasePriceLabel().setText(String.valueOf(product.getPurchase_price()));
        view.getSellingPriceLabel().setText(String.valueOf(product.getSelling_price()));
        view.getAppropriateInventoryLabel().setText(String.valueOf(product.getAppropriate_inventory()));
        view.getCurrentInventoryLabel().setText(String.valueOf(product.getCurrent_inventory()));
        view.getActiveInventoryLabel().setText(String.valueOf(product.getActive_inventory()));
    }

    private void initControl() {
        view.getBtnAdjustRequest().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adjustRequest();
            }
        });
    }

    private void adjustRequest() {
    	MainForm.nav.push("inventoryUpdate", true, product);
    }

    @Override
    public String toString() {
        return "상품상세정보";
    }
}