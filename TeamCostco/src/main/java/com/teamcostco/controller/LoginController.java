package main.java.com.teamcostco.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

import main.java.com.teamcostco.MainForm;
import main.java.com.teamcostco.model.AuthManager;
import main.java.com.teamcostco.model.LogManager;
import main.java.com.teamcostco.model.MemberModel;
import main.java.com.teamcostco.view.panels.LoginPanel;

public class LoginController extends PanelController<LoginPanel> {
	
	public LoginController() {
		initComponents();
	}

	public void initComponents() {
		view.getLoginButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = view.getIdField().getText().trim();
				String pw = new String(view.getPasswordField().getPassword()).trim();
				boolean loginSuccess = AuthManager.getInstance().login(id, pw);
				if (loginSuccess) {
					MainForm.nav.navigateTo("home", false);
				}
			}
		});
	}

	@Override
	public String toString() {
		return "로그인";
	}
}
