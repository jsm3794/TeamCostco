package main.java.com.teamcostco.component;

import java.awt.BorderLayout;
import java.lang.reflect.Constructor;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import main.java.com.teamcostco.controller.PanelController;


/**
 * 네비게이션 기능을 구현한 클래스
 * JPanel을 상속받아 UI컴포넌트로 사용 가능
 */
public class Navigator extends JPanel {
	private static final long serialVersionUID = 1L;

	private TopNavigator topNav;
	private volatile boolean canUpdate = true;

	private final Map<String, Class<? extends PanelController<? extends JPanel>>> mappings;
	private final Deque<PanelControllerPair> panelStack;
	private final String homeRoute;

	/**
     * @param homeRoute 홈 라우트 문자열
     */
	public Navigator(String homeRoute) {
		setLayout(new BorderLayout());
		mappings = new HashMap<>();
		panelStack = new ArrayDeque<>();
		this.homeRoute = homeRoute;
	}

	/**
     * 라우트와 패널 컨트롤러 클래스를 매핑
     * @param route 라우트 문자열
     * @param panelClass 패널 컨트롤러 클래스
     */
	public void mappingTarget(String route, Class<? extends PanelController<? extends JPanel>> panelClass) {
		mappings.put(route, panelClass);
	}

	public void stopUpdate() {
		canUpdate = false;
	}

	public void resumeUpdate() {
		canUpdate = true;
		updateView();
	}

	public boolean isCanUpdate() {
		return canUpdate;
	}

	public void navigateToHome() {
		navigateTo(homeRoute, false);
	}

	 /**
     * 특정 라우트로 이동
     * @param route 이동할 라우트
     * @param useTopNav 상단 네비게이터 사용 여부
     * @param params 컨트롤러 생성에 필요한 파라미터들
     */
	public void navigateTo(String route, boolean useTopNav, Object... params) {
		if (!panelStack.isEmpty()) {
			pop();
		}
		push(route, useTopNav, params);
	}

	/**
     * 최상위 패널 제거
     * @return 제거된 패널의 컨트롤러
     */
	public PanelController<?> pop() {
		if (!panelStack.isEmpty()) {
			PanelControllerPair pair = panelStack.pop();
			pair.getPanel().removeAll();
			updateView();
			return pair.getController();
		}
		return null;
	}

	/**
     * 새 패널 추가
     * @param route 라우트
     * @param useTopNav 상단 네비게이터 사용 여부
     * @param params 컨트롤러 생성에 필요한 파라미터들
     */
	public void push(String route, boolean useTopNav, Object... params) {
		PanelControllerPair pair = getPanelInstance(route, params);
		if (pair == null)
			return;

		JPanel panel = pair.getPanel();
		PanelController<?> controller = pair.getController();

		if (useTopNav && panel != null) {
			topNav = new TopNavigator(controller.toString());
			topNav.setClickListener(e -> SwingUtilities.invokeLater(() -> {
				pop();
				if (panelStack.isEmpty())
					navigateToHome();
			}));
			panel = addTopNav(panel, topNav);
		}

		panelStack.push(new PanelControllerPair(panel, controller));
		updateView();
	}

	 /**
     * 현재 컨트롤러 반환
     * @return 현재 컨트롤러
     */
	public PanelController<?> getCurrent() {
		return panelStack.isEmpty() ? null : panelStack.peek().getController();
	}

	/**
     * 이전 컨트롤러 반환
     * @return 이전 컨트롤러
     */
	public PanelController<?> getPrev() {
		return panelStack.size() > 1 ? panelStack.stream().skip(panelStack.size() - 2).findFirst().get().getController()
				: null;
	}

	/**
     * 패널 인스턴스 생성
     * @param route 라우트
     * @param params 컨트롤러 생성에 필요한 파라미터들
     * @return 생성된 패널과 컨트롤러 쌍
     */
	private PanelControllerPair getPanelInstance(String route, Object... params) {
		Class<? extends PanelController<? extends JPanel>> controllerClass = mappings.get(route);
		if (controllerClass == null) {
			throw new IllegalArgumentException("No mapping found for route: " + route);
		}

		try {
			PanelController<? extends JPanel> controller = createController(controllerClass, params);
			return new PanelControllerPair(controller.getPanel(), controller);
		} catch (ReflectiveOperationException e) {
			// 로깅 추가
			throw new RuntimeException("Failed to create controller for route: " + route, e);
		}
	}

	/**
     * 컨트롤러 생성
     * @param controllerClass 컨트롤러 클래스
     * @param params 생성자 파라미터들
     * @return 생성된 컨트롤러
     * @throws ReflectiveOperationException 리플렉션 관련 예외
     */
	private PanelController<? extends JPanel> createController(
			Class<? extends PanelController<? extends JPanel>> controllerClass, Object... params)
			throws ReflectiveOperationException {
		if (params == null || params.length == 0) {
			return controllerClass.getDeclaredConstructor().newInstance();
		}

		for (Constructor<?> constructor : controllerClass.getDeclaredConstructors()) {
			if (constructor.getParameterCount() == params.length && isCompatibleConstructor(constructor, params)) {
				return (PanelController<?>) constructor.newInstance(params);
			}
		}

		throw new NoSuchMethodException("No matching constructor found");
	}

	/**
     * 생성자 호환성 확인
     * @param constructor 확인할 생성자
     * @param params 파라미터들
     * @return 호환 여부
     */
	private boolean isCompatibleConstructor(Constructor<?> constructor, Object[] params) {
		Class<?>[] paramTypes = constructor.getParameterTypes();
		for (int i = 0; i < params.length; i++) {
			if (!paramTypes[i].isInstance(params[i])) {
				return false;
			}
		}
		return true;
	}

	 /**
     * 상단 네비게이터 추가
     * @param panel 원본 패널
     * @param topNav 추가할 상단 네비게이터
     * @return 상단 네비게이터가 추가된 새 패널
     */
	private JPanel addTopNav(JPanel panel, TopNavigator topNav) {
		JPanel mergedPanel = new JPanel(new BorderLayout());
		mergedPanel.add(topNav, BorderLayout.NORTH);
		mergedPanel.add(panel, BorderLayout.CENTER);
		return mergedPanel;
	}

	private void updateView() {
		if (!panelStack.isEmpty() && canUpdate) {
			JPanel view = panelStack.peek().getPanel();
			removeAll();
			add(view);
			revalidate();
			repaint();
		}
	}
}

/**
 * 패널과 컨트롤러를 쌍으로 관리하는 내부 클래스
 */
class PanelControllerPair {
	private final JPanel panel;
	private final PanelController<? extends JPanel> controller;

	public PanelControllerPair(JPanel panel, PanelController<? extends JPanel> controller) {
		this.panel = panel;
		this.controller = controller;
	}

	public JPanel getPanel() {
		return panel;
	}

	public PanelController<?> getController() {
		return controller;
	}
}