package main.java.com.teamcostco.view.panels;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class DefectiveInventoryPanel extends JPanel {
	private JTextField textField;
	private JTable table;

	public DefectiveInventoryPanel() {
		
		JPanel datePanel = new JPanel();
		datePanel.setBackground(Color.WHITE);
		datePanel.setBounds(8, 10, 359, 40);
		add(datePanel);
		GridBagLayout gbl_datePanel = new GridBagLayout();
		gbl_datePanel.columnWidths = new int[] { 75, 252, 0 };
		gbl_datePanel.rowHeights = new int[] { 35, 0 };
		gbl_datePanel.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_datePanel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		datePanel.setLayout(gbl_datePanel);

		JLabel dateLabel = new JLabel("창고위치");
		dateLabel.setFont(new Font("굴림", Font.BOLD, 12));
		GridBagConstraints gbc_dateLabel = new GridBagConstraints();
		gbc_dateLabel.anchor = GridBagConstraints.WEST;
		gbc_dateLabel.insets = new Insets(0, 0, 0, 5);
		gbc_dateLabel.gridx = 0;
		gbc_dateLabel.gridy = 0;
		datePanel.add(dateLabel, gbc_dateLabel);

		Choice choice = new Choice();
		GridBagConstraints gbc_choice = new GridBagConstraints();
		gbc_choice.fill = GridBagConstraints.HORIZONTAL;
		gbc_choice.gridx = 1;
		gbc_choice.gridy = 0;
		datePanel.add(choice, gbc_choice);
		choice.select("All");
		choice.add("All");
		choice.add("A1");
		choice.add("B1");
		choice.add("C1");
		choice.add("D1");
		choice.add("E1");

		JPanel datePanel_1 = new JPanel();
		datePanel_1.setBackground(Color.WHITE);
		datePanel_1.setBounds(8, 60, 359, 40);
		add(datePanel_1);
		GridBagLayout gbl_datePanel_1 = new GridBagLayout();
		gbl_datePanel_1.columnWidths = new int[] { 68, 231, 36, 0 };
		gbl_datePanel_1.rowHeights = new int[] { 35, 0 };
		gbl_datePanel_1.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_datePanel_1.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		datePanel_1.setLayout(gbl_datePanel_1);

		JLabel dateLabel_1 = new JLabel("자재명");
		dateLabel_1.setFont(new Font("굴림", Font.BOLD, 12));
		GridBagConstraints gbc_dateLabel_1 = new GridBagConstraints();
		gbc_dateLabel_1.anchor = GridBagConstraints.WEST;
		gbc_dateLabel_1.insets = new Insets(0, 0, 0, 5);
		gbc_dateLabel_1.gridx = 0;
		gbc_dateLabel_1.gridy = 0;
		datePanel_1.add(dateLabel_1, gbc_dateLabel_1);

		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.LEFT);
		textField.setColumns(10);
		textField.setBorder(null);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(5, 5, 0, 5);
		gbc_textField.fill = GridBagConstraints.VERTICAL;
		gbc_textField.anchor = GridBagConstraints.WEST;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		datePanel_1.add(textField, gbc_textField);

		JButton btnNewButton = new JButton("검색");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.VERTICAL;
		gbc_btnNewButton.gridx = 2;
		gbc_btnNewButton.gridy = 0;
		datePanel_1.add(btnNewButton, gbc_btnNewButton);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(8, 110, 359, 289);
		add(scrollPane);

		table = new JTable();

		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "상품ID", "상품이름", "잔여수량", "창고위치" }));

		scrollPane.setViewportView(table);
	}
}
