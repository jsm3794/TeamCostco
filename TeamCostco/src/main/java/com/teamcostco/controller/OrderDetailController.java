package main.java.com.teamcostco.controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import main.java.com.teamcostco.model.OrderModel;
import main.java.com.teamcostco.view.panels.OrderDetailPanel;

public class OrderDetailController extends PanelController<OrderDetailPanel> {
	
	public OrderModel data;

	public OrderDetailController(OrderModel data) {
		this.data = data;
		initControl(data);
	}

	private void initControl(OrderModel data) {

		for (String line : data.toString().split("\n")) {
			JLabel label = new JLabel(line);
			label.setFont(new Font("Serif", Font.PLAIN, 18)); // 글자 크기 키우기
			label.setBorder(new LineBorder(Color.BLACK)); // 테두리 추가
			label.setPreferredSize(new Dimension(400, 50)); // 각 항목의 크기 설정
			label.setOpaque(true);
			label.setBackground(Color.WHITE);
			label.setHorizontalAlignment(JLabel.CENTER); // 가운데 정렬
			view.dataPanel.add(label);
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "발주상세조회";
	}
}