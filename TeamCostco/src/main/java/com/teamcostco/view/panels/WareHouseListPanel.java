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

import main.java.com.teamcostco.model.ProductTableModel;
import main.utils.Constants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class WareHouseListPanel extends JPanel {
    JTextField productNameField;
    JComboBox<String> mainCateComboBox;
    JTable productTable;
    JButton searchButton;
    ProductTableModel tableModel;
    private JComboBox<String> midiumCateCombo;
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

    public JComboBox<String> getMidiumCateCombo() {
        return midiumCateCombo;
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
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        productNameField = new JTextField(15);
        inputPanel.add(productNameField);

        add(inputPanel, BorderLayout.NORTH);

        categoryPanel = new JPanel();
        inputPanel.add(categoryPanel);
        categoryPanel.setLayout(new GridLayout(3, 1, 5, 5));

        mainCateComboBox = new JComboBox<>();
        categoryPanel.add(mainCateComboBox);

        midiumCateCombo = new JComboBox<>();
        categoryPanel.add(midiumCateCombo);
        midiumCateCombo.setEnabled(false);

        smallCateCombo = new JComboBox<>();
        categoryPanel.add(smallCateCombo);
        smallCateCombo.setEnabled(false);

        searchButton = new JButton("Search");
        searchButton.setBackground(Constants.BUTTON_BACKGROUND_COLOR);
        searchButton.setForeground(Constants.BUTTON_FOREGROUND_COLOR);
        inputPanel.add(searchButton);

        // Initialize the table model
        tableModel = new ProductTableModel(new ArrayList<>());
        productTable = new JTable(tableModel);
        add(new JScrollPane(productTable), BorderLayout.CENTER);
    }

   

    public static void main(String[] args) {
        JFrame frame = new JFrame("조정요청");
        frame.getContentPane().add(new WareHouseListPanel());
        frame.setSize(480, 640);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
