package main.java.com.teamcostco.view.panels;

import javax.swing.*;
import java.awt.*;
import main.utils.Constants;

public class EditAccountPanel extends JPanel {
    public JTextField idField, nameField, emailField;
    public JPasswordField passwordField, confirmPasswordField;
    public JButton saveButton, cancelButton;

    public EditAccountPanel() {
        setPreferredSize(new Dimension(480, 640));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);

        addLabelAndField("아이디:", idField = createReadOnlyTextField(), gbc, 0);
        addLabelAndField("이름:", nameField = new JTextField(), gbc, 1);
        addLabelAndField("이메일:", emailField = new JTextField(), gbc, 2);
        addLabelAndField("새 비밀번호:", passwordField = new JPasswordField(), gbc, 3);
        addLabelAndField("비밀번호 확인:", confirmPasswordField = new JPasswordField(), gbc, 4);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 10, 5, 10);
        saveButton = createStyledButton("저장");
        add(saveButton, gbc);

        gbc.gridy = 6;
        cancelButton = createStyledButton("취소");
        add(cancelButton, gbc);
    }

    private JTextField createReadOnlyTextField() {
        JTextField field = new JTextField();
        field.setEditable(false);
        field.setBackground(new Color(240, 240, 240));  // 연한 회색 배경
        field.setBorder(BorderFactory.createEtchedBorder());
        return field;
    }

    private void addLabelAndField(String labelText, JTextField field, GridBagConstraints gbc, int y) {
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        field.setPreferredSize(new Dimension(300, 30));
        add(field, gbc);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Constants.BUTTON_BACKGROUND_COLOR);
        button.setForeground(Constants.BUTTON_FOREGROUND_COLOR);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(440, 40));
        return button;
    }

    public void setUserProfile(String id, String name, String email) {
        idField.setText(id);
        nameField.setText(name);
        emailField.setText(email);
    }

    public String getId() {
        return idField.getText();
    }

    public String getName() {
        return nameField.getText();
    }

    public String getEmail() {
        return emailField.getText();
    }

    public char[] getPassword() {
        return passwordField.getPassword();
    }

    public char[] getConfirmPassword() {
        return confirmPasswordField.getPassword();
    }
}