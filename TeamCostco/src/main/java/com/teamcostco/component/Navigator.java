package main.java.com.teamcostco.component;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import main.java.com.teamcostco.controller.PanelController;
import main.java.com.teamcostco.model.OrderModel;

public class Navigator extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private TopNavigator topNav = null; // 상단 네이게이션바
	private JPanel mainView = null; // 컨텐츠 패널
	private JPanel myPanel = null; // 실제로 보여지는 패널
	private boolean canUpdate = true;

	private Map<String, Class<? extends PanelController<?>>> mappings;
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
	public void mappingTarget(String route, Class<? extends PanelController<?>> panelClass) {
		mappings.put(route, panelClass);
	}

	public void stopUpdate() {
		canUpdate = false;
	}

	public void resumeUpdate() {
		canUpdate = true;
		updateView();
	}

	public boolean getCanUpdate() {
		return canUpdate;
	}

	// 지정한 최상단 경로로 이동합니다.
	public void navigateToHome() {
		navigateTo(homeRoute, false);
	}

	/**
	 * 주어진 경로에 해당하는 패널 인스턴스를 반환하는 메서드.
	 * 
	 * @param route 네비게이션 경로를 나타내는 문자열.
	 * @param params 추가로 전달할 파라미터들
	 * @return 주어진 경로에 매핑된 패널 클래스의 새 인스턴스. 매핑된 패널 클래스가 없거나 인스턴스를 생성할 수 없는 경우 null을
	 *         반환합니다.
	 */
	private PanelControllerPair getPanelInstance(String route, Object... params) {
	    Class<? extends PanelController<?>> controllerClass = mappings.get(route);
	    if (controllerClass == null) {
	        return null;
	    }

	    try {
	        PanelController<?> controller;
	        if (params == null || params.length == 0) {
	            controller = controllerClass.getDeclaredConstructor().newInstance();
	        } else {
	            Constructor<?> matchingConstructor = null;
	            for (Constructor<?> constructor : controllerClass.getDeclaredConstructors()) {
	                if (constructor.getParameterCount() == params.length) {
	                    Class<?>[] paramTypes = constructor.getParameterTypes();
	                    boolean match = true;
	                    for (int i = 0; i < params.length; i++) {
	                        if (!paramTypes[i].isInstance(params[i])) {
	                            match = false;
	                            break;
	                        }
	                    }
	                    if (match) {
	                        matchingConstructor = constructor;
	                        break;
	                    }
	                }
	            }
	            if (matchingConstructor == null) {
	                throw new NoSuchMethodException("No matching constructor found");
	            }
	            controller = (PanelController<?>) matchingConstructor.newInstance(params);
	        }
	        JPanel panel = controller.getPanel();
	        return new PanelControllerPair(panel, controller);
	    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
	        e.printStackTrace();
	        return null;
	    }
	}


	/**
	 * 주어진 경로로 네비게이션하는 메서드.
	 * 
	 * @param route     네비게이션할 경로를 나타내는 문자열.
	 * @param useTopNav 최상단 네비게이션을 사용할지 여부를 나타내는 boolean 값. true이면 최상단 네비게이션을 사용합니다.
	 * @param params    추가로 전달할 파라미터들
	 */
	public void navigateTo(String route, boolean useTopNav, Object... params) {
		if (!panelStack.isEmpty()) {
			pop();
		}
		push(route, useTopNav, params);
		updateView();
	}

	// 최상단 패널을 제거합니다.
	public void pop() {
		if (!panelStack.isEmpty()) {
			JPanel panel = panelStack.pop();
			panel.removeAll();
			panel = null;
			updateView();
		}
	}

	/**
	 * 주어진 경로와 옵션을 사용하여 새로운 패널을 스택에 추가하는 메서드
	 * 
	 * @param route     네비게이션할 경로를 나타내는 문자열
	 * @param useTopNav 최상단 네비게이션을 사용할지 여부를 나타내는 boolean 값
	 * @param params    추가로 전달할 파라미터들
	 */
	public void push(String route, boolean useTopNav, Object... params) {
	    PanelControllerPair pair = getPanelInstance(route, params);
	    if (pair == null) {
	        return;
	    }

	    JPanel panel = pair.getPanel();
	    Object controller = pair.getController();

	    if (useTopNav && panel != null) {
	        topNav = new TopNavigator(controller.toString());
	        topNav.setClickListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                SwingUtilities.invokeLater(() -> {
	                    pop();
	                    if (!panelStack.isEmpty()) {
	                        updateView();
	                    } else {
	                        navigateToHome();
	                    }
	                });
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
	 * @param panel  기존의 메인 패널.
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
		if (!panelStack.isEmpty() && getCanUpdate()) {
			JPanel view = panelStack.peek();
			this.removeAll();
			this.add(view);
			this.revalidate();
			this.repaint();
		}
	}
}


class PanelControllerPair {
    private JPanel panel;
    private Object controller; // 적절한 컨트롤러 타입으로 변경하세요.

    public PanelControllerPair(JPanel panel, Object controller) {
        this.panel = panel;
        this.controller = controller;
    }

    public JPanel getPanel() {
        return panel;
    }

    public Object getController() {
        return controller;
    }
}
