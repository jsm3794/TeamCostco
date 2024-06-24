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
		lblNewLabel.setBounds(125, 286, 20, 15);
		add(lblNewLabel);

		idField = new JTextField(15);
		idField.setBounds(182, 283, 116, 21);
		add(idField);
		idField.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("PASSWORD");
		lblNewLabel_1.setBounds(96, 317, 74, 15);
		add(lblNewLabel_1);

		loginButton = new JButton("로그인");
		loginButton.setForeground(new Color(255, 255, 255));
		loginButton.setBounds(161, 366, 152, 33);
		loginButton.setBackground(new Color(0, 122, 255));
		add(loginButton);
		passwordField = new JPasswordField();
		passwordField.setBounds(182, 314, 116, 21);
		add(passwordField);

		JPanel image_panel = new JPanel();
		image_panel.setBounds(12, 58, 456, 179);
		ImageIcon imageIcon = new ImageIcon(
				"C:\\aiautomationkdw\\repositories\\JavaStudy\\JavaStudy\\src\\project\\123.png"); // 이미지 경로 설정
		JLabel imageLabel = new JLabel(imageIcon);
		image_panel.add(imageLabel);
		add(image_panel);

		SignButton = new JButton("회원가입");
		SignButton.setForeground(new Color(255, 255, 255));
		SignButton.setBackground(new Color(0, 122, 255));
		SignButton.setBounds(161, 423, 152, 33);
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
