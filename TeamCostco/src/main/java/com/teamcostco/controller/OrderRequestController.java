package main.java.com.teamcostco.controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import main.java.com.teamcostco.model.database.DatabaseUtil;
import main.java.com.teamcostco.view.panels.OrderRequestPanel;

public class OrderRequestController extends PanelController<OrderRequestPanel> {

	public OrderRequestController() {
		addResult();
	}

	private void addResult(String productName, int appQty, int curQty) {
		String str = String.format("<html>제품: %s<br>적정재고: %dEA<br>현재재고: %dEA<br>발주요청: %dEA</html>", productName, appQty,
				curQty, appQty - curQty);
		JLabel label = new JLabel(str);
		label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		label.setPreferredSize(new Dimension(400, 150)); // 예시로 고정된 크기 설정
		view.getResultPanel().add(label);
	}

	private void addResult() {
		String sql = "SELECT product_name, appropriate_inventory, current_inventory "
				+ "FROM product WHERE appropriate_inventory > current_inventory";
		try (Connection con = DatabaseUtil.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();) {

			while (rs.next()) {
				String name = rs.getString("product_name");
				int appQty = rs.getInt("appropriate_inventory");
				int curQty = rs.getInt("current_inventory");
				addResult(name, appQty, curQty);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void requestBtn() {
		view.getRequestBtn().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				List<String> list = new ArrayList<>();
				
				
				
			}
		});
	}

	@Override
	public String toString() {
		return "발주요청";
	}
}
