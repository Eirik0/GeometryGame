package gui.test;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import gui.ConstructionImage;
import gui.test.TestQuadruple.TestCircle;
import gui.test.TestQuadruple.TestFilledRectangle;
import gui.test.TestQuadruple.TestLine;
import gui.test.TestQuadruple.TestRectangle;

public class TestImage implements ConstructionImage {
	private int imageWidth;
	private int imageHeight;

	public final Set<TestPoint> points = new HashSet<>();
	public final Set<TestLine> lines = new HashSet<>();
	public final Set<TestCircle> circles = new HashSet<>();
	public final Set<TestRectangle> rectangles = new HashSet<>();
	public final Set<TestFilledRectangle> filledRectangles = new HashSet<>();
	public final Set<TestCenteredString> centeredStrings = new HashSet<>();

	private Color currentColor;

	@Override
	public void setSize(int imageWidth, int imageHeight) {
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
	}

	@Override
	public int getWidth() {
		return imageWidth;
	}

	@Override
	public int getHeight() {
		return imageHeight;
	}

	@Override
	public void setColor(Color color) {
		currentColor = color;
	}

	@Override
	public void drawPoint(int x, int y) {
		points.add(new TestPoint(x, y, currentColor));
	}

	@Override
	public void drawLine(int x0, int y0, int x1, int y1) {
		lines.add(new TestLine(x0, y0, x1, y1, currentColor));
	}

	@Override
	public void drawCircle(int x, int y, int width, int height) {
		circles.add(new TestCircle(x, y, width, height, currentColor));
	}

	@Override
	public void drawRectangle(int x, int y, int width, int height) {
		rectangles.add(new TestRectangle(x, y, width, height, currentColor));
	}

	@Override
	public void fillRectangle(int x, int y, int width, int height) {
		filledRectangles.add(new TestFilledRectangle(x, y, width, height, currentColor));
	}

	@Override
	public void drawCenteredString(int x, int y, String text) {
		centeredStrings.add(new TestCenteredString(x, y, text, currentColor));
	}
}
