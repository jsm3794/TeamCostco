package main.java.com.teamcostco.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.com.teamcostco.MainForm;
import main.java.com.teamcostco.model.database.DatabaseUtil;
import main.java.com.teamcostco.model.manager.DialogManager;
import main.java.com.teamcostco.view.panels.ProductRegistrationPanel;

public class ProductRegistrationController extends PanelController<ProductRegistrationPanel> {

    private List<String> largeCategories = new ArrayList<>();
    private Map<String, List<String>> mediumCategories = new HashMap<>();
    private Map<String, List<String>> smallCategories = new HashMap<>();

    public ProductRegistrationController() {
        loadCategories();
        initializationComponents();
        productRegistrationComponents();
    }

    private void loadCategories() {
        loadLargeCategories();
        loadMediumCategories();
        loadSmallCategories();
    }

    private void loadLargeCategories() {
        String sql = "SELECT main_name FROM maincategory";
        try (Connection con = DatabaseUtil.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                largeCategories.add(rs.getString("main_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadMediumCategories() {
        String sql = "SELECT main_name, midium_name FROM midiumcategory INNER JOIN maincategory USING(main_id)";
        try (Connection con = DatabaseUtil.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String largeCategory = rs.getString("main_name");
                String mediumCategory = rs.getString("midium_name");
                if (!mediumCategories.containsKey(largeCategory)) {
                    mediumCategories.put(largeCategory, new ArrayList<>());
                }
                mediumCategories.get(largeCategory).add(mediumCategory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadSmallCategories() {
        String sql = "SELECT midium_name, small_name FROM smallcategory INNER JOIN midiumcategory USING(midium_id)";
        try (Connection con = DatabaseUtil.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String mediumCategory = rs.getString("midium_name");
                String smallCategory = rs.getString("small_name");
                if (!smallCategories.containsKey(mediumCategory)) {
                    smallCategories.put(mediumCategory, new ArrayList<>());
                }
                smallCategories.get(mediumCategory).add(smallCategory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initializationComponents() {
        view.getInitializationBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainForm.nav.navigateTo("registration", true);
            }
        });

        for (String largeCategory : largeCategories) {
            view.getComboBoxLargeCategory().addItem(largeCategory);
        }

        view.getComboBoxLargeCategory().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedLargeCategory = (String) view.getComboBoxLargeCategory().getSelectedItem();
                view.getComboBoxMediumCategory().removeAllItems();
                if (mediumCategories.containsKey(selectedLargeCategory)) {
                    for (String mediumCategory : mediumCategories.get(selectedLargeCategory)) {
                        view.getComboBoxMediumCategory().addItem(mediumCategory);
                    }
                }
            }
        });

        view.getComboBoxMediumCategory().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedMediumCategory = (String) view.getComboBoxMediumCategory().getSelectedItem();
                view.getComboBoxSmallCategory().removeAllItems();
                if (smallCategories.containsKey(selectedMediumCategory)) {
                    for (String smallCategory : smallCategories.get(selectedMediumCategory)) {
                        view.getComboBoxSmallCategory().addItem(smallCategory);
                    }
                }
            }
        });
    }

    public void productRegistrationComponents() {
        view.getproductRegistrationBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
                String productCode = view.getTextFieldProductCode().getText();
                String productName = view.gettextFieldProductName().getText();
                String largeCategory = (String) view.getComboBoxLargeCategory().getSelectedItem();
                String mediumCategory = (String) view.getComboBoxMediumCategory().getSelectedItem();
                String smallCategory = (String) view.getComboBoxSmallCategory().getSelectedItem();
                int purchasePrice = Integer.parseInt(view.getTextFieldPurchasePrice().getText());
                int sellingPrice = Integer.parseInt(view.getTextFieldSellingPrice().getText());
                
                String sql = "INSERT INTO product VALUES(product_seq.nextval, ?, ?, ?, ?, ?, ?, ?, 0, 0)";
                
                try (Connection con = DatabaseUtil.getConnection();
                     PreparedStatement pstmt = con.prepareStatement(sql)) {
                    pstmt.setString(1, productCode);
                    pstmt.setString(2, productName);
                    pstmt.setString(3, findLargeCategoryId(largeCategory));
                    pstmt.setString(4, findMediumCategoryId(mediumCategory));
                    pstmt.setString(5, findSmallCategoryId(smallCategory));
                    pstmt.setInt(6, purchasePrice);
                    pstmt.setInt(7, sellingPrice);

                    pstmt.executeUpdate();
                    DialogManager.showMessageBox(view, "등록성공", false, null, null);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    private String findLargeCategoryId(String text) {
        String str = "";
        String sql = "SELECT main_id FROM maincategory WHERE main_name = ?";
        try (Connection con = DatabaseUtil.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, text);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    str = rs.getString("main_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return str;
    }

    private String findMediumCategoryId(String text) {
        String str = "";
        String sql = "SELECT midium_id FROM midiumcategory WHERE midium_name = ?";
        try (Connection con = DatabaseUtil.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, text);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    str = rs.getString("midium_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return str;
    }

    private String findSmallCategoryId(String text) {
        String str = "";
        String sql = "SELECT small_id FROM smallcategory WHERE small_name = ?";
        try (Connection con = DatabaseUtil.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, text);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    str = rs.getString("small_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return str;
    }

    @Override
    public String toString() {
        return "상품등록";
    }
}
