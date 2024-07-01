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
import main.java.com.teamcostco.model.manager.DialogManager;
import main.java.com.teamcostco.view.panels.AmountModifyPanel;

// 구버전 입니다 상품검색 후 재고를 수정합니다.

public class AmountModifyController extends PanelController<AmountModifyPanel> {

	private DatabaseUtil connector;
	private static int cnt = 0;
	private int current_inventory;

	public AmountModifyController() {
		connector = new DatabaseUtil();
		initComponents();

		// 수량 수정
		view.getModifyButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DialogManager.showMessageBox(view, "수량을 수정하시겠습니까?", evt -> {
					try {
						updateAmount();
					} catch (ExeedStorageAmount e1) {
						e1.printStackTrace();
					}
				}, null);
			}
		});

		// 상태 초기화
		view.getCancelButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				resetFields();
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

				StringBuilder sql = new StringBuilder(
						"SELECT product_name, current_inventory, active_inventory, selling_price, product_code FROM product "
								+ "INNER JOIN maincategory USING (main_id)");

				setSearchInfo(sql, input_pn, selected_category);

			}
		});

	}

	private void setComboBoxValue() {
		String sql = "SELECT main_name FROM maincategory";

		try (
				Connection conn = connector.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
		) {

			while (rs.next()) {
				String categori_name = rs.getString("main_name");
				view.getCategoryComboBox().addItem(categori_name);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void setSearchInfo(StringBuilder sql, String input_pn, String selected_category) {
		if (!input_pn.isEmpty()) {
			sql.append("WHERE main_name = ? AND product_name LIKE ?");
			input_pn = '%' + input_pn + '%';
		}

		sql.append(" ORDER BY product_name ASC, main_name ASC");

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
					if (!input_pn.isEmpty()) {
						String name = rs.getString("product_name");
						String amount = new String(rs.getInt("active_inventory") + "");
						String sellPrice = rs.getInt("selling_price") + "";
						String code = rs.getString("product_code");
						current_inventory = rs.getInt("current_inventory");
						view.getProductNameField().setText(name);
						view.getAmount_txtField().setText(amount);
						view.getSellPrice_txtArea().setText(sellPrice);
						view.getPid_txtArea().setText(code);

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

	private void updateAmount() throws ExeedStorageAmount {
		String input_pn = view.getProductNameField().getText().trim();
		String selected_category = (String) view.getCategoryComboBox().getSelectedItem();

		StringBuilder sql = new StringBuilder("UPDATE product SET active_inventory = ? " + "WHERE product_id = ?");
		// 상품명 입력 안할시 리턴
		if (input_pn.isEmpty()) {
			return;
		}
		try (
				Connection conn = connector.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		) {
			conn.setAutoCommit(false);

			Integer amount = Integer.parseInt(view.getAmount_txtField().getText());
			if (current_inventory < amount) {
				JOptionPane.showMessageDialog(null, "보유수량을 초과하는 수량입니다.");
				return;
			}

			pstmt.setInt(1, amount);
			pstmt.setString(2, view.getPid_txtArea().getText());

			try {
				int row = pstmt.executeUpdate();
				System.out.printf("%d행 업데이트\n", row);
				conn.commit();
				JOptionPane.showMessageDialog(view, "수정 완료!", "Success", JOptionPane.INFORMATION_MESSAGE);
			} catch (SQLException e2) {
				System.out.println("문제가 생겨서 롤백");
				conn.rollback();
				JOptionPane.showMessageDialog(view, "업데이트 실패!", "Error", JOptionPane.ERROR_MESSAGE);
			}

		} catch (SQLException e1) {
			e1.printStackTrace();

		}
	}

	public void resetFields() {
		view.getProductNameField().setText("");
		view.getCategoryComboBox().setSelectedIndex(0); // Assuming the first item is a placeholder like "Select
														// Category"
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

	private class NegativeAmount extends Exception {
		public NegativeAmount() {
			super("수량이 음수입니다.");
		}
	}

	private class ExeedStorageAmount extends Exception {
		public ExeedStorageAmount() {
			super("전체 보유 수량을 초과할 수 없습니다.");
		}
	}

	private class NonNumericValueException extends Exception {
		public NonNumericValueException() {
			super("숫자만 입력 가능합니다.");
		}
	}

}
