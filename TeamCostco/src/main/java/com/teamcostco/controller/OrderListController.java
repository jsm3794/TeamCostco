package main.java.com.teamcostco.controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
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

import main.java.com.teamcostco.MainForm;
import main.java.com.teamcostco.model.OrderModel;
import main.java.com.teamcostco.view.panels.OrderListPanel;

public class OrderListController extends PanelController<OrderListPanel> {

	private static final String DB_URL = "jdbc:oracle:thin:@3.34.139.200:1521:xe"; // 데이터베이스 URL
	private static final String DB_USER = "TeamCostco"; // 데이터베이스 사용자 이름
	private static final String DB_PASSWORD = "1234"; // 데이터베이스 비밀번호

	public OrderListController() {
		initControl();
	}

	private void initControl() {

		view.startDateField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				searchDatabase();
			}
		});

		view.endDateField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				searchDatabase();
			}
		});

		// 수량
		view.itemNumberField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				searchDatabase();
			}
		});

		// 거래처
		view.supplierField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				searchDatabase();
			}
		});

		// 검색 버튼 동작
		view.searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				searchDatabase();
			}
		});

		// 엔터 키로 검색 동작 설정
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
					searchDatabase();
				}
			}
		};

		view.startDateField.addKeyListener(enterListener);
		view.endDateField.addKeyListener(enterListener);
		view.itemNumberField.addKeyListener(enterListener);
		view.supplierField.addKeyListener(enterListener);

	}

	private void searchDatabase() {
		view.resultPanel.removeAll(); // 기존 결과 제거

		// 입력된 일자 파싱
		LocalDate startDate = parseDate(view.startDateField.getText());
		LocalDate endDate = parseDate(view.endDateField.getText());

		List<OrderModel> filteredData = new ArrayList<>();

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
			String sql = "SELECT * FROM orderrequest WHERE 1=1";
			if (startDate != null) {
				sql += " AND order_date >= ?";
			}
			if (endDate != null) {
				sql += " AND order_date <= ?";
			}
			if (!view.itemNumberField.getText().trim().isEmpty()) {
				// 부분 일치 검색을 위해 % 기호 추가
				sql += " AND item_number LIKE ?";
			}
			if (!view.supplierField.getText().trim().isEmpty()) {
				// 부분 일치 검색을 위해 % 기호 추가
				sql += " AND client LIKE ?";
			}

			// PreparedStatement 설정 부분에서 문자열 앞뒤로 % 기호 추가
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				int paramIndex = 1;
				if (startDate != null) {
					pstmt.setDate(paramIndex++, java.sql.Date.valueOf(startDate));
				}
				if (endDate != null) {
					pstmt.setDate(paramIndex++, java.sql.Date.valueOf(endDate));
				}
				if (!view.itemNumberField.getText().trim().isEmpty()) {
					// 사용자 입력값에서 공백 제거하고 % 기호 추가
					pstmt.setString(paramIndex++, "%" + view.itemNumberField.getText().trim() + "%");
				}
				if (!view.supplierField.getText().trim().isEmpty()) {
					// 사용자 입력값에서 공백 제거하고 % 기호 추가
					pstmt.setString(paramIndex++, "%" + view.supplierField.getText().trim() + "%");
				}
				// 나머지 코드는 동일하게 유지

				try (ResultSet rs = pstmt.executeQuery()) {
					while (rs.next()) {
						filteredData.add(new OrderModel(rs));
					}

				}
			}
		} catch (SQLException e) {
			System.out.println("연결안됨");
		}

		for (OrderModel data : filteredData) {
			JPanel entryPanel = createEntryPanel(data);
			if (entryPanel != null) {
				view.resultPanel.add(entryPanel);
			}
		}

		// 결과 패널 업데이트
		view.resultPanel.revalidate();
		view.resultPanel.repaint();
	}

	private JPanel createEntryPanel(OrderModel data) {
		JPanel entryPanel = new JPanel();
		entryPanel.setLayout(new BoxLayout(entryPanel, BoxLayout.Y_AXIS));
		for (String line : data.toString().split("\n")) {
			JLabel label = new JLabel(line);
			if (line.startsWith("입고수량:")) {
				label.setFont(label.getFont().deriveFont(Font.BOLD, 14)); // 글씨 크기 설정
			}
			entryPanel.add(label);
		}
		entryPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		entryPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, entryPanel.getPreferredSize().height));
		entryPanel.setPreferredSize(new Dimension(460, entryPanel.getPreferredSize().height));

		entryPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MainForm.nav.push("orderdetail", true, data);
			}
		});
		return entryPanel;
	}

	private LocalDate parseDate(String dateStr) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			return LocalDate.parse(dateStr, formatter);
		} catch (DateTimeParseException e) {
			return null; // 날짜 파싱에 실패하면 null 반환
		}
	}

	@Override
	public String toString() {
		return "발주목록";
	}

}