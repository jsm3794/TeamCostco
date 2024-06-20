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

    private TopNavigator topNav = null;
    private JPanel mainView = null;
    private JPanel myPanel = null;

    private Map<String, Class<? extends JPanel>> mappings;
    private Stack<JPanel> panelStack;
    private String homeRoute;

    public Navigator(String homeRoute) {
        initPanelStyles();
        mappings = new HashMap<>();
        panelStack = new Stack<>();
        this.homeRoute = homeRoute;
    }

    private void initPanelStyles() {
        topNav = new TopNavigator();
        mainView = new JPanel();
        mainView.setSize(this.getSize());
        setLayout(new BorderLayout());
    }

    public void mappingTarget(String route, Class<? extends JPanel> panelClass) {
        mappings.put(route, panelClass);
    }

    public void navigateToHome() {
        navigateTo(homeRoute, false);
    }
    
    private JPanel getPanelInstance(String route) {
        Class<? extends JPanel> panelClass = mappings.get(route);
        if (panelClass == null) {
            return null;
        }

        try {
            return panelClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void navigateTo(String route, boolean useTopNav) {
        if (!panelStack.isEmpty()) {
            pop();
        }
        push(route, useTopNav);
        updateView();
    }

    public void pop() {
        panelStack.pop();
    }

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

    private JPanel addTopNav(JPanel panel, TopNavigator topNav) {
        JPanel mergedPanel = new JPanel();
        mergedPanel.setLayout(new BorderLayout());
        mergedPanel.add(topNav, BorderLayout.NORTH);
        mergedPanel.add(panel, BorderLayout.CENTER);
        return mergedPanel;
    }

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
