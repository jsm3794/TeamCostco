package main.java.com.teamcostco.view.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.MaskFormatter;

import main.utils.Constants;
import java.awt.Font;
import javax.swing.JCheckBox;

public class SignPanelTest extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField new_idField;
	private JPasswordField new_passwordField;
	private JTextField nameField;
	private JTextField emailField;
	private JTextField phone_numberField;
	private JButton signButton;
	private JLabel lblNewLabel_6;
	private JCheckBox agreeCheckbox;

	/**
	 * Create the panel.
	 */
	public SignPanelTest() {
		setPreferredSize(new Dimension(480, 640));
		setLayout(null);

		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(12, 130, 126, 40);
		add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("PASSWORD");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(12, 180, 126, 40);
		add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("이름");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(12, 230, 126, 40);
		add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("이메일");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(12, 280, 126, 40);
		add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("전화번호");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBounds(12, 330, 126, 40);
		add(lblNewLabel_4);

		new_idField = new JTextField();
		new_idField.setHorizontalAlignment(SwingConstants.CENTER);
		new_idField.setBounds(150, 130, 235, 40);
		add(new_idField);
		new_idField.setColumns(10);

		nameField = new JTextField();
		nameField.setHorizontalAlignment(SwingConstants.CENTER);
		nameField.setColumns(10);
		nameField.setBounds(150, 230, 235, 40);
		add(nameField);

		emailField = new JTextField();
		emailField.setHorizontalAlignment(SwingConstants.CENTER);
		emailField.setColumns(10);
		emailField.setBounds(150, 280, 235, 40);
		add(emailField);

		 try {
	            MaskFormatter formatter = new MaskFormatter("###-####-####");
	            formatter.setPlaceholderCharacter(' '); // 입력하지 않은 부분을 지정할 문자
	            phone_numberField = new JFormattedTextField(formatter);
	            phone_numberField.setHorizontalAlignment(SwingConstants.CENTER);
	        } catch (ParseException e) {
	            e.printStackTrace();
	            phone_numberField = new JFormattedTextField();
	        }
		 
		phone_numberField.setColumns(10);
		phone_numberField.setBounds(150, 330, 235, 40);
		add(phone_numberField);

		signButton = new JButton("회원 가입");		
		signButton.setBounds(60, 500, 360, 44);
		signButton.setBackground(Constants.BUTTON_BACKGROUND_COLOR);
		signButton.setForeground(Constants.BUTTON_FOREGROUND_COLOR);
		add(signButton);

		new_passwordField = new JPasswordField();
		new_passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		new_passwordField.setBounds(150, 180, 235, 40);
		add(new_passwordField);
		
		JLabel lblNewLabel_5 = new JLabel("회원가입을 위해 정보를 입력해주세요.");
		lblNewLabel_5.setFont(new Font("굴림", Font.BOLD, 22));
		lblNewLabel_5.setForeground(Constants.BUTTON_BACKGROUND_COLOR);
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5.setBounds(12, 27, 456, 57);
		add(lblNewLabel_5);
		
		lblNewLabel_6 = new JLabel("이용약관 개인정보 수집 및 정보이용에 동의합니다.       ");
		lblNewLabel_6.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_6.setBounds(92, 425, 328, 28);
		add(lblNewLabel_6);
		
		agreeCheckbox = new JCheckBox("");
		agreeCheckbox.setBounds(60, 430, 24, 23);
		add(agreeCheckbox);
	}

	public JCheckBox getAgreeCheckbox() {
		return agreeCheckbox;
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
