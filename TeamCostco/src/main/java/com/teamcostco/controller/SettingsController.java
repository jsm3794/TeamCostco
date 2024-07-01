package main.java.com.teamcostco.controller;

import main.java.com.teamcostco.MainForm;
import main.java.com.teamcostco.model.MemberModel;
import main.java.com.teamcostco.model.manager.AuthManager;
import main.java.com.teamcostco.model.manager.AutoLogoutManager;
import main.java.com.teamcostco.model.manager.SettingsManager;
import main.java.com.teamcostco.view.panels.SettingsPanel;

public class SettingsController extends PanelController<SettingsPanel> {
    private MemberModel member;

    public SettingsController() {
        initializeUser();
        setupUserInterface();
    }

    private void initializeUser() {
        member = AuthManager.getInstance().getCurrentUserModel();
    }

    private void setupUserInterface() {
        setupUserProfile();
        setupEditAccountButton();
        setupAutoLockSettings();
        setupLogoutButton();
    }

    private void setupUserProfile() {
        view.setUserProfile(member.getId(), member.getName(), member.getEmail());
    }

    private void setupEditAccountButton() {
        view.editAccountButton.addActionListener(evt -> 
            MainForm.nav.push("editaccount", true, member));
    }

    private void setupAutoLockSettings() {
        loadAutoLockSettings();
        view.autoLockToggle.addActionListener(e -> saveAutoLockSettings());
        view.autoLockTimeComboBox.addActionListener(e -> saveAutoLockSettings());
    }

    private void setupLogoutButton() {
        view.logoutButton.addActionListener(e -> performLogout());
    }

    private void loadAutoLockSettings() {
        boolean enabled = SettingsManager.isAutoLockEnabled();
        String time = SettingsManager.getAutoLockTime();
        updateAutoLockView(enabled, time);
    }

    private void updateAutoLockView(boolean enabled, String time) {
        view.autoLockToggle.setSelected(enabled);
        view.autoLockTimeComboBox.setSelectedItem(time);
        view.autoLockTimeComboBox.setEnabled(enabled);
    }

    private void saveAutoLockSettings() {
        boolean enabled = view.isAutoLockEnabled();
        String time = view.getAutoLockTime();
        SettingsManager.saveAutoLockSettings(enabled, time);
        AutoLogoutManager.getInstance().updateLogoutTime();
    }

    private void performLogout() {
        AuthManager.getInstance().logout();
        MainForm.nav.popAll();
        MainForm.nav.navigateTo("home", false);
    }

    @Override
    public String toString() {
        return "환경설정";
    }
}