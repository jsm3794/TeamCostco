package main.java.com.teamcostco.view.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import main.java.com.teamcostco.MainForm;

public class ReceivingProcessPanel extends JPanel {

	private JTextField textField_1;
	private JTextField textField;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_5;
	private JTextField textField_4;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;

	public ReceivingProcessPanel() {
		setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(30, 75, 338, 40);
		add(panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 75, 252, 0 };
		gbl_panel_1.rowHeights = new int[] { 35, 0 };
		gbl_panel_1.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		JLabel lblNewLabel_1 = new JLabel("발주번호");
		lblNewLabel_1.setFont(new Font("굴림", Font.BOLD, 12));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 0;
		panel_1.add(lblNewLabel_1, gbc_lblNewLabel_1);

		textField_1 = new JTextField();
		textField_1.setHorizontalAlignment(SwingConstants.LEFT);
		textField_1.setColumns(10);
		textField_1.setBorder(null);
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.fill = GridBagConstraints.VERTICAL;
		gbc_textField_1.anchor = GridBagConstraints.WEST;
		gbc_textField_1.insets = new Insets(5, 5, 0, 0);
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 0;
		panel_1.add(textField_1, gbc_textField_1);

		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBackground(Color.WHITE);
		panel_1_1.setBounds(30, 125, 338, 40);
		add(panel_1_1);
		GridBagLayout gbl_panel_1_1 = new GridBagLayout();
		gbl_panel_1_1.columnWidths = new int[] { 75, 252, 0 };
		gbl_panel_1_1.rowHeights = new int[] { 35, 0 };
		gbl_panel_1_1.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_1_1.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel_1_1.setLayout(gbl_panel_1_1);

		JLabel lblNewLabel_1_1 = new JLabel("카테고리");
		lblNewLabel_1_1.setFont(new Font("굴림", Font.BOLD, 12));
		GridBagConstraints gbc_lblNewLabel_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_1_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_1_1.gridx = 0;
		gbc_lblNewLabel_1_1.gridy = 0;
		panel_1_1.add(lblNewLabel_1_1, gbc_lblNewLabel_1_1);

		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.LEFT);
		textField.setColumns(10);
		textField.setBorder(null);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(5, 5, 0, 0);
		gbc_textField.fill = GridBagConstraints.VERTICAL;
		gbc_textField.anchor = GridBagConstraints.WEST;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		panel_1_1.add(textField, gbc_textField);

		JPanel panel_1_1_1 = new JPanel();
		panel_1_1_1.setBackground(Color.WHITE);
		panel_1_1_1.setBounds(30, 25, 338, 40);
		add(panel_1_1_1);
		GridBagLayout gbl_panel_1_1_1 = new GridBagLayout();
		gbl_panel_1_1_1.columnWidths = new int[] { 75, 252, 0 };
		gbl_panel_1_1_1.rowHeights = new int[] { 35, 0 };
		gbl_panel_1_1_1.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_1_1_1.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel_1_1_1.setLayout(gbl_panel_1_1_1);

		JLabel lblNewLabel_1_1_1 = new JLabel("처리일자");
		lblNewLabel_1_1_1.setFont(new Font("굴림", Font.BOLD, 12));
		GridBagConstraints gbc_lblNewLabel_1_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_1_1_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1_1_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_1_1_1.gridx = 0;
		gbc_lblNewLabel_1_1_1.gridy = 0;
		panel_1_1_1.add(lblNewLabel_1_1_1, gbc_lblNewLabel_1_1_1);

		textField_2 = new JTextField();
		textField_2.setHorizontalAlignment(SwingConstants.LEFT);
		textField_2.setColumns(10);
		textField_2.setBorder(null);
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(5, 5, 0, 0);
		gbc_textField_2.fill = GridBagConstraints.VERTICAL;
		gbc_textField_2.anchor = GridBagConstraints.WEST;
		gbc_textField_2.gridx = 1;
		gbc_textField_2.gridy = 0;
		panel_1_1_1.add(textField_2, gbc_textField_2);

		JPanel panel_1_1_2 = new JPanel();
		panel_1_1_2.setBackground(Color.WHITE);
		panel_1_1_2.setBounds(30, 175, 338, 40);
		add(panel_1_1_2);
		GridBagLayout gbl_panel_1_1_2 = new GridBagLayout();
		gbl_panel_1_1_2.columnWidths = new int[] { 75, 252, 0 };
		gbl_panel_1_1_2.rowHeights = new int[] { 35, 0 };
		gbl_panel_1_1_2.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_1_1_2.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel_1_1_2.setLayout(gbl_panel_1_1_2);

		JLabel lblNewLabel_1_1_2 = new JLabel("상품명");
		lblNewLabel_1_1_2.setFont(new Font("굴림", Font.BOLD, 12));
		GridBagConstraints gbc_lblNewLabel_1_1_2 = new GridBagConstraints();
		gbc_lblNewLabel_1_1_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1_1_2.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_1_1_2.gridx = 0;
		gbc_lblNewLabel_1_1_2.gridy = 0;
		panel_1_1_2.add(lblNewLabel_1_1_2, gbc_lblNewLabel_1_1_2);

		textField_3 = new JTextField();
		textField_3.setHorizontalAlignment(SwingConstants.LEFT);
		textField_3.setColumns(10);
		textField_3.setBorder(null);
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.insets = new Insets(5, 5, 0, 0);
		gbc_textField_3.fill = GridBagConstraints.VERTICAL;
		gbc_textField_3.anchor = GridBagConstraints.WEST;
		gbc_textField_3.gridx = 1;
		gbc_textField_3.gridy = 0;
		panel_1_1_2.add(textField_3, gbc_textField_3);

		JPanel panel_1_1_2_1 = new JPanel();
		panel_1_1_2_1.setBackground(Color.WHITE);
		panel_1_1_2_1.setBounds(30, 228, 338, 40);
		add(panel_1_1_2_1);
		GridBagLayout gbl_panel_1_1_2_1 = new GridBagLayout();
		gbl_panel_1_1_2_1.columnWidths = new int[] { 75, 42, 0, 0, 0 };
		gbl_panel_1_1_2_1.rowHeights = new int[] { 35, 0 };
		gbl_panel_1_1_2_1.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_1_1_2_1.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel_1_1_2_1.setLayout(gbl_panel_1_1_2_1);

		JLabel lblNewLabel_1_1_2_1 = new JLabel("발주수량");
		lblNewLabel_1_1_2_1.setFont(new Font("굴림", Font.BOLD, 12));
		GridBagConstraints gbc_lblNewLabel_1_1_2_1 = new GridBagConstraints();
		gbc_lblNewLabel_1_1_2_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1_1_2_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_1_1_2_1.gridx = 0;
		gbc_lblNewLabel_1_1_2_1.gridy = 0;
		panel_1_1_2_1.add(lblNewLabel_1_1_2_1, gbc_lblNewLabel_1_1_2_1);

		textField_4 = new JTextField();
		textField_4.setText("0");
		textField_4.setHorizontalAlignment(SwingConstants.LEFT);
		textField_4.setColumns(10);
		textField_4.setBorder(null);
		GridBagConstraints gbc_textField_4 = new GridBagConstraints();
		gbc_textField_4.insets = new Insets(0, 0, 0, 5);
		gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_4.gridx = 1;
		gbc_textField_4.gridy = 0;
		panel_1_1_2_1.add(textField_4, gbc_textField_4);

		JLabel lblNewLabel_1_1_2_1_1 = new JLabel("입고수량");
		lblNewLabel_1_1_2_1_1.setFont(new Font("굴림", Font.BOLD, 12));
		GridBagConstraints gbc_lblNewLabel_1_1_2_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_1_1_2_1_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_1_1_2_1_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1_1_2_1_1.gridx = 2;
		gbc_lblNewLabel_1_1_2_1_1.gridy = 0;
		panel_1_1_2_1.add(lblNewLabel_1_1_2_1_1, gbc_lblNewLabel_1_1_2_1_1);

		textField_5 = new JTextField();
		textField_5.setText("0");
		textField_5.setHorizontalAlignment(SwingConstants.LEFT);
		textField_5.setColumns(10);
		textField_5.setBorder(null);
		GridBagConstraints gbc_textField_5 = new GridBagConstraints();
		gbc_textField_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_5.gridx = 3;
		gbc_textField_5.gridy = 0;
		panel_1_1_2_1.add(textField_5, gbc_textField_5);

		JPanel panel_1_1_2_1_1 = new JPanel();
		panel_1_1_2_1_1.setBackground(Color.WHITE);
		panel_1_1_2_1_1.setBounds(30, 281, 338, 40);
		add(panel_1_1_2_1_1);
		GridBagLayout gbl_panel_1_1_2_1_1 = new GridBagLayout();
		gbl_panel_1_1_2_1_1.columnWidths = new int[] { 75, 42, 0, 0, 0 };
		gbl_panel_1_1_2_1_1.rowHeights = new int[] { 35, 0 };
		gbl_panel_1_1_2_1_1.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_1_1_2_1_1.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel_1_1_2_1_1.setLayout(gbl_panel_1_1_2_1_1);

		JLabel lblNewLabel_1_1_2_1_2 = new JLabel("잔량");
		lblNewLabel_1_1_2_1_2.setFont(new Font("굴림", Font.BOLD, 12));
		GridBagConstraints gbc_lblNewLabel_1_1_2_1_2 = new GridBagConstraints();
		gbc_lblNewLabel_1_1_2_1_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1_1_2_1_2.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_1_1_2_1_2.gridx = 0;
		gbc_lblNewLabel_1_1_2_1_2.gridy = 0;
		panel_1_1_2_1_1.add(lblNewLabel_1_1_2_1_2, gbc_lblNewLabel_1_1_2_1_2);

		textField_6 = new JTextField();
		textField_6.setText("0");
		textField_6.setHorizontalAlignment(SwingConstants.LEFT);
		textField_6.setColumns(10);
		textField_6.setBorder(null);
		GridBagConstraints gbc_textField_6 = new GridBagConstraints();
		gbc_textField_6.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_6.insets = new Insets(0, 0, 0, 5);
		gbc_textField_6.gridx = 1;
		gbc_textField_6.gridy = 0;
		panel_1_1_2_1_1.add(textField_6, gbc_textField_6);

		JLabel lblNewLabel_1_1_2_1_1_1 = new JLabel("입고수량");
		lblNewLabel_1_1_2_1_1_1.setFont(new Font("굴림", Font.BOLD, 12));
		GridBagConstraints gbc_lblNewLabel_1_1_2_1_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_1_1_2_1_1_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1_1_2_1_1_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_1_1_2_1_1_1.gridx = 2;
		gbc_lblNewLabel_1_1_2_1_1_1.gridy = 0;
		panel_1_1_2_1_1.add(lblNewLabel_1_1_2_1_1_1, gbc_lblNewLabel_1_1_2_1_1_1);

		textField_7 = new JTextField();
		textField_7.setText("0");
		textField_7.setHorizontalAlignment(SwingConstants.LEFT);
		textField_7.setColumns(10);
		textField_7.setBorder(null);
		GridBagConstraints gbc_textField_7 = new GridBagConstraints();
		gbc_textField_7.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_7.gridx = 3;
		gbc_textField_7.gridy = 0;
		panel_1_1_2_1_1.add(textField_7, gbc_textField_7);

		JPanel panel_1_1_2_1_2 = new JPanel();
		panel_1_1_2_1_2.setBackground(Color.WHITE);
		panel_1_1_2_1_2.setBounds(30, 331, 338, 40);
		add(panel_1_1_2_1_2);
		GridBagLayout gbl_panel_1_1_2_1_2 = new GridBagLayout();
		gbl_panel_1_1_2_1_2.columnWidths = new int[] { 75, 98, 0, 0 };
		gbl_panel_1_1_2_1_2.rowHeights = new int[] { 35, 0 };
		gbl_panel_1_1_2_1_2.columnWeights = new double[] { 0.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_panel_1_1_2_1_2.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel_1_1_2_1_2.setLayout(gbl_panel_1_1_2_1_2);

		JLabel lblNewLabel_1_1_2_1_3 = new JLabel("창고위치");
		lblNewLabel_1_1_2_1_3.setFont(new Font("굴림", Font.BOLD, 12));
		GridBagConstraints gbc_lblNewLabel_1_1_2_1_3 = new GridBagConstraints();
		gbc_lblNewLabel_1_1_2_1_3.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1_1_2_1_3.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_1_1_2_1_3.gridx = 0;
		gbc_lblNewLabel_1_1_2_1_3.gridy = 0;
		panel_1_1_2_1_2.add(lblNewLabel_1_1_2_1_3, gbc_lblNewLabel_1_1_2_1_3);

		textField_8 = new JTextField();
		textField_8.setText("0");
		textField_8.setHorizontalAlignment(SwingConstants.LEFT);
		textField_8.setColumns(10);
		textField_8.setBorder(null);
		GridBagConstraints gbc_textField_8 = new GridBagConstraints();
		gbc_textField_8.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_8.insets = new Insets(0, 0, 0, 5);
		gbc_textField_8.gridx = 1;
		gbc_textField_8.gridy = 0;
		panel_1_1_2_1_2.add(textField_8, gbc_textField_8);

		textField_9 = new JTextField();
		textField_9.setText("0");
		textField_9.setHorizontalAlignment(SwingConstants.LEFT);
		textField_9.setColumns(10);
		textField_9.setBorder(null);
		GridBagConstraints gbc_textField_9 = new GridBagConstraints();
		gbc_textField_9.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_9.gridx = 2;
		gbc_textField_9.gridy = 0;
		panel_1_1_2_1_2.add(textField_9, gbc_textField_9);

		JButton btnNewButton = new JButton("저장");
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainForm.nav.push("defective", true);
			}
		});
		btnNewButton.setBounds(10, 501, 359, 64);
		add(btnNewButton);

	}
}
