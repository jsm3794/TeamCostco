package main.java.com.teamcostco.controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import main.java.com.teamcostco.MainForm;
import main.java.com.teamcostco.model.OrderDetailModel;
import main.java.com.teamcostco.model.database.DatabaseUtil;
import main.java.com.teamcostco.model.manager.DialogManager;
import main.java.com.teamcostco.view.panels.OrderListPanel;

public class OrderListController extends PanelController<OrderListPanel> {

	public OrderListController() {
		initControl();
	}

	private void initControl() {

		view.searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DialogManager.Context context = DialogManager.showLoadingBox(view);
				new SwingWorker<Void, Void>() {

					@Override
					protected Void doInBackground() throws Exception {
						searchDatabase();
						return null;
					}

					@Override
					protected void done() {
						context.close();
						super.done();
					}

				}.execute();

			}
		});

		KeyListener enterListener = new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					view.searchButton.doClick();
				}
			}
		};

		view.startDateField.addKeyListener(enterListener);
		view.endDateField.addKeyListener(enterListener);
		view.itemNumberField.addKeyListener(enterListener);
		view.supplierField.addKeyListener(enterListener);
	}

	public void searchDatabase() {
		view.resultPanel.removeAll();

		LocalDate startDate = parseDate(view.startDateField.getText());
		LocalDate endDate = parseDate(view.endDateField.getText());

		List<OrderDetailModel> filteredData = new ArrayList<>();

		try (Connection conn = DatabaseUtil.getConnection()) {
			String sql = "SELECT " + "order_request_id, " + "o.product_code, " + "p.product_name AS product_name, "
					+ "m.main_name AS main_name, " + "orderemployee_id, " + "order_quantity, " + "request_date, "
					+ "request_status, " + "client_name, " + "quantity_of_wh " + "FROM ORDERREQUEST o "
					+ "INNER JOIN product p ON p.product_code = o.product_code "
					+ "INNER JOIN maincategory m ON p.main_id = m.main_id " + "WHERE 1=1 ";
			if (startDate != null) {
				sql += " AND REQUEST_DATE >= ?";
			}
			if (endDate != null) {
				sql += " AND REQUEST_DATE <= ?";
			}
			if (!view.itemNumberField.getText().trim().isEmpty()) {
				sql += " AND p.PRODUCT_NAME LIKE ?";
			}
			if (!view.supplierField.getText().trim().isEmpty()) {
				sql += " AND CLIENT_NAME LIKE ?";
			}
			if (view.waitingwarehousing.isSelected()) {
		        sql += " AND o.ORDER_QUANTITY > o.quantity_of_wh";
		    } else {
		        sql += " AND o.ORDER_QUANTITY <= o.quantity_of_wh";
		    }
		    
		    sql += " order by o.order_request_id DESC";


			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				int paramIndex = 1;
				if (startDate != null) {
					pstmt.setDate(paramIndex++, java.sql.Date.valueOf(startDate));
				}
				if (endDate != null) {
					pstmt.setDate(paramIndex++, java.sql.Date.valueOf(endDate));
				}
				if (!view.itemNumberField.getText().trim().isEmpty()) {
					pstmt.setString(paramIndex++, "%" + view.itemNumberField.getText().trim() + "%");
				}
				if (!view.supplierField.getText().trim().isEmpty()) {
					pstmt.setString(paramIndex++, "%" + view.supplierField.getText().trim() + "%");
				}

				try (ResultSet rs = pstmt.executeQuery()) {
					while (rs.next()) {
						filteredData.add(new OrderDetailModel(rs));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		for (OrderDetailModel data : filteredData) {
			JPanel entryPanel = createEntryPanel(data);
			if (entryPanel != null) {
				view.resultPanel.add(entryPanel);
			}
		}

		view.resultPanel.revalidate();
		view.resultPanel.repaint();
	}

	private JPanel createEntryPanel(OrderDetailModel data) {
		JPanel entryPanel = new JPanel();
		entryPanel.setLayout(new BoxLayout(entryPanel, BoxLayout.Y_AXIS));

		// 폰트 크기 설정
		Font labelFont = new Font("맑은 고딕", Font.PLAIN, 16); // 폰트 크기를 14로 설정

		// 라벨 생성 및 설정
		JLabel requestDateLabel = new JLabel("요청일자: " + data.getRequestDate());
		JLabel orderRequestIdLabel = new JLabel("발주요청ID: " + data.getOrderRequestId());
		JLabel clientLabel = new JLabel("거래처: " + data.getClientName());
		JLabel productNameLabel = new JLabel("상품명: " + data.getProductName());
		JLabel orderQuantityLabel = new JLabel("발주수량: " + data.getOrderQuantity());

		JLabel[] labels = { requestDateLabel, orderRequestIdLabel, productNameLabel, orderQuantityLabel, clientLabel };

		for (JLabel label : labels) {
			label.setFont(labelFont);
			label.setAlignmentX(Component.LEFT_ALIGNMENT); // 왼쪽 정렬
			entryPanel.add(label);
		}

		entryPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		entryPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, entryPanel.getPreferredSize().height));
		entryPanel.setPreferredSize(new Dimension(460, entryPanel.getPreferredSize().height));

		entryPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MainForm.nav.push("orderHistoryDetail", true, data);
			}
		});

		return entryPanel;
	}

	private LocalDate parseDate(String dateStr) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM/dd");
			return LocalDate.parse(dateStr, formatter);
		} catch (DateTimeParseException e) {
			return null;
		}
	}

	@Override
	public String toString() {
		return "발주목록";
	}
}