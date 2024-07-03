package main.java.com.teamcostco.controller;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import javax.swing.Timer;

import main.java.com.teamcostco.model.GridObject;
import main.java.com.teamcostco.view.panels.MapPanel;

public class MapController extends PanelController<MapPanel> {
	private MapPanel mapPanel;
	private final double MIN_SCALE = 0.455;
	private final double MAX_SCALE = 1.0;
	private Point dragStart;
	private Point2D.Double gridCenter;
	private Timer zoomTimer;
	private double targetScale;
	private Point zoomMousePoint;

	public MapController() {
		this.mapPanel = view;
		this.mapPanel.setScale(MIN_SCALE);
		setupMouseListeners();
		calculateGridCenter();
	}

	private void calculateGridCenter() {
		double totalX = 0, totalY = 0;
		int count = mapPanel.getGRID_SIZE_X();

		if (count == 0) {
			gridCenter = new Point2D.Double(mapPanel.getGRID_SIZE_X() * mapPanel.getCellSize() / 2.0,
					mapPanel.getGRID_SIZE_X() * mapPanel.getCellSize() / 2.0);
			return;
		}

		for (GridObject obj : mapPanel.getGridObjects()) {
			Rectangle2D rect = obj.rect;
			totalX += rect.getCenterX();
			totalY += rect.getCenterY();
		}

		gridCenter = new Point2D.Double(totalX / count, totalY / count);
	}
	private void setupMouseListeners() {
	    mapPanel.addMouseWheelListener(new MouseWheelListener() {
	        @Override
	        public void mouseWheelMoved(MouseWheelEvent e) {
	            zoomMousePoint = e.getPoint();
	            double oldScale = mapPanel.getScale();
	            double scaleIncrement = e.getPreciseWheelRotation() * 0.1; // 회전량에 더 민감하게 반응하도록 설정
	            targetScale = oldScale - scaleIncrement;
	            targetScale = Math.max(MIN_SCALE, Math.min(MAX_SCALE, targetScale));

	            if (oldScale != targetScale) {
	                if (zoomTimer != null && zoomTimer.isRunning()) {
	                    zoomTimer.stop();
	                }

	                zoomTimer = new Timer(1, event -> {
	                    double currentScale = mapPanel.getScale();
	                    double delta = (targetScale - currentScale) / 3; // 더 빠르게 반응하도록 설정
	                    double newScale = currentScale + delta;

	                    if (Math.abs(targetScale - newScale) < 0.001) {
	                        newScale = targetScale;
	                        zoomTimer.stop();
	                    }

	                    applyZoom(newScale, zoomMousePoint);
	                });
	                zoomTimer.start();
	            }
	        }
	    });

	    mapPanel.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mousePressed(MouseEvent e) {
	            dragStart = e.getPoint();
	        }
	    });

	    mapPanel.addMouseMotionListener(new MouseMotionAdapter() {
	        @Override
	        public void mouseDragged(MouseEvent e) {
	            if (dragStart != null) {
	                Point currentPoint = e.getPoint();
	                Point viewPosition = mapPanel.getViewPosition();

	                int dx = currentPoint.x - dragStart.x;
	                int dy = currentPoint.y - dragStart.y;

	                setViewPositionWithinBounds(new Point(viewPosition.x - dx, viewPosition.y - dy));
	                dragStart = currentPoint;
	            }
	        }
	    });
	}

	private void applyZoom(double newScale, Point zoomCenter) {
	    double oldScale = mapPanel.getScale();
	    double scaleFactor = newScale / oldScale;

	    Point viewPosition = mapPanel.getViewPosition();
	    int newX = (int) ((zoomCenter.x + viewPosition.x) * scaleFactor - zoomCenter.x);
	    int newY = (int) ((zoomCenter.y + viewPosition.y) * scaleFactor - zoomCenter.y);

	    mapPanel.setScale(newScale);
	    setViewPositionWithinBounds(new Point(newX, newY));
	}

	private void setViewPositionWithinBounds(Point newPosition) {
		double scale = mapPanel.getScale();
		int scaledGridWidth = (int) (mapPanel.getGRID_SIZE_X() * mapPanel.getCellSize() * scale);
		int scaledGridHeight = (int) (mapPanel.getGRID_SIZE_Y() * mapPanel.getCellSize() * scale);

		int viewportWidth = mapPanel.getWidth();
		int viewportHeight = mapPanel.getHeight();

		int maxX = Math.max(0, scaledGridWidth - viewportWidth);
		int maxY = Math.max(0, scaledGridHeight - viewportHeight);

		int x = Math.min(Math.max(newPosition.x, 0), maxX);
		int y = Math.min(Math.max(newPosition.y, 0), maxY);

		mapPanel.setViewPosition(new Point(x, y));
	}

	@Override
	public String toString() {
		return "매장지도";
	}

	public void updateGridCenter() {
		calculateGridCenter();
		centerViewOnGrid();
	}

	private void centerViewOnGrid() {
		int centerX = (view.getGRID_SIZE_X() * view.getCellSize() - mapPanel.getWidth()) / 2;
		int centerY = (view.getGRID_SIZE_Y() * view.getCellSize() - mapPanel.getHeight()) / 2;
		setViewPositionWithinBounds(new Point(centerX, centerY));
	}
}
