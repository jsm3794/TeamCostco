package main.java.com.teamcostco.view.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class HomePanel extends JPanel {

    private static final long serialVersionUID = 1L;
    public JLabel timeLabel;
    public JButton[] buttons;

    public HomePanel() {
        setBackground(SystemColor.menu);
        setPreferredSize(new Dimension(480, 640));
        setLayout(new BorderLayout());

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());
        northPanel.setPreferredSize(new Dimension(480, 100));

        JLabel titleLabel = new JLabel("TEAM COSTCO");
        titleLabel.setFont(new Font("굴림", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        northPanel.add(titleLabel, BorderLayout.CENTER);

        timeLabel = new JLabel("...");
        timeLabel.setFont(new Font("굴림", Font.PLAIN, 14));
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        northPanel.add(timeLabel, BorderLayout.SOUTH);

        add(northPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(0, 2, 10, 10));
        centerPanel.setBorder(new EmptyBorder(30, 10, 10, 10));

        buttons = new JButton[10];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton();
            centerPanel.add(buttons[i]);
        }

        add(centerPanel, BorderLayout.CENTER);
    }

    public void updateTime(String time) {
        timeLabel.setText(time);
    }
}
