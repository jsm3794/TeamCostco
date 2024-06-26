package main.java.com.teamcostco.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

import main.java.com.teamcostco.MainForm;
import main.java.com.teamcostco.model.database.DatabaseUtil;
import main.java.com.teamcostco.model.manager.DialogManager;
import main.java.com.teamcostco.view.panels.InventorySearchPanel;

public class InventorySearchController extends PanelController<InventorySearchPanel> {

	public Map<String, String> categoriMap = new HashMap<>();
	
	private InventorySearchPanel inventorySearchPanel;

    public InventorySearchController() {
    	//변수초기화한거잦나이게
        inventorySearchPanel = new InventorySearchPanel();
        //inventorySearchPanel.initializeComboBoxes(); 
        
		InventorySearchPanel panel = new InventorySearchPanel();

		// 검색 버튼 클릭 시 DB에서 데이터를 가져와 테이블에 표시
		view.searchButton.addActionListener(e -> {
			DialogManager.showMessageBox(view, "메세지<br>gdgdgdgdㅎㅇ", true, evt -> System.out.println("예"), null);
			String searchKeyword = view.searchField.getText();
			String categoriName = (String) view.categoryComboBox.getSelectedItem();
			loadData(view.model, searchKeyword, categoriName);
			bindTableAction();
		});

		loadCategories(view.categoryComboBox);

	}

	private void loadCategories(JComboBox<String> comboBox) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = DatabaseUtil.getConnection();
			

			String sql = "SELECT * FROM product2";
			pstmt = connection.prepareStatement(sql);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				String id = rs.getString("product_id");
				String name = rs.getString("product_name");

				categoriMap.put(name, id);
				comboBox.addItem(name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			DatabaseUtil.close(connection, pstmt, rs);
		}
	}

	private void loadData(DefaultTableModel model, String searchKeyword, String categori_name) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String categori_id = categoriMap.get(categori_name);

		try {
			connection = DatabaseUtil.getConnection();

			String sql = "SELECT * FROM product2";

			System.out.println(sql);

			pstmt = connection.prepareStatement(sql);

			pstmt.setString(1, "%" + searchKeyword + "%");
			pstmt.setString(2, "%" + categori_id + "%");

			rs = pstmt.executeQuery();

			model.setRowCount(0);

			while (rs.next()) {
				String product_id = rs.getString("id");
				String productName = rs.getString("product_name");
				
				

				model.addRow(new Object[] { product_id, productName,});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.close(connection, pstmt, rs);
		}

	}

	public void bindTableAction() {
		view.table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = view.table.rowAtPoint(e.getPoint());

				if (row >= 0) {
					String product_id = (String)view.table.getValueAt(row, 1);
					MainForm.nav.push("productdetail", true, product_id);
				}
			}
		});
	}

	@Override
	public String toString() {
		return "재고조회";
	}

}
