package main.java.com.teamcostco.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import main.java.com.teamcostco.MainForm;
import main.java.com.teamcostco.model.manager.DialogManager;
import main.java.com.teamcostco.model.manager.LoginDuplicationCheck;
import main.java.com.teamcostco.view.panels.DBConnector;
import main.java.com.teamcostco.view.panels.SignPanelTest;

public class SignTestController extends PanelController<SignPanelTest>{
	
	private boolean checkBox;

	public SignTestController() {
		test();
		checkbox();
	}
	
    public void test() {
    	
    	
        view.getSignButton().addActionListener(new ActionListener() {
            DBConnector connector = new DBConnector("TeamCostco", "1234");

            @Override
            public void actionPerformed(ActionEvent e) {
                String login_id = view.getNew_idField().getText();
                String login_pw = new String(view.getNew_passwordField().getPassword());
                String name = view.getNameField().getText();
                String member_email = view.getEmailField().getText();
                String member_phone_number = view.getPhone_numberField().getText();
                String email = view.getEmailField().getText();
                
                if (isDuplicateID(login_id, connector)) {
                    DialogManager.showMessageBox(view, "이미 사용중인 아이디입니다.", null);
                }else if (isDuplicateEmail(email, connector)) {
                	DialogManager.showMessageBox(view, "이미 사용중인 이메일입니다.", null);
                }else if (login_id.isEmpty() || login_pw.isEmpty() || name.isEmpty()){
                	DialogManager.showMessageBox(view, "아이디, 비밀번호 또는 이름을 입력해주세요.", null);
                }else if (!checkBox) {
                	DialogManager.showMessageBox(view, "약관동의 필요", null);
                }else if (!isValidEmail(email)){
                	DialogManager.showMessageBox(view, "이메일을 다시 입력해주세요.", null);
                }else {
                    String sql = "INSERT INTO employees(employee_id, login_id, login_pw, employee_name, employee_email, employee_phone_number, create_date)"
                    		+ " VALUES(employee_seq.nextval, ?, ?, ?, ?, ?, sysdate)";
                    try (
                        Connection con = connector.getConnection();
                        PreparedStatement pstmt = con.prepareStatement(sql);
                    ) {
                        pstmt.setString(1, login_id);
                        pstmt.setString(2, login_pw);
                        pstmt.setString(3, name);
                        pstmt.setString(4, member_email);
                        pstmt.setString(5, member_phone_number);

                        pstmt.executeUpdate();
                        DialogManager.showMessageBox(view, "가입성공", evt ->{
                        	MainForm.nav.navigateToHome();
                        });

                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    
                    
                    }
                    
                }
            }
        });  
    }
    
    private void checkbox() {
    	
    	view.getAgreeCheckbox().addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					checkBox = true;
				} else {
					checkBox = false;
				}
				
			}
		});
    }
    
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
    private boolean isDuplicateID(String id, DBConnector connector) {
        String sql = "SELECT COUNT(*) FROM employees WHERE login_id = ?";
        try (
            Connection con = connector.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
        ) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("중복 검사 실패");
        }
        return false;
    }
    
    private boolean isDuplicateEmail(String email, DBConnector connector) {
        String sql = "SELECT COUNT(*) FROM employees WHERE employee_email = ?";
        try (
            Connection con = connector.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
        ) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("중복 검사 실패");
        }
        return false;
    }
	
	@Override
	public String toString() {
		return "회원가입";
	}
	
}






