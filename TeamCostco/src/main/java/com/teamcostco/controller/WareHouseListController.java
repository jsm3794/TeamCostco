package main.java.com.teamcostco.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import main.java.com.teamcostco.model.AllCategoryJoin;
import main.java.com.teamcostco.model.CategoryData;
import main.java.com.teamcostco.model.WareHouseListModel;
import main.java.com.teamcostco.model.database.DatabaseUtil;
import main.java.com.teamcostco.view.panels.WareHouseListPanel;

public class WareHouseListController extends PanelController<WareHouseListPanel> {
    private DatabaseUtil connector;
    private CategoryData categoryData;

    public WareHouseListController() {
        this.connector = new DatabaseUtil();
        this.categoryData = new CategoryData(loadAllCategories());
        initialize();
    }

    private void initialize() {
        // Populate main category combo box
        view.getMainCateComboBox().addItem("대분류 전체");
        for (String main : categoryData.getMainCategories()) {
            view.getMainCateComboBox().addItem(main);
        }

        // Add action listener to main category combo box
        view.getMainCateComboBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedMain = (String) view.getMainCateComboBox().getSelectedItem();
                loadMediumCategories(selectedMain);
                view.getSmallCateCombo().removeAllItems();
                view.getSmallCateCombo().addItem("소분류 전체");
                view.getSmallCateCombo().setEnabled(false);
            }
        });

        // Add action listener to medium category combo box
        view.getMediumCateCombo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedMedium = (String) view.getMediumCateCombo().getSelectedItem();
                loadSmallCategories(selectedMedium);
            }
        });

        // Search products on button click
        view.getSearchButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchDefectiveProducts();
            }
        });
    }

    private void searchDefectiveProducts() {
        StringBuilder sql = new StringBuilder(
        		  "SELECT DISTINCT product_name, disposal_id, defect_amount, created_at "
                + "FROM defectproduct dp "
                + "INNER JOIN product p USING ( product_code ) "
                + "INNER JOIN maincategory m ON p.main_id = m.main_id "
                + "INNER JOIN mediumcategory mc ON p.medium_id = mc.medium_id "
                + "INNER JOIN smallcategory s ON p.small_id = s.small_id "
                + "WHERE 1=1 ");

        List<Object> parameters = new ArrayList<>();

        String productName = view.getProductNameField().getText();
        if (productName != null && !productName.trim().isEmpty()) {
            sql.append("AND p.product_name LIKE ? ");
            parameters.add("%" + productName.trim() + "%");
        }

        String selectedMain = (String) view.getMainCateComboBox().getSelectedItem();
        if (selectedMain != null && !"대분류 전체".equals(selectedMain)) {
            sql.append("AND m.main_name LIKE ? ");
            parameters.add("%" + selectedMain + "%");
        }

        String selectedMedium = (String) view.getMediumCateCombo().getSelectedItem();
        if (selectedMedium != null && !"중분류 전체".equals(selectedMedium)) {
            sql.append("AND mc.medium_name LIKE ? ");
            parameters.add("%" + selectedMedium + "%");
        }

        String selectedSmall = (String) view.getSmallCateCombo().getSelectedItem();
        if (selectedSmall != null && !"소분류 전체".equals(selectedSmall)) {
            sql.append("AND s.small_name LIKE ? ");
            parameters.add("%" + selectedSmall + "%");
        }

        sql.append("ORDER BY dp.disposal_id ");
        
        System.out.println(sql.toString());

        try (Connection conn = connector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < parameters.size(); i++) {
                pstmt.setObject(i + 1, parameters.get(i));
                System.out.println(parameters.get(i));
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

    private List<AllCategoryJoin> loadAllCategories() {
        List<AllCategoryJoin> allCategories = new ArrayList<>();
        String sql = "SELECT main_name, medium_name, small_name "
                + "FROM maincategory "
                + "INNER JOIN mediumcategory USING (main_id) "
                + "INNER JOIN smallcategory USING (medium_id)";

        try (Connection conn = connector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                allCategories.add(new AllCategoryJoin(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCategories;
    }

    private void loadMediumCategories(String selectedMain) {
        view.getMediumCateCombo().removeAllItems();
        view.getMediumCateCombo().addItem("중분류 전체");

        if (selectedMain == null || selectedMain.equals("대분류 전체")) {
            view.getMediumCateCombo().setEnabled(false);
            view.getSmallCateCombo().setEnabled(false);
            return;
        }

        Set<String> mediumCategories = categoryData.getMediumCategories(selectedMain);
        for (String medium : mediumCategories) {
            view.getMediumCateCombo().addItem(medium);
        }
        view.getMediumCateCombo().setEnabled(true);
    }

    private void loadSmallCategories(String selectedMedium) {
        view.getSmallCateCombo().removeAllItems();
        view.getSmallCateCombo().addItem("소분류 전체");

        if (selectedMedium == null || selectedMedium.equals("중분류 전체")) {
            view.getSmallCateCombo().setEnabled(false);
            return;
        }

        Set<String> smallCategories = categoryData.getSmallCategories(selectedMedium);
        for (String small : smallCategories) {
            view.getSmallCateCombo().addItem(small);
        }
        view.getSmallCateCombo().setEnabled(true);
    }

    @Override
    public String toString() {
        return "불량재고";
    }
}
