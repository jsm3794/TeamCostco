package main.java.com.teamcostco.controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import main.java.com.teamcostco.MainForm;
import main.java.com.teamcostco.dao.OrderDetailDAO;
import main.java.com.teamcostco.model.OrderDetailModel;
import main.java.com.teamcostco.model.database.DatabaseUtil;
import main.java.com.teamcostco.model.manager.DialogManager;
import main.java.com.teamcostco.view.panels.WareHouseReceivingPanel;

public class WareHouseReceivingController extends PanelController<WareHouseReceivingPanel> {

	public OrderDetailModel data;

	public WareHouseReceivingController(OrderDetailModel data) {
		this.data = data;
		initControl();
	}

	private void initControl() {
		String[] lines = data.toString().split("\n");
		view.dataPanel.setLayout(new BoxLayout(view.dataPanel, BoxLayout.Y_AXIS));

		// 첫 번째 아이템 전에 상단 마진 추가
		view.dataPanel.add(Box.createVerticalStrut(5));

		for (String line : lines) {
			JPanel itemPanel = new JPanel();
			itemPanel.setBackground(Color.WHITE);
			itemPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
			itemPanel.setPreferredSize(new Dimension(390, 50)); // 너비를 약간 줄임
			itemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
			itemPanel.setLayout(new GridLayout(1, 2));

			String[] keyValue = line.split("=", 2);
			JLabel keyLabel = new JLabel(keyValue[0]);
			keyLabel.setBorder(new EmptyBorder(5, 10, 5, 10));
			keyLabel.setOpaque(true);
			keyLabel.setBackground(new Color(240, 240, 240));

			itemPanel.add(keyLabel);

			if (keyValue[0].equals("입고수량")) {
				view.receivingQuantityField = new JTextField();
				view.receivingQuantityField.setBorder(new EmptyBorder(5, 10, 5, 10));
				view.receivingQuantityField.setHorizontalAlignment(JTextField.CENTER);
				view.receivingQuantityField.setForeground(Color.BLUE);
				view.receivingQuantityField.setFont(view.receivingQuantityField.getFont().deriveFont(Font.BOLD));
				itemPanel.add(view.receivingQuantityField);
			} else {
				JLabel valueLabel = new JLabel(keyValue[1]);
				valueLabel.setBorder(new EmptyBorder(5, 10, 5, 10));
				valueLabel.setHorizontalAlignment(JLabel.CENTER);
				itemPanel.add(valueLabel);
			}

			JPanel wrapperPanel = new JPanel();
			wrapperPanel.setLayout(new BoxLayout(wrapperPanel, BoxLayout.X_AXIS));
			wrapperPanel.add(Box.createHorizontalStrut(5)); // 왼쪽 마진
			wrapperPanel.add(itemPanel);
			wrapperPanel.add(Box.createHorizontalStrut(5)); // 오른쪽 마진
			wrapperPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60)); // 높이를 약간 늘림

			view.dataPanel.add(wrapperPanel);
			view.dataPanel.add(Box.createVerticalStrut(5)); // 아이템 간 세로 간격
		}

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				view.receivingQuantityField.setText(String.valueOf(data.getRemainremainingQuantity()));
				view.receivingQuantityField.requestFocusInWindow();
				view.receivingQuantityField.selectAll();

			}
		});

		int remainingQuantity = data.getRemainremainingQuantity();
		if (remainingQuantity <= 0) {
			view.saveButton.setEnabled(false);
			view.getReceivingQuantityField().setEditable(false);
		}

		view.saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (view.getReceivingQuantityField().getText().isEmpty()) {
					DialogManager.showMessageBox(view, "수량확인필요", null);
					return;
				}

				int receivingQuantity = Integer.parseInt(view.getReceivingQuantityField().getText());

				if (receivingQuantity > remainingQuantity || receivingQuantity == 0) {
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
					
					// 입고상태 업데이트
					// 입고 상태 업데이트
					String updateStatusSql = "UPDATE orderrequest SET request_status = CASE " +
					    "WHEN quantity_of_wh = order_quantity THEN '입고완료' " +
					    "WHEN quantity_of_wh > 0 THEN '입고중' " +
					    "ELSE request_status END " +
					    "WHERE order_request_id = ?";

					try (Connection conn = DatabaseUtil.getConnection();
					     PreparedStatement pstmt = conn.prepareStatement(updateStatusSql)) {
					    pstmt.setInt(1, data.getOrderRequestId());
					    pstmt.executeUpdate();
					} catch (SQLException e1) {
					    e1.printStackTrace();
					}

					// 입고처리 후 발주상세조회창 업데이트

					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							MainForm.nav.pop();
							((OrderListController) MainForm.nav.getPrev()).searchDatabase();
							MainForm.nav
									.navigateTo("orderHistoryDetail", true,
											OrderDetailDAO.getOrderDetailModel(data.getOrderRequestId()))
									.thenRun(() -> {
										DialogManager.showMessageBox(MainForm.nav.getCurrent().getPanel(), "입고처리되었습니다.",
												null);
									});

						}
					});

				}, null);

			}
		});

	}

	private void saveReceivingQuantity() {
		String receivingQuantity = view.receivingQuantityField.getText();
		System.out.println("입고수량 저장: " + receivingQuantity);
		MainForm.nav.pop();
	}

	@Override
	public String toString() {
		return "입고처리";
	}
}