package main.java.com.teamcostco.controller;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import main.java.com.teamcostco.MainForm;
import main.java.com.teamcostco.dao.ProductDAO;
import main.java.com.teamcostco.model.database.DatabaseUtil;
import main.java.com.teamcostco.model.manager.DialogManager;
import main.java.com.teamcostco.model.manager.DialogManager.Context;
import main.java.com.teamcostco.view.panels.InventorySearchPanel;

public class InventorySearchController extends PanelController<InventorySearchPanel> {

    public InventorySearchController() {
        initializeCategoryComboBox();
        initializeSearchButton();
        bindTableAction();
    }

    private void initializeCategoryComboBox() {
        String[] categories = {"전체", "대분류", "중분류", "소분류"};
        for (String category : categories) {
            view.categoryComboBox.addItem(category);
        }

        view.categoryComboBox.addActionListener(this::handleCategorySelection);
        view.categoryComboBox.addItemListener(e -> view.cb_CategorizeName.setEnabled(!e.getItem().equals("전체")));
    }

    private void handleCategorySelection(ActionEvent e) {
        String selectedCategory = (String) view.categoryComboBox.getSelectedItem();
        switch (selectedCategory) {
            case "대분류":
                loadCategories("SELECT main_name FROM maincategory", "main_name");
                break;
            case "중분류":
                loadCategories("SELECT medium_name FROM mediumcategory", "medium_name");
                break;
            case "소분류":
                loadCategories("SELECT small_name FROM smallcategory", "small_name");
                break;
        }
    }

    private void loadCategories(String sql, String columnName) {
        view.cb_CategorizeName.removeAllItems();
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                view.cb_CategorizeName.addItem(rs.getString(columnName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initializeSearchButton() {
        view.searchButton.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            Context context = DialogManager.showLoadingBox(view);
            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() {
                    search();
                    return null;
                }

                @Override
                protected void done() {
                    context.close();
                }
            }.execute();
        }));
    }

    public void search() {
        String searchKeyword = view.searchField.getText();
        String selectedCategory = (String) view.cb_CategorizeName.getSelectedItem();
        String categoryKind = (String) view.categoryComboBox.getSelectedItem();
        loadData(view.model, searchKeyword, categoryKind, selectedCategory);
    }

    private void loadData(DefaultTableModel tableModel, String searchKeyword, String categoryKind, String categoryName) {
        String joinSql = "";
        String whereClause = "WHERE p.product_name LIKE ? ";
        List<String> params = new ArrayList<>();
        params.add("%" + searchKeyword + "%");

        if (!categoryKind.equals("전체")) {
            switch (categoryKind) {
                case "대분류":
                    joinSql = "JOIN maincategory m ON p.main_id = m.main_id ";
                    whereClause += "AND m.main_name LIKE ? ";
                    break;
                case "중분류":
                    joinSql = "JOIN maincategory m ON p.main_id = m.main_id " +
                              "JOIN mediumcategory mc ON p.medium_id = mc.medium_id ";
                    whereClause += "AND mc.medium_name LIKE ? ";
                    break;
                case "소분류":
                    joinSql = "JOIN maincategory m ON p.main_id = m.main_id " +
                              "JOIN smallcategory s ON p.small_id = s.small_id ";
                    whereClause += "AND s.small_name LIKE ? ";
                    break;
            }
            params.add("%" + categoryName + "%");
        }

        String sql = "SELECT p.product_id, p.product_name, p.product_code, p.main_id, " +
                     "p.current_inventory, p.active_inventory, p.selling_price " +
                     "FROM product p " + joinSql + whereClause;

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            for (int i = 0; i < params.size(); i++) {
                pstmt.setString(i + 1, params.get(i));
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                tableModel.setRowCount(0);
                while (rs.next()) {
                    tableModel.addRow(new Object[]{
                            rs.getString("main_id"),
                            rs.getString("product_code"),
                            rs.getString("product_name"),
                            rs.getInt("current_inventory"),
                            rs.getInt("active_inventory")
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void bindTableAction() {
        view.table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = view.table.rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        String productCode = (String) view.table.getValueAt(row, 1);
                        MainForm.nav.push("productdetail", true, ProductDAO.getProductByCode(productCode));
                    }
                }
            }
        });
    }

    @Override
    public String toString() {
        return "재고조회";
    }
}