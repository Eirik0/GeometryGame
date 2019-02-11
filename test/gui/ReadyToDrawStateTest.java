package gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import geometric.CCircle;
import geometric.CLine;
import geometric.CPoint;
import gui.test.TestPoint;
import gui.test.TestQuadruple.TestCircle;
import gui.test.TestQuadruple.TestFilledRectangle;
import gui.test.TestQuadruple.TestLine;
import gui.test.TestUI;

public class ReadyToDrawStateTest {
	@Test
	public void testDrawPoints() {
		TestUI ui = new TestUI();
		ui.ui.draw();
		assertEquals(2, ui.image.points.size());
		assertTrue(ui.image.points.contains(new TestPoint(341, 384, ConstructionColors.getIntersectionColor())));
		assertTrue(ui.image.points.contains(new TestPoint(683, 384, ConstructionColors.getIntersectionColor())));
	}

	@Test
	public void testDrawBackground() {
		TestUI ui = new TestUI();
		ui.ui.draw();
		assertTrue(ui.image.filledRectangles.contains(new TestFilledRectangle(0, 0, 1024, 768, ConstructionColors.getBackgroundColor())));
	}

	@Test
	public void testDrawHorizontalLine() {
		TestUI ui = new TestUI();
		ui.construction.draw(CLine.newLine(CPoint.newPoint(0, 0), CPoint.newPoint(1, 0)));
		ui.ui.draw();
		assertEquals(1, ui.image.lines.size());
		assertTrue(ui.image.lines.contains(new TestLine(0, 384, 1024, 384, ConstructionColors.getLineAndCircleColor())));
	}

	@Test
	public void testDrawVerticalLine() {
		TestUI ui = new TestUI();
		ui.construction.draw(CLine.newLine(CPoint.newPoint(0, 0), CPoint.newPoint(0, 1)));
		ui.ui.draw();
		assertEquals(1, ui.image.lines.size());
		assertTrue(ui.image.lines.contains(new TestLine(341, 0, 341, 768, ConstructionColors.getLineAndCircleColor())));
	}

	@Test
	public void testDrawSlopedLine_WS() {
		TestUI ui = new TestUI();
		ui.construction.draw(CLine.newLine(CPoint.newPoint(-1, -1), CPoint.newPoint(0, -2)));
		ui.ui.draw();
		assertEquals(1, ui.image.lines.size());
		assertTrue(ui.image.lines.contains(new TestLine(0, 43, 43, 0, ConstructionColors.getLineAndCircleColor())));
	}

	@Test
	public void testDrawSlopedLine_WE() {
		TestUI ui = new TestUI();
		ui.construction.draw(CLine.newLine(CPoint.newPoint(-1, -1), CPoint.newPoint(2, 0)));
		ui.ui.draw();
		assertEquals(1, ui.image.lines.size());
		assertTrue(ui.image.lines.contains(new TestLine(0, 43, 1024, 384, ConstructionColors.getLineAndCircleColor())));
	}

	@Test
	public void testDrawSlopedLine_WN() {
		TestUI ui = new TestUI();
		ui.construction.draw(CLine.newLine(CPoint.newPoint(0, 0), CPoint.newPoint(1, 1)));
		ui.ui.draw();
		assertEquals(1, ui.image.lines.size());
		assertTrue(ui.image.lines.contains(new TestLine(0, 43, 725, 768, ConstructionColors.getLineAndCircleColor())));
	}

	@Test
	public void testDrawSlopedLine_SE() {
		TestUI ui = new TestUI();
		ui.construction.draw(CLine.newLine(CPoint.newPoint(1, -1), CPoint.newPoint(2, 0)));
		ui.ui.draw();
		assertEquals(1, ui.image.lines.size());
		assertTrue(ui.image.lines.contains(new TestLine(640, 0, 1024, 384, ConstructionColors.getLineAndCircleColor())));
	}

	@Test
	public void testDrawSlopedLine_SN() {
		TestUI ui = new TestUI();
		ui.construction.draw(CLine.newLine(CPoint.newPoint(0, -1), CPoint.newPoint(1, 1)));
		ui.ui.draw();
		assertEquals(1, ui.image.lines.size());
		assertTrue(ui.image.lines.contains(new TestLine(320, 0, 704, 768, ConstructionColors.getLineAndCircleColor())));
	}

	@Test
	public void testDrawSlopedLine_EN() {
		TestUI ui = new TestUI();
		ui.construction.draw(CLine.newLine(CPoint.newPoint(1, 1), CPoint.newPoint(2, 0)));
		ui.ui.draw();
		assertEquals(1, ui.image.lines.size());
		assertTrue(ui.image.lines.contains(new TestLine(1024, 384, 640, 768, ConstructionColors.getLineAndCircleColor())));
	}

	@Test
	public void testDontDrawSlopedLine_OffImage() {
		TestUI ui = new TestUI();
		ui.construction.draw(CLine.newLine(CPoint.newPoint(0, 4), CPoint.newPoint(4, 5)));
		ui.ui.draw();
		assertEquals(0, ui.image.lines.size());
	}

	@Test
	public void testDrawCircle() {
		TestUI ui = new TestUI();
		ui.construction.draw(new CCircle(CPoint.newPoint(0, 0), CPoint.newPoint(1, 0)));
		ui.ui.draw();
		assertEquals(1, ui.image.circles.size());
		assertTrue(ui.image.circles.contains(new TestCircle(0, 43, 683, 682, ConstructionColors.getLineAndCircleColor())));
	}

	@Test
	public void testClickOnButtonReleaseOff() {
		TestUI ui = new TestUI();
		ui.ui.handleEvent(UserEvent.LEFT_CLICK_PRESSED, TestUI.LINE_BUTTON_X, TestUI.LINE_BUTTON_Y);
		ui.ui.handleEvent(UserEvent.MOUSE_DRAGGED, TestUI.LINE_BUTTON_X, 0);
		ui.ui.handleEvent(UserEvent.LEFT_CLICK_RELEASED, TestUI.LINE_BUTTON_X, 0);
		ui.ui.draw();
		assertTrue(ui.image.circles.isEmpty()); // No circles means we are still in the ready to draw state
	}

	@Test
	public void testClickOffButtonReleaseOn() {
		TestUI ui = new TestUI();
		ui.ui.handleEvent(UserEvent.LEFT_CLICK_PRESSED, TestUI.LINE_BUTTON_X, 0);
		ui.ui.handleEvent(UserEvent.MOUSE_DRAGGED, TestUI.LINE_BUTTON_X, TestUI.LINE_BUTTON_Y);
		ui.ui.handleEvent(UserEvent.LEFT_CLICK_RELEASED, TestUI.LINE_BUTTON_X, TestUI.LINE_BUTTON_Y);
		ui.ui.draw();
		assertTrue(ui.image.circles.isEmpty()); // No circles means we are still in the ready to draw state
	}

	@Test
	public void testClickOnButtonReleaseOnOtherButton() {
		TestUI ui = new TestUI();
		ui.ui.handleEvent(UserEvent.LEFT_CLICK_PRESSED, TestUI.LINE_BUTTON_X, TestUI.LINE_BUTTON_Y);
		ui.ui.handleEvent(UserEvent.MOUSE_DRAGGED, TestUI.CIRCLE_BUTTON_X, TestUI.CIRCLE_BUTTON_Y);
		ui.ui.handleEvent(UserEvent.LEFT_CLICK_RELEASED, TestUI.CIRCLE_BUTTON_X, TestUI.CIRCLE_BUTTON_Y);
		ui.ui.draw();
		assertTrue(ui.image.circles.isEmpty()); // No circles means we are still in the ready to draw state
	}

	@Test
	public void testDragAndReleaseFromNowhere() {
		TestUI ui = new TestUI();
		ui.ui.handleEvent(UserEvent.MOUSE_DRAGGED, TestUI.CIRCLE_BUTTON_X, TestUI.CIRCLE_BUTTON_Y);
		ui.ui.handleEvent(UserEvent.LEFT_CLICK_RELEASED, TestUI.CIRCLE_BUTTON_X, TestUI.CIRCLE_BUTTON_Y);
		ui.ui.draw();
		assertTrue(ui.image.circles.isEmpty()); // No circles means we are still in the ready to draw state
	}
}
