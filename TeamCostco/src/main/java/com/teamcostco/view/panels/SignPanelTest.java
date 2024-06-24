package main.java.com.teamcostco.view.panels;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;

public class SignPanelTest extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField new_idField;
	private JPasswordField new_passwordField;
	private JTextField nameField;
	private JTextField emailField;
	private JTextField phone_numberField;
	private JButton signButton;

	/**
	 * Create the panel.
	 */
	public SignPanelTest() {
		setPreferredSize(new Dimension(480, 640));
		setLayout(null);

		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(68, 100, 57, 15);
		add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("PASSWORD");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(58, 140, 73, 15);
		add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("이름");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(68, 180, 57, 15);
		add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("이메일");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(68, 220, 57, 15);
		add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("전화번호");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBounds(68, 260, 57, 15);
		add(lblNewLabel_4);

		new_idField = new JTextField();
		new_idField.setBounds(150, 97, 235, 21);
		add(new_idField);
		new_idField.setColumns(10);

		nameField = new JTextField();
		nameField.setColumns(10);
		nameField.setBounds(150, 177, 235, 21);
		add(nameField);

		emailField = new JTextField();
		emailField.setColumns(10);
		emailField.setBounds(150, 217, 235, 21);
		add(emailField);

		phone_numberField = new JTextField();
		phone_numberField.setColumns(10);
		phone_numberField.setBounds(150, 257, 235, 21);
		add(phone_numberField);

		signButton = new JButton("회원 가입");
		signButton.setBounds(68, 322, 317, 44);
		add(signButton);

		JPanel panel = new JPanel();
		panel.setBounds(12, 391, 456, 239);
		add(panel);

		new_passwordField = new JPasswordField();
		new_passwordField.setBounds(150, 137, 235, 21);
		add(new_passwordField);
	}

	public JTextField getNew_idField() {
		return new_idField;
	}

	public JPasswordField getNew_passwordField() {
		return new_passwordField;
	}

	public JTextField getNameField() {
		return nameField;
	}

	public JTextField getEmailField() {
		return emailField;
	}

	public JTextField getPhone_numberField() {
		return phone_numberField;
	}

	public JButton getSignButton() {
		return signButton;
	}
}
