package main.java.com.teamcostco.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.com.teamcostco.model.WareHouseListModel;
import main.java.com.teamcostco.model.database.DatabaseUtil;
import main.java.com.teamcostco.view.panels.WareHouseListPanel;

public class WareHouseListController extends PanelController<WareHouseListPanel> {
    private DatabaseUtil connector;

    public WareHouseListController() {
        this.connector = new DatabaseUtil();
        initialize();
    }

    private void initialize() {
        view.getSearchButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productName = view.getProductNameField().getText();
                String disposalMethod = (String) view.getDisposalComboBox().getSelectedItem();
                searchDefect(productName, disposalMethod);
            }
        });
    }

    private void searchDefect(String productName, String disposalMethod) {
        String sql = "SELECT product_code, product_name, disposal_method, defect_amount "
                + "FROM defectproduct "
                + "INNER JOIN product USING (product_code) ";

        boolean hasProductName = !productName.isEmpty();
        boolean hasDisposalMethod = !disposalMethod.equals("모든 불량사유");

        if (hasProductName || hasDisposalMethod) {
            sql += "WHERE ";
            if (hasProductName) {
                sql += "product_name LIKE ? ";
                if (hasDisposalMethod) {
                    sql += "AND ";
                }
            }
            if (hasDisposalMethod) {
                sql += "disposal_method = ? ";
            }
        }

        try (
            Connection conn = connector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            int paramIndex = 1;
            if (hasProductName) {
                pstmt.setString(paramIndex++, '%' + productName + '%');
            }
            if (hasDisposalMethod) {
                pstmt.setString(paramIndex, disposalMethod.substring(0, disposalMethod.indexOf(" ")));
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                List<WareHouseListModel> results = new ArrayList<>();
                while (rs.next()) {
                    results.add(new WareHouseListModel(rs));
                }
                view.getTableModel().setData(results);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "불량목록";
    }
}