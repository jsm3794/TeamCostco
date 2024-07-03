package main.java.com.teamcostco.controller;

import main.java.com.teamcostco.MainForm;
import main.java.com.teamcostco.model.Productinspection;
import main.java.com.teamcostco.view.panels.ProductInspectionPanel;
import main.java.com.teamcostco.model.database.DatabaseUtil;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductInspectionController extends PanelController<ProductInspectionPanel> {


	private List<Productinspection> productInspections;
	private List<String> categories;

	public ProductInspectionController() {
		productInspections = new ArrayList<>();
		categories = new ArrayList<>();
		view = new ProductInspectionPanel();
		loadData();
		setupEventListeners();
	}

	public void loadData() {
		loadProductInspections();
		loadCategories();
	}

	private void loadCategories() {
		categories.add("전체");
		try (Connection conn = DatabaseUtil.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT main_name FROM maincategory")) {
			while (rs.next()) {
				categories.add(rs.getString("main_name"));
			}
			for (String category : categories) {
				view.categoryComboBox.addItem(category);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(view, "카테고리를 가져오는 중 오류가 발생했습니다.", "데이터베이스 오류", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void loadProductInspections() {
		String sql = "SELECT pi.PRODUCT_INSPECTION_ID as PRODUCT_INSPECTION_ID, pi.product_code as product_code, mc.main_name as main_name, "
				+ "p.product_name as product_name, pi.ORDER_QUANTITY as ORDER_QUANTITY, "
				+ "pi.CHECK_QUANTITY AS CHECK_QUANTITY, pi.DATE_OF_RECEIPT as DATE_OF_RECEIPT "
				+ "FROM productinspection pi " + "JOIN product p ON pi.product_code = p.product_code "
				+ "JOIN maincategory mc ON p.main_id = mc.main_id";
		productInspections.clear();
		try (Connection conn = DatabaseUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				Productinspection inspection = new Productinspection(rs);
				productInspections.add(inspection);
			}
			updateView(productInspections);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(view, "제품 검수 데이터를 가져오는 중 오류가 발생했습니다: " + e.getMessage(), "데이터베이스 오류",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void setupEventListeners() {
		view.productNumberField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				updateView();
			}

			public void removeUpdate(DocumentEvent e) {
				updateView();
			}

			public void insertUpdate(DocumentEvent e) {
				updateView();
			}
		});

		view.categoryComboBox.addActionListener(e -> updateView());

		view.checkButton.addActionListener(e -> {
			view.printCheckBoxStatus(); // 체크박스 상태 출력
			List<Integer> selectedProductCodes = view.getSelectedProductCodes();
			if (selectedProductCodes.isEmpty()) {
				JOptionPane.showMessageDialog(view, "선택된 제품이 없습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
			} else {
				StringBuilder message = new StringBuilder("선택된 제품 코드:\n");
				for (Integer code : selectedProductCodes) {
					message.append(code).append("\n");
				}
				List<Productinspection> result = new ArrayList<>();
				for (Productinspection item : productInspections) {
					if (selectedProductCodes.contains(item.getPRODUCT_INSPECTION_ID())) {
						result.add(item);
					}
				}
				System.out.println(result.size());
				MainForm.nav.push("productDetailCheck", true, result);
			}
		});
	}

	private void updateView() {
		String productCode = view.productNumberField.getText().trim();
		String selectedCategory = (String) view.categoryComboBox.getSelectedItem();
		List<Productinspection> filteredList = filterProductInspections(productCode, selectedCategory);
		updateView(filteredList);
	}

	private List<Productinspection> filterProductInspections(String productCode, String category) {
		List<Productinspection> filteredList = new ArrayList<>();
		for (Productinspection inspection : productInspections) {
			if ((productCode.isEmpty() || inspection.getProductcode().contains(productCode))
					&& (category.equals("전체") || inspection.getMain_name().equals(category))) {
				filteredList.add(inspection);
			}
		}
		return filteredList;
	}

	private void updateView(List<Productinspection> list) {
		view.updateProductInspectionList(list);
	}

	@Override
	public String toString() {
		return "제품검수";
	}
}