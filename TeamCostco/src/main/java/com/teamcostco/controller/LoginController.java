package main.java.com.teamcostco.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import main.java.com.teamcostco.MainForm;
import main.java.com.teamcostco.model.manager.AuthManager;
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
