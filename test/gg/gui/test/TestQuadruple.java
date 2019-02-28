package gg.gui.test;

import java.awt.Color;

public abstract class TestQuadruple {
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

    public static class TestFilledRectangle extends TestQuadruple {
        public TestFilledRectangle(int x, int y, int width, int height, Color color) {
            super(x, y, width, height, color);
        }
    }
}
