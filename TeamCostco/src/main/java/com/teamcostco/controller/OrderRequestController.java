package main.java.com.teamcostco.controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.Box;

import main.java.com.teamcostco.MainForm;
import main.java.com.teamcostco.model.database.DatabaseUtil;
import main.java.com.teamcostco.model.manager.DialogManager;
import main.java.com.teamcostco.view.panels.OrderRequestPanel;

public class OrderRequestController extends PanelController<OrderRequestPanel> {

    public OrderRequestController() {
        addResult();
        requestBtn();
    }

    private void addResult(String productName, int appQty, int curQty) {
        String str = String.format("<html>제품: %s<br>적정재고: %dEA<br>현재재고: %dEA<br>발주요청: %dEA</html>", productName, appQty,
                curQty, appQty - curQty);
        JLabel label = new JLabel(str);
        label.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        label.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        label.setMaximumSize(new Dimension(Integer.MAX_VALUE, label.getPreferredSize().height));
        view.getResultPanel().add(label);
    }

    private void addResult() {
        String sql = "SELECT product_name, appropriate_inventory, current_inventory "
                + "FROM product WHERE appropriate_inventory > current_inventory";
        try (Connection con = DatabaseUtil.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("product_name");
                int appQty = rs.getInt("appropriate_inventory");
                int curQty = rs.getInt("current_inventory");
                addResult(name, appQty, curQty);
            }
            view.getResultPanel().add(Box.createVerticalGlue());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void requestBtn() {
        view.getRequestBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogManager.showMessageBox(view, "발주요청 완료", evt -> {
                    MainForm.nav.navigateToHome();
                });
            }
        });
    }

    @Override
    public String toString() {
        return "발주요청";
    }
}