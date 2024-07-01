package main.java.com.teamcostco.view.panels;

import javax.swing.*;
import java.awt.*;
import main.utils.Constants;

public class SettingsPanel extends JPanel {
    public JLabel idLabel, nameLabel, emailLabel;
    public JToggleButton autoLockToggle;
    public JComboBox<String> autoLockTimeComboBox;
    public JButton logoutButton, editAccountButton;

    public SettingsPanel() {
        setPreferredSize(new Dimension(480, 640));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);

        addUserInfoSection(gbc);
        addEditAccountButton(gbc);
        addAutoLockSection(gbc);
        addLogoutButton(gbc);
    }

    private void addUserInfoSection(GridBagConstraints gbc) {
        addLabelAndValue("직원 ID:", idLabel = new JLabel(), gbc, 0);
        addLabelAndValue("이름:", nameLabel = new JLabel(), gbc, 1);
        addLabelAndValue("이메일:", emailLabel = new JLabel(), gbc, 2);
    }

    private void addEditAccountButton(GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        editAccountButton = createStyledButton("회원정보 수정");
        add(editAccountButton, gbc);
    }

    private void addAutoLockSection(GridBagConstraints gbc) {
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(55, 10, 5, 10);
        add(new JLabel("자동 잠금:"), gbc);

        gbc.gridx = 1;
        autoLockToggle = createToggleButton();
        add(autoLockToggle, gbc);

        gbc.gridx = 2;
        String[] times = { "10초", "1분", "5분", "10분", "30분", "1시간" };
        autoLockTimeComboBox = new JComboBox<>(times);
        autoLockTimeComboBox.setEnabled(false);
        add(autoLockTimeComboBox, gbc);
    }

    private void addLogoutButton(GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(5, 10, 5, 10);
        logoutButton = createStyledButton("로그아웃");
        add(logoutButton, gbc);
    }

    private JToggleButton createToggleButton() {
        JToggleButton toggle = new JToggleButton("Off") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                boolean isSelected = isSelected();
                int w = getWidth();
                int h = getHeight();

                g2.setColor(isSelected ? new Color(52, 152, 219) : Color.GRAY);
                g2.fillRoundRect(0, 0, w, h, h, h);

                g2.setColor(Color.WHITE);
                int diameter = h - 4;
                int x = isSelected ? w - diameter - 2 : 2;
                g2.fillOval(x, 2, diameter, diameter);

                g2.dispose();
            }
        };

        toggle.setPreferredSize(new Dimension(60, 30));
        toggle.addItemListener(e -> {
            toggle.setText(toggle.isSelected() ? "On" : "Off");
            autoLockTimeComboBox.setEnabled(toggle.isSelected());
        });

        toggle.setBorderPainted(false);
        toggle.setFocusPainted(false);
        toggle.setContentAreaFilled(false);

        return toggle;
    }

    private void addLabelAndValue(String labelText, JLabel valueLabel, GridBagConstraints gbc, int y) {
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        valueLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        valueLabel.setPreferredSize(new Dimension(300, 30));
        add(valueLabel, gbc);
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
        idLabel.setText(id);
        nameLabel.setText(name);
        emailLabel.setText(email);
    }

    public boolean isAutoLockEnabled() {
        return autoLockToggle.isSelected();
    }

    public String getAutoLockTime() {
        return (String) autoLockTimeComboBox.getSelectedItem();
    }
}