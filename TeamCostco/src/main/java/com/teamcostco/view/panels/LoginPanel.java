package main.java.com.teamcostco.view.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import main.java.com.teamcostco.view.textfields.JPlaceholderPasswordField;
import main.java.com.teamcostco.view.textfields.JPlaceholderTextField;
import main.utils.Constants;

public class LoginPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final Dimension PANEL_SIZE = new Dimension(480, 640);

	public JPlaceholderTextField idField;
	public JPlaceholderPasswordField passwordField;
	public JButton loginButton;
	public JButton signButton;
	public JPanel imagePanel;
	public JLabel imageLabel;
	public JLabel lblPdaLogin;

	/**
	 * Create the panel.
	 */
	public LoginPanel() {
		setBackground(Color.WHITE);
		initializeComponents();
		layoutComponents();
	}

	private void initializeComponents() {
		setPreferredSize(PANEL_SIZE);
		setLayout(null);

		idField = new JPlaceholderTextField("아이디");
		idField.setBounds(12, 298, 456, 40);

		loginButton = new JButton("로그인");
		loginButton.setForeground(Constants.BUTTON_FOREGROUND_COLOR);
		loginButton.setBackground(Constants.BUTTON_BACKGROUND_COLOR);
		loginButton.setBounds(12, 398, 456, 40); // 크기 설정 추가

		passwordField = new JPlaceholderPasswordField("비밀번호");
		passwordField.setBounds(12, 348, 456, 40); // 크기 설정 추가

		imagePanel = new JPanel();
		imagePanel.setBounds(12, 10, 456, 154);
		imagePanel.setBackground(null);
		ImageIcon imageIcon = new ImageIcon(LoginPanel.class.getResource("/main/resources/logo.png"));
		imageLabel = new JLabel(imageIcon);
		imagePanel.add(imageLabel);

		signButton = new JButton("회원가입");
		signButton.setForeground(Constants.BUTTON_FOREGROUND_COLOR);
		signButton.setBackground(Constants.BUTTON_BACKGROUND_COLOR);
		signButton.setBounds(12, 448, 456, 40);
	}

	private void layoutComponents() {
		add(idField);
		add(loginButton);
		add(passwordField);
		add(imagePanel);
		add(signButton);

		JLabel lblNewLabel = new JLabel("TeamCostco");
		lblNewLabel.setForeground(new Color(0x067FC4));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		lblNewLabel.setBounds(12, 173, 457, 28);
		add(lblNewLabel);

		lblPdaLogin = new JLabel("PDA Login");
		lblPdaLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblPdaLogin.setForeground(Color.DARK_GRAY);
		lblPdaLogin.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		lblPdaLogin.setBounds(12, 205, 456, 28);
		add(lblPdaLogin);
	}

	public JPlaceholderTextField getIdField() {
		return idField;
	}

	public void setIdField(JPlaceholderTextField idField) {
		this.idField = idField;
	}

	public JPlaceholderPasswordField getPasswordField() {
		return passwordField;
	}

	public void setPasswordField(JPlaceholderPasswordField passwordField) {
		this.passwordField = passwordField;
	}

	public JButton getLoginButton() {
		return loginButton;
	}

	public void setLoginButton(JButton loginButton) {
		this.loginButton = loginButton;
	}

	public JButton getSignButton() {
		return signButton;
	}

	public void setSignButton(JButton signButton) {
		this.signButton = signButton;
	}
}
