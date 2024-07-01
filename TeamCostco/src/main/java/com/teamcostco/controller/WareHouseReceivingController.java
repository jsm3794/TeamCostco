package main.java.com.teamcostco.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import main.java.com.teamcostco.MainForm;
import main.java.com.teamcostco.dao.OrderDetailDAO;
import main.java.com.teamcostco.model.OrderDetailModel;
import main.java.com.teamcostco.model.database.DatabaseUtil;
import main.java.com.teamcostco.model.manager.DialogManager;
import main.java.com.teamcostco.view.panels.WarehouseReceivingPanel;

public class WareHouseReceivingController extends PanelController<WarehouseReceivingPanel> {

	public OrderDetailModel data;

	public WareHouseReceivingController(OrderDetailModel data) {
		this.data = data;
		initComponents();
	}

	private void initComponents() {
		// 잔량이 0이면 버튼 비활성화

		view.currentDateLabel.setText(getCurrentDate());
		view.productIdLabel.setText(data.getProductCode());
		view.categoryLabel.setText(data.getMain_name());
		view.productNameLabel.setText(data.getProductName());
		int remainingQuantity = data.getOrderQuantity() - data.getQuantityOfWh();
		if (remainingQuantity <= 0) {
			view.saveButton.setEnabled(false);
			view.getTextField().setEditable(false);
		}

		view.remaining_capacity.setText(String.valueOf(remainingQuantity));
		view.order_quantity.setText(String.valueOf(data.getOrderQuantity()));
		view.saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (view.getTextField().getText().isEmpty()) {
					DialogManager.showMessageBox(view, "수량확인필요", null);
					return;
				}
				
				int receivingQuantity = Integer.parseInt(view.getTextField().getText());
				
				
				if (receivingQuantity > remainingQuantity ||
					receivingQuantity == 0) {
					DialogManager.showMessageBox(view, "수량확인필요", null);
					return;
				}
				
				String alertMsg = String.format("발주번호: %s<br>상품명: %s<br>수량: %d개<br>입고처리하시겠습니까?",
						data.getOrderRequestId(), data.getProductName(), receivingQuantity);
				DialogManager.showMessageBox(view, alertMsg, evt -> {

					String sql = "INSERT INTO productinspection "
							+ "VALUES(product_inspection_seq.nextval, ?,?,?,sysdate)";
					try (Connection conn = DatabaseUtil.getConnection();
							PreparedStatement pstmt = conn.prepareStatement(sql);) {
						pstmt.setString(1, data.getProductCode());
						pstmt.setInt(2, data.getOrderQuantity());
						pstmt.setInt(3, receivingQuantity);
						pstmt.executeUpdate();

					} catch (SQLException e1) {

						e1.printStackTrace();
					}

					// orderrequest 입고수량 수정
					sql = "UPDATE orderrequest SET quantity_of_wh = quantity_of_wh + ? WHERE order_request_id = ?";
					try (Connection conn = DatabaseUtil.getConnection();
							PreparedStatement pstmt = conn.prepareStatement(sql)) {
						pstmt.setInt(1, receivingQuantity);
						pstmt.setInt(2, data.getOrderRequestId());
						pstmt.executeUpdate();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}

					// 입고처리 후 발주상세조회창 업데이트

					MainForm.nav.pop();
					MainForm.nav.navigateTo("orderHistoryDetail", true,
							OrderDetailDAO.getOrderDetailModel(data.getOrderRequestId()));

				}, null);

			}
		});

	}

	public static String getCurrentDate() {
		LocalDate now = LocalDate.now();
		int year = now.getYear();
		int month = now.getMonthValue();
		int day = now.getDayOfMonth();
		return year + "년 " + month + "월 " + day + "일";
	}

	@Override
	public String toString() {
		return "입고처리";
	}
}