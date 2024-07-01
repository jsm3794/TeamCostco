package main.java.com.teamcostco.view.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import main.java.com.teamcostco.view.textfields.JPlaceholderTextField;
import main.utils.Constants;

public class InventorySearchPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	public JButton searchButton;
	public JPlaceholderTextField searchField;
	public JComboBox<String> categoryComboBox;
	public JComboBox<String> cb_CategorizeName;
	public DefaultTableModel model;
	public JTable table;

	public InventorySearchPanel() {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(480, 640));
		setBorder(new EmptyBorder(10, 10, 10, 10)); // 전체 패널에 5픽셀 인셋 유지

		// 상단 검색 패널 생성
		JPanel searchPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		searchPanel.setBorder(new EmptyBorder(0, 0, 10, 0)); // 일관된 5픽셀 인셋 적용

		// 대분류와 상품분류를 위한 새 패널 생성
		JPanel categoryPanel = new JPanel(new GridBagLayout());
		GridBagConstraints categoryGbc = new GridBagConstraints();
		categoryPanel.setBorder(new EmptyBorder(0, 0, 5, 0)); // 하단에만 5픽셀 인셋 적용

		JLabel lblNewLabel = new JLabel("분류구분");
		categoryGbc.gridx = 0;
		categoryGbc.gridy = 0;
		categoryGbc.anchor = GridBagConstraints.WEST;
		categoryGbc.insets = new Insets(0, 0, 0, 5); // 우측에 5픽셀 간격
		categoryPanel.add(lblNewLabel, categoryGbc);

		categoryComboBox = new JComboBox<>();
		categoryComboBox.addItem("전체");
		categoryComboBox.setPreferredSize(new Dimension(100, categoryComboBox.getPreferredSize().height));

		categoryGbc.gridx = 1;
		categoryGbc.insets = new Insets(0, 0, 0, 5); // 우측에 5픽셀 간격
		categoryPanel.add(categoryComboBox, categoryGbc);

		cb_CategorizeName = new JComboBox<>();
		cb_CategorizeName.addItem("상품분류");
		cb_CategorizeName.setSelectedIndex(0);
		categoryGbc.gridx = 2;
		categoryGbc.fill = GridBagConstraints.HORIZONTAL;
		categoryGbc.weightx = 1.0;
		categoryGbc.insets = new Insets(0, 0, 0, 0); // 마지막 컴포넌트는 우측 간격 없음
		categoryPanel.add(cb_CategorizeName, categoryGbc);

		searchField = new JPlaceholderTextField("검색어를 입력하세요");
		searchField.setPreferredSize(new Dimension(200, searchField.getPreferredSize().height));

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		gbc.insets = new Insets(5, 0, 0, 5); // 상단과 우측에 5픽셀 간격
		searchPanel.add(searchField, gbc);

		// 카테고리 패널을 검색 패널에 추가
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 3;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		gbc.insets = new Insets(0, 0, 0, 0); // 카테고리 패널은 이미 내부 간격이 있으므로 추가 간격 불필요
		searchPanel.add(categoryPanel, gbc);

		searchButton = new JButton("검색");
		searchButton.setBackground(Constants.BUTTON_BACKGROUND_COLOR);
		searchButton.setForeground(Constants.BUTTON_FOREGROUND_COLOR);
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.weightx = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(5, 0, 0, 0); // 상단에만 5픽셀 간격
		searchPanel.add(searchButton, gbc);

		// 상단 검색 패널을 NORTH에 추가
		add(searchPanel, BorderLayout.NORTH);

		

		
		// 테이블 생성 및 CENTER에 추가 부분을 다음과 같이 수정합니다
		model = new DefaultTableModel(new Object[][] {}, new String[] { "구역", "상품코드", "상품명", "수량" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table = new JTable(model);
		Font currentFont = table.getFont();
		Font newRowFont = currentFont.deriveFont(16f); // 현재 폰트에서 크기만 14로 변경
		table.setFont(newRowFont);
		
		table.setDefaultEditor(Object.class, null);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setRowHeight(30);

		// 테이블 너비를 퍼센트로 설정
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		int totalWidth = table.getPreferredSize().width;

		// 각 컬럼의 너비를 퍼센트로 설정
		int[] percentages = { 10, 30, 50, 10 }; // 각 컬럼의 퍼센트 (총합 100%)
		for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
			TableColumn column = table.getColumnModel().getColumn(i);
			int width = totalWidth * percentages[i] / 100;
			column.setPreferredWidth(width);
		}

		add(scrollPane, BorderLayout.CENTER);
	}

	// Getters
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public JButton getSearchButton() {
		return searchButton;
	}

	public JTextField getSearchField() {
		return searchField;
	}

	public JComboBox<String> getCategoryComboBox() {
		return categoryComboBox;
	}

	public JComboBox<String> getCb_CategorizeName() {
		return cb_CategorizeName;
	}

	public DefaultTableModel getModel() {
		return model;
	}

	public JTable getTable() {
		return table;
	}
}