package main.java.com.teamcostco.controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import main.java.com.teamcostco.model.OrderModel;
import main.java.com.teamcostco.view.panels.OrderDetailPanel;

public class OrderDetailController extends PanelController<OrderDetailPanel> {
    
    public OrderModel data;

    public OrderDetailController(OrderModel data) {
        this.data = data;
        initControl(data);
    }

    private void initControl(OrderModel data) {
        String[] lines = data.toString().split("\n");

        view.dataPanel.setLayout(new GridLayout(lines.length, 1, 10, 10)); // 세로로 레이블 정렬, 간격 설정

        for (String line : lines) {
            JPanel itemPanel = new JPanel();
            itemPanel.setBorder(new LineBorder(Color.BLACK, 1)); // 테두리 추가
            itemPanel.setBackground(Color.WHITE);
            itemPanel.setPreferredSize(new Dimension(400, 50));
            itemPanel.setLayout(new GridLayout(1, 2));

            String[] keyValue = line.split("=", 2);
            JLabel keyLabel = new JLabel(keyValue[0]);
            keyLabel.setFont(new Font("Serif", Font.BOLD, 18)); // 레이블을 굵게
            keyLabel.setBorder(new EmptyBorder(5, 10, 5, 10)); // 여백 설정
            keyLabel.setOpaque(true);
            keyLabel.setBackground(new Color(240, 240, 240));

            JLabel valueLabel = new JLabel(keyValue[1]);
            valueLabel.setFont(new Font("Serif", Font.PLAIN, 18));
            valueLabel.setBorder(new EmptyBorder(5, 10, 5, 10));
            valueLabel.setHorizontalAlignment(JLabel.CENTER);

            itemPanel.add(keyLabel);
            itemPanel.add(valueLabel);

            view.dataPanel.add(itemPanel);
        }
    }

    @Override
    public String toString() {
        return "발주상세조회";
    }
}