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

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.utils.Constants;

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
		setPreferredSize(new Dimension(480, 50));
		setLayout(createLayout());
		setBackground(Constants.TOPNAVIGATOR_BACKGROUND_COLOR);
		createNavLabel();
		createTitleLabel();
	}

	private GridBagLayout createLayout() {
		GridBagLayout layout = new GridBagLayout();
		layout.columnWidths = new int[] { 40, 435, 0 }; // Set the width for labelNav
		layout.rowHeights = new int[] { 50, 0 };
		layout.columnWeights = new double[] { 0.0, 1.0 }; // Adjust column weights
		layout.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		return layout;
	}

	private void createNavLabel() {
	    labelNav = new JLabel();
	    labelNav.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    labelNav.setForeground(Color.BLACK);
	    labelNav.setIcon(new ImageIcon(TopNavigator.class.getResource("/main/resources/back.png")));
	    labelNav.setPreferredSize(new Dimension(40, 40)); // Set the preferred size to make it 40x40
	    labelNav.setHorizontalAlignment(JLabel.CENTER); // Center horizontally
	    labelNav.setVerticalAlignment(JLabel.CENTER); // Center vertically
	    labelNav.addMouseListener(new java.awt.event.MouseAdapter() {
	        public void mouseClicked(java.awt.event.MouseEvent evt) {
	            if (clickListener != null) {
	                clickListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
	            }
	        }
	    });

	    GridBagConstraints gbcNavLabel = new GridBagConstraints();
	    gbcNavLabel.insets = new Insets(0, 0, 0, 0);
	    gbcNavLabel.gridx = 0;
	    gbcNavLabel.gridy = 0;
	    gbcNavLabel.fill = GridBagConstraints.BOTH; // Fill both horizontally and vertically
	    add(labelNav, gbcNavLabel);
	}

	private void createTitleLabel() {
		labelTitle = new JLabel(title);
		labelTitle.setForeground(Color.WHITE);
		labelTitle.setFont(labelTitle.getFont().deriveFont(Font.BOLD));

		GridBagConstraints gbcTitleLabel = new GridBagConstraints();
		gbcTitleLabel.anchor = GridBagConstraints.WEST;
		gbcTitleLabel.gridx = 1;
		gbcTitleLabel.gridy = 0;
		gbcTitleLabel.insets = new Insets(0, 10, 0, 0);
		gbcTitleLabel.fill = GridBagConstraints.HORIZONTAL; // Ensure the title label fills horizontally
		add(labelTitle, gbcTitleLabel);
	}

	public void setTitle(String title) {
		if (title.length() > 20) {
			this.title = title.substring(0, 20);
		} else {
			this.title = title;
		}
		labelTitle.setText(this.title); // Update the label text
	}

	public String getTitle() {
		return title;
	}

	public void setClickListener(ActionListener clickListener) {
		this.clickListener = clickListener;
	}
}
