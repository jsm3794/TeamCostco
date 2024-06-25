package main.java.com.teamcostco.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import main.java.com.teamcostco.MainForm;
import main.java.com.teamcostco.model.manager.AuthManager;
import main.java.com.teamcostco.view.panels.LoginPanel;

public class LoginController extends PanelController<LoginPanel>{

	public LoginController() {
		signComponents();
		initComponents();
	}
	
	public void initComponents() {
		view.getLoginButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = view.getIdField().getText();
				String pw = new String(view.getPasswordField().getPassword());
				boolean loginSuccess = AuthManager.getInstance().login(id, pw);
				if (loginSuccess) {
					MainForm.nav.navigateTo("home", false);
				} else {
					JOptionPane.showMessageDialog(view, "ID/PW 틀림", "id/pw체크", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}
	
	public void signComponents() {
		view.getSignButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainForm.nav.navigateTo("sign", true);
				
			}
		});
	}
	
	@Override
	public String toString() {
		return "로그인";
	}

}
