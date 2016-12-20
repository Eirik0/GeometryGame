package gui;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;

import algebraic.Pair;
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

public class ConstructionUI {
	private final Construction construction;

	public final ConstructionImage image;

	private double x0;
	private double y0;

	private double constructionWidth;
	private double constructionHeight;

	private double pixelsPerUnit;

	private ConstructionUIState state;

	public ConstructionUI(Construction construction, ConstructionImage image) {
		this.construction = construction;
		this.image = image;
		x0 = -1;
		constructionWidth = 3.0;
		pixelsPerUnit = image.getWidth() / constructionWidth;
		constructionHeight = image.getHeight() / pixelsPerUnit;
		y0 = -constructionHeight / 2;
		state = new ReadyToDrawState(this);
	}

	public void setState(ConstructionUIState state) {
		this.state = state;
	}

	public void handleEvent(UserEvent event, int x, int y) {
		switch (event) {
		case SCROLL_UP:
			zoom(0.9);
			break;
		case SCROLL_DOWN:
			zoom(1.1);
			break;
		default:
			state.handleEvent(event, x, y);
		}
	}

	public void addLineOrCircle(LineOrCircle lineOrCircle) {
		construction.draw(lineOrCircle);
	}

	public void setSize(int width, int height) {
		double dx = (width - image.getWidth()) / pixelsPerUnit;
		double dy = (height - image.getHeight()) / pixelsPerUnit;
		constructionWidth += dx;
		constructionHeight += dy;
		x0 -= dx / 2;
		y0 -= dy / 2;
		image.setSize(width, height);
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
		image.setColor(ConstructionColors.getBackgroundColor());
		image.fillRectangle(0, 0, image.getWidth(), image.getHeight());
		state.draw();
	}

	public void drawLinesAndCircles() {
		image.setColor(ConstructionColors.getLineAndCircleColor());
		for (LineOrCircle lineOrCircle : construction.getLinesAndCircles()) {
			drawLineOrCircle(lineOrCircle);
		}
	}

	public void drawLineOrCircle(LineOrCircle lineOrCircle) {
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
		//   N
		// W   E
		//   S
		boolean intersectsWest = y_x0 >= 0 && y_x0 <= image.getHeight(); // (0, y_x0)
		boolean intersectsNorth = x_y0 >= 0 && x_y0 <= image.getWidth(); // (x_y0, 0)
		boolean intersectsEast = y_x1 >= 0 && y_x1 <= image.getHeight(); // (width, y_x1)
		// default intersects south (x_y1, height)
		if (intersectsWest) {
			if (intersectsNorth) {
				image.drawLine(0, y_x0, x_y0, 0);
			} else if (intersectsEast) {
				image.drawLine(0, y_x0, image.getWidth(), y_x1);
			} else {
				image.drawLine(0, y_x0, x_y1, image.getHeight());
			}
		} else if (intersectsNorth) {
			if (intersectsEast) {
				image.drawLine(x_y0, 0, image.getWidth(), y_x1);
			} else {
				image.drawLine(x_y0, 0, x_y1, image.getHeight());
			}
		} else if (intersectsEast) {
			image.drawLine(image.getWidth(), y_x1, x_y1, image.getHeight());
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
		image.setColor(ConstructionColors.getIntersectionColor());
		for (CPoint intersection : construction.getIntersections()) {
			image.drawPoint(transformX(intersection.x.doubleValue()), transformY(intersection.y.doubleValue()));
		}
	}

	public CPoint findIntersectionNearest(int x, int y) {
		double minDistance = Double.MAX_VALUE;
		CPoint minPoint = null;
		for (CPoint point : construction.getIntersections()) {
			double dx = (transformX(point.x.doubleValue()) - x);
			double dy = (transformY(point.y.doubleValue()) - y);
			double distance = Math.sqrt(dx * dx + dy * dy);
			if (distance < minDistance) {
				minDistance = distance;
				minPoint = point;
			}
		}
		return minPoint;
	}

	public Set<CPoint> findAllowableSecondIntersections(CPoint firstIntersection, Function<Pair<CPoint>, ? extends LineOrCircle> lineOrCircleFunction) {
		Set<CPoint> intersections = new HashSet<>(construction.getIntersections());
		intersections.remove(firstIntersection);
		Iterator<CPoint> intersectionIter = intersections.iterator();
		while (intersectionIter.hasNext()) {
			CPoint secondIntersection = intersectionIter.next();
			if (construction.getLinesAndCircles().contains(lineOrCircleFunction.apply(Pair.valueOf(firstIntersection, secondIntersection)))) {
				intersectionIter.remove();
			}
		}
		return intersections;
	}

	public static int round(double d) {
		return (int) Math.round(d);
	}
}
