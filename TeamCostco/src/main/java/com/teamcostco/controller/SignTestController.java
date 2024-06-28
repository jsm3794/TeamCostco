package main.java.com.teamcostco.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
    	
    	LoginDuplicationCheck login = new LoginDuplicationCheck();
    	
        view.getSignButton().addActionListener(new ActionListener() {
            DBConnector connector = new DBConnector("TeamCostco", "1234");

            @Override
            public void actionPerformed(ActionEvent e) {
                String login_id = view.getNew_idField().getText();
                String login_pw = new String(view.getNew_passwordField().getPassword());
                String name = view.getNameField().getText();
                String member_email = view.getEmailField().getText();
                String member_phone_number = view.getPhone_numberField().getText();
                
                if (login.isDuplicateID(login_id, connector)) {
                    JOptionPane.showMessageDialog(view, "이미 사용 중인 아이디입니다.", "중복된 아이디", JOptionPane.WARNING_MESSAGE);
                }else if (login_id.isEmpty() || login_pw.isEmpty()){
                	JOptionPane.showMessageDialog(view, "아이디 또는 비밀번호를 입력해주세요.", "id/pw공란", JOptionPane.WARNING_MESSAGE);
                }else if (!checkBox) {
                	DialogManager.showMessageBox(view, "약관동의 필요", null);
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
                        System.out.println("실패");
                    
                    
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
	
	@Override
	public String toString() {
		return "회원가입";
	}
	
}






