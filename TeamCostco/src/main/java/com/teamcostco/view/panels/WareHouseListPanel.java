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
import main.java.com.teamcostco.view.textfields.JPlaceholderTextField;
import main.utils.Constants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class WareHouseListPanel extends JPanel {
    private JTextField productNameField;
    private JTable productTable;
    private JButton searchButton;
    private ProductTableModel tableModel;  
    private JComboBox<String> disposalComboBox;
    private String[] disposalMethodItem = {
    		"DAM001 - 파손 (Damage)"
    				+ "DEF002 - 결함 (Defective)"
    				+ "EXP003- 유통기한 경과 (Expired)"
    				+ "MIS004 - 잘못된 사양 (Mismatched Specification)"
    				+ "CON005 - 오염 (Contaminated)"
    };
    
    
    // 불량사유 배열에 값을 추가합니다.
    public void addDmItem(String item) {
    	
    }
    

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
        
        disposalComboBox = new JComboBox<String>(disposalMethodItem);
        disposalComboBox.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        inputPanel.add(disposalComboBox);

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
