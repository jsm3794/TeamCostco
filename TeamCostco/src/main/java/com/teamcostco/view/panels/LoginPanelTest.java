package main.java.com.teamcostco.view.panels;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;

public class LoginPanelTest extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField idField;
	private JPasswordField passwordField;
	private JButton loginButton;
	private JButton SignButton;

	/**
	 * Create the panel.
	 */
	public LoginPanelTest() {

		setPreferredSize(new Dimension(480, 640));
		setLayout(null);

		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("굴림", Font.BOLD, 20));
		lblNewLabel.setBounds(12, 292, 130, 30);
		add(lblNewLabel);

		idField = new JTextField(15);
		idField.setBounds(156, 292, 166, 30);
		add(idField);
		idField.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("PASSWORD");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("굴림", Font.BOLD, 20));
		lblNewLabel_1.setBounds(12, 340, 130, 30);
		add(lblNewLabel_1);

		loginButton = new JButton("로그인");
		loginButton.setFont(new Font("굴림", Font.BOLD, 20));
		loginButton.setForeground(new Color(255, 255, 255));
		loginButton.setBounds(156, 401, 166, 40);
		loginButton.setBackground(new Color(0, 122, 255));
		add(loginButton);
		passwordField = new JPasswordField();
		passwordField.setBounds(154, 341, 166, 30);
		add(passwordField);

		JPanel image_panel = new JPanel();
		image_panel.setBounds(12, 58, 456, 154);
		ImageIcon imageIcon = new ImageIcon(
				LoginPanelTest.class.getResource("/main/resources/logo.png")); // 이미지 경로 설정
		JLabel imageLabel = new JLabel(imageIcon);
		image_panel.add(imageLabel);
		add(image_panel);

		SignButton = new JButton("회원가입");
		SignButton.setFont(new Font("굴림", Font.BOLD, 20));
		SignButton.setForeground(new Color(255, 255, 255));
		SignButton.setBackground(new Color(0, 122, 255));
		SignButton.setBounds(156, 451, 166, 40);
		add(SignButton);

	}
	
    public JTextField getIdTestField() {
        return idField;
    }

    public JPasswordField getTestPasswordField() {
        return passwordField;
    }

    public JButton getLoginTestButton() {
        return loginButton;
    }
    
    public JButton getSignTestButton() {
    	return SignButton;
    }

}
