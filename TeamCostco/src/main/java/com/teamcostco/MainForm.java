package main.java.com.teamcostco;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import main.java.com.teamcostco.view.panels.LoginPanel;

public class MainForm {
    public static void main(String[] args) {
    	
        JFrame frame = new JFrame("팀코스트코");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);
        frame.setLocationRelativeTo(null);

        
        // 본인이 만드는 패널로 교체해서 실행해보세요
        JPanel panel = new LoginPanel();
        panel.setBackground(Color.LIGHT_GRAY);

        frame.add(panel);

        frame.setVisible(true);
    }
}
