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

import javax.swing.SwingUtilities;

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

		// 라지콤보박스 초기화
		view.getComboBoxLargeCategory().addItem(""); // 빈 항목 추가
		for (String largeCategory : largeCategories) {
			view.getComboBoxLargeCategory().addItem(largeCategory);
		}
		view.getComboBoxLargeCategory().setSelectedIndex(-1); // 선택 초기화

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
				view.getComboBoxMediumCategory().setSelectedIndex(-1); // 선택 초기화
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
				view.getComboBoxSmallCategory().setSelectedIndex(-1); // 선택 초기화
			}
		});
	}

	public void productRegistrationComponents() {
		view.getproductRegistrationBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String productName = view.gettextFieldProductName().getText();
				String purchasePriceText = view.getTextFieldPurchasePrice().getText();
				String sellingPriceText = view.getTextFieldSellingPrice().getText();

				// 필수 입력값 확인
				if (productName.isEmpty() || productName.isEmpty() || purchasePriceText.isEmpty()
						|| sellingPriceText.isEmpty()) {
					DialogManager.showMessageBox(view, "모든 필드를 입력하세요.", null);
					return;
				}

				int purchasePrice, sellingPrice;
				try {
					purchasePrice = Integer.parseInt(purchasePriceText);
					sellingPrice = Integer.parseInt(sellingPriceText);
				} catch (NumberFormatException ex) {
					DialogManager.showMessageBox(view, "가격 필드는 숫자여야 합니다.", null);
					return;
				}

				String largeCategory = (String) view.getComboBoxLargeCategory().getSelectedItem();
				String mediumCategory = (String) view.getComboBoxMediumCategory().getSelectedItem();
				String smallCategory = (String) view.getComboBoxSmallCategory().getSelectedItem();

				// 시퀀스 값 가져오기
				long seq_val = 0;

				try (Connection con = DatabaseUtil.getConnection();
						PreparedStatement pstmtSeq = con
								.prepareStatement("SELECT PRODUCT_SEQ1.nextval AS seq FROM dual")) {
					ResultSet rs = pstmtSeq.executeQuery();
					if (rs.next()) {
						seq_val = rs.getLong("seq");
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
				}

				// INSERT 쿼리 실행
				String sql = "INSERT INTO product VALUES(?, 'PRID' || LPAD(?, 8, '0'), ?, ?, ?, ?, ?, ?, 0, 0)";
				try (Connection con = DatabaseUtil.getConnection();
						PreparedStatement pstmt = con.prepareStatement(sql)) {

					pstmt.setLong(1, 0); // 첫 번째 ?는 알아서 증가함
					pstmt.setLong(2, seq_val); // 2번째 시퀀스는 새로운 시퀀스를 만들어서 써야함
					pstmt.setString(3, productName);
					pstmt.setString(4, findLargeCategoryId(largeCategory));
					pstmt.setString(5, findMediumCategoryId(mediumCategory));
					pstmt.setString(6, findSmallCategoryId(smallCategory));
					pstmt.setInt(7, purchasePrice);
					pstmt.setInt(8, sellingPrice);

					pstmt.executeUpdate();
					String msg = String.format("등록성공<br>ID: PRID%08d<br>상품: %s", seq_val, productName);
					DialogManager.showMessageBox(view, msg, evt -> {
						MainForm.nav.navigateTo("registration", true);
					});

				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		});
	}

	private String findLargeCategoryId(String text) {
		String str = "";
		String sql = "SELECT main_id FROM maincategory WHERE main_name = ?";
		try (Connection con = DatabaseUtil.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
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
		try (Connection con = DatabaseUtil.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
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
		try (Connection con = DatabaseUtil.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
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

	private String getProductCode() {
		try (Connection con = DatabaseUtil.getConnection();) {
			String sql = "SELECT product_seq.currval FROM product";
			try (PreparedStatement pstmt = con.prepareStatement(sql)) {
				try (ResultSet rs = pstmt.executeQuery();) {
					if (rs.next()) {
						return String.format("PRID%08d", rs.getInt("product_seq.currval"));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String toString() {
		return "상품등록";
	}
}
