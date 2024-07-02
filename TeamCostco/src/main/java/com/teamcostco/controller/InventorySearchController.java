package main.java.com.teamcostco.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

import main.java.com.teamcostco.MainForm;
import main.java.com.teamcostco.dao.ProductDAO;
import main.java.com.teamcostco.model.Product;
import main.java.com.teamcostco.model.database.DatabaseUtil;
import main.java.com.teamcostco.view.panels.InventorySearchPanel;

public class InventorySearchController extends PanelController<InventorySearchPanel> {

	private InventorySearchPanel inventorySearchPanel;

	public InventorySearchController() {

		// 카테고리 콤보 박스에 데이터 로딩
		initializeCategoryComboBox(view.categoryComboBox);

		// 카테고리 콤보 박스에 선택 이벤트 추가
		view.categoryComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedCategory = (String) view.categoryComboBox.getSelectedItem();
				if (selectedCategory.equals("대분류")) {
					loadMainCategories(view.cb_CategorizeName);
				} else if (selectedCategory.equals("중분류")) {
					loadmediumCategories(view.cb_CategorizeName);
				} else if (selectedCategory.equals("소분류")) {
					loadSmallCategories(view.cb_CategorizeName);
				}
			}
		});

		// 검색 버튼 클릭 시 DB에서 데이터를 가져와 테이블에 표시
		view.searchButton.addActionListener(e -> {
			search();
		});
	}

	public void search() {
		String searchKeyword = view.searchField.getText();
		String selectedCategory = (String) view.cb_CategorizeName.getSelectedItem();
		String categoryKind = (String) view.categoryComboBox.getSelectedItem();
		loadData(view.model, searchKeyword, categoryKind, selectedCategory);
		bindTableAction();
	}

	private void initializeCategoryComboBox(JComboBox<String> comboBox) {
		comboBox.removeAllItems();
		comboBox.addItem("전체");
		comboBox.addItem("대분류");
		comboBox.addItem("중분류");
		comboBox.addItem("소분류");

		comboBox.addItemListener(item -> {
			System.out.println(item.getItem());
			if (item.getItem().equals("전체")) {
				view.cb_CategorizeName.setEnabled(false);
			} else {
				view.cb_CategorizeName.setEnabled(true);
			}
		});
	}

	private void loadMainCategories(JComboBox<String> comboBox) {
		comboBox.removeAllItems();
		try (Connection connection = DatabaseUtil.getConnection();
				PreparedStatement pstmt = connection.prepareStatement("SELECT main_name FROM maincategory");
				ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				comboBox.addItem(rs.getString("main_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void loadmediumCategories(JComboBox<String> comboBox) {
		comboBox.removeAllItems();
		try (Connection connection = DatabaseUtil.getConnection();
				PreparedStatement pstmt = connection.prepareStatement("SELECT medium_name FROM mediumcategory");
				ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				comboBox.addItem(rs.getString("medium_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void loadSmallCategories(JComboBox<String> comboBox) {
		comboBox.removeAllItems();
		try (Connection connection = DatabaseUtil.getConnection();
				PreparedStatement pstmt = connection.prepareStatement("SELECT small_name FROM smallcategory");
				ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				comboBox.addItem(rs.getString("small_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void loadData(DefaultTableModel tableModel, String searchKeyword, String categoryKind,
			String categoryName) {
		String joinSql = "";
		String whereClause = "WHERE p.product_name LIKE ? ";
		List<String> params = new ArrayList<>();
		params.add("%" + searchKeyword + "%");

		// "전체" 카테고리일 경우 추가 조건 없이 모든 상품 검색
		if (!categoryKind.equals("전체")) {
			switch (categoryKind) {
			case "대분류":
				joinSql = "JOIN maincategory m ON p.main_id = m.main_id ";
				whereClause += "AND m.main_name LIKE ? ";
				params.add("%" + categoryName + "%");
				break;
			case "중분류":
				joinSql = "JOIN maincategory m ON p.main_id = m.main_id "
						+ "JOIN mediumcategory mc ON p.medium_id = mc.medium_id ";
				whereClause += "AND mc.medium_name LIKE ? ";
				params.add("%" + categoryName + "%");
				break;
			case "소분류":
				joinSql = "JOIN maincategory m ON p.main_id = m.main_id "
						+ "JOIN smallcategory s ON p.small_id = s.small_id ";
				whereClause += "AND s.small_name LIKE ? ";
				params.add("%" + categoryName + "%");
				break;
			}
		}

		String sql = "SELECT p.product_id, p.product_name AS product_name, p.product_code AS product_code, p.main_id AS main_id, "
				+ "p.current_inventory AS current_inventory, p.active_inventory AS active_inventory, p.selling_price "
				+ "FROM product p " + joinSql + whereClause;

		try (Connection connection = DatabaseUtil.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(sql)) {

			for (int i = 0; i < params.size(); i++) {
				pstmt.setString(i + 1, params.get(i));
			}

			try (ResultSet rs = pstmt.executeQuery()) {
				tableModel.setRowCount(0);
				while (rs.next()) {
					String locationId = rs.getString("main_id");
					String productName = rs.getString("product_name");
					int currentInventory = rs.getInt("current_inventory");
					int activeInventory = rs.getInt("active_inventory");
					String productCode = rs.getString("product_code");

					tableModel.addRow(
							new Object[] { locationId, productCode, productName, currentInventory, activeInventory });
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void bindTableAction() {
		view.table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) { // 더블 클릭 확인
					int row = view.table.rowAtPoint(e.getPoint());
					if (row >= 0) {
						String productCode = (String) view.table.getValueAt(row, 1); // 인덱스를 1로 가정 (상품코드 컬럼)
						MainForm.nav.push("productdetail", true, ProductDAO.getProductByCode(productCode));
					}
				}
			}
		});
	}

	@Override
	public String toString() {
		return "재고조회";
	}
}
