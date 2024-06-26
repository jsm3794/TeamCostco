package main.java.com.teamcostco.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.com.teamcostco.MainForm;
import main.java.com.teamcostco.model.database.DatabaseUtil;
import main.java.com.teamcostco.view.panels.ProductRegistrationPanel;

public class ProductRegistrationController extends PanelController<ProductRegistrationPanel> {

	public ProductRegistrationController() {
		searchLargeCategory();
		initializationComponents();
		productRegistrationComponents();
	}

	public void initializationComponents() {
		view.getInitializationBtn().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainForm.nav.navigateTo("registration", true);
			}
		});
	}

	public void productRegistrationComponents() {
		view.getproductRegistrationBtn().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String productCode = view.getTextFieldProductCode().getText();
			}
		});
	}

	private void searchLargeCategory() {
		String sql = "SELECT main_name FROM maincategory";
		try (Connection con = DatabaseUtil.getConnection();) {
			try (PreparedStatement pstmt = con.prepareStatement(sql); ResultSet rs = pstmt.executeQuery();) {
				while (rs.next()) {
					view.getComboBoxLargeCategory().addItem(rs.getString("main_name"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		view.getComboBoxLargeCategory().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedLargeCategory = (String) view.getComboBoxLargeCategory().getSelectedItem();
				view.getComboBoxMediumCategory().removeAllItems();
				searchMediumCategory(selectedLargeCategory);
			}
		});
	}

	private void searchMediumCategory(String selectedLargeCategory) {
		String sql = "SELECT midium_name FROM midiumcategory midiumc INNER JOIN maincategory mainc USING(main_id) WHERE mainc.main_name = ?";
		try (Connection con = DatabaseUtil.getConnection();) {
			try (PreparedStatement pstmt = con.prepareStatement(sql);) {
				pstmt.setString(1, selectedLargeCategory);
				try (ResultSet rs = pstmt.executeQuery();) {
					while (rs.next()) {
						view.getComboBoxMediumCategory().addItem(rs.getString("midium_name"));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		view.getComboBoxMediumCategory().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedMediumCategory = (String) view.getComboBoxMediumCategory().getSelectedItem();
				view.getComboBoxSmallCategory().removeAllItems();
				searchSmallCategory(selectedMediumCategory);
			}
		});
	}

	private void searchSmallCategory(String selectedMediumCategory) {
		String sql = "SELECT small_name FROM smallcategory smallc INNER JOIN midiumcategory midiumc USING(midium_id) WHERE midiumc.midium_name = ?";
		try (Connection con = DatabaseUtil.getConnection();) {
			try (PreparedStatement pstmt = con.prepareStatement(sql);) {
				pstmt.setString(1, selectedMediumCategory);
				try (ResultSet rs = pstmt.executeQuery();) {
					while (rs.next()) {
						view.getComboBoxSmallCategory().addItem(rs.getString("small_name"));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "상품등록";
	}
}
