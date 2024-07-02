
package main.java.com.teamcostco.view.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import main.java.com.teamcostco.model.ProductTableModel;
import main.java.com.teamcostco.view.textfields.JPlaceholderTextField;
import main.utils.Constants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class WareHouseListPanel extends JPanel {
	JTextField productNameField;
	JComboBox<String> mainCateComboBox;
	JTable productTable;
	JButton searchButton;
	ProductTableModel tableModel;
	private JComboBox<String> mediumCateCombo;
	private JComboBox<String> smallCateCombo;
	private JPanel categoryPanel;

	public JTextField getProductNameField() {
		return productNameField;
	}

	public JTable getProductTable() {
		return productTable;
	}

	public JButton getSearchButton() {
		return searchButton;
	}

	public ProductTableModel getTableModel() {
		return tableModel;
	}

	public JComboBox<String> getMainCateComboBox() {
		return mainCateComboBox;
	}

	public JComboBox<String> getmediumCateCombo() {
		return mediumCateCombo;
	}

	public JComboBox<String> getSmallCateCombo() {
		return smallCateCombo;
	}

	public WareHouseListPanel() {
		setBackground(new Color(255, 255, 255));
		initializeUI();
	}

	private void initializeUI() {
		setLayout(new BorderLayout());

		// Create the input panel
		JPanel inputPanel = new JPanel();

		add(inputPanel, BorderLayout.NORTH);
		inputPanel.setLayout(new GridLayout(2, 2, 10, 10));

		productNameField = new JPlaceholderTextField("상품명을 입력해주세요");
		productNameField.setHorizontalAlignment(SwingConstants.CENTER);
		productNameField.setFont(new Font("맑은 고딕 Semilight", Font.PLAIN, 15));
		inputPanel.add(productNameField);

		categoryPanel = new JPanel();
		inputPanel.add(categoryPanel);
		categoryPanel.setLayout(new GridLayout(3, 1, 5, 5));

		mainCateComboBox = new JComboBox<>();
		categoryPanel.add(mainCateComboBox);

		mediumCateCombo = new JComboBox<>();
		categoryPanel.add(mediumCateCombo);
		mediumCateCombo.setEnabled(false);

		smallCateCombo = new JComboBox<>();
		categoryPanel.add(smallCateCombo);
		smallCateCombo.setEnabled(false);

		searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		searchButton.setBackground(Constants.BUTTON_BACKGROUND_COLOR);
		searchButton.setForeground(Constants.BUTTON_FOREGROUND_COLOR);
		inputPanel.add(searchButton);

		// Initialize the table model
		tableModel = new ProductTableModel(new ArrayList<>());
		productTable = new JTable(tableModel);
		productTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Disable auto resizing

		// Set preferred column widths
		TableColumnModel columnModel = productTable.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(100); // Product ID
		columnModel.getColumn(1).setPreferredWidth(200); // Product Name
		columnModel.getColumn(2).setPreferredWidth(150); // Main Category

		// Set table font
		productTable.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		productTable.setRowHeight(24);

		// Alternating row colors
		productTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				if (row % 2 == 0) {
					setBackground(new Color(240, 240, 240));
				} else {
					setBackground(Color.WHITE);
				}
				if (isSelected) {
					setBackground(new Color(184, 207, 229));
				}
				return this;
			}
		});

		// Add tooltip to table cells
		// productTable.setToolTipText("Double click to view details");

		JScrollPane scrollPane = new JScrollPane(productTable);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane, BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("조정요청");
		frame.getContentPane().add(new WareHouseListPanel());
		frame.setSize(480, 640); // Adjust the frame size as needed
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}