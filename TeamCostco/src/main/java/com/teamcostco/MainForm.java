package main.java.com.teamcostco;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import main.java.com.teamcostco.component.Navigator;
import main.java.com.teamcostco.controller.AmountModify2Controller;
import main.java.com.teamcostco.controller.EditAccountController;
import main.java.com.teamcostco.controller.HomeTestController;
import main.java.com.teamcostco.controller.InventorySearchController;
import main.java.com.teamcostco.controller.LoginController;
import main.java.com.teamcostco.controller.OrderDetailController;
import main.java.com.teamcostco.controller.OrderListController;
import main.java.com.teamcostco.controller.PanelController;
import main.java.com.teamcostco.controller.ProductInspectionController;
import main.java.com.teamcostco.controller.ProductRegistrationController;
import main.java.com.teamcostco.controller.SettingsController;
import main.java.com.teamcostco.controller.SignTestController;
import main.java.com.teamcostco.controller.WareHouseListController;
import main.java.com.teamcostco.controller.WareHouseReceivingController;
import main.java.com.teamcostco.model.manager.AutoLogoutManager;
import main.java.com.teamcostco.model.manager.DialogManager;
import main.utils.TCPReceiver;

public class MainForm {

	public static Navigator nav = new Navigator("home");

	public static final int FORM_WIDTH = 480;
	public static final int FORM_HEIGHT = 640;

	public static JFrame frame = new JFrame("팀코스트코");

	public static void main(String[] args) {

		// 초기 설정
		initSettings();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(nav, BorderLayout.CENTER);
		frame.pack(); // 내부 컨텐츠 크기에 맞게 폼 사이즈 조정
		frame.setMinimumSize(frame.getSize());
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private static void initSettings() {
		// UI 기본 설정
		setupUISettings();
		// Navigator 설정
		setupNavigator();
	}

	private static void setupNavigator() {

		// Navigator 컨트롤러 매핑
		nav.mappingTarget("home", HomeTestController.class); // 홈화면
		nav.mappingTarget("login", LoginController.class); // 로그인
		nav.mappingTarget("sign", SignTestController.class); // 회원가입
		nav.mappingTarget("orderHistory", OrderListController.class); // 발주내역
		nav.mappingTarget("orderHistoryDetail", OrderDetailController.class); // 발주내역상세보기
		nav.mappingTarget("productEntry", ProductRegistrationController.class); // 상품등록
		nav.mappingTarget("inventorySearch", InventorySearchController.class); // 재고조회
		nav.mappingTarget("storageList", WareHouseListController.class); // 창고목록
		nav.mappingTarget("inventoryUpdate", AmountModify2Controller.class);
		nav.mappingTarget("productCheck", ProductInspectionController.class); // 제품검수
		nav.mappingTarget("warehousereceiving", WareHouseReceivingController.class); // 입고처리
		nav.mappingTarget("setting", SettingsController.class); // 환경설정
		nav.mappingTarget("editaccount", EditAccountController.class); // 회원정보수정

		// Navigator 디자인
		nav.setPreferredSize(new Dimension(FORM_WIDTH, FORM_HEIGHT));

		// 기본 페이지로 이동
		nav.navigateTo("home", false);
	}

	private static void setupUISettings() {
		Enumeration<Object> keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof FontUIResource) {
				UIManager.put(key, new FontUIResource(new Font("맑은 고딕", Font.PLAIN, 18)));
			}
		}
	}
}
