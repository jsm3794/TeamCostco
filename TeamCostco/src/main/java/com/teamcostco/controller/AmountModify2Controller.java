package main.java.com.teamcostco.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import main.java.com.teamcostco.model.AmountModifyModel;
import main.java.com.teamcostco.model.database.DatabaseUtil;
import main.java.com.teamcostco.model.manager.DialogManager;
import main.java.com.teamcostco.view.panels.AmountModifyPanel2;

// 미구현 :  ()
// 상품상세조회패널이랑 연동된 패널이라 생각하고 구현.
// ex > 재고조회? or 상품상세정보 -> 조정요청
// 상품명, 카테고리 재고 입고날짜 가격 코드 적재위치 불러오기 설정 : 다른 패널이랑 연동시켜야될것같아서 일단 보류


public class AmountModify2Controller extends PanelController<AmountModifyPanel2> {

	private AmountModifyModel model;
	private DatabaseUtil connector;
	private static int cnt = 0;
	
	public AmountModify2Controller() {
		connector = new DatabaseUtil();
		initComponents();
		
		// + 버튼 기능 
		view.getPlusButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				plusAmount();
				
			}
		});
		
		// - 버튼 기능
		view.getMinusButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					minusAmount();
				} catch (NegativeAmount e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}				
			}
		});
		
		
		// (수량 수정)
		view.getModifyButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DialogManager.showMessageBox
				(
					view,
					"수량을 수정하시겠습니까?",
					true,
					evt -> updateAmount(),
					evt -> System.out.println("아니오 클릭")
				);
				
				
			}
		});

		// 취소버튼
		view.getCancelButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getDetailInfo();
			}
		});

	}

	
	
	public void initComponents() {
		setComboBoxValue();
		
		// 상품 상세정보 패널에서 상품 정보 가져오기
		// 아래는 내용 예시로 채웠읍니다
		getDetailInfo();
		
	}

	private void getDetailInfo() {
//		view.getProductNameField().setText("");
//		view.getCategoryComboBox().setSelectedItem("식품");
//		view.getAmount_txtField().setText("0");
//		view.getWhdate_txtArea().setText("미정");
//		view.getSellPrice_txtArea().setText("1000(예시)");
//		view.getPid_txtArea().setText("PRID00000000000000000000(예시)");
//		view.getLocation_txtArea().setText("A-01(예시)");
	}
	
	// 대분류 불러옴
	private void setComboBoxValue() {
		String sql = "SELECT DISTINCT main_name FROM maincategory";

		try (
				Connection conn = connector.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
		) {

			while (rs.next()) {
				String categori_name = rs.getString("main_name");
				view.getCategoryComboBox().addItem(categori_name);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// 재고 수정 + (수정사유반영)
	private void updateAmount() {
		String input_pn = view.getProductNameField().getText().trim();
		String selected_category = (String) view.getCategoryComboBox().getSelectedItem();
		
		StringBuilder sql = new StringBuilder( 
				"UPDATE storage SET amount = ? "
				+ "WHERE product_id = ? AND storage_id = ?"				
				);
		// 상품명 입력 안할시 리턴
		if (input_pn.isEmpty()) {
			System.out.println("상품명이 공백입니다.");
			return;
		}
		try (
				Connection conn = connector.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		) {
				conn.setAutoCommit(false);
				
				String amount = view.getAmount_txtField().getText();
				
				pstmt.setInt(1, Integer.parseInt(amount));
				pstmt.setString(2, view.getPid_txtArea().getText());
				pstmt.setString(3, view.getLocation_txtArea().getText());
				
				try {
					int row = pstmt.executeUpdate();
					System.out.printf("%d행 업데이트\n", row);
					conn.setSavepoint("수정" + ++cnt);
				} catch (SQLException e2) {
					System.out.println("문제가 생겨서 롤백");
					conn.rollback();
				}
				
				
		} catch (SQLException e1) {
			e1.printStackTrace();
			
		}
	}
	
	private void plusAmount() {
		String amount = view.getAmount_txtField().getText();
		Integer change = Integer.parseInt(amount) + 1;
		amount = change.toString();
		
		view.getAmount_txtField().setText(amount);
	}
	
	private void minusAmount() throws NegativeAmount {
		String amount = view.getAmount_txtField().getText();
		Integer change = Integer.parseInt(amount);
		if (change - 1 < 0) {
			 throw new  NegativeAmount();
		} else {
			--change;
		}
		amount = change.toString();
		
		view.getAmount_txtField().setText(amount);
	}
	
	public void resetFields() {
	    view.getProductNameField().setText("");
	    view.getCategoryComboBox().setSelectedIndex(0); // Assuming the first item is a placeholder like "Select Category"
	    view.getAmount_txtField().setText("0");
	    view.getWhdate_txtArea().setText("");
	    view.getSellPrice_txtArea().setText("");
	    view.getPid_txtArea().setText("");
	    view.getLocation_txtArea().setText("");
	    view.getMr_comboBox().setSelectedIndex(0);
	}
	
	
	@Override
	public String toString() {
		return "재고수정";
	}

}

// 예외
// 수량이 음수
class NegativeAmount extends Exception {
	public NegativeAmount() {
		super("수량이 음수입니다.");
	}
}

class ExeedStorageAmount extends Exception {
	public ExeedStorageAmount() {
		super("창고보관수량을 초과하는 수량입니다.");
	}
}


