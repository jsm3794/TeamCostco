package main.java.com.teamcostco.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import main.java.com.teamcostco.model.manager.LoginDuplicationCheck;
import main.java.com.teamcostco.view.panels.DBConnector;
import main.java.com.teamcostco.view.panels.SignPanelTest;

public class SignTestController extends PanelController<SignPanelTest>{

	public SignTestController() {
		test();
	}
	
    public void test() {
    	
    	LoginDuplicationCheck login = new LoginDuplicationCheck();
    	
        view.getSignButton().addActionListener(new ActionListener() {
            DBConnector connector = new DBConnector("TeamCostco", "1234");

            @Override
            public void actionPerformed(ActionEvent e) {
                String id = view.getNew_idField().getText();
                String pw = new String(view.getNew_passwordField().getPassword());
                String email = view.getEmailField().getText();
                
                if (login.isDuplicateID(id, connector)) {
                    JOptionPane.showMessageDialog(view, "이미 사용 중인 아이디입니다.", "중복된 아이디", JOptionPane.WARNING_MESSAGE);
                }else if (id.isEmpty() || pw.isEmpty()){
                	JOptionPane.showMessageDialog(view, "아이디 또는 비밀번호를 입력해주세요.", "id/pw공란", JOptionPane.WARNING_MESSAGE);
                }
                
                else {
                    String sql = "INSERT INTO member VALUES(1008, ?, ?, ?, 1, null, null)";
                    try (
                        Connection con = connector.getConnection();
                        PreparedStatement pstmt = con.prepareStatement(sql);
                    ) {
                        pstmt.setString(1, id);
                        pstmt.setString(2, pw);
                        pstmt.setString(3, email);

                        pstmt.executeUpdate();
                        JOptionPane.showMessageDialog(view, "회원가입 성공!", "가입 성공", JOptionPane.WARNING_MESSAGE);

                    } catch (SQLException e1) {
                        System.out.println("실패");
                    
                    
                    }
                    
                }
            }
        });
    }
	
	@Override
	public String toString() {
		return "회원가입";
	}
	
}
