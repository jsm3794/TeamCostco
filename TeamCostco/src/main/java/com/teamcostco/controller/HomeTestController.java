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
				startTimer();
				inventorySeach();
				materialDispatch();
				productInspection();
				orderList();
				wareHouseList();
				inventorymodification();
				stockAccumulation();
				receivingProcess();
				productRegistration();
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
	
	public void materialDispatch() {
		
		view.getMaterialDispatchBtn().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainForm.nav.push("sign", true);
			}
		});
	}
	
	public void productInspection() {

		view.getProductInspectionBtn().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainForm.nav.push("sign", true);
			}
		});
	}
	
	public void orderList() {

		view.getOrderListBtn().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainForm.nav.push("sign", true);
			}
		});
	}
	
	public void wareHouseList() {

		view.getWareHouseListBtn().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainForm.nav.push("sign", true);
			}
		});
	}
	
	public void inventorymodification() {

		view.getInventorymodificationBtn().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainForm.nav.push("sign", true);
			}
		});
	}
	
	public void stockAccumulation() {

		view.getStockAccumulationBtn().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainForm.nav.push("sign", true);
			}
		});
	}
	
	public void receivingProcess() {

		view.getReceivingProcessBtn().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainForm.nav.push("sign", true);
			}
		});
	}
	
	public void productRegistration() {
		
		view.getProductRegistrationBtn().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainForm.nav.push("registration", true);
				
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