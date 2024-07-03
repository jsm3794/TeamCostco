package main.java.com.teamcostco.controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import main.java.com.teamcostco.MainForm;
import main.java.com.teamcostco.model.OrderDetailModel;
import main.java.com.teamcostco.view.panels.OrderDetailPanel;

public class OrderDetailController extends PanelController<OrderDetailPanel> {

	public OrderDetailModel data;

	public OrderDetailController(OrderDetailModel data) {
		this.data = data;
		initControl();
	}

	private void initControl() {

		view.processButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainForm.nav.push("warehousereceiving", true, data);
			}

		});

		String[] lines = data.toString().split("\n");

		view.dataPanel.setLayout(new BoxLayout(view.dataPanel, BoxLayout.Y_AXIS));

		view.dataPanel.add(Box.createVerticalStrut(5));

		for (String line : lines) {
			JPanel itemPanel = new JPanel();
			itemPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
			itemPanel.setBackground(Color.WHITE);
			itemPanel.setPreferredSize(new Dimension(390, 50)); // 너비를 약간 줄임
			itemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
			itemPanel.setLayout(new GridLayout(1, 2));

			String[] keyValue = line.split("=", 2);
			JLabel keyLabel = new JLabel(keyValue[0]);
			keyLabel.setBorder(new EmptyBorder(5, 10, 5, 10));
			keyLabel.setOpaque(true);
			keyLabel.setBackground(new Color(240, 240, 240));
			JLabel valueLabel = new JLabel(keyValue[1]);
			valueLabel.setBorder(new EmptyBorder(5, 10, 5, 10));
			valueLabel.setHorizontalAlignment(JLabel.CENTER);
			itemPanel.add(keyLabel);
			itemPanel.add(valueLabel);

			JPanel wrapperPanel = new JPanel();
			wrapperPanel.setLayout(new BoxLayout(wrapperPanel, BoxLayout.X_AXIS));
			wrapperPanel.add(Box.createHorizontalStrut(5)); // 왼쪽 마진
			wrapperPanel.add(itemPanel);
			wrapperPanel.add(Box.createHorizontalStrut(5)); // 오른쪽 마진
			wrapperPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60)); // 높이를 약간 늘림

			view.dataPanel.add(wrapperPanel);
			view.dataPanel.add(Box.createVerticalStrut(5)); // 아이템 간 세로 간격
		}

		if (data.getOrderQuantity() <= data.getQuantityOfWh()) {
			view.processButton.setEnabled(false);
		}
	}

	@Override
	public String toString() {
		return "발주상세조회";
	}
}