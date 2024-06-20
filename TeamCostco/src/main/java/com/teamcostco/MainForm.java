package main.java.com.teamcostco;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.border.Border;

import main.java.com.teamcostco.component.Navigator;
import main.java.com.teamcostco.view.panels.DefectiveInventoryPanel;
import main.java.com.teamcostco.view.panels.OrderListPanel;
import main.java.com.teamcostco.view.panels.ReceivingProcessPanel;

public class MainForm {

	public static Navigator nav = new Navigator("orderlist");

	public static void main(String[] args) {

		JFrame frame = new JFrame("팀코스트코");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(480, 640);
		frame.setLayout(null);
		frame.setLocationRelativeTo(null);
		
		{
			// Navigator 매핑
			nav.mappingTarget("orderlist", OrderListPanel.class);
			nav.mappingTarget("receiving", ReceivingProcessPanel.class);
			nav.mappingTarget("defective", DefectiveInventoryPanel.class);

			// Navigator 사이즈 설정
			nav.setSize(new Dimension(480, 640));

			// 페이지 이동
			nav.navigateToHome();
		}

		frame.add(nav);
		frame.setVisible(true);
	}
}
