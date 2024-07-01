package main.java.com.teamcostco.view.panels;

import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import main.java.com.teamcostco.model.database.DatabaseUtil;
import main.utils.Constants;

import javax.swing.JTextArea;
import java.awt.Color;

// 구버전 입니다. (상품검색 후 재고 수정)

public class AmountModifyPanel extends JPanel {
	private JTextField productNameField;
	private JComboBox<String> categoryComboBox;
	private DefaultTableModel tableModel;
	private JButton searchButton;
	private JButton modifyButton;
	private JButton cancelButton;
	private JTextField amount_txtField;
	private JTextArea whdate_txtArea;
	private JTextArea sellPrice_txtArea;
	private JTextArea pid_txtArea;
	private JTextArea location_txtArea;

	public JTextField getProductNameField() {
		return productNameField;
	}

	public JComboBox<String> getCategoryComboBox() {
		return categoryComboBox;
	}

	public DefaultTableModel getTableModel() {
		return tableModel;
	}

	public JButton getSearchButton() {
		return searchButton;
	}

	public JButton getModifyButton() {
		return modifyButton;
	}

	public JButton getCancelButton() {
		return cancelButton;
	}

	public JTextField getAmount_txtField() {
		return amount_txtField;
	}

	public JTextArea getWhdate_txtArea() {
		return whdate_txtArea;
	}

	public JTextArea getSellPrice_txtArea() {
		return sellPrice_txtArea;
	}

	public JTextArea getPid_txtArea() {
		return pid_txtArea;
	}

	public JTextArea getLocation_txtArea() {
		return location_txtArea;
	}

	public AmountModifyPanel() {
		setBackground(new Color(240, 240, 240));
		JPanel inputPanel = new JPanel(new GridLayout(7, 2, 10, 10));
		inputPanel.setBackground(new Color(255, 255, 255));
		inputPanel.setBounds(12, 66, 332, 442);
		inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JLabel productNameLabel = new JLabel("제품명");
		inputPanel.add(productNameLabel);

		productNameField = new JTextField();
		inputPanel.add(productNameField);

		JLabel categoryLabel = new JLabel("대분류");
		inputPanel.add(categoryLabel);

		categoryComboBox = new JComboBox<String>();
		inputPanel.add(categoryComboBox);

		JLabel amountLabel = new JLabel("수량");
		inputPanel.add(amountLabel);

		tableModel = new DefaultTableModel(new String[] { "상품명", "카테고리", "적재위치" }, 0);

		JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 1, 1));
		buttonPanel.setBackground(new Color(240, 240, 240));
		buttonPanel.setBounds(-8, 540, 477, 43);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		modifyButton = new JButton("수정요청");
		modifyButton.setBackground(Constants.BUTTON_BACKGROUND_COLOR);
		modifyButton.setForeground(Constants.BUTTON_FOREGROUND_COLOR);
		buttonPanel.add(modifyButton);

		cancelButton = new JButton("취소");
		cancelButton.setBackground(Constants.BUTTON_BACKGROUND_COLOR);
		cancelButton.setForeground(Constants.BUTTON_FOREGROUND_COLOR);
		buttonPanel.add(cancelButton);
		setLayout(null);

		add(inputPanel);

		amount_txtField = new JTextField();
		inputPanel.add(amount_txtField);
		amount_txtField.setColumns(10);

		JLabel whDateLabel = new JLabel("입고날짜");
		inputPanel.add(whDateLabel);

		whdate_txtArea = new JTextArea();
		 whdate_txtArea.setEditable(false); // 사용자가 수정하지 못하도록 설정
		inputPanel.add(whdate_txtArea);

		JLabel sellPriceLabel = new JLabel("판매가격");
		inputPanel.add(sellPriceLabel);

		sellPrice_txtArea = new JTextArea();
		sellPrice_txtArea.setEditable(false); // 사용자가 수정하지 못하도록 설정
		inputPanel.add(sellPrice_txtArea);

		JLabel pidLabel = new JLabel("상품코드");
		inputPanel.add(pidLabel);

		pid_txtArea = new JTextArea();
		pid_txtArea.setEditable(false); // 사용자가 수정하지 못하도록 설정
		inputPanel.add(pid_txtArea);

		JLabel locationLabel = new JLabel("적재위치");
		inputPanel.add(locationLabel);

		location_txtArea = new JTextArea();
		location_txtArea.setEditable(false); // 사용자가 수정하지 못하도록 설정
		inputPanel.add(location_txtArea);
		add(buttonPanel);

		searchButton = new JButton("검색");
		searchButton.setBackground(new Color(6, 127, 196));
		searchButton.setBounds(344, 78, 113, 112);
		add(searchButton);
	}

	// test
	public static void main(String[] args) {
		JFrame frame = new JFrame("조정요청");
		frame.getContentPane().add(new AmountModifyPanel());
		frame.setSize(480, 640);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}
}
