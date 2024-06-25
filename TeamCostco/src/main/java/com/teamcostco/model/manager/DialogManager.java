package main.java.com.teamcostco.model.manager;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import main.utils.Constants;

public class DialogManager {
	private static DialogManager instance = new DialogManager();

	private static final float OVERLAY_MAX_OPACITY = 0.3f;
	private static final float DISPLAY_MAX_OPACITY = 1.0f;
	private static final float OPACITY_INCREMENT = 0.1f;
	private static final int TIMER_DELAY = 10;
	private static final Color OVERLAY_COLOR = new Color(0, 0, 0, 150); // 반투명 검정색
	private static final Color DISPLAY_COLOR = new Color(255, 255, 255); // 불투명 흰색
	private static final int ARC_WIDTH = 20;
	private static final int ARC_HEIGHT = 20;

	private JPanel overlayPanel;
	private JPanel displayPanel;
	private static boolean initialized = false;
	private float overlayOpacity = 0.0f;
	private float displayOpacity = 0.0f;

	private DialogManager() {
	}

	public static synchronized void ensureInitialized() {
		if (!initialized) {
			instance = new DialogManager();
			instance.initialize();
			initialized = true;
		}
	}

	private void initialize() {
		// 오버레이 패널 설정
		overlayPanel = createOverlayPanel();
		overlayPanel.setOpaque(false);
		overlayPanel.setLayout(new GridBagLayout());

		// 모든 이벤트를 차단하는 MouseAdapter 및 KeyAdapter 추가
		addEventBlockersToOverlayPanel();

		// 디스플레이 패널 설정
		displayPanel = createDisplayPanel();
		displayPanel.setBackground(new Color(0, 0, 0, 0));

		// 오버레이 패널에 디스플레이 패널 추가
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		overlayPanel.add(displayPanel, gbc);
	}

	private JPanel createOverlayPanel() {
		return new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, overlayOpacity)); // 투명도 설정
				g2d.setColor(OVERLAY_COLOR);
				g2d.fillRect(0, 0, getWidth(), getHeight());
			}
		};
	}

	private JPanel createDisplayPanel() {
		return new JPanel(new BorderLayout()) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, displayOpacity)); // 완전 불투명
				g2d.setColor(DISPLAY_COLOR);
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.fillRoundRect(0, 0, getWidth(), getHeight(), ARC_WIDTH, ARC_HEIGHT);
				setOpaque(false);
			}
		};
	}

	private void addEventBlockersToOverlayPanel() {
		overlayPanel.addMouseListener(new MouseAdapter() {
		});
		overlayPanel.addMouseMotionListener(new MouseMotionAdapter() {
		});
		overlayPanel.addKeyListener(new KeyAdapter() {
		});
		overlayPanel.setFocusable(true);
	}

	
	
	public static Context showLoadingBox(JPanel parentPanel) {
        ensureInitialized();

        instance.overlayOpacity = 0.0f;
        instance.displayOpacity = 0.0f;

        instance.displayPanel = instance.createDisplayPanel();
        instance.displayPanel.setPreferredSize(new Dimension(100, 100)); // 고정 크기 설정

        ImageIcon scaledLoadingIcon = new ImageIcon(DialogManager.class.getResource("/main/resources/loading.gif"));

        JLabel loadingLabel = new JLabel(scaledLoadingIcon);
        loadingLabel.setHorizontalAlignment(JLabel.CENTER);
        loadingLabel.setVerticalAlignment(JLabel.CENTER);
        instance.displayPanel.add(loadingLabel, BorderLayout.CENTER);

        instance.overlayPanel.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        instance.overlayPanel.add(instance.displayPanel, gbc);

        JLayeredPane layeredPane = parentPanel.getRootPane().getLayeredPane();
        instance.overlayPanel.setSize(parentPanel.getSize());

        if (instance.overlayPanel.getParent() != null) {
            ((JLayeredPane) instance.overlayPanel.getParent()).remove(instance.overlayPanel);
        }

        layeredPane.add(instance.overlayPanel, JLayeredPane.MODAL_LAYER);
        layeredPane.revalidate();
        layeredPane.repaint();

        instance.overlayPanel.requestFocusInWindow(); // overlayPanel이 포커스를 받도록 설정

        instance.fadeIn();

        return new Context(layeredPane, parentPanel);
    }

	public static Context showMessageBox(JPanel parentPanel, String message, boolean isConfirmation,
			ActionListener yesAction, ActionListener noAction) {
		ensureInitialized();

		instance.overlayOpacity = 0.0f;
		instance.displayOpacity = 0.0f;

		instance.displayPanel = instance.createDisplayPanel();
		instance.displayPanel.removeAll();

		JLabel messageLabel = new JLabel(
				"<html><body style='text-align: center; padding: 10px;'>" + message + "</body></html>", JLabel.CENTER);

		int defaultWidth = 200;
		int defaultHeight = 200;
		int maxWidth = (int) (parentPanel.getWidth() * 0.7);
		int maxHeight = (int) (parentPanel.getHeight() * 0.7);

		JPanel messagePanel = new JPanel(new BorderLayout()) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.setColor(Color.LIGHT_GRAY);
				g2d.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
			}
		};
		messagePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		messagePanel.add(messageLabel, BorderLayout.CENTER);
		messagePanel.setOpaque(false);

		instance.displayPanel.add(messagePanel, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel(new GridLayout(1, 3)) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.setColor(getBackground());
				g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
			}
		};
		buttonPanel.setBackground(new Color(255, 255, 255, 0));
		buttonPanel.setOpaque(false);

		if (isConfirmation) {
			JButton yesButton = createRoundButton("예", e -> {
				if (yesAction != null) {
					yesAction.actionPerformed(e);
				}
				closeDialog(parentPanel);
			});

			JButton noButton = createRoundButton("아니오", e -> {
				if (noAction != null) {
					noAction.actionPerformed(e);
				}
				closeDialog(parentPanel);
			});
			noButton.setForeground(Color.GRAY);

			buttonPanel.setLayout(new GridLayout(1, 2));
			buttonPanel.add(yesButton);
			buttonPanel.add(noButton);
		} else {
			JButton okButton = createRoundButton("확인", e -> closeDialog(parentPanel));
			buttonPanel.setLayout(new GridLayout(1, 1));
			buttonPanel.add(okButton);
		}

		instance.displayPanel.add(buttonPanel, BorderLayout.SOUTH);

		instance.displayPanel.setPreferredSize(new Dimension(defaultWidth, defaultHeight));
		instance.displayPanel.revalidate();

		int labelWidth = messageLabel.getPreferredSize().width + 20;
		int labelHeight = messageLabel.getPreferredSize().height + 80;

		int newWidth = Math.min(labelWidth, maxWidth);
		int newHeight = Math.min(labelHeight, maxHeight);

		instance.displayPanel.setPreferredSize(new Dimension(newWidth, newHeight));
		instance.displayPanel.setMaximumSize(new Dimension(newWidth, newHeight));

		instance.overlayPanel.removeAll();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		instance.overlayPanel.add(instance.displayPanel, gbc);

		instance.overlayPanel.setSize(parentPanel.getSize());
		JLayeredPane layeredPane = parentPanel.getRootPane().getLayeredPane();

		if (instance.overlayPanel.getParent() != null) {
			((JLayeredPane) instance.overlayPanel.getParent()).remove(instance.overlayPanel);
		}

		layeredPane.add(instance.overlayPanel, JLayeredPane.MODAL_LAYER);
		layeredPane.revalidate();
		layeredPane.repaint();

		instance.overlayPanel.requestFocusInWindow(); // overlayPanel이 포커스를 받도록 설정

		instance.fadeIn();

		return new Context(layeredPane, parentPanel);
	}

	private static void closeDialog(JPanel parentPanel) {
		if (instance.displayPanel.getParent() != null) {
			instance.displayPanel.getParent().remove(instance.displayPanel);
		}
		if (instance.overlayPanel.getParent() != null) {
			instance.overlayPanel.getParent().remove(instance.overlayPanel);
		}
		parentPanel.revalidate();
		parentPanel.repaint();
	}

	private static JButton createRoundButton(String text, ActionListener action) {
		JButton button = new JButton(text) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.setColor(getBackground());
				g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
			}

			@Override
			public Dimension getPreferredSize() {
				return new Dimension(100, 40);
			}
		};
		button.setBackground(new Color(255, 255, 255, 0));
		button.setContentAreaFilled(false);
		button.setOpaque(false);
		button.addActionListener(action);
		button.setBorderPainted(false);
		button.setFocusPainted(false);
		button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		button.setForeground(Constants.BUTTON_BACKGROUND_COLOR);
		return button;
	}

	public void fadeIn() {
		Timer timer = new Timer(TIMER_DELAY, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean done = true;

				if (overlayOpacity < OVERLAY_MAX_OPACITY) {
					overlayOpacity = Math.min(overlayOpacity + OPACITY_INCREMENT, OVERLAY_MAX_OPACITY);
					overlayPanel.repaint();
					done = false;
				}

				if (displayOpacity < DISPLAY_MAX_OPACITY) {
					displayOpacity = Math.min(displayOpacity + OPACITY_INCREMENT, DISPLAY_MAX_OPACITY);
					displayPanel.repaint();
					done = false;
				}

				if (done) {
					((Timer) e.getSource()).stop();
				}
			}
		});
		timer.start();
	}

	public static class Context {
		private JLayeredPane layeredPane;

		public Context(JLayeredPane layeredPane, JPanel parentPanel) {
			this.layeredPane = layeredPane;
		}

		public void close() {
			Container parent = instance.overlayPanel.getParent();
			if (parent != null) {
				layeredPane.remove(instance.overlayPanel);
				layeredPane.revalidate();
				layeredPane.repaint();
			}
		}
	}
}
