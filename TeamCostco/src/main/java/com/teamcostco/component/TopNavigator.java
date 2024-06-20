package main.java.com.teamcostco.component;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TopNavigator extends JPanel {

    private JLabel labelNav;
    private JLabel labelTitle;
    private String title = "No Title";
    private ActionListener clickListener;

    public TopNavigator() {
        initialize();
    }

    public TopNavigator(String title) {
        this();
        setTitle(title);
    }

    private void initialize() {
        setPreferredSize(new Dimension(480, 35));
        setLayout(createLayout());
        setBackground(Color.LIGHT_GRAY);
        createNavLabel();
        createTitleLabel();
    }

    private GridBagLayout createLayout() {
        GridBagLayout layout = new GridBagLayout();
        layout.columnWidths = new int[]{27, 435, 0};
        layout.rowHeights = new int[]{35, 0};
        layout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        layout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        return layout;
    }

    private void createNavLabel() {
        labelNav = new JLabel("<");
        labelNav.setCursor(new Cursor(Cursor.HAND_CURSOR));
        labelNav.setForeground(Color.BLACK);
        labelNav.setFont(new Font("굴림", Font.BOLD, 12));
        labelNav.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (clickListener != null) {
                    clickListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
                }
            }
        });

        GridBagConstraints gbcNavLabel = new GridBagConstraints();
        gbcNavLabel.insets = new Insets(0, 0, 0, 5);
        gbcNavLabel.gridx = 0;
        gbcNavLabel.gridy = 0;
        add(labelNav, gbcNavLabel);
    }

    private void createTitleLabel() {
        labelTitle = new JLabel(title);
        labelTitle.setForeground(Color.BLACK);
        labelTitle.setFont(new Font("굴림", Font.BOLD, 12));

        GridBagConstraints gbcTitleLabel = new GridBagConstraints();
        gbcTitleLabel.anchor = GridBagConstraints.WEST;
        gbcTitleLabel.gridx = 1;
        gbcTitleLabel.gridy = 0;
        gbcTitleLabel.insets = new Insets(0, 10, 0, 0);
        add(labelTitle, gbcTitleLabel);
    }

    public void setTitle(String title) {
        if (title.length() > 20) {
            this.title = title.substring(0, 20);
        } else {
            this.title = title;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setClickListener(ActionListener clickListener) {
        this.clickListener = clickListener;
    }
}
