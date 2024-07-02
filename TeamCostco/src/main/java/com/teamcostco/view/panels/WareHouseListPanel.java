package main.java.com.teamcostco.view.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import main.java.com.teamcostco.model.ProductTableModel;
import main.java.com.teamcostco.view.textfields.JPlaceholderTextField;
import main.utils.Constants;

import java.awt.Font;

public class WareHouseListPanel extends JPanel {
    private JTextField productNameField;
    private JTable productTable;
    private JButton searchButton;
    private ProductTableModel tableModel;  
    private JComboBox<String> disposalComboBox;
    private static String[] disposalMethodItem = {
            "모든 불량사유"
            , "DAM001 - 파손 (Damage)"
            , "DEF002 - 결함 (Defective)"
            , "EXP003 - 유통기한 경과 (Expired)"
            , "MIS004 - 잘못된 사양 (Mismatched Specification)"
            , "CON005 - 오염 (Contaminated)"
            , "MSW006 - 창고로 재고이동 (Moving Stock to Warehouse)"
    };

    public WareHouseListPanel() {
        setBackground(new Color(255, 255, 255));
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Create the input panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        productNameField = new JPlaceholderTextField("상품명 입력");
        inputPanel.add(productNameField);

        add(inputPanel, BorderLayout.NORTH);
        
        disposalComboBox = new JComboBox<>(disposalMethodItem);
        disposalComboBox.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        inputPanel.add(disposalComboBox);

        searchButton = new JButton("Search");
        searchButton.setBackground(Constants.BUTTON_BACKGROUND_COLOR);
        searchButton.setForeground(Constants.BUTTON_FOREGROUND_COLOR);
        inputPanel.add(searchButton);

        // Initialize the table model
        tableModel = new ProductTableModel(new ArrayList<>());
        productTable = new JTable(tableModel);

        // Set column widths
        setTableColumnWidths();

        // Disable auto resize mode for the table to allow horizontal scrolling
        productTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Add horizontal scroll pane
        JScrollPane scrollPane = new JScrollPane(productTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void setTableColumnWidths() {
        int[] columnWidths = {100, 200, 300, 150}; // Adjusted column widths
        TableColumnModel columnModel = productTable.getColumnModel();
        for (int i = 0; i < columnWidths.length; i++) {
            TableColumn column = columnModel.getColumn(i);
            column.setPreferredWidth(columnWidths[i]);
        }
    }

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

    public JComboBox<String> getDisposalComboBox() {
        return disposalComboBox;
    }

    public static String[] getDisposalMethodItem() {
        return disposalMethodItem;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("불량목록");
        frame.getContentPane().add(new WareHouseListPanel());
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
