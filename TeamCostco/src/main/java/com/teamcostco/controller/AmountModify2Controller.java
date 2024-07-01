package main.java.com.teamcostco.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import main.java.com.teamcostco.model.AmountModifyModel;
import main.java.com.teamcostco.model.database.DatabaseUtil;
import main.java.com.teamcostco.model.manager.DialogManager;
import main.java.com.teamcostco.view.panels.AmountModifyPanel2;

public class AmountModify2Controller extends PanelController<AmountModifyPanel2> {

    private AmountModifyModel model;
    private DatabaseUtil connector;
    private static int cnt = 0;
    
    public AmountModify2Controller() {
        connector = new DatabaseUtil();
        initComponents();
        
        // + 버튼 기능 
        view.getPlusButton().addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                plusAmount();
                
            }
        });
        
        // - 버튼 기능
        view.getMinusButton().addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    minusAmount();
                } catch (NegativeAmount e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                }                
            }
        });
        
        // 수량칸은 숫자키만 입력되도록함 (나머지 키는 허용안함)
        view.getAmount_txtField().addKeyListener(new KeyListener() {
            
            @Override
            public void keyTyped(KeyEvent e) {
                try {
                    char c = e.getKeyChar();
                    if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
                        e.consume();  // Ignore the event if it's not a digit or control key
                        throw new NonNumericValueException();
                    }
                } catch (NonNumericValueException ex) {
                    JOptionPane.showMessageDialog(view, ex.getMessage(), "Invalid Input", JOptionPane.WARNING_MESSAGE);
                }
            }
            
            @Override
            public void keyReleased(KeyEvent e) {
                // No action required on key release
            }
            
            @Override
            public void keyPressed(KeyEvent e) {
                // No action required on key press
            }
        });
        
        // (수량 수정)
        view.getModifyButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                DialogManager.showMessageBox(
                    view,
                    "수량을 수정하시겠습니까?",
                    evt -> updateAmount(),
                    null
                );
            }
        });

        // 취소버튼
        view.getCancelButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                getDetailInfo();
            }
        });
    }

    public void initComponents() {
        setComboBoxValue();
        getDetailInfo();
    }

    private void getDetailInfo() {
        // Fetch and display product details from another panel or a data source
        // Example implementation (should be replaced with actual data fetching logic)
        view.getProductNameField().setText("");
        view.getCategoryComboBox().setSelectedItem("식품");
        view.getAmount_txtField().setText("0");
        view.getWhdate_txtArea().setText("미정");
        view.getSellPrice_txtArea().setText("1000(예시)");
        view.getPid_txtArea().setText("PRID00000000000000000000(예시)");
        view.getLocation_txtArea().setText("A-01(예시)");
    }
    
    // 대분류 불러옴
    private void setComboBoxValue() {
        String sql = "SELECT DISTINCT main_name FROM maincategory";

        try (
            Connection conn = connector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
        ) {
            while (rs.next()) {
                String categori_name = rs.getString("main_name");
                view.getCategoryComboBox().addItem(categori_name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 재고 수정 + (수정사유반영)
    private void updateAmount() {
        String input_pn = view.getProductNameField().getText().trim();
        String selected_category = (String) view.getCategoryComboBox().getSelectedItem();
        
        StringBuilder sql = new StringBuilder( 
            "UPDATE storage SET amount = ? WHERE product_id = ? AND storage_id = ?"
        );
        
        if (input_pn.isEmpty()) {
            JOptionPane.showMessageDialog(view, "상품명이 공백입니다.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (
            Connection conn = connector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
        ) {
            conn.setAutoCommit(false);
            
            String amount = view.getAmount_txtField().getText();
            pstmt.setInt(1, Integer.parseInt(amount));
            pstmt.setString(2, view.getPid_txtArea().getText());
            pstmt.setString(3, view.getLocation_txtArea().getText());
            
            try {
                int row = pstmt.executeUpdate();
                System.out.printf("%d행 업데이트\n", row);
                conn.setSavepoint("수정" + ++cnt);
                JOptionPane.showMessageDialog(view, "수정 완료!", "Success", JOptionPane.INFORMATION_MESSAGE);
                conn.commit();
            } catch (SQLException e2) {
                System.out.println("문제가 생겨서 롤백");
                conn.rollback();
                JOptionPane.showMessageDialog(view, "업데이트 실패!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    private void plusAmount() {
        String amount = view.getAmount_txtField().getText();
        Integer change = Integer.parseInt(amount) + 1;
        amount = change.toString();
        
        view.getAmount_txtField().setText(amount);
    }

    private void minusAmount() throws NegativeAmount {
        String amount = view.getAmount_txtField().getText();
        Integer change = Integer.parseInt(amount);
        if (change - 1 < 0) {
            throw new NegativeAmount();
        } else {
            --change;
        }
        amount = change.toString();
        
        view.getAmount_txtField().setText(amount);
    }

    public void resetFields() {
        view.getProductNameField().setText("");
        view.getCategoryComboBox().setSelectedIndex(0);
        view.getAmount_txtField().setText("0");
        view.getWhdate_txtArea().setText("");
        view.getSellPrice_txtArea().setText("");
        view.getPid_txtArea().setText("");
        view.getLocation_txtArea().setText("");
        view.getMr_comboBox().setSelectedIndex(0);
    }
    
    @Override
    public String toString() {
        return "재고수정";
    }

}

// 예외
// 수량이 음수
class NegativeAmount extends Exception {
    public NegativeAmount() {
        super("수량이 음수입니다.");
    }
}

class ExeedStorageAmount extends Exception {
    public ExeedStorageAmount() {
        super("창고보관수량을 초과하는 수량입니다.");
    }
}

class NonNumericValueException extends Exception {
    public NonNumericValueException() {
        super("숫자만 입력 가능합니다.");
    }
}
