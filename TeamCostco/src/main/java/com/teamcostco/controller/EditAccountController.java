package main.java.com.teamcostco.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.com.teamcostco.MainForm;
import main.java.com.teamcostco.model.MemberModel;
import main.java.com.teamcostco.model.database.DatabaseUtil;
import main.java.com.teamcostco.model.manager.AuthManager;
import main.java.com.teamcostco.model.manager.DialogManager;
import main.java.com.teamcostco.view.panels.EditAccountPanel;

public class EditAccountController extends PanelController<EditAccountPanel> {
    private MemberModel member;

    public EditAccountController(MemberModel member) {
        this.member = member;
        initializeView();
        setupEventListeners();
    }

    private void initializeView() {
        view.idField.setText(member.getId());
        view.nameField.setText(member.getName());
        view.emailField.setText(member.getEmail());
    }

    private void setupEventListeners() {
        view.cancelButton.addActionListener(evt -> MainForm.nav.pop());
        view.saveButton.addActionListener(evt -> handleSaveButtonClick());
    }

    private void handleSaveButtonClick() {
        String name = view.nameField.getText();
        String email = view.emailField.getText();
        String pw1 = new String(view.getPassword());
        String pw2 = new String(view.getConfirmPassword());
        if (validateInputData(name, email, pw1, pw2)) {
            updateMemberData(name, email, pw1);
        }
    }

    private void updateMemberData(String name, String email, String password) {
        String sql = "UPDATE employees SET employee_name = ?, employee_email = ?, login_pw = ? WHERE employee_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            pstmt.setString(4, member.getMember_id());

            int rowsAffected = pstmt.executeUpdate();
            handleUpdateResult(rowsAffected);
        } catch (SQLException e) {
            handleDatabaseError(e);
        }
    }

    private void handleUpdateResult(int rowsAffected) {
        if (rowsAffected > 0) {
        	AuthManager.getInstance().updateAuth();
            showSuccessMessage();
        } else {
            showErrorMessage("회원 정보 업데이트에 실패했습니다.");
        }
    }

    private void showSuccessMessage() {
        DialogManager.showMessageBox(view, "회원 정보가 업데이트되었습니다.", e -> {
            MainForm.nav.pop();
            MainForm.nav.navigateTo("setting", true);
        });
    }

    private void showErrorMessage(String message) {
        DialogManager.showMessageBox(view, "<font color='red'>" + message + "</font>", null);
    }

    private void handleDatabaseError(SQLException e) {
        e.printStackTrace();
        showErrorMessage("회원 정보 업데이트에 실패했습니다.");
    }

    private boolean validateInputData(String name, String email, String pw1, String pw2) {
        if (!isPasswordValid(pw1, pw2)) {
            DialogManager.showMessageBox(view, "비밀번호를 확인해주세요", null);
            return false;
        }

        if (isEmailAlreadyExists(email)) {
            DialogManager.showMessageBox(view, "이미 존재하는 이메일 주소입니다.", null);
            return false;
        }

        return true;
    }

    private boolean isPasswordValid(String pw1, String pw2) {
        return pw1.equals(pw2) && !pw1.isEmpty() && !pw2.isEmpty();
    }

    private boolean isEmailAlreadyExists(String email) {
        String sql = "SELECT employee_id FROM employees WHERE lower(employee_email) = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email.toLowerCase());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String empId = rs.getString("employee_id");
                    return !empId.equals(member.getMember_id());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String toString() {
        return "회원정보수정";
    }
}