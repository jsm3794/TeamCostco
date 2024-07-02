package main.java.com.teamcostco.view.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import main.java.com.teamcostco.model.GridObject;

public class MapPanel extends JPanel {
	private double scale = 1.0;
	private final int GRID_SIZE_X = 20;
	private final int GRID_SIZE_Y = 25;
	private final int CELL_SIZE = 50;
	private List<GridObject> gridObjects = new ArrayList<>();
	private Point viewPosition;
	private static final int MARGIN = 10;
	private final int PANEL_WIDTH = GRID_SIZE_X * CELL_SIZE + (MARGIN * 2);
	private final int PANEL_HEIGHT = GRID_SIZE_Y * CELL_SIZE + (MARGIN * 2);

	public MapPanel() {
		initializeGrid();
		setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		setBorder(BorderFactory.createEmptyBorder(MARGIN, MARGIN, MARGIN, MARGIN));
		viewPosition = new Point(0, 0); // 초기 뷰포지션을 (0,0)으로 설정
	}

	// MapPanel 클래스 내부에서 초기화 메서드를 다음과 같이 수정합니다.
	private void initializeGrid() {

		// 입구
		addGridObject(0, 0, 6, 1, "입구");

		// 왼쪽 열
		addGridObject(0, 3, 3, 5, "기전제품 ET-1");
		addGridObject(0, 9, 3, 5, "생활용품 HG-2");
		addGridObject(0, 15, 3, 5, "주류 AB-3");
		addGridObject(0, 21, 3, 4, "베이커리 BA-17");

		// 중앙 열
		addGridObject(4, 4, 5, 2, "전자기기/제품 ED-4");
		addGridObject(4, 7, 5, 2, "생활용품 HG-5");
		addGridObject(4, 10, 5, 2, "시즌상품 SS-6");
		addGridObject(4, 13, 5, 2, "가구 FI-7");
		addGridObject(4, 16, 5, 2, "의류 CG-8");
		addGridObject(4, 21, 5, 4, "잡목 MT-18");

		// 오른쪽 열
		addGridObject(10, 7, 5, 1, "건강식품 HF-8");
		addGridObject(10, 9, 5, 1, "조미료 CD-9");
		addGridObject(10, 11, 5, 1, "냉장식품 RF-11");
		addGridObject(10, 13, 5, 1, "과자류 SN-12");
		addGridObject(10, 15, 5, 1, "육식식품 PF-13");
		addGridObject(10, 17, 5, 1, "과일류 FR-14");
		addGridObject(10, 21, 5, 4, "수산 SF-19");

		// 맨 오른쪽 열
		addGridObject(12, 0, 4, 3, "사무실");
		addGridObject(16, 0, 4, 3, "창고 ST-16");
		addGridObject(16, 7, 4, 13, "냉동식품 FF-15");
		addGridObject(16, 21, 4, 4, "채소류 VT-20");
	}

	public void addGridObject(int gridX, int gridY, int gridWidth, int gridHeight, String text) {
		double x = gridX * CELL_SIZE;
		double y = gridY * CELL_SIZE;
		double width = gridWidth * CELL_SIZE;
		double height = gridHeight * CELL_SIZE;

		Rectangle2D rect = new Rectangle2D.Double(x, y, width, height);
		gridObjects.add(new GridObject(rect, text));
		repaint();
	}

	// 기존 그리드를 지우고 새로 시작하는 메서드
	public void clearGrid() {
		gridObjects.clear();
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    Graphics2D g2d = (Graphics2D) g.create();
	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	    // 클리핑 영역 설정 (여백 제외)
	    g2d.setClip(MARGIN, MARGIN, getWidth() - MARGIN * 2, getHeight() - MARGIN * 2);

	    // 뷰포트 변환 적용
	    Point viewPosition = getViewPosition();
	    g2d.translate(-viewPosition.x + MARGIN, -viewPosition.y + MARGIN);
	    g2d.scale(scale, scale);

	    // 그리드 영역 계산
	    int gridWidth = GRID_SIZE_X * CELL_SIZE;
	    int gridHeight = GRID_SIZE_Y * CELL_SIZE;

	    // 전체 그리드 라인 그리기
	    g2d.setColor(new Color(200,200,200));
	    for (int i = 0; i <= GRID_SIZE_X; i++) {
	        g2d.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, gridHeight);
	    }
	    for (int j = 0; j <= GRID_SIZE_Y; j++) {
	        g2d.drawLine(0, j * CELL_SIZE, gridWidth, j * CELL_SIZE);
	    }

	    // 그리드 객체 그리기
	    for (GridObject obj : gridObjects) {
	        // 배경 채우기
	        g2d.setColor(new Color(220, 220, 230));
	        g2d.fill(obj.rect);
	        
	        // 테두리 그리기
	        g2d.setColor(new Color(200,200,200));
	        g2d.draw(obj.rect);
	        
	        // 텍스트 그리기
	        g2d.setColor(Color.BLACK);
	        g2d.setFont(new Font("맑은 고딕", Font.PLAIN, 18)); // 맑은 고딕 폰트 설정
	        drawCenteredString(g2d, obj.text, obj.rect);
	    }

	    g2d.dispose();
	}

	private void drawCenteredString(Graphics2D g2d, String text, Rectangle2D rect) {
	    FontMetrics metrics = g2d.getFontMetrics();
	    int x = (int) (rect.getX() + (rect.getWidth() - metrics.stringWidth(text)) / 2);
	    int y = (int) (rect.getY() + ((rect.getHeight() - metrics.getHeight()) / 2) + metrics.getAscent());
	    g2d.drawString(text, x, y);
	}

	public void setScale(double scale) {
		this.scale = scale;
		repaint();
	}

	public void setViewPosition(Point newPosition) {
		this.viewPosition = newPosition;
		repaint();
	}

	public double getScale() {
		return scale;
	}

	public Point getViewPosition() {
		return viewPosition;
	}

	public int getPanelWidth() {
		return (int) (GRID_SIZE_X * CELL_SIZE * scale);
	}

	public int getPanelHeight() {
		return (int) (GRID_SIZE_Y * CELL_SIZE * scale);
	}

	public int getGRID_SIZE_X() {
		return GRID_SIZE_X;
	}

	public int getGRID_SIZE_Y() {
		return GRID_SIZE_Y;
	}

	public int getCellSize() {
		return CELL_SIZE;
	}

	public List<GridObject> getGridObjects() {
		return gridObjects;
	}

	public void setGridObjects(List<GridObject> gridObjects) {
		this.gridObjects = gridObjects;
	}

}
