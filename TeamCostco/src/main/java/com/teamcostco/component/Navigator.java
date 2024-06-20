package main.java.com.teamcostco.component;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javax.swing.JPanel;

public class Navigator extends JPanel {

	private TopNavigator topNav = null; // 상단 네이게이션바
	private JPanel mainView = null; // 컨텐츠 패널
	private JPanel myPanel = null; // 실제로 보여지는 패널

	private Map<String, Class<? extends JPanel>> mappings;
	private Stack<JPanel> panelStack;
	private String homeRoute;

	/***
	 * Navigator 클래스의 생성자.
	 * 
	 * @param homeRoute 최상단 경로를 나타내는 문자열. 네비게이션의 홈 경로로 사용됩니다.
	 */
	public Navigator(String homeRoute) {
		initPanelStyles();
		mappings = new HashMap<>();
		panelStack = new Stack<>();
		this.homeRoute = homeRoute;
	}

	// 사용할 패널들의 스타일을 초기화시킵니다.
	private void initPanelStyles() {
		topNav = new TopNavigator();
		mainView = new JPanel();
		mainView.setSize(this.getSize());
		setLayout(new BorderLayout());
	}

	/**
	 * 주어진 경로와 패널 클래스를 매핑하는 메서드.
	 * 
	 * @param route      네비게이션 경로를 나타내는 문자열.
	 * @param panelClass 주어진 경로와 매핑될 패널 클래스. 이 클래스는 JPanel를 상속받은 클래스여야합니다.
	 */
	public void mappingTarget(String route, Class<? extends JPanel> panelClass) {
		mappings.put(route, panelClass);
	}

	// 지정한 최상단 경로로 이동합니다.
	public void navigateToHome() {
		navigateTo(homeRoute, false);
	}

	/**
	 * 주어진 경로에 해당하는 패널 인스턴스를 반환하는 메서드.
	 * 
	 * @param route 네비게이션 경로를 나타내는 문자열.
	 * @return 주어진 경로에 매핑된 패널 클래스의 새 인스턴스. 매핑된 패널 클래스가 없거나 인스턴스를 생성할 수 없는 경우 null을
	 *         반환합니다.
	 */
	private JPanel getPanelInstance(String route) {
		Class<? extends JPanel> panelClass = mappings.get(route);
		if (panelClass == null) {
			return null;
		}

		try {
			return panelClass.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 주어진 경로로 네비게이션하는 메서드.
	 * 
	 * @param route     네비게이션할 경로를 나타내는 문자열.
	 * @param useTopNav 최상단 네비게이션을 사용할지 여부를 나타내는 boolean 값. true이면 최상단 네비게이션을 사용합니다.
	 */
	public void navigateTo(String route, boolean useTopNav) {
		if (!panelStack.isEmpty()) {
			pop();
		}
		push(route, useTopNav);
		updateView();
	}

	// 최상단 패널을 제거합니다.
	public void pop() {
		panelStack.pop();
	}

	/**
	 * 주어진 경로와 옵션을 사용하여 새로운 패널을 스택에 추가하는 메서드
	 * 
	 * @param route     네비게이션할 경로를 나타내는 문자열
	 * @param useTopNav 최상단 네비게이션을 사용할지 여부를 나타내는 boolean 값
	 */
	public void push(String route, boolean useTopNav) {
		JPanel panel = getPanelInstance(route);

		if (useTopNav && panel != null) {
			topNav = new TopNavigator(panel.toString());
			topNav.setClickListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					pop();
					if (!panelStack.isEmpty()) {
						updateView();
					} else {
						navigateToHome();
					}
				}
			});
			myPanel = addTopNav(panel, topNav);
		} else {
			myPanel = panel;
		}

		if (myPanel != null) {
			panelStack.push(myPanel);
		}

		updateView();
	}

	/**
	 * 주어진 패널에 최상단 네비게이션 패널을 추가하는 메서드.
	 * 
	 * @param panel 기존의 메인 패널.
	 * @param topNav 최상단 네비게이션을 나타내는 TopNavigator 패널.
	 * @return 최상단 네비게이션과 메인 패널이 결합된 새로운 JPanel.
	 */
	private JPanel addTopNav(JPanel panel, TopNavigator topNav) {
	    JPanel mergedPanel = new JPanel();
	    mergedPanel.setLayout(new BorderLayout());
	    mergedPanel.add(topNav, BorderLayout.NORTH);
	    mergedPanel.add(panel, BorderLayout.CENTER);
	    return mergedPanel;
	}


	// 최상단 패널을 다시 화면에 뿌려줍니다.
	private void updateView() {
		if (!panelStack.isEmpty()) {
			JPanel view = panelStack.peek();
			this.removeAll();
			this.add(view);
			this.revalidate();
			this.repaint();
		}
	}
}
