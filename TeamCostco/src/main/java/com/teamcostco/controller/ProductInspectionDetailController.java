package main.java.com.teamcostco.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import main.java.com.teamcostco.MainForm;
import main.java.com.teamcostco.dao.ProductInspectionDAO;
import main.java.com.teamcostco.model.Productinspection;
import main.java.com.teamcostco.model.database.DatabaseUtil;
import main.java.com.teamcostco.model.manager.DialogManager;
import main.java.com.teamcostco.view.panels.ProductInspectionDetailPanel;

public class ProductInspectionDetailController extends PanelController<ProductInspectionDetailPanel> {
	private List<Productinspection> selectedProducts;

	public ProductInspectionDetailController(List<Productinspection> selectedProducts) {
		this.selectedProducts = selectedProducts;
		view.setSelectedProducts(selectedProducts);
		view.initializeUI();
		setupEventListeners();
	}

	private void setupEventListeners() {
		view.getConfirmButton().addActionListener(e -> {
			updateInventory();
			saveDefectProducts();
			deleteInspectionProducts();

			DialogManager.showMessageBox(view, "검수 결과가 저장되었습니다.", evt -> {

				MainForm.nav.pop();
				ProductInspectionController pc = (ProductInspectionController) MainForm.nav.getCurrent();
				pc.loadData();
			});

		});
	}

	private void deleteInspectionProducts() {
		String sql = "delete from productinspection where PRODUCT_INSPECTION_ID = ?";
		for (Productinspection product : selectedProducts) {
			int pi_id = product.getPRODUCT_INSPECTION_ID();
			System.out.println(pi_id);
			ProductInspectionDAO.delete(pi_id);
		}
	}

	private void updateInventory() {
		String sql = "UPDATE product SET CURRENT_INVENTORY = CURRENT_INVENTORY + ? WHERE PRODUCT_CODE = ?";
		try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			for (Productinspection product : selectedProducts) {
				pstmt.setInt(1, product.getCheckquantity());
				pstmt.setString(2, product.getProductcode());
				pstmt.executeUpdate();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(view, "입고 수량을 업데이트하는 중 오류가 발생했습니다.", "데이터베이스 오류", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void saveDefectProducts() {
		String sql = "INSERT INTO defectproduct (PRODUCT_CODE, DEFECT_AMOUNT, CREATED_AT) VALUES (?, ?, CURRENT_TIMESTAMP)";
		try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			for (int i = 0; i < selectedProducts.size(); i++) {
				Productinspection product = selectedProducts.get(i);
				int defectAmount = 0;
				if(view.getMissingFields().get(i).getText().isEmpty()) {
					pstmt.setString(1, product.getProductcode());
					pstmt.setInt(2, 0);
					pstmt.executeUpdate();
				}else {
					defectAmount = Integer.parseInt(view.getMissingFields().get(i).getText());
					pstmt.setString(1, product.getProductcode());
					pstmt.setInt(2, defectAmount);
					pstmt.executeUpdate();
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(view, "불량 제품을 저장하는 중 오류가 발생했습니다.", "데이터베이스 오류", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public String toString() {
		return "제품검수상세";
	}
}