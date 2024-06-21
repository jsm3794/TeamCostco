package main.java.com.teamcostco.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

import main.java.com.teamcostco.MainForm;
import main.java.com.teamcostco.model.HomeModel;
import main.java.com.teamcostco.model.manager.AuthManager;
import main.java.com.teamcostco.view.panels.HomePanel;

public class HomeController extends PanelController<HomePanel> {

	private Timer timer;
	private HomeModel model;

	public HomeController() {

		model = new HomeModel();
		
		SwingUtilities.invokeLater(() -> {
			if (AuthManager.getInstance().isLoggedIn()) {
				initComponents();
				startTimer();
			} else {
				MainForm.nav.navigateTo("login", false);
			}
		});
	}

	// 컴포넌트를 초기화하는 메서드
	public void initComponents() {
		List<String> labels = model.getButtonLabels();
		for (int i = 0; i < view.buttons.length; i++) {
			view.buttons[i].setText(labels.get(i));
		}

		view.buttons[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainForm.nav.push("login", true);
			}
		});
	}

	// 타이머를 시작하는 메서드
	public void startTimer() {
		timer = new Timer(1000, e -> updateTime());
		timer.start();
	}

	// 현재 시간을 업데이트하는 메서드
	private void updateTime() {
		LocalDateTime now = LocalDateTime.now();
		if (view != null) {
			view.updateTime(HomeModel.DATE_FORMATTER.format(now));
		}
	}

	// HomePanel 객체를 반환하는 메서드
	@Override
	public HomePanel getPanel() {
		return view;
	}

	// 페이지 이름을 반환하는 메서드
	@Override
	public String toString() {
		return "홈";
	}
}