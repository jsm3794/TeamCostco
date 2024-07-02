package main.java.com.teamcostco.controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.SwingUtilities;

import main.java.com.teamcostco.MainForm;
import main.java.com.teamcostco.dao.DefectProductDAO;
import main.java.com.teamcostco.dao.ProductDAO;
import main.java.com.teamcostco.model.Product;
import main.java.com.teamcostco.model.database.DatabaseUtil;
import main.java.com.teamcostco.model.manager.DialogManager;
import main.java.com.teamcostco.view.panels.AmountModifyPanel2;

public class AmountModify2Controller extends PanelController<AmountModifyPanel2> {

	private Product model;
	private DatabaseUtil connector;

	public AmountModify2Controller(Product model) {
		this.model = model;
		this.connector = new DatabaseUtil();
		initComponents();
		setupEventListeners();
	}

	private void initComponents() {
		getDetailInfo();
	}

	private void setupEventListeners() {
		// 수량 텍스트필드에 숫자만 입력되도록 제한
		view.getDefectAmountField().addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
					e.consume();
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
		view.getBtnAdjustRequest().addActionListener(this::handleAdjustRequest);
	}

	private void handleAdjustRequest(ActionEvent e) {
		DialogManager.showMessageBox(view, "수량을 수정하시겠습니까?", evt -> {
			if (checkValid()) {
				updateAmount();
			}
		}, null);
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
		view.getActiveInventoryLabel().setText(String.valueOf(model.getActive_inventory()));
		view.getPurchasePriceLabel().setText(String.valueOf(model.getPurchase_price()));
		view.getSellingPriceLabel().setText(String.valueOf(model.getSelling_price()));
		view.getProductCodeLabel().setText(model.getProduct_code());
	}

	private boolean checkValid() {
		String input = view.getDefectAmountField().getText();

		if (input.isEmpty()) {
			DialogManager.showMessageBox(view, "수량을 입력해주세요.", null);
			return false;
		}

		try {
			int userInput = Integer.parseInt(input);
			int activeInventory = model.getActive_inventory();

			if (userInput < 0 || userInput > activeInventory) {
				DialogManager.showMessageBox(view, "수량이 0이거나 기존보다<br>많을 수 없습니다.", null);
				return false;
			}
		} catch (NumberFormatException e) {
			DialogManager.showMessageBox(view, "올바른 숫자를 입력해주세요.", null);
			return false;
		}

		return true;
	}

	private void updateAmount() {
		String sql = "UPDATE product SET active_inventory = ? WHERE product_code = ?";

		try (Connection conn = connector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			conn.setAutoCommit(false);

			int currentAmount = model.getActive_inventory();
			int defectAmount = Integer.parseInt(view.getDefectAmountField().getText());
			int newAmount = currentAmount - defectAmount;

			pstmt.setInt(1, newAmount);
			pstmt.setString(2, model.getProduct_code());

			int row = pstmt.executeUpdate();
			if (row > 0) {
				conn.commit();
				handleSuccessfulUpdate(defectAmount);
			} else {
				conn.rollback();
				DialogManager.showMessageBox(view, "수정요청 실패", null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			DialogManager.showMessageBox(view, "데이터베이스 오류", null);
		}
	}

	private void handleSuccessfulUpdate(int defectAmount) {
		String adjustment = view.getAdjustmentReasonComboBox().getSelectedItem().toString().split(" ")[0];
		DefectProductDAO.insertDefectProduct(adjustment, String.valueOf(model.getProduct_code()), defectAmount);

		SwingUtilities.invokeLater(() -> {

			MainForm.nav.pop();
			InventorySearchController isc = (InventorySearchController) MainForm.nav
					.findLastControllerByClass(InventorySearchController.class);
			isc.search();

			MainForm.nav.navigateTo("productdetail", true, ProductDAO.getProductByCode(model.getProduct_code()))
					.thenRun(() -> {

						DialogManager.showMessageBox(MainForm.nav.getCurrent().getPanel(), "수정요청이 성공적으로 처리되었습니다.",
								null);

					});

		});

	}

	public void resetFields() {
		view.getProductNameLabel().setText("");
		view.getMainIdLabel().setText("");
		view.getMediumIdLabel().setText("");
		view.getSmallIdLabel().setText("");
		view.getCurrentInventoryLabel().setText("");
		view.getPurchasePriceLabel().setText("");
		view.getActiveInventoryLabel().setText("");
		view.getSellingPriceLabel().setText("");
		view.getProductCodeLabel().setText("");
		view.getAdjustmentReasonComboBox().setSelectedIndex(0);
	}

	@Override
	public String toString() {
		return "재고수정";
	}
}