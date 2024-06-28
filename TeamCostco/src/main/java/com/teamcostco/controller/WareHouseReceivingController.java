package main.java.com.teamcostco.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.print.attribute.standard.DialogOwner;

import main.java.com.teamcostco.MainForm;
import main.java.com.teamcostco.model.OrderDetailModel;
import main.java.com.teamcostco.model.database.DatabaseUtil;
import main.java.com.teamcostco.model.manager.DialogManager;
import main.java.com.teamcostco.view.panels.WarehouseReceivingPanel;

public class WareHouseReceivingController extends PanelController<WarehouseReceivingPanel> {

	public OrderDetailModel data;
	
	public WareHouseReceivingController(OrderDetailModel data) {
		this.data = data;
		initComponents();
	}
	
	private void initComponents() {
		view.currentDateLabel.setText(getCurrentDate());
		view.productIdLabel.setText(data.getItem_number());
		view.categoryLabel.setText(data.getmain_name());
		view.productNameLabel.setText(data.getProduct_name()); 
		int remainingQuantity = data.getOrder_quantity() - data.getQuantity_of_wh();
		view.remaining_capacity.setText(String.valueOf(remainingQuantity));
		view.order_quantity.setText(String.valueOf(data.getOrder_quantity()));
		view.saveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				DialogManager.showMessageBox(view, "  ", evt ->{
					
					String sql = "INSERT INTO productinspection "
							+ "VALUES(시퀀스자리, ?,?,?,?,?,sysdate)";
					try(
						Connection conn = DatabaseUtil.getConnection();
						PreparedStatement pstmt = conn.prepareStatement(sql);					
					){
						pstmt.setString(1, data.getItem_number());
						pstmt.setInt(2, data.getOrder_quantity());
						pstmt.setInt(3, Integer.parseInt(view.getTextField().getText()));
						
						pstmt.executeUpdate();
						
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					
				}, null);
				
				
			
			}
		});
		
	}
	
	
	
	
	
	public static String getCurrentDate() {
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        return year + "년 " + month + "월 " + day + "일";
    }

	@Override
	public String toString() {
		return "입고처리";
	}
}
