package main.java.com.teamcostco.view.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import main.utils.Constants;

public class OrderDetailPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public JPanel dataPanel;
	public JButton processButton;
	/**
	 * Create the panel.
	 */ 
	public OrderDetailPanel() {
		setLayout(new BorderLayout());



		// 데이터 표시 패널 설정
		dataPanel = new JPanel();
		dataPanel.setLayout(new GridLayout(0, 1));
		dataPanel.setBorder(new LineBorder(Color.BLACK, 1));
		add(dataPanel, BorderLayout.CENTER);

		// 하단 패널 설정
		JPanel bottomPanel = new JPanel(new BorderLayout());
		//bottomPanel.setBackground(Color.WHITE); // 배경색 설정

		// 입고처리 버튼 설정
		processButton = new JButton("입고처리");
		processButton.setFont(new Font("맑은 고딕", Font.PLAIN, 18)); // 글자 크기 키우기
		processButton.setFocusPainted(false); // 포커스 제거
		processButton.setBackground(Constants.BUTTON_BACKGROUND_COLOR); // 배경색 설정
		processButton.setForeground(Color.WHITE); // 글자색 설정
		processButton.setBorderPainted(false); // 테두리 제거
		bottomPanel.add(processButton, BorderLayout.CENTER);

		add(bottomPanel, BorderLayout.SOUTH);

	}
}
