package main.java.com.teamcostco.view.panels;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import main.utils.Constants;

public class AmountModifyPanel2 extends JPanel {
    private JTextField productNameField;
    private JComboBox<String> categoryComboBox;
    
    private JButton modifyButton;
    private JButton cancelButton;
    private JTextField amount_txtField;
    private JTextArea whdate_txtArea;
    private JTextArea sellPrice_txtArea;
    private JTextArea pid_txtArea;
    private JTextArea location_txtArea;
    private JButton plusButton;
    private JButton minusButton;
    private JComboBox<String> mr_comboBox;
    
    private String[] modification_reason = new String[] {
            "DAM001 - 파손 (Damage)",
            "DEF002 - 결함 (Defective)",
            "EXP003- 유통기한 경과 (Expired)",
            "MIS004 - 잘못된 사양 (Mismatched Specification)",
            "CON005 - 오염 (Contaminated)",
            "MSW006 - 창고로 재고이동 (Moving Stock to Warehouse)",
            "IWI007 - 창고에서 재고 추가 (Import Warehouse Inventory)"
    };

   
	public JTextField getProductNameField() {
		return productNameField;
	}

	public JComboBox<String> getCategoryComboBox() {
		return categoryComboBox;
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

	public JButton getPlusButton() {
		return plusButton;
	}

	public JButton getMinusButton() {
		return minusButton;
	}

	public String[] getModification_reason() {
		return modification_reason;
	}

	
	public JComboBox<String> getMr_comboBox() {
		return mr_comboBox;
	}

	public AmountModifyPanel2() {
        setBackground(new Color(240, 240, 240));
        JPanel inputPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        inputPanel.setBackground(new Color(240, 240, 240));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel productNameLabel = new JLabel("제품명");
        productNameLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        inputPanel.add(productNameLabel);

        productNameField = new JTextField();
        productNameField.setHorizontalAlignment(SwingConstants.CENTER);
        productNameField.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        productNameField.setEditable(false); // 사용자가 수정하지 못하도록 설정
        inputPanel.add(productNameField);

        JLabel categoryLabel = new JLabel("대분류");
        categoryLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        inputPanel.add(categoryLabel);

        categoryComboBox = new JComboBox<String>();
        categoryComboBox.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        categoryComboBox.setEnabled(false); // 사용자가 수정하지 못하도록 설정
        inputPanel.add(categoryComboBox);

        JLabel amountLabel = new JLabel("판매중인 수량");
        amountLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        inputPanel.add(amountLabel);

        // Add a panel for the amount field and buttons
        JPanel amountPanel = new JPanel(new BorderLayout(1, 1));
        amount_txtField = new JTextField("0");
        amount_txtField.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        amount_txtField.setHorizontalAlignment(SwingConstants.CENTER);
        amountPanel.add(amount_txtField, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1));
        
        plusButton = new JButton("+");
        plusButton.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        buttonPanel.add(plusButton);
                
                       
        minusButton = new JButton("-");
        minusButton.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        buttonPanel.add(minusButton);

        amountPanel.add(buttonPanel, BorderLayout.EAST);

        inputPanel.add(amountPanel);

        JLabel whDateLabel = new JLabel("입고날짜");
        whDateLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        inputPanel.add(whDateLabel);

        whdate_txtArea = new JTextArea();
        whdate_txtArea.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        whdate_txtArea.setEditable(false); // 사용자가 수정하지 못하도록 설정
        inputPanel.add(whdate_txtArea);

        JLabel sellPriceLabel = new JLabel("판매가격");
        sellPriceLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        inputPanel.add(sellPriceLabel);

        sellPrice_txtArea = new JTextArea();
        sellPrice_txtArea.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        sellPrice_txtArea.setEditable(false); // 사용자가 수정하지 못하도록 설정
        inputPanel.add(sellPrice_txtArea);

        JLabel pidLabel = new JLabel("상품코드");
        pidLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        inputPanel.add(pidLabel);

        pid_txtArea = new JTextArea();
        pid_txtArea.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        pid_txtArea.setEditable(false); // 사용자가 수정하지 못하도록 설정
        inputPanel.add(pid_txtArea);

        JLabel locationLabel = new JLabel("적재위치");
        locationLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        inputPanel.add(locationLabel);

        location_txtArea = new JTextArea();
        location_txtArea.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        location_txtArea.setEditable(false); // 사용자가 수정하지 못하도록 설정
        inputPanel.add(location_txtArea);

        JLabel modification_reason_label = new JLabel("조정사유");
        modification_reason_label.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        inputPanel.add(modification_reason_label);

        mr_comboBox = new JComboBox<>(modification_reason);
        mr_comboBox.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        inputPanel.add(mr_comboBox);

        JPanel bottomButtonPanel = new JPanel(new GridLayout(1, 2, 1, 1));
        bottomButtonPanel.setBackground(new Color(240, 240, 240));
        bottomButtonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        modifyButton = new JButton("수정요청");
        modifyButton.setBackground(Constants.BUTTON_BACKGROUND_COLOR);
        modifyButton.setForeground(Constants.BUTTON_FOREGROUND_COLOR);
        bottomButtonPanel.add(modifyButton);

        cancelButton = new JButton("초기화");
        cancelButton.setBackground(Constants.BUTTON_BACKGROUND_COLOR);
        cancelButton.setForeground(Constants.BUTTON_FOREGROUND_COLOR);
        bottomButtonPanel.add(cancelButton);
        setLayout(new BorderLayout(0, 0));
        add(inputPanel, BorderLayout.CENTER);
        add(bottomButtonPanel, BorderLayout.SOUTH);
    }

    // test
    public static void main(String[] args) {
        JFrame frame = new JFrame("조정요청");
        frame.getContentPane().add(new AmountModifyPanel2());
        frame.setSize(480, 640);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
