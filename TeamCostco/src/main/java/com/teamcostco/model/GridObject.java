package main.java.com.teamcostco.model;

import java.awt.geom.Rectangle2D;

public class GridObject {
    public Rectangle2D rect;
    public String text;

    public GridObject(Rectangle2D rect, String text) {
        this.rect = rect;
        this.text = text;
    }
}