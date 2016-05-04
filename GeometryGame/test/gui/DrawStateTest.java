package gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;

import org.junit.Test;

import geometric.CCircle;
import geometric.CLine;
import geometric.CPoint;
import gui.test.TestPoint;
import gui.test.TestQuadruple.TestCircle;
import gui.test.TestUI;

public class DrawStateTest {
	@Test
	public void testDrawLine() {
		TestUI testUI = new TestUI();
		testUI.moveLeftClickAndReleaseAt(TestUI.LINE_BUTTON_X, TestUI.LINE_BUTTON_Y);
		testUI.moveLeftClickAndReleaseAt(341, 384);
		testUI.moveLeftClickAndReleaseAt(683, 384);
		assertTrue(testUI.construction.getLinesAndCircles().contains((CLine.newLine(CPoint.newPoint(0, 0), CPoint.newPoint(1, 0)))));
	}

	@Test
	public void testDrawCircle() {
		TestUI testUI = new TestUI();
		testUI.moveLeftClickAndReleaseAt(TestUI.CIRCLE_BUTTON_X, TestUI.CIRCLE_BUTTON_Y);
		testUI.moveLeftClickAndReleaseAt(341, 384);
		testUI.moveLeftClickAndReleaseAt(683, 384);
		assertTrue(testUI.construction.getLinesAndCircles().contains(new CCircle(CPoint.newPoint(0, 0), CPoint.newPoint(1, 0))));
	}

	@Test
	public void testDraw_HighlightFirstPoint() {
		TestUI testUI = new TestUI();
		testUI.moveLeftClickAndReleaseAt(768, 50);
		testUI.ui.handleEvent(UserEvent.MOUSE_MOVED, 341, 384);
		testUI.ui.draw();
		// points should be there
		assertEquals(2, testUI.image.points.size());
		assertTrue(testUI.image.points.contains(new TestPoint(341, 384, ConstructionColors.getIntersectionColor())));
		assertTrue(testUI.image.points.contains(new TestPoint(683, 384, ConstructionColors.getIntersectionColor())));
		// first point highlighted
		assertEquals(1, testUI.image.circles.size());
		assertTrue(testUI.image.circles.contains(new TestCircle(336, 379, 10, 10, Color.RED)));
		// no buttons
		assertEquals(0, testUI.image.rectangles.size());
	}

	@Test
	public void testDraw_HighlightSecondPoint() {
		TestUI testUI = new TestUI();
		testUI.moveLeftClickAndReleaseAt(TestUI.CIRCLE_BUTTON_X, TestUI.CIRCLE_BUTTON_Y);
		testUI.moveLeftClickAndReleaseAt(341, 384);
		testUI.ui.handleEvent(UserEvent.MOUSE_MOVED, 683, 384); // move to 2nd point
		testUI.ui.draw();
		assertEquals(2, testUI.image.circles.size());
		assertTrue(testUI.image.circles.contains(new TestCircle(336, 379, 10, 10, Color.RED)));
		assertTrue(testUI.image.circles.contains(new TestCircle(678, 379, 10, 10, Color.RED)));
	}
}
