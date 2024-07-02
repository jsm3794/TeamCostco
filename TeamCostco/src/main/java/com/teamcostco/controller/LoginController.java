package main.java.com.teamcostco.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import main.java.com.teamcostco.MainForm;
import main.java.com.teamcostco.model.manager.AuthManager;
import main.java.com.teamcostco.model.manager.DialogManager;
import main.java.com.teamcostco.view.panels.LoginPanel;

public class LoginController extends PanelController<LoginPanel> {

	public LoginController() {
		signComponents();
		initComponents();
	}

	public void initComponents() {

		view.getLoginButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				performLogin();
			}
		});


		view.getPasswordField().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					performLogin();
				}
			}
		});

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				view.getIdField().requestFocusInWindow();
			}
		});
	}

	private void performLogin() {
		DialogManager.Context context = DialogManager.showLoadingBox(view);

		new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				String id = view.getIdField().getText();
				String pw = new String(view.getPasswordField().getPassword());

				AuthManager.getInstance().login(id, pw);
				return null;
			}

			@Override
			protected void done() {
				context.close();
				if (AuthManager.getInstance().isLoggedIn()) {
					MainForm.nav.navigateTo("home", false);
				} else {
					DialogManager.showMessageBox(view, "아이디 또는 비밀번호를<br>확인해주세요", null);
				}
			}
		}.execute();
	}

	public void doLogin() {
		DialogManager.Context context = DialogManager.showLoadingBox(view);

		new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {
				String id = view.getIdField().getText();
				String pw = new String(view.getPasswordField().getPassword());

				AuthManager.getInstance().login(id, pw);
				return null;
			}

			@Override
			protected void done() {
				context.close();
				if (AuthManager.getInstance().isLoggedIn()) {
					MainForm.nav.navigateTo("home", false);
				} else {
					DialogManager.showMessageBox(view, "아이디 또는 비밀번호를<br>확인해주세요", null);
				}
			};

		}.execute();
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
