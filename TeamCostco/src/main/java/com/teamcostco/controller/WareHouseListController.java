package main.java.com.teamcostco.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import main.java.com.teamcostco.model.WareHouseListModel;
import main.java.com.teamcostco.model.database.DatabaseUtil;
import main.java.com.teamcostco.view.panels.WareHouseListPanel;

public class WareHouseListController extends PanelController<WareHouseListPanel> {
	private DatabaseUtil connector;

	public WareHouseListController() {
		this.connector = new DatabaseUtil();
		initialize();
	}

	private void initialize() {
		// Load main categories
		loadMainCategories();

		// Load midium categories on main category selection
		view.getMainCateComboBox().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

					loadMidiumCategories();
					
				

			}
		});

		// Load small categories on midium category selection
		view.getMidiumCateCombo().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedMidium = (String) view.getMidiumCateCombo().getSelectedItem();
				loadSmallCategories(selectedMidium);

			}
		});

		// Search products on button click
		view.getSearchButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				searchProducts();
			}
		});
	}

		private void loadMidiumCategories() {
		    view.getMidiumCateCombo().removeAllItems();
		    view.getMidiumCateCombo().addItem("All");
		    String selectedMain = (String) view.getMainCateComboBox().getSelectedItem();
		    if (selectedMain != null) {
		        String sql = "SELECT midium_name FROM midiumcategory INNER JOIN maincategory USING (main_id) WHERE main_name LIKE ?";
		        try (Connection conn = connector.getConnection();
		             PreparedStatement pstmt = conn.prepareStatement(sql)) {
		            pstmt.setString(1, selectedMain.equals("All") ? "%" : selectedMain);
		            try (ResultSet rs = pstmt.executeQuery()) {
		                while (rs.next()) {
		                    view.getMidiumCateCombo().addItem(rs.getString("midium_name"));
		                }
		                view.getMidiumCateCombo().setEnabled(true);
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    } else {
		        // Handle the case where selectedMain is null, possibly disable or clear related components
		        view.getMidiumCateCombo().setEnabled(false);
		        view.getSmallCateCombo().setEnabled(false);
		    }
		}


		private void loadSmallCategories(String selected_midi) {
		    view.getSmallCateCombo().removeAllItems();
		    view.getSmallCateCombo().addItem("All");

		    if (selected_midi == null || selected_midi.equals("All")) {
		        selected_midi = "%";
		    }

		    String sql = "SELECT small_name FROM smallcategory INNER JOIN midiumcategory USING (midium_id) WHERE midium_name LIKE ?";
		    try (Connection conn = connector.getConnection();
		         PreparedStatement pstmt = conn.prepareStatement(sql)) {
		        pstmt.setString(1, selected_midi.equals("All") ? "%" : selected_midi);
		        try (ResultSet rs = pstmt.executeQuery()) {
		            while (rs.next()) {
		                view.getSmallCateCombo().addItem(rs.getString("small_name"));
		            }
		            if (!selected_midi.equals("%")) {
		                view.getSmallCateCombo().setEnabled(true);
		            }
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}




	private void loadMainCategories() {
		String sql = "SELECT main_name FROM maincategory";
		try (Connection conn = connector.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			view.getMainCateComboBox().addItem("All");
			while (rs.next()) {
				view.getMainCateComboBox().addItem(rs.getString("main_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void searchProducts() {
		String sql = "SELECT storage_id, main_name, midium_name, small_name, product_name, current_inventory FROM storage s "
				+ "INNER JOIN product p ON p.product_code = s.product_id "
				+ "INNER JOIN maincategory main ON main.main_id = p.main_id "
				+ "INNER JOIN midiumcategory midi ON midi.midium_id = p.medium_id "
				+ "INNER JOIN smallcategory small ON small.small_id = p.small_id " + "WHERE product_name LIKE ? ";

		List<String> conditions = new ArrayList<>();
		List<Object> parameters = new ArrayList<>();

		parameters.add("%" + view.getProductNameField().getText() + "%");

		String selectedMain = (String) view.getMainCateComboBox().getSelectedItem();
		if (selectedMain != null && !"All".equals(selectedMain)) {
			conditions.add("AND main_name = ?");
			parameters.add(selectedMain);
		}

		String selectedMidium = (String) view.getMidiumCateCombo().getSelectedItem();
		if (selectedMidium != null && !"All".equals(selectedMidium)) {
			conditions.add("AND midium_name = ?");
			parameters.add(selectedMidium);
		}

		String selectedSmall = (String) view.getSmallCateCombo().getSelectedItem();
		if (selectedSmall != null && !"All".equals(selectedSmall)) {
			conditions.add("AND small_name = ?");
			parameters.add(selectedSmall);
		}

		for (String condition : conditions) {
			sql += condition + " ";
		}

		try (Connection conn = connector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
