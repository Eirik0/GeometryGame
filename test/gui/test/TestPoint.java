package gui.test;

import java.awt.Color;

public class TestPoint {
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
