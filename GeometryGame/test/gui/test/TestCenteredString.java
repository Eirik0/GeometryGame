package gui.test;

import java.awt.Color;

public class TestCenteredString {
	public final int x;
	public final int y;
	public String text;
	public final Color color;

	public TestCenteredString(int x, int y, String text, Color color) {
		this.x = x;
		this.y = y;
		this.text = text;
		this.color = color;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		return prime * (prime * (prime * (prime + x) + y) + text.hashCode()) + color.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		TestCenteredString other = (TestCenteredString) obj;
		return x == other.x && y == other.y && text.equals(other.text) && color.equals(other.color);
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + "): " + text + ", " + color;
	}
}
