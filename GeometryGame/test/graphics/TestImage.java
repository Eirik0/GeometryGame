package graphics;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

public class TestImage implements ConstructionImage {
	private int imageWidth;
	private int imageHeight;

	public final Set<TestPoint> points = new HashSet<>();
	public final Set<TestLine> lines = new HashSet<>();
	public final Set<TestCircle> circles = new HashSet<>();
	public final Set<TestRectangle> rectangles = new HashSet<>();

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

	public static class TestPoint {
		public final int x;
		public final int y;
		public final Color color;

		public TestPoint(int x, int y, Color color) {
			this.x = x;
			this.y = y;
			this.color = color;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			return prime * (prime * (prime + x) + y) + color.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			} else if (obj == null || getClass() != obj.getClass()) {
				return false;
			}
			TestPoint other = (TestPoint) obj;
			return x == other.x && y == other.y && color.equals(other.color);
		}

		@Override
		public String toString() {
			return "(" + x + ", " + y + "), " + color;
		}
	}

	public static abstract class TestQuadruple {
		public final int x0;
		public final int y0;
		public final int x1;
		public final int y1;
		public final Color color;

		public TestQuadruple(int x0, int y0, int x1, int y1, Color color) {
			this.x0 = x0;
			this.y0 = y0;
			this.x1 = x1;
			this.y1 = y1;
			this.color = color;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			return prime * (prime * (prime * (prime * (prime + x0) + y0) + x1) + y1) + color.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			} else if (obj == null || getClass() != obj.getClass()) {
				return false;
			}
			TestQuadruple other = (TestQuadruple) obj;
			return x0 == other.x0 && y0 == other.y0 && x1 == other.x1 && y1 == other.y1 && color.equals(other.color);
		}

		@Override
		public String toString() {
			return "(" + x0 + ", " + y0 + ") - (" + x1 + ", " + y1 + "), " + color;
		}
	}

	public static class TestLine extends TestQuadruple {
		public TestLine(int x0, int y0, int x1, int y1, Color color) {
			super(x0, y0, x1, y1, color);
		}
	}

	public static class TestCircle extends TestQuadruple {
		public TestCircle(int x, int y, int width, int height, Color color) {
			super(x, y, width, height, color);
		}
	}

	public static class TestRectangle extends TestQuadruple {
		public TestRectangle(int x, int y, int width, int height, Color color) {
			super(x, y, width, height, color);
		}
	}
}
