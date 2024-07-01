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
import main.java.com.teamcostco.view.panels.DBConnector;
import main.java.com.teamcostco.view.panels.ProductDetailPanel;

public class ProductDetailController extends PanelController<ProductDetailPanel> {

		private Product product;
		private CategoryModel categorymodel;
	
//		  public ProductDetailController(String productCode) {
//		        this.product = new Product();
//		        this.product.setProduct_code(productCode);
//		        view = new ProductDetailPanel();
//		        initControl();
		
		  public ProductDetailController() {
		        product = new Product();
		        categorymodel = new CategoryModel();
		        view = new ProductDetailPanel();
		        initControl();
		  
			//그냥 다른 클래스에서 조인해서 가져오는 방식으로ㅎ
		        
		        
			try (Connection conn = DatabaseUtil.getConnection()) {
				String str = "SELECT * FROM product WHERE product_code = ?";
				PreparedStatement pstmt = conn.prepareStatement(str);
				pstmt.setString(1, product.getProduct_code());
				ResultSet rs = pstmt.executeQuery();
				CategoryModel.MainCategory mainCategory = categorymodel.new MainCategory(rs);
				
				if (rs.next()) {
					product = new Product(rs);
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
			
		public void initControl() {
		
			// 뷰에 데이터를 표시
			view.getProductCode().setText(product.getProduct_code());
			//view.getMain_name().setText(CategoryModel.getMain_name());
			view.getProductName().setText(product.getProduct_name());
			view.getPurchasePrice().setText(String.valueOf(product.getPurchase_price()));
			view.getSellingPrice().setText(String.valueOf(product.getSelling_price()));
			view.getProperInventory().setText(String.valueOf(product.getAppropriate_inventory()));
			view.getCurrentInventory().setText(String.valueOf(product.getCurrent_inventory()));
		
		        
		        
		// "조정 요청" 버튼에 이벤트 리스너를 추가
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
		return "상품상세정보";
	}
}
		
