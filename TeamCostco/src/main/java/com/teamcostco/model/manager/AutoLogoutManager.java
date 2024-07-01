package main.java.com.teamcostco.model.manager;

import javax.swing.*;
import main.java.com.teamcostco.MainForm;
import java.awt.*;
import java.awt.event.*;

public class AutoLogoutManager {
	private static AutoLogoutManager instance;
	private Timer timer;
	private JFrame frame;
	private int logoutTime; // 밀리초 단위

	private AutoLogoutManager() {
	}

	public static AutoLogoutManager getInstance() {
		if (instance == null) {
			instance = new AutoLogoutManager();
		}
		return instance;
	}

	public void initialize(JFrame frame) {
		this.frame = frame;
		updateLogoutTime();
		setupActivityListener();
	}

	public void updateLogoutTime() {
		if (SettingsManager.isAutoLockEnabled()) {
			String timeString = SettingsManager.getAutoLockTime();
			this.logoutTime = convertTimeStringToMillis(timeString);
			setupNewTimer();
		} else {
			stopTimer();
		}
	}

	private void setupNewTimer() {
		stopTimer();
		timer = new Timer(logoutTime, e -> {
			if (SettingsManager.isAutoLockEnabled()) {
				logout();
			}
		});
		timer.setRepeats(false);
		timer.start();
	}

	private int convertTimeStringToMillis(String timeString) {
		int time = 0;
		if (timeString.endsWith("초")) {
			time = Integer.parseInt(timeString.replace("초", "")) * 1000;
		} else if (timeString.endsWith("분")) {
			time = Integer.parseInt(timeString.replace("분", "")) * 60 * 1000;
		} else if (timeString.endsWith("시간")) {
			time = Integer.parseInt(timeString.replace("시간", "")) * 60 * 60 * 1000;
		}
		return time;
	}

	private void setupActivityListener() {
		AWTEventListener activityListener = event -> {
			if (event instanceof MouseEvent || event instanceof KeyEvent) {
				if (SettingsManager.isAutoLockEnabled() && timer != null) {
					resetTimer();
				}
			}
		};
		Toolkit.getDefaultToolkit().addAWTEventListener(activityListener,
				AWTEvent.MOUSE_EVENT_MASK | AWTEvent.KEY_EVENT_MASK);
	}

	private void resetTimer() {
		if (timer != null) {
			timer.restart();
		}
	}

	private void stopTimer() {
		if (timer != null) {
			timer.stop();
			timer = null;
		}
	}

	private void logout() {
		AuthManager.getInstance().logout();
		MainForm.nav.popAll();
		MainForm.nav.navigateTo("login", false).thenRun(() -> {
			DialogManager.showMessageBox(MainForm.nav.getCurrent().getPanel(), "장시간 활동이 없어<br>자동 로그아웃되었습니다.", null);
		});
		stopTimer();
	}
}