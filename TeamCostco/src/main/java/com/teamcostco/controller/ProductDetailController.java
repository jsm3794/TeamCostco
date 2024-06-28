package main.java.com.teamcostco.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import main.java.com.teamcostco.model.Product;
import main.java.com.teamcostco.model.database.DatabaseUtil;
import main.java.com.teamcostco.view.panels.ProductDetailPanel;

import javax.xml.crypto.Data;

import main.java.com.teamcostco.model.Product;
import main.java.com.teamcostco.model.database.DatabaseUtil;
import main.java.com.teamcostco.view.panels.ProductDetailPanel;

public class ProductDetailController extends PanelController<ProductDetailPanel> {
  
	public ProductDetailController(Product product) {
        this.product = product;
        view = new ProductDetailPanel(); // 뷰 생성
        initControl(); 
    }

	
	private Product product; 


    private void initControl() {
        // 데이터베이스에서 상품 정보를 가져옵니다.
        try (Connection conn = DatabaseUtil.getConnection()) {
            String query = "SELECT * FROM product WHERE product_code = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, product.getProduct_code());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // ResultSet에서 데이터를 가져와 product 객체에 설정합니다.
                product.setLargeCategory(rs.getString("large_category"));
                product.setProductName(rs.getString("product_name"));
                product.setPurchasePrice(rs.getInt("purchase_price"));
                product.setSellingPrice(rs.getInt("selling_price"));
                product.setAppropriateInventory(rs.getInt("appropriate_inventory"));
                product.setCurrentInventory(rs.getInt("current_inventory"));
                product.setWarehousingDate(rs.getString("warehousing_date"));
                product.setLoadingPosition(rs.getString("loading_position"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 뷰에 데이터를 표시합니다.
        view.getProductCode().setText(product.getProduct_code());
        view.getLargeCategory().setText(product.getLargeCategory());
        view.getProductName().setText(product.getProductName());
        view.getPurchasePrice().setText(String.valueOf(product.getPurchasePrice()));
        view.getSellingPrice().setText(String.valueOf(product.getSellingPrice()));
        view.getProperInventory().setText(String.valueOf(product.getAppropriateInventory()));
        view.getCurrentInventory().setText(String.valueOf(product.getCurrentInventory()));
        view.getWarehousingDate().setText(product.getWarehousingDate());
        view.loadingPosition().setText(product.getLoadingPosition());

        // "조정 요청" 버튼에 이벤트 리스너를 추가합니다.
        view.getBtnAdjustRequest().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adjustRequest(); 
            }
        });
    }

    // 조정 요청 처리
    private void adjustRequest() {
        String remark = view.getTextField().getText();
        JOptionPane.showMessageDialog(view, "비고: " + remark + "\n조정 요청이 완료되었습니다.");
    }

    @Override
    public String toString() {
        return "상품상세조회";
    }
}
	
	
	
	private void initControl(Product data) {
		
		
	}
	
	
	
	private ProductDetailPanel productDetailPanel;
	
	public ProductDetailController() {
		productDetailPanel = new ProductDetailPanel();
		view = productDetailPanel;
		
		
	
			
			
		
	
	try(Connection cn = DatabaseUtil.getConnection();
}
	
	
	

	@Override
    public String toString() {
        return "상품상세조회";
    }
}




    