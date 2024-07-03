package main.java.com.teamcostco.component;

import java.awt.BorderLayout;
import java.lang.reflect.Constructor;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import main.java.com.teamcostco.controller.PanelController;

/**
 * 네비게이션 기능을 구현한 클래스 JPanel을 상속받아 UI컴포넌트로 사용 가능
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
	 * 
	 * @param route      라우트 문자열
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
	 * 
	 * @param route     이동할 라우트
	 * @param useTopNav 상단 네비게이터 사용 여부
	 * @param params    컨트롤러 생성에 필요한 파라미터들
	 * @return CompletableFuture<Void> 네비게이션 완료 후 실행될 Future
	 */
	public CompletableFuture<Void> navigateTo(String route, boolean useTopNav, Object... params) {
	    CompletableFuture<Void> future = new CompletableFuture<>();

	    SwingUtilities.invokeLater(() -> {
	        try {
	            if (!panelStack.isEmpty()) {
	                pop();
	            }
	            push(route, useTopNav, params).thenRun(() -> {
	                future.complete(null);
	            }).exceptionally(ex -> {
	                future.completeExceptionally(ex);
	                return null;
	            });
	        } catch (Exception e) {
	            future.completeExceptionally(e);
	        }
	    });

	    return future;
	}

	/**
	 * 최상위 패널 제거
	 * 
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

	public void popAll() {
		while (!panelStack.isEmpty()) {
			PanelControllerPair pair = panelStack.pop();
			pair.getPanel().removeAll();
		}
		updateView();
	}

	/**
	 * 새 패널 추가
	 * 
	 * @param route     라우트
	 * @param useTopNav 상단 네비게이터 사용 여부
	 * @param params    컨트롤러 생성에 필요한 파라미터들
	 * @return CompletableFuture<Void> 패널 추가 완료 후 실행될 Future
	 */
	public CompletableFuture<Void> push(String route, boolean useTopNav, Object... params) {
	    CompletableFuture<Void> future = new CompletableFuture<>();

	    SwingUtilities.invokeLater(() -> {
	        try {
	            PanelControllerPair pair = getPanelInstance(route, params);
	            if (pair == null) {
	                future.completeExceptionally(new RuntimeException("Failed to create panel instance"));
	                return;
	            }

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
	            
	            // 100밀리초 지연 후 future 완료
	            Timer timer = new Timer(1, e -> future.complete(null));
	            timer.setRepeats(false);
	            timer.start();
	        } catch (Exception e) {
	            future.completeExceptionally(e);
	        }
	    });

	    return future;
	}

	/**
	 * 현재 컨트롤러 반환
	 * 
	 * @return 현재 컨트롤러
	 */
	public PanelController<?> getCurrent() {
		return panelStack.isEmpty() ? null : panelStack.peek().getController();
	}

	/**
	 * 이전 컨트롤러 반환
	 * 
	 * @return 이전 컨트롤러, 없으면 null
	 */
	public PanelController<?> getPrev() {
		if (panelStack.size() < 2) {
			return null;
		}
		Iterator<PanelControllerPair> iterator = panelStack.iterator();
		iterator.next(); // 현재 top 요소를 건너뛰기
		return iterator.next().getController(); // 그 다음 요소(이전 컨트롤러) 반환
	}

	/**
	 * 특정 클래스의 가장 최근 컨트롤러를 찾습니다.
	 * 
	 * @param clazz 찾고자 하는 컨트롤러의 클래스
	 * @return 찾은 컨트롤러, 없으면 null
	 */
	public PanelController<?> findLastControllerByClass(Class<?> clazz) {
		for (PanelControllerPair pair : panelStack) {
			if (clazz.isInstance(pair.getController())) {
				return pair.getController();
			}
		}
		return null;
	}

	/**
	 * 특정 클래스의 가장 처음 컨트롤러를 찾습니다.
	 * 
	 * @param clazz 찾고자 하는 컨트롤러의 클래스
	 * @return 찾은 컨트롤러, 없으면 null
	 */
	public PanelController<?> findFirstControllerByClass(Class<?> clazz) {
		for (PanelControllerPair pair : new ArrayList<>(panelStack)) {
			if (clazz.isInstance(pair.getController())) {
				return pair.getController();
			}
		}
		return null;
	}

	/**
	 * 특정 인덱스의 컨트롤러를 찾습니다. 0은 가장 최근에 추가된 컨트롤러입니다.
	 * 
	 * @param index 찾고자 하는 컨트롤러의 인덱스
	 * @return 찾은 컨트롤러, 인덱스가 범위를 벗어나면 null
	 */
	public PanelController<?> getControllerAtIndex(int index) {
		if (index < 0 || index >= panelStack.size()) {
			return null;
		}
		return new ArrayList<>(panelStack).get(panelStack.size() - 1 - index).getController();
	}

	/**
	 * 패널 인스턴스 생성
	 * 
	 * @param route  라우트
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
	 * 
	 * @param controllerClass 컨트롤러 클래스
	 * @param params          생성자 파라미터들
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
	 * 
	 * @param constructor 확인할 생성자
	 * @param params      파라미터들
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
	 * 
	 * @param panel  원본 패널
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