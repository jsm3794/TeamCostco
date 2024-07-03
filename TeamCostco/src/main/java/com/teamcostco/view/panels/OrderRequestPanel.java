package main.java.com.teamcostco.view.panels;

import javax.swing.*;
import main.utils.Constants;
import java.awt.*;
import java.time.LocalDate;

public class OrderRequestPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JPanel resultPanel;
    private JButton requestBtn;

    public OrderRequestPanel() {
        LocalDate currentDate = getCurrentDate();

        setBounds(new Rectangle(0, 0, 480, 640));
        setLayout(null);

        resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        
        // 스크롤팬 생성
        JScrollPane scrollPane = new JScrollPane(resultPanel);
        scrollPane.setBounds(12, 89, 456, 410);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane);

        requestBtn = new JButton("발주요청");
        requestBtn.setBounds(12, 524, 456, 55);
        requestBtn.setBackground(Constants.BUTTON_BACKGROUND_COLOR);
        requestBtn.setForeground(Constants.BUTTON_FOREGROUND_COLOR);
        add(requestBtn);

        JLabel dateLabel = new JLabel(currentDate.getYear() + "년" +
                currentDate.getMonthValue() + "월" + currentDate.getDayOfMonth() + "일 발주요청");
        dateLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 22));
        dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dateLabel.setBounds(12, 18, 456, 52);
        add(dateLabel);
    }

    public JPanel getResultPanel() {
        return resultPanel;
    }

    public JButton getRequestBtn() {
        return requestBtn;
    }

    public static LocalDate getCurrentDate() {
        return LocalDate.now();
    }
}