package main.java.com.teamcostco.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import main.java.com.teamcostco.model.CategoryModel;
import main.java.com.teamcostco.model.Product;
import main.java.com.teamcostco.model.database.DatabaseUtil;
import main.java.com.teamcostco.view.panels.ProductDetailPanel;

public class ProductDetailController extends PanelController<ProductDetailPanel> {

    private Product product;
    private CategoryModel.MainCategory mainCategory;
    private CategoryModel.MidiumCategory midiumCategory;
    private CategoryModel.SmallCategory smallCategory;
    
    public ProductDetailController() {
        product = new Product();
        initControl();
        fetchData();
    }

    private void fetchData() {
        try (Connection conn = DatabaseUtil.getConnection()) {
            String query =  "SELECT" 
            	    +"p.PRODUCT_ID"
            	    +"p.PRODUCT_CODE"
            	    +"p.PRODUCT_NAME"
            	    +"mc.main_id AS main_id"
            	    +"mc.Main_name AS main_name"
            	    +"p.PURCHASE_PRICE"
            	    +"p.SELLING_PRICE"
            	    +"p.APPROPRIATE_INVENTORY"
            	    +"p.CURRENT_INVENTORY"
            	+ "FROM product p JOIN MainCategory mc ON p.MAIN_ID = mc.Main_id WHERE p.product_code = ?";
            
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, product.getProduct_code());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                product.setProduct_id(rs.getInt("p.PRODUCT_ID"));
                product.setProduct_code(rs.getString("p.PRODUCT_CODE"));
                product.setProduct_name(rs.getString("p.PRODUCT_NAME"));
                product.setPurchase_price(rs.getDouble("p.PURCHASE_PRICE"));
                product.setSelling_price(rs.getDouble("p.SELLING_PRICE"));
                product.setAppropriate_inventory(rs.getInt("p.APPROPRIATE_INVENTORY"));
                product.setCurrent_inventory(rs.getInt("p.CURRENT_INVENTORY"));
               
                
                mainCategory = new CategoryModel().new MainCategory(rs);
                
                updateView();
                
            } else {
                System.out.println("상품코드를 찾지 못하였습니다.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateView() {
    	view.getProductCode().setText(product.getProduct_code());
        view.getCategoryLabel().setText(mainCategory.getMain_name());
        view.getProductName().setText(product.getProduct_name());
        view.getPurchase_price().setText(String.valueOf(product.getPurchase_price()));
        view.getSelling_price().setText(String.valueOf(product.getSelling_price()));
        view.getAppropriate_inventory().setText(String.valueOf(product.getAppropriate_inventory()));
        view.getCurrent_inventory().setText(String.valueOf(product.getCurrent_inventory()));
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
        String remark = view.getTextField().getText();
        JOptionPane.showMessageDialog(view, "비고: " + remark + "\n조정 요청이 완료되었습니다.");
    }

    @Override
    public String toString() {
        return "상품상세정보";
    }
}
