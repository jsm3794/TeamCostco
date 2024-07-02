
package main.java.com.teamcostco.view.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import main.java.com.teamcostco.model.ProductTableModel;
import main.java.com.teamcostco.view.textfields.JPlaceholderTextField;
import main.utils.Constants;

public class WareHouseListPanel extends JPanel {
    JTextField productNameField;
    JComboBox<String> mainCateComboBox;
    JTable productTable;
    JButton searchButton;
    ProductTableModel tableModel;
    private JComboBox<String> mediumCateCombo;
    private JComboBox<String> smallCateCombo;

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
        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(new Color(240, 240, 240));
        add(searchPanel, BorderLayout.NORTH);

        GridBagLayout gbl_searchPanel = new GridBagLayout();
        gbl_searchPanel.columnWidths = new int[]{239, 196}; // Divided into two columns
        gbl_searchPanel.rowHeights = new int[]{72, 25, 0}; // Two rows
        gbl_searchPanel.columnWeights = new double[]{0.7, 0.3}; // Adjusted weights based on layout requirements
        gbl_searchPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        searchPanel.setLayout(gbl_searchPanel);

        // Input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(new Color(240, 240, 240));
        inputPanel.setLayout(new BorderLayout(5, 5));

        productNameField = new JPlaceholderTextField("상품명을 입력해주세요");
        inputPanel.add(productNameField, BorderLayout.CENTER);
        productNameField.setHorizontalAlignment(SwingConstants.CENTER);
        productNameField.setFont(new Font("맑은 고딕", Font.PLAIN, 15));

        searchButton = new JButton("검색");
        inputPanel.add(searchButton, BorderLayout.EAST);
        searchButton.setBackground(Constants.BUTTON_BACKGROUND_COLOR);
        searchButton.setForeground(Constants.BUTTON_FOREGROUND_COLOR);
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Perform search action
            }
        });

        GridBagConstraints gbc_inputPanel = new GridBagConstraints();
        gbc_inputPanel.fill = GridBagConstraints.BOTH;
        gbc_inputPanel.insets = new Insets(5, 10, 5, 5); // Adjust insets to add spacing
        gbc_inputPanel.gridx = 0;
        gbc_inputPanel.gridy = 0;
        searchPanel.add(inputPanel, gbc_inputPanel);

        // Category panel
        JPanel categoryPanel = new JPanel();
        categoryPanel.setBackground(new Color(240, 240, 240));
        categoryPanel.setLayout(new GridLayout(3, 1, 5, 5));

        mainCateComboBox = new JComboBox<>();
        categoryPanel.add(mainCateComboBox);

        mediumCateCombo = new JComboBox<>();
        mediumCateCombo.setEnabled(false);
        categoryPanel.add(mediumCateCombo);

        smallCateCombo = new JComboBox<>();
        smallCateCombo.setEnabled(false);
        categoryPanel.add(smallCateCombo);

        GridBagConstraints gbc_categoryPanel = new GridBagConstraints();
        gbc_categoryPanel.anchor = GridBagConstraints.NORTH;
        gbc_categoryPanel.fill = GridBagConstraints.HORIZONTAL;
        gbc_categoryPanel.insets = new Insets(5, 5, 5, 10); // Adjust insets to add spacing
        gbc_categoryPanel.gridx = 1; // Position in the second column
        gbc_categoryPanel.gridy = 0;
        gbc_categoryPanel.gridheight = 2; // Span vertically to take up space
        searchPanel.add(categoryPanel, gbc_categoryPanel);

        // Initialize the table model
        tableModel = new ProductTableModel(new ArrayList<>());
        productTable = new JTable(tableModel);
        productTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Disable auto resizing

        // Set preferred column widths
        TableColumnModel columnModel = productTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(170); // 상품명
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(2).setPreferredWidth(80);
        columnModel.getColumn(3).setPreferredWidth(200); // 날짜

        // Set table font and row height
        productTable.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        productTable.setRowHeight(24);

        // Alternating row colors
        productTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    setBackground(new Color(184, 207, 229));
                } else {
                    setBackground(row % 2 == 0 ? new Color(240, 240, 240) : Color.WHITE);
                }
                return this;
            }
        });

        // Create the scroll pane and add the table to it
        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        // Adjust scroll bar policies
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Add the scroll pane to the center of the panel
        add(scrollPane, BorderLayout.CENTER);

    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("창고목록");
        frame.getContentPane().add(new WareHouseListPanel());
        frame.setSize(480, 640); // Adjust the frame size as needed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
