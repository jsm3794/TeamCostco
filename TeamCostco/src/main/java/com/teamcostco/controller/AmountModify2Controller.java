package main.java.com.teamcostco.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import main.java.com.teamcostco.model.Product;
import main.java.com.teamcostco.model.database.DatabaseUtil;
import main.java.com.teamcostco.model.manager.DialogManager;
import main.java.com.teamcostco.view.panels.AmountModifyPanel2;

public class AmountModify2Controller extends PanelController<AmountModifyPanel2> {

	private Product model;
	private DatabaseUtil connector;

	public AmountModify2Controller(Product model) {
		connector = new DatabaseUtil();
		this.model = model;
		initComponents();

		// 수량 텍스트필드에 숫자만 입력되도록 제한
		view.getActiveInventoryTextField().addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				try {
					char c = e.getKeyChar();
					if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
						e.consume();
						throw new NonNumericValueException();
					}
				} catch (NonNumericValueException ex) {
					JOptionPane.showMessageDialog(view, ex.getMessage(), "Invalid Input", JOptionPane.WARNING_MESSAGE);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});

		// 조정요청 버튼
		view.getBtnAdjustRequest().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DialogManager.showMessageBox(view, "수량을 수정하시겠습니까?", evt -> updateAmount(), null);
			}
		});
		
	}

	public void initComponents() {
		getDetailInfo();
	}

	private void getDetailInfo() {
		// Fetch and display product details from the model
		view.getProductIdLabel().setText(String.valueOf(model.getProduct_id()));
		view.getProductNameLabel().setText(model.getProduct_name());
		view.getMainIdLabel().setText(model.getMain_id());
		view.getMediumIdLabel().setText(model.getMedium_id());
		view.getSmallIdLabel().setText(model.getSmall_id());
		view.getAppropriateInventoryLabel().setText(String.valueOf(model.getAppropriate_inventory()));
		view.getCurrentInventoryLabel().setText(String.valueOf(model.getCurrent_inventory()));
		view.getActiveInventoryTextField().setText(String.valueOf(model.getActive_inventory()));
		view.getPurchasePriceLabel().setText(String.valueOf(model.getPurchase_price()));
		view.getSellingPriceLabel().setText(String.valueOf(model.getSelling_price()));
		view.getProductCodeLabel().setText(model.getProduct_code());
		// Set other fields as needed
	}

	private void updateAmount() {
		String sql = "UPDATE product SET amount = ? WHERE product_id = ?";

		try (Connection conn = connector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			conn.setAutoCommit(false);

			int amount = Integer.parseInt(view.getActiveInventoryTextField().getText());
			pstmt.setInt(1, amount);
			pstmt.setInt(2, model.getProduct_id());

			int row = pstmt.executeUpdate();
			if (row > 0) {
				conn.commit();
				JOptionPane.showMessageDialog(view, "수정 완료!", "Success", JOptionPane.INFORMATION_MESSAGE);
			} else {
				conn.rollback();
				JOptionPane.showMessageDialog(view, "업데이트 실패!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(view, "데이터베이스 오류", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void resetFields() {
		view.getProductNameLabel().setText("");
		view.getMainIdLabel().setText("");
		view.getMediumIdLabel().setText("");
		view.getSmallIdLabel().setText("");
		view.getActiveInventoryTextField().setText("0");
		view.getPurchasePriceLabel().setText("");
		view.getSellingPriceLabel().setText("");
		view.getProductCodeLabel().setText("");
		view.getAdjustmentReasonComboBox().setSelectedIndex(0);
	}

	@Override
	public String toString() {
		return "재고수정";
	}
}

// 예외 클래스들은 그대로 유지
class NegativeAmount extends Exception {
	public NegativeAmount() {
		super("수량이 음수입니다.");
	}
}

class ExeedStorageAmount extends Exception {
	public ExeedStorageAmount() {
		super("창고보관수량을 초과하는 수량입니다.");
	}
}

class NonNumericValueException extends Exception {
	public NonNumericValueException() {
		super("숫자만 입력 가능합니다.");
	}
}