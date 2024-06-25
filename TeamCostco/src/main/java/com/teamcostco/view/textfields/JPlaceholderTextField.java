package main.java.com.teamcostco.view.textfields;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.JTextField;

@SuppressWarnings("serial")
public class JPlaceholderTextField extends JTextField {

	private String ph;

	public JPlaceholderTextField(String ph) {
		this.ph = ph;
		setPreferredSize(new Dimension(100, 40));
		setMargin(new Insets(5, 5, 5, 5));
	}

	public JPlaceholderTextField() {
		this(null);
	}

	/**
	 * Gets text, returns placeholder if nothing specified
	 */
	@Override
	public String getText() {
		String text = super.getText();

		if (text.trim().length() == 0 && ph != null) {
			text = ph;
		}

		return text;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (super.getText().length() > 0 || ph == null) {
			return;
		}

		Graphics2D g2 = (Graphics2D) g;

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.LIGHT_GRAY);
		g2.drawString(ph, getInsets().left, g.getFontMetrics().getMaxAscent() + getInsets().top);
	}
}