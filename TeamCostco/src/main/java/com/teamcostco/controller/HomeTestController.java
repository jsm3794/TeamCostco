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
import main.java.com.teamcostco.view.panels.NewHome;

public class HomeTestController extends PanelController<NewHome> {
	
	private Timer timer;
	
	
	public HomeTestController() {
		
		SwingUtilities.invokeLater(() -> {
			if (AuthManager.getInstance().isLoggedIn()) {

			} else {
				MainForm.nav.navigateTo("login", false);
			}
		});
		
		startTimer();
		inventorySeach();
		inventoryMovement();
		productCheck();
		orderHistory();
		storageList();
		inventoryUpdate();
		inventoryPlacement();
		productReceiving();
		productEntry();
		
	}
	
	public void inventorySeach() {
		
		view.getInventorySeachBtn().addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				MainForm.nav.push("inventorySearch", true);
			}
		});
	}
	
	public void inventoryMovement() {
		
		view.getMaterialDispatchBtn().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainForm.nav.push("inventoryMovement", true);
			}
		});
	}
	
	public void productCheck() {

		view.getProductInspectionBtn().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainForm.nav.push("productCheck", true);
			}
		});
	}
	
	public void orderHistory() {

		view.getOrderListBtn().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainForm.nav.push("orderHistory", true);
			}
		});
	}
	
	public void storageList() {

		view.getWareHouseListBtn().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainForm.nav.push("storageList", true);
			}
		});
	}
	
	public void inventoryUpdate() {

		view.getInventorymodificationBtn().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainForm.nav.push("inventoryUpdate", true);
			}
		});
	}
	
	public void inventoryPlacement() {

		view.getStockAccumulationBtn().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainForm.nav.push("inventoryPlacement", true);
			}
		});
	}
	
	public void productReceiving() {

		view.getReceivingProcessBtn().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainForm.nav.push("productReceiving", true);
			}
		});
	}
	
	public void productEntry() {
		
		view.getProductRegistrationBtn().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainForm.nav.push("productEntry", true);
				
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