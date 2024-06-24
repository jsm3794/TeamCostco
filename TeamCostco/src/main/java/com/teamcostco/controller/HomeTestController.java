package main.java.com.teamcostco.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

import main.java.com.teamcostco.MainForm;
import main.java.com.teamcostco.model.HomeModel;
import main.java.com.teamcostco.model.manager.AuthManager;
import main.java.com.teamcostco.view.panels.HomeTestPanel;

public class HomeTestController extends PanelController<HomeTestPanel> {
	
	private Timer timer;
	
	public HomeTestController() {
		
		SwingUtilities.invokeLater(() -> {
			if (AuthManager.getInstance().isLoggedIn()) {
				startTimer();
				inventorySeach();
				MaterialDispatch();
				ProductInspection();
				OrderList();
				WareHouseList();
				Inventorymodification();
				StockAccumulation();
				ReceivingProcess();
			} else {
				MainForm.nav.navigateTo("login", false);
			}
		});
		
	}
	
	public void inventorySeach() {
		
		view.getInventorySeachBtn().addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				MainForm.nav.push("sign", true);
			}
		});
	}
	
	public void MaterialDispatch() {
		
		view.getMaterialDispatchBtn().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainForm.nav.push("sign", true);
			}
		});
	}
	
	public void ProductInspection() {

		view.getProductInspectionBtn().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainForm.nav.push("sign", true);
			}
		});
	}
	
	public void OrderList() {

		view.getOrderListBtn().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainForm.nav.push("sign", true);
			}
		});
	}
	
	public void WareHouseList() {

		view.getWareHouseListBtn().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainForm.nav.push("sign", true);
			}
		});
	}
	
	public void Inventorymodification() {

		view.getInventorymodificationBtn().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainForm.nav.push("sign", true);
			}
		});
	}
	
	public void StockAccumulation() {

		view.getStockAccumulationBtn().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainForm.nav.push("sign", true);
			}
		});
	}
	
	public void ReceivingProcess() {

		view.getReceivingProcessBtn().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainForm.nav.push("sign", true);
			}
		});
	}
	

	public void startTimer() {
		timer = new Timer(1000, e -> updateTime());
		timer.start();
	}
	
	private void updateTime() {
		LocalDateTime now = LocalDateTime.now();
		if (view != null) {
			view.updateTime(HomeModel.DATE_FORMATTER.format(now));
		}
	}
	

	// 페이지 이름을 반환하는 메서드
	@Override
	public String toString() {
		return "홈";
	}
}