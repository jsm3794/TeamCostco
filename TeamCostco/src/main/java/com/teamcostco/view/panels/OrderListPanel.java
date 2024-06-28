package main.java.com.teamcostco.view.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class OrderListPanel extends JPanel {

	public JTextField startDateField;
	public JTextField endDateField;
	public JTextField itemNumberField;
	public JTextField supplierField;
	
	public JPanel resultPanel;
	public JPanel searchPanel;
	public JLabel dateLabel;
	public JPanel datePanel;
	public ButtonGroup buttonGroup;
	public JRadioButton waitingwarehousing;
	public JRadioButton completedwarehousing;


	public JLabel supplierLabel;
	public JButton searchButton;

	public OrderListPanel() {
		// 패널 설정
		setLayout(new BorderLayout());

		// 검색 조건 패널
		searchPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5); // 간격 설정

		// 발주 일자
		dateLabel = new JLabel("발주일자");
		gbc.gridx = 0;
		gbc.gridy = 0;
		searchPanel.add(dateLabel, gbc);

		datePanel = new JPanel(new FlowLayout());
		startDateField = new JTextField(11);
		startDateField.setHorizontalAlignment(SwingConstants.CENTER);
		endDateField = new JTextField(9);
		endDateField.setHorizontalAlignment(SwingConstants.CENTER);
		datePanel.add(startDateField);
		datePanel.add(new JLabel("~"));
		datePanel.add(endDateField);
		gbc.gridx = 1;
		gbc.gridy = 0;
		searchPanel.add(datePanel, gbc);

		// 품번
		JLabel itemNumberLabel = new JLabel("품번");
		gbc.gridx = 0;
		gbc.gridy = 1;
		searchPanel.add(itemNumberLabel, gbc);
		itemNumberField = new JTextField(10);
		gbc.gridx = 1;
		gbc.gridy = 1;
		searchPanel.add(itemNumberField, gbc);

		// 거래처
		JLabel supplierLabel = new JLabel("거래처");
		gbc.gridx = 0;
		gbc.gridy = 2;
		searchPanel.add(supplierLabel, gbc);
		supplierField = new JTextField(10);

		gbc.gridx = 1;
		gbc.gridy = 2;
		searchPanel.add(supplierField, gbc);
		
		buttonGroup = new ButtonGroup();
		
		waitingwarehousing = new JRadioButton("입고대기");
		waitingwarehousing.setSelected(true);
		buttonGroup.add(waitingwarehousing);
		
		completedwarehousing = new JRadioButton("입고완료");
		buttonGroup.add(completedwarehousing);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		searchPanel.add(waitingwarehousing, gbc);
		gbc.gridx = 0;
		gbc.gridy = 4;
		searchPanel.add(completedwarehousing, gbc);

		// 검색 버튼
		searchButton = new JButton("검색");
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 2; // 2개의 셀을 차지하도록 설정
		gbc.anchor = GridBagConstraints.CENTER; // 가운데 정렬
		gbc.weightx = 1.0; // X 방향으로 전체 공간을 균등하게 차지
		searchPanel.add(searchButton, gbc);
		searchButton.setBackground(new Color(6, 127, 196));
		searchButton.setForeground(new Color(255, 255, 255));

		// 결과 패널
		resultPanel = new JPanel();
		resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
		JScrollPane scrollPane = new JScrollPane(resultPanel);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);

		// 패널에 패널 추가
		add(searchPanel, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
	}

}