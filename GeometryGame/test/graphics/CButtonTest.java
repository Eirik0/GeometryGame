package graphics;

import static org.junit.Assert.assertTrue;

import java.awt.Color;

import org.junit.Test;

import graphics.TestImage.TestRectangle;

public class CButtonTest {
	@Test
	public void testDraw_DrawLineButton() {
		TestUI ui = new TestUI();
		ui.ui.draw();
		assertTrue(ui.image.rectangles.contains(new TestRectangle(128, 15, 256, 64, Color.BLACK)));
	}

	@Test
	public void testDraw_DrawCircleButton() {
		TestUI ui = new TestUI();
		ui.ui.draw();
		assertTrue(ui.image.rectangles.contains(new TestRectangle(640, 15, 256, 64, Color.BLACK)));
	}
}
