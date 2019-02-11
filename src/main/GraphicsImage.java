package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import gui.ConstructionImage;
import gui.ConstructionUI;

public class GraphicsImage implements ConstructionImage {
	private BufferedImage image;
	private Graphics2D g;
	private Font font;
	private final FontRenderContext fontRenderContext;

	public GraphicsImage(int width, int height) {
		createNewImage(width, height);
		fontRenderContext = new FontRenderContext(null, true, true);
	}

	private void createNewImage(int width, int height) {
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		g = image.createGraphics();
		font = g.getFont().deriveFont(10.0f);
		g.setFont(font);
	}

	@Override
	public void setSize(int width, int height) {
		if ((width > 0 && width != image.getWidth()) || (height > 0 && height != image.getHeight())) {
			createNewImage(width, height);
		}
	}

	@Override
	public int getWidth() {
		return image.getWidth();
	}

	@Override
	public int getHeight() {
		return image.getHeight();
	}

	@Override
	public void setColor(Color color) {
		g.setColor(color);
	}

	@Override
	public void drawPoint(int x, int y) {
		g.fillOval(x - 2, y - 2, 4, 4); // fill the interior
		g.drawOval(x - 2, y - 2, 4, 4); // draw the outside
	}

	@Override
	public void drawLine(int x0, int y0, int x1, int y1) {
		g.drawLine(x0, y0, x1, y1);
	}

	@Override
	public void drawCircle(int x, int y, int width, int height) {
		g.drawOval(x, y, width, height);
	}

	@Override
	public void drawRectangle(int x, int y, int width, int height) {
		g.drawRect(x, y, width, height);
	}

	@Override
	public void fillRectangle(int x, int y, int width, int height) {
		g.fillRect(x, y, width, height);
	}

	@Override
	public void drawCenteredString(int x, int y, String text) {
		Rectangle2D stringBounds = font.getStringBounds(text, fontRenderContext);
		g.drawString(text, ConstructionUI.round(x - stringBounds.getWidth() / 2), ConstructionUI.round(y + stringBounds.getHeight() / 2));
	}

	public Image getImage() {
		return image;
	}
}
