package main.java.com.teamcostco.controller;

import java.time.LocalDate;

import main.java.com.teamcostco.model.OrderDetailModel;
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
		view.categoryLabel.setText(data.getCategory_name());
		view.productNameLabel.setText(data.getProduct_name()); 
		int remainingQuantity = data.getOrder_quantity() - data.getQuantity_of_wh();
		view.remaining_capacity.setText(String.valueOf(remainingQuantity));
		view.order_quantity.setText(String.valueOf(data.getOrder_quantity()));
		
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
