package main.java.com.teamcostco.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

import main.java.com.teamcostco.MainForm;
import main.java.com.teamcostco.model.HomeModel;
import main.java.com.teamcostco.model.manager.AuthManager;
import main.java.com.teamcostco.view.panels.NewHome;

public class HomeTestController extends PanelController<NewHome> {

	private Timer timer;

	public HomeTestController() {
		view.setVisible(false);

		SwingUtilities.invokeLater(() -> {
			if (AuthManager.getInstance().isLoggedIn()) {
				startTimer();
				inventorySeach();
				inventoryMovement();
				productCheck();
				orderHistory();
				defectiveInventory();
				pdaSetting();
				productEntry();
				orderRequest();
				view.setVisible(true);

			} else {
				MainForm.nav.navigateTo("login", false);
			}
		});

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

	public void defectiveInventory() {

		view.getDefectiveInventoryBtn().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainForm.nav.push("defectiveInventory", true);
			}
		});
	}

	public void orderRequest() {

		view.getOrderRequestBtn().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainForm.nav.push("orderRequest", true);
			}
		});
	}

	public void pdaSetting() {

		view.getReceivingProcessBtn().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainForm.nav.push("pdaSetting", true);
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