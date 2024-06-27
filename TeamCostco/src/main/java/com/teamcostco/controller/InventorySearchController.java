package main.java.com.teamcostco.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import main.java.com.teamcostco.MainForm;
import main.java.com.teamcostco.model.database.DatabaseUtil;
import main.java.com.teamcostco.view.panels.InventorySearchPanel;

public class InventorySearchController extends PanelController<InventorySearchPanel> {

	private InventorySearchPanel inventorySearchPanel;

	public InventorySearchController() {
		inventorySearchPanel = new InventorySearchPanel();
		view = inventorySearchPanel;

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
					loadMidiumCategories(view.cb_CategorizeName);
				} else if (selectedCategory.equals("소분류")) {
					loadSmallCategories(view.cb_CategorizeName);
				}
			}
		});

		// 검색 버튼 클릭 시 DB에서 데이터를 가져와 테이블에 표시
		view.searchButton.addActionListener(e -> {
			String searchKeyword = view.textField.getText();
			String selectedCategory = (String) view.cb_CategorizeName.getSelectedItem();
			String categoryKind = (String) view.categoryComboBox.getSelectedItem();
			loadData(view.model, searchKeyword, categoryKind, selectedCategory);
			bindTableAction();
		});
	}

	private void initializeCategoryComboBox(JComboBox<String> comboBox) {
		comboBox.removeAllItems();
		comboBox.addItem("대분류");
		comboBox.addItem("중분류");
		comboBox.addItem("소분류");
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

	private void loadMidiumCategories(JComboBox<String> comboBox) {
		comboBox.removeAllItems();
		try (Connection connection = DatabaseUtil.getConnection();
				PreparedStatement pstmt = connection.prepareStatement("SELECT midium_name FROM midiumcategory");
				ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				comboBox.addItem(rs.getString("midium_name"));
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
		String joinSql;
		String joinSql2;
		switch (categoryKind) {
		case "대분류":
			joinSql = "JOIN maincategory m ON p.main_id = m.main_id ";
			joinSql2 = "AND m.main_name LIKE ?";
			break;
		case "중분류":
			joinSql = "JOIN midiumcategory mc ON p.medium_id = mc.midium_id ";
			joinSql2 = "AND mc.medium_name LIKE ?";
			break;
		case "소분류":
			joinSql = "JOIN smallcategory s ON p.small_id = s.small_id ";
			joinSql2 = "AND s.small_name LIKE ?";
			break;
		default:
			joinSql = "";
			joinSql2 = "";
		}

		String sql = "SELECT p.product_id, p.product_name, main_name, p.current_inventory, p.selling_price "
				+ "FROM product p " + joinSql + "WHERE p.product_name LIKE ? " + joinSql2;

		System.out.println(searchKeyword);
		System.out.println(categoryName);
		
		try (Connection connection = DatabaseUtil.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(sql)) {

			if (!joinSql.isEmpty()) {
				pstmt.setString(1, "%" + searchKeyword + "%");
			}

			if (!joinSql2.isEmpty()) {
				pstmt.setString(2, "%" + categoryName + "%");
			}

			try (ResultSet rs = pstmt.executeQuery()) {
				tableModel.setRowCount(0);
				while (rs.next()) {
					System.out.println("d");
					String productId = rs.getString("product_id");
					String productName = rs.getString("product_name");
					String smallName = rs.getString("main_name");
					int currentInventory = rs.getInt("current_inventory");
					double sellingPrice = rs.getDouble("selling_price");

					System.out.println(productName);
					tableModel
							.addRow(new Object[] { productId, productName, smallName, currentInventory, sellingPrice });
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void bindTableAction() {
		view.table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = view.table.rowAtPoint(e.getPoint());
				if (row >= 0) {
					String productId = (String) view.table.getValueAt(row, 0);
					loadProductDetails(productId);
				}
			}
		});
	}

	private void loadProductDetails(String productId) {
		try (Connection connection = DatabaseUtil.getConnection();
				PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM product WHERE product_id = ?")) {

			pstmt.setString(1, productId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					String productName = rs.getString("product_name");
					String productCode = rs.getString("product_code");
					double purchasePrice = rs.getDouble("purchase_price");
					double sellingPrice = rs.getDouble("selling_price");
					int appropriateInventory = rs.getInt("appropriate_inventory");
					int currentInventory = rs.getInt("current_inventory");

					// 상품 상세 정보를 다이얼로그 또는 다른 방법으로 표시
					// 예: DialogManager.showProductDetails(productId, productName, productCode,
					// purchasePrice, sellingPrice, appropriateInventory, currentInventory);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "재고조회";
	}
}
