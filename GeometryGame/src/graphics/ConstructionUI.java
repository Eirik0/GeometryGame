package graphics;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import algebraic.SquareRoot;
import construction.Construction;
import geometric.CCircle;
import geometric.CLine;
import geometric.CLine.LineType;
import geometric.CPoint;
import geometric.HorizontalLine;
import geometric.LineOrCircle;
import geometric.SlopedLine;
import geometric.VerticalLine;
import input.UserInputBridge.UserEvent;

public class ConstructionUI {
	public static final int DEFAULT_IMAGE_WIDTH = 1024;
	public static final int DEFAULT_IMAGE_HEIGHT = 768;

	public static final Color INTERSECTION_COLOR = Color.BLACK;
	public static final Color LINE_AND_CIRCLE_COLOR = Color.GRAY;

	private final Construction construction;

	public final ConstructionImage image;

	private double x0;
	private double y0;

	private double constructionWidth;
	private double constructionHeight;

	private double pixelsPerUnit;

	private ConstructionUIState state;
	public final List<CButton> buttons;

	public ConstructionUI(Construction construction, ConstructionImage image) {
		this.construction = construction;
		this.image = image;
		x0 = -1;
		constructionWidth = 3.0;
		pixelsPerUnit = image.getWidth() / constructionWidth;
		constructionHeight = image.getHeight() / pixelsPerUnit;
		y0 = -constructionHeight / 2;
		state = new ReadyToDrawState(this);
		buttons = new ArrayList<>();
		// Draw Line button
		buttons.add(new DrawButton(1.0 / 8, () -> state = new DrawState(this, ps -> construction.draw(CLine.newLine(ps.first, ps.second)))));
		// Draw Circle button
		buttons.add(new DrawButton(5.0 / 8, () -> state = new DrawState(this, ps -> construction.draw(new CCircle(ps.first, ps.second)))));
	}

	public void handleEvent(UserEvent event, int x, int y) {
		state.handleEvent(event, x, y);
	}

	public void setState(ConstructionUIState state) {
		this.state = state;
	}

	public void zoom(double zoom) {
		x0 -= (constructionWidth * (zoom - 1)) / 2;
		y0 -= (constructionHeight * (zoom - 1)) / 2;
		constructionWidth *= zoom;
		constructionHeight *= zoom;
		pixelsPerUnit /= zoom;
	}

	public void move(int x, int y) {
		x0 -= x / pixelsPerUnit;
		y0 -= y / pixelsPerUnit;
	}

	public int transformX(double x) {
		return round((x - x0) * pixelsPerUnit);
	}

	public int transformY(double y) {
		return round((y - y0) * pixelsPerUnit);
	}

	public void draw() {
		state.draw();
	}

	public void drawLinesAndCircles() {
		image.setColor(LINE_AND_CIRCLE_COLOR);
		for (LineOrCircle lineOrCircle : construction.getLinesAndCircles()) {
			if (lineOrCircle instanceof CLine) {
				LineType lineType = ((CLine) lineOrCircle).getType();
				if (lineType == LineType.HORIZONTAL) {
					drawHorizontalLine((HorizontalLine) lineOrCircle);
				} else if (lineType == LineType.VERTICAL) {
					drawVerticalLine((VerticalLine) lineOrCircle);
				} else {
					drawSlopedLine((SlopedLine) lineOrCircle);
				}
			} else {
				drawCircle((CCircle) lineOrCircle);
			}
		}
	}

	private void drawHorizontalLine(HorizontalLine line) {
		int y = transformY(line.y.doubleValue());
		image.drawLine(0, y, image.getWidth(), y);
	}

	private void drawVerticalLine(VerticalLine line) {
		int x = transformX(line.x.doubleValue());
		image.drawLine(x, 0, x, image.getHeight());
	}

	private void drawSlopedLine(SlopedLine line) {
		double m = line.m.doubleValue();
		double b = line.b.doubleValue();
		// y = mx + b
		int y_x0 = transformY(m * x0 + b);
		int y_x1 = transformY(m * (x0 + constructionWidth) + b);
		// x = (y - b)/m
		int x_y0 = transformX((y0 - b) / m);
		int x_y1 = transformX(((y0 + constructionHeight) - b) / m);
		boolean intersectsWest = y_x0 >= 0 && y_x0 <= image.getHeight();
		boolean intersectsSouth = x_y0 >= 0 && x_y0 <= image.getWidth();
		boolean intersectsEast = y_x1 >= 0 && y_x1 <= image.getHeight();
		if (intersectsWest) {
			if (intersectsSouth) {
				image.drawLine(0, y_x0, x_y0, -y_x0);
			} else if (intersectsEast) {
				image.drawLine(0, y_x0, image.getWidth(), y_x1 - y_x0);
			} else { // intersects north
				image.drawLine(0, y_x0, x_y1, image.getHeight() - y_x0);
			}
		} else if (intersectsSouth) {
			if (intersectsEast) {
				image.drawLine(x_y0, 0, image.getWidth() - x_y0, y_x1);
			} else { // intersects north
				image.drawLine(x_y0, 0, x_y1 - x_y0, image.getHeight());
			}
		} else if (intersectsEast) { // intersects north
			image.drawLine(x_y1, image.getHeight(), image.getWidth() - x_y1, y_x1 - image.getHeight());
		}
	}

	private void drawCircle(CCircle circle) {
		int c_x0 = transformX(circle.h.subtract(SquareRoot.of(circle.rSquared)).doubleValue());
		int c_y0 = transformY(circle.k.subtract(SquareRoot.of(circle.rSquared)).doubleValue());
		int c_x1 = transformX(circle.h.add(SquareRoot.of(circle.rSquared)).doubleValue());
		int c_y1 = transformY(circle.k.add(SquareRoot.of(circle.rSquared)).doubleValue());
		image.drawCircle(c_x0, c_y0, c_x1 - c_x0, c_y1 - c_y0);
	}

	public void drawIntersections() {
		image.setColor(INTERSECTION_COLOR);
		for (CPoint intersection : construction.getIntersections()) {
			image.drawPoint(transformX(intersection.x.doubleValue()), transformY(intersection.y.doubleValue()));
		}
	}

	public void drawButtons() {
		for (CButton button : buttons) {
			button.drawOn(image);
		}
	}

	public CPoint findIntersectionNearest(int x, int y) {
		double minDistance = Double.MAX_VALUE;
		CPoint minPoint = null;
		for (CPoint point : construction.getIntersections()) {
			double dx = (transformX(point.x.doubleValue()) - x);
			double dy = (transformX(point.y.doubleValue()) - y);
			double distance = Math.sqrt(dx * dx + dy * dy);
			if (distance < minDistance) {
				minDistance = distance;
				minPoint = point;
			}
		}
		return minPoint;
	}

	public static int round(double d) {
		return (int) Math.round(d);
	}
}
