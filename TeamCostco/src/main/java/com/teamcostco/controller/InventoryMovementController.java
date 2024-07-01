package main.java.com.teamcostco.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.SwingWorker;

import main.java.com.teamcostco.model.database.DatabaseUtil;
import main.java.com.teamcostco.model.manager.DialogManager;
import main.java.com.teamcostco.view.panels.InventoryMovementPanel;

public class InventoryMovementController extends PanelController<InventoryMovementPanel> {

	int crrQty = 0;
	int appQty = 0;
	
	public InventoryMovementController() {
		loadInitialProductData();
		searchBtnComponents();
		showCurrQuantity();
		movementBtn();
	}
	
	private void loadInitialProductData() {
        String sql = "SELECT product_name FROM product";
        try (Connection con = DatabaseUtil.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                view.getComboBoxSmallCategory().addItem(rs.getString("product_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
    private void searchBtnComponents() {
        view.getSearchBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = view.getTextFieldProductName().getText();
                String sql = "SELECT product_name FROM product WHERE product_name LIKE ?";
                try (Connection con = DatabaseUtil.getConnection();
                     PreparedStatement pstmt = con.prepareStatement(sql)) {
                    pstmt.setString(1, '%' + str + '%');

                    view.getComboBoxSmallCategory().removeAllItems();

                    try (ResultSet rs = pstmt.executeQuery()) {
                        while (rs.next()) {
                            view.getComboBoxSmallCategory().addItem(rs.getString("product_name"));
                        }
                    }
                    
                    // 검색 결과가 없을 경우 원래 데이터를 다시 로드
                    if (view.getComboBoxSmallCategory().getItemCount() == 0) {
                        loadInitialProductData();
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

	private void showCurrQuantity() {
		view.getComboBoxSmallCategory().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String sql = "SELECT * FROM product WHERE product_name = ?";
				String str = (String) view.getComboBoxSmallCategory().getSelectedItem();
				try (Connection con = DatabaseUtil.getConnection();
						PreparedStatement pstmt = con.prepareStatement(sql);) {
					pstmt.setString(1, str);
					try (ResultSet rs = pstmt.executeQuery();) {
						if (rs.next()) {
							view.getCurrentQuantity().setText("수량: " + rs.getInt("current_inventory"));
						}
					}

				} catch (SQLException e1) {

					e1.printStackTrace();
				}
			}
		});
	}

	private void movementBtn() {
	    view.getMovementBtn().addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            //String name = view.getTextFieldProductName().getText();
	            String num = view.getTextFieldOutgoingQuantity().getText();
	            if (num.isEmpty()) {
	                DialogManager.showMessageBox(view, "출고수량을 입력해주세요.", null);
	                return;
	            }
	            int outgoingQuantity;
	            try {
	                outgoingQuantity = Integer.parseInt(num);
	            } catch (NumberFormatException ex) {
	                DialogManager.showMessageBox(view, "출고수량은 숫자만 입력가능합니다.", null);
	                return;
	            }

	            String productName = (String) view.getComboBoxSmallCategory().getSelectedItem();
	            
	            // 현재 재고량 확인
	            int currentInventory = getCurrentInventory(productName);
	            if (currentInventory == -1) {
	                DialogManager.showMessageBox(view, "재고 정보를 가져오는 데 실패했습니다.", null);
	                return;
	            }
	            
	            if (outgoingQuantity > currentInventory) {
	                DialogManager.showMessageBox(view, "출고수량이 현재 재고량보다 많습니다.", null);
	                return;
	            }

	            String str = "출고하시겠습니까?";
				DialogManager.showMessageBox(view, str, evt -> {

					new SwingWorker<Void, Void>() {
						int number = Integer.parseInt(view.getTextFieldOutgoingQuantity().getText());
						String productName = (String) view.getComboBoxSmallCategory().getSelectedItem();

						@Override
						protected Void doInBackground() throws Exception {
							
							String text = "UPDATE product SET current_inventory = current_inventory - ? WHERE product_name = ?";
							String text2 = "UPDATE product SET active_inventory = active_inventory + ? WHERE product_name = ?";
							String text3 = "SELECT current_inventory, appropriate_inventory FROM product WHERE product_name = ?";

							try (Connection con = DatabaseUtil.getConnection();
								PreparedStatement pstmt = con.prepareStatement(text);
								PreparedStatement pstmt2 = con.prepareStatement(text2);
								PreparedStatement pstmt3 = con.prepareStatement(text3)
							) {
								pstmt.setInt(1, number);
								pstmt.setString(2, productName);
								pstmt2.setInt(1, number);
								pstmt2.setString(2, productName);
								pstmt.executeUpdate();
								pstmt2.executeUpdate();
								pstmt3.setString(1, productName);
								
								try(
									ResultSet rs = pstmt3.executeQuery();
								) {
									if (rs.next()) {
										crrQty = rs.getInt("current_inventory");
										appQty = rs.getInt("appropriate_inventory");
									}
								}
							} catch (SQLException e1) {
								e1.printStackTrace();
							}

							return null;
						}

						@Override
						protected void done() {
							String message = String.format("<html>출고완료!<br>상품: %s<br>출고수량: %d<br>현재재고: %s</html>", productName, number, crrQty);
							DialogManager.showMessageBox(view, message, evt -> {							
								view.getCurrentQuantity().setText("수량: " + crrQty);
							});
						}

					}.execute();
	            }, null);
	        }
	    });
	}

	private int getCurrentInventory(String productName) {
	    String sql = "SELECT current_inventory FROM product WHERE product_name = ?";
	    try (Connection con = DatabaseUtil.getConnection();
	         PreparedStatement pstmt = con.prepareStatement(sql)) {
	        pstmt.setString(1, productName);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt("current_inventory");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return -1; 
	}
	

	@Override
	public String toString() {
		return "재고이동";
	}
}
