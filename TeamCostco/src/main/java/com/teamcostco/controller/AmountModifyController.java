package main.java.com.teamcostco.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import main.java.com.teamcostco.model.AmountModifyModel;
import main.java.com.teamcostco.model.database.DatabaseUtil;
import main.java.com.teamcostco.view.panels.AmountModifyPanel;

// 구버전 입니다 상품검색 후 재고를 수정합니다.

public class AmountModifyController extends PanelController<AmountModifyPanel> {

	private AmountModifyModel model;
	private DatabaseUtil connector;
	private static int cnt = 0;
	
	public AmountModifyController() {
		connector = new DatabaseUtil();
		initComponents();
		
		// 수량 수정
		view.getModifyButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int answer = 
						JOptionPane.showConfirmDialog
						(null, "수량을 수정하시겠습니까?", "알림", JOptionPane.YES_NO_OPTION);
				if (answer == JOptionPane.YES_OPTION) {
					updateAmount();
				}
			}
		});

		// 상태 초기화
		view.getCancelButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});

	}

	public void initComponents() {
		setComboBoxValue();

		view.getSearchButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String input_pn = view.getProductNameField().getText().trim();
				String selected_category = (String) view.getCategoryComboBox().getSelectedItem();

				StringBuilder sql = new StringBuilder("SELECT p.product_name, c.categori_name, amount, "
						+ "unit_price, p.product_id, " + "s.storage_id, warehousing_date " + "FROM product p "
						+ "INNER JOIN categori c USING (categori_id) "
						+ "INNER JOIN orderrequest o ON p.product_id = o.product_id "
						+ "INNER JOIN storage s ON s.product_Id = p.product_Id "
						+ "INNER JOIN warehousing w ON w.storage_id = s.storage_id ");

				setSearchInfo(sql, input_pn, selected_category);

			}
		});

	}

	private void setComboBoxValue() {
		String sql = "SELECT DISTINCT categori_name FROM categori";

		try (Connection conn = connector.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();) {

			while (rs.next()) {
				String categori_name = rs.getString("categori_name");
				view.getCategoryComboBox().addItem(categori_name);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void setSearchInfo(StringBuilder sql, String input_pn, String selected_category) {
		if (!input_pn.isEmpty()) {
			sql.append("WHERE c.categori_name = ? AND p.product_name LIKE ?");
			input_pn = '%' + input_pn + '%';
		}

		sql.append(" ORDER BY product_name ASC, categori_name ASC");

		try (
				Connection conn = connector.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		) {

			if (!input_pn.isEmpty()) {
				pstmt.setString(1, selected_category);
				pstmt.setString(2, input_pn);
			}
			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next()) {
					model = new AmountModifyModel(rs);
					if (!input_pn.isEmpty()) {
						view.getAmount_txtField().setText(model.getAmount().toString());
						view.getLocation_txtArea().setText(model.getStorage_id());
						view.getSellPrice_txtArea().setText(model.getUnit_price().toString());
						view.getPid_txtArea().setText(model.getProduct_id());
						view.getWhdate_txtArea().setText(model.getWarehousing_date().toString());
					} else {
						// 상품명 입력하세요 알림창 생성
						JOptionPane.showMessageDialog(null, "상품명을 입력해주세요", "info", JOptionPane.INFORMATION_MESSAGE);
						break;
					}
				}

			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	private void updateAmount() {
		String input_pn = view.getProductNameField().getText().trim();
		String selected_category = (String) view.getCategoryComboBox().getSelectedItem();
		
		StringBuilder sql = new StringBuilder( 
				"UPDATE storage SET amount = ? "
				+ "WHERE product_id = ? AND storage_id = ?"				
				);
		// 상품명 입력 안할시 리턴
		if (input_pn.isEmpty()) {
			return;
		}
		try (
				Connection conn = connector.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		) {
				conn.setAutoCommit(false);
				
				String amount = view.getAmount_txtField().getText();
				
				pstmt.setInt(1, Integer.parseInt(amount));
				pstmt.setString(2, view.getPid_txtArea().getText());
				pstmt.setString(3, view.getLocation_txtArea().getText());
				
				try {
					int row = pstmt.executeUpdate();
					System.out.printf("%d행 업데이트\n", row);
					conn.setSavepoint("수정" + ++cnt);
				} catch (SQLException e2) {
					System.out.println("문제가 생겨서 롤백");
					conn.rollback();
				}
				
				
		} catch (SQLException e1) {
			e1.printStackTrace();
			
		}
	}
	public void resetFields() {
	    view.getProductNameField().setText("");
	    view.getCategoryComboBox().setSelectedIndex(0); // Assuming the first item is a placeholder like "Select Category"
	    view.getAmount_txtField().setText("");
	    view.getWhdate_txtArea().setText("");
	    view.getSellPrice_txtArea().setText("");
	    view.getPid_txtArea().setText("");
	    view.getLocation_txtArea().setText("");
	}

	@Override
	public String toString() {
		return "재고수정";
	}

}
