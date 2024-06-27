package main.java.com.teamcostco.view.textfields;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.JPasswordField;

@SuppressWarnings("serial")
public class JPlaceholderPasswordField extends JPasswordField {
    private String ph;

    public JPlaceholderPasswordField(String ph) {
        this.ph = ph;
        setPreferredSize(new Dimension(100, 40));
        setMargin(new Insets(5, 5, 5, 5));
    }

    public JPlaceholderPasswordField() {
        this(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (getPassword().length == 0 && ph != null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.LIGHT_GRAY);
            g2.setFont(getFont());
            g2.drawString(ph, getInsets().left, g.getFontMetrics().getMaxAscent() + getInsets().top);
        }
    }
}