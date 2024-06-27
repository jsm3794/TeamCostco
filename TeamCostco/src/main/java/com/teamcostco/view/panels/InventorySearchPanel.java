package main.java.com.teamcostco.view.panels;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import main.java.com.teamcostco.view.textfields.JPlaceholderTextField;
import javax.swing.JLabel;

public class InventorySearchPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    public JButton searchButton;
    public JTextField searchField;
    public JComboBox<String> categoryComboBox;
    public JComboBox<String> cb_CategorizeName;
    public DefaultTableModel model;
    public JTable table;
    public JPlaceholderTextField textField;
    public JTable table_1;

    public InventorySearchPanel() {
        setLayout(null);
        setSize(new Dimension(480, 640));
        
     
        categoryComboBox = new JComboBox<>();
        categoryComboBox.addItem("전체"); 
        categoryComboBox.setBounds(119, 10, 153, 40);
        add(categoryComboBox);

        cb_CategorizeName = new JComboBox<>();
        cb_CategorizeName.addItem("상품분류");
        cb_CategorizeName.setSelectedIndex(0);
        cb_CategorizeName.setBounds(284, 10, 184, 40);
        add(cb_CategorizeName);
        
        textField = new JPlaceholderTextField("상품명");
        textField.setBounds(23, 60, 324, 43);
        add(textField);
        textField.setColumns(10);

       

        // Create the table with a DefaultTableModel
        model = new DefaultTableModel(new Object[][] {}, new String[] { "구역", "상품명", "수량" });
        table_1 = new JTable(model);

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table_1);
        scrollPane.setBounds(23, 141, 445, 404);
        add(scrollPane);

        searchButton = new JButton("검색");
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                // Implement search functionality here
            }
        });
        searchButton.setBounds(365, 60, 103, 43);
        add(searchButton);
        
        JLabel lblNewLabel = new JLabel("분류구분");
        lblNewLabel.setBounds(44, 23, 63, 15);
        add(lblNewLabel);
    }
}

