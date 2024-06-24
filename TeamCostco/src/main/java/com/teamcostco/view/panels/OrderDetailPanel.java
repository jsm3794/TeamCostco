package main.java.com.teamcostco.view.panels;

import javax.swing.JPanel;
import java.awt.Rectangle;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTextPane;
import javax.swing.border.LineBorder;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OrderDetailPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public JPanel dataPanel;
	/**
	 * Create the panel.
	 */
	public OrderDetailPanel() {
		setLayout(new BorderLayout());

		// 상단 패널 설정
		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.setBackground(Color.WHITE); // 배경색 설정


		// "발주내역 상세보기" 텍스트를 표시할 라벨
		JLabel detailsLabel = new JLabel("발주내역 상세보기");
		detailsLabel.setFont(new Font("Serif", Font.PLAIN, 18));
		detailsLabel.setBorder(new LineBorder(Color.BLACK)); // 테두리 추가
		detailsLabel.setPreferredSize(new Dimension(200, 50)); // 크기 설정
		detailsLabel.setHorizontalAlignment(JLabel.CENTER); // 가운데 정렬
		detailsLabel.setOpaque(true); // 배경색을 위해 불투명 설정
		detailsLabel.setBackground(new Color(0x0099FF)); // ← 버튼과 같은 배경색 설정
		detailsLabel.setForeground(Color.WHITE);
		topPanel.add(detailsLabel, BorderLayout.CENTER);

		add(topPanel, BorderLayout.NORTH);

		// 데이터 표시 패널 설정
		dataPanel = new JPanel();
		dataPanel.setLayout(new GridLayout(0, 1));
		dataPanel.setBorder(new LineBorder(Color.BLACK, 1));
		add(dataPanel, BorderLayout.CENTER);

		// 하단 패널 설정
		JPanel bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.setBackground(Color.WHITE); // 배경색 설정

		// 입고처리 버튼 설정
		JButton processButton = new JButton("입고처리");
		processButton.setFont(new Font("Serif", Font.PLAIN, 18)); // 글자 크기 키우기
		processButton.setFocusPainted(false); // 포커스 제거
		processButton.setBackground(new Color(0x0099FF)); // 배경색 설정
		processButton.setForeground(Color.WHITE); // 글자색 설정
		processButton.setBorderPainted(false); // 테두리 제거
		bottomPanel.add(processButton, BorderLayout.CENTER);

		add(bottomPanel, BorderLayout.SOUTH);

	}
}
