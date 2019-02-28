package gg.gui;

import java.awt.Color;

public interface ConstructionImage {
    public void setSize(int width, int height);

    public int getWidth();

    public int getHeight();

    public void setColor(Color color);

    public void drawPoint(int x, int y);

    public void drawLine(int x0, int y0, int x1, int y1);

    public void drawCircle(int x, int y, int width, int height);

    public void drawRectangle(int x, int y, int width, int height);

    public void fillRectangle(int x, int y, int width, int height);

    public void drawCenteredString(int x, int y, String text);
}
