
package main.java.com.teamcostco.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import main.java.com.teamcostco.model.AllCategoryJoin;
import main.java.com.teamcostco.model.CategoryData;
import main.java.com.teamcostco.model.WareHouseListModel;
import main.java.com.teamcostco.model.database.DatabaseUtil;
import main.java.com.teamcostco.view.panels.WareHouseListPanel;

public class WareHouseListController extends PanelController<WareHouseListPanel> {
	private DatabaseUtil connector;
	private CategoryData categoryData;

	public WareHouseListController() {
		this.connector = new DatabaseUtil();
		this.categoryData = new CategoryData(loadAllCategories());
		initialize();
	}

	private void initialize() {
		// Populate main category combo box
		view.getMainCateComboBox().addItem("대분류 전체");
		for (String main : categoryData.getMainCategories()) {
			view.getMainCateComboBox().addItem(main);
		}

		// Add action listener to main category combo box
		view.getMainCateComboBox().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedMain = (String) view.getMainCateComboBox().getSelectedItem();
				loadmediumCategories(selectedMain);
			}
		});

		// Add action listener to medium category combo box
		view.getmediumCateCombo().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedmedium = (String) view.getmediumCateCombo().getSelectedItem();
				loadSmallCategories(selectedmedium);
			}
		});

		// Search products on button click
		view.getSearchButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				searchDefectiveProducts();
			}
		});
	}

	private void searchDefectiveProducts() {
		StringBuilder sql = new StringBuilder("SELECT DISTINCT product_name, disposal_id, defect_amount, created_at "
				+ "FROM defectproduct " + "INNER JOIN product USING (product_code) "
				+ "INNER JOIN maincategory main ON main.main_id = product.main_id "
				+ "INNER JOIN mediumcategory medi ON main.main_id = medi.main_id "
				+ "INNER JOIN smallcategory small ON medi.medium_id = small.medium_id " + "WHERE 1=1 ");

		List<Object> parameters = new ArrayList<>();

		String productName = view.getProductNameField().getText();
		if (productName != null && !productName.trim().isEmpty()) {
			sql.append("AND product_name LIKE ? ");
			parameters.add("%" + productName.trim() + "%");
		}

		String selectedMain = (String) view.getMainCateComboBox().getSelectedItem();
		if (selectedMain != null && !"대분류 전체".equals(selectedMain)) {
			sql.append("AND main_name = ? ");
			parameters.add(selectedMain);
		}

		String selectedMedium = (String) view.getmediumCateCombo().getSelectedItem();
		if (selectedMedium != null && !"중분류 전체".equals(selectedMedium)) {
			sql.append("AND medium_name = ? ");
			parameters.add(selectedMedium);
		}

		String selectedSmall = (String) view.getSmallCateCombo().getSelectedItem();
		if (selectedSmall != null && !"소분류 전체".equals(selectedSmall)) {
			sql.append("AND small_name = ? ");
			parameters.add(selectedSmall);
		}

		sql.append("ORDER BY disposal_id ");

		try (Connection conn = connector.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
			for (int i = 0; i < parameters.size(); i++) {
				pstmt.setObject(i + 1, parameters.get(i));
			}
			try (ResultSet rs = pstmt.executeQuery()) {
				List<WareHouseListModel> results = new ArrayList<>();
				while (rs.next()) {
					results.add(new WareHouseListModel(rs));
				}
				view.getTableModel().setData(results);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private List<AllCategoryJoin> loadAllCategories() {
		List<AllCategoryJoin> allCategories = new ArrayList<>();
		String sql = "SELECT main_name, medium_name, small_name " + "FROM maincategory "
				+ "INNER JOIN mediumcategory USING (main_id) " + "INNER JOIN smallcategory USING (medium_id)";

		try (Connection conn = connector.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				allCategories.add(new AllCategoryJoin(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allCategories;
	}

	private void loadmediumCategories(String selectedMain) {
		view.getmediumCateCombo().removeAllItems();
		view.getmediumCateCombo().addItem("중분류 전체");

		if (selectedMain == null || selectedMain.equals("대분류 전체")) {
			view.getmediumCateCombo().setEnabled(false);
			view.getSmallCateCombo().setEnabled(false);
			return;
		}

		Set<String> mediumCategories = categoryData.getmediumCategories(selectedMain);
		for (String medium : mediumCategories) {
			view.getmediumCateCombo().addItem(medium);
		}
		view.getmediumCateCombo().setEnabled(true);
	}

	private void loadSmallCategories(String selectedmedium) {
		view.getSmallCateCombo().removeAllItems();
		view.getSmallCateCombo().addItem("소분류 전체");

		if (selectedmedium == null || selectedmedium.equals("중분류 전체")) {
			view.getSmallCateCombo().setEnabled(false);
			return;
		}

		Set<String> smallCategories = categoryData.getSmallCategories(selectedmedium);
		for (String small : smallCategories) {
			view.getSmallCateCombo().addItem(small);
		}
		view.getSmallCateCombo().setEnabled(true);
	}

	private void searchProducts() {
		StringBuilder sql = new StringBuilder("SELECT " + "product_id, " + "main_name, " + "medium_name, "
				+ "small_name, " + "product_name, " + "current_inventory - active_inventory AS \"inventory_amount\" "
				+ "FROM product p " + "INNER JOIN maincategory main ON main.main_id = p.main_id "
				+ "INNER JOIN mediumcategory medi ON medi.medium_id = p.medium_id "
				+ "INNER JOIN smallcategory small ON small.small_id = p.small_id " + "WHERE product_name LIKE ? ");

		List<String> conditions = new ArrayList<>();
		List<Object> parameters = new ArrayList<>();

		parameters.add("%" + view.getProductNameField().getText() + "%");

		String selectedMain = (String) view.getMainCateComboBox().getSelectedItem();
		if (selectedMain != null && !"대분류 전체".equals(selectedMain)) {
			conditions.add("AND main_name = ? ");
			parameters.add(selectedMain);
		}

		String selectedmedium = (String) view.getmediumCateCombo().getSelectedItem();
		if (selectedmedium != null && !"중분류 전체".equals(selectedmedium)) {
			conditions.add("AND medium_name = ? ");
			parameters.add(selectedmedium);
		}

		String selectedSmall = (String) view.getSmallCateCombo().getSelectedItem();
		if (selectedSmall != null && !"소분류 전체".equals(selectedSmall)) {
			conditions.add("AND small_name = ?");
			parameters.add(selectedSmall);
		}

		for (String condition : conditions) {
			sql.append(condition);
		}
		sql.append("ORDER BY product_id");

		try (Connection conn = connector.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
			for (int i = 0; i < parameters.size(); i++) {
				pstmt.setObject(i + 1, parameters.get(i));
			}
			try (ResultSet rs = pstmt.executeQuery()) {
				List<WareHouseListModel> results = new ArrayList<>();
				while (rs.next()) {
					results.add(new WareHouseListModel(rs));
				}
				view.getTableModel().setData(results);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "창고목록";
	}
}