package gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import geometric.CPoint;
import gui.test.TestPoint;
import gui.test.TestUI;

public class ConstructionUITest {
	@Test
	public void testSetSize() {
		TestUI ui = new TestUI();
		ui.ui.setSize(512, 384);
		ui.ui.draw();
		assertTrue(ui.image.points.contains(new TestPoint(85, 192, ConstructionColors.getIntersectionColor())));
		assertTrue(ui.image.points.contains(new TestPoint(427, 192, ConstructionColors.getIntersectionColor())));
	}

	@Test
	public void testChagneImageWidth() {
		TestUI ui = new TestUI();
		ui.ui.setSize(512, 768);
		ui.ui.draw();
		assertTrue(ui.image.points.contains(new TestPoint(85, 384, ConstructionColors.getIntersectionColor())));
		assertTrue(ui.image.points.contains(new TestPoint(427, 384, ConstructionColors.getIntersectionColor())));
	}

	@Test
	public void testChangeImageHeight() {
		TestUI ui = new TestUI();
		ui.ui.setSize(1024, 384);
		ui.ui.draw();
		assertTrue(ui.image.points.contains(new TestPoint(341, 192, ConstructionColors.getIntersectionColor())));
		assertTrue(ui.image.points.contains(new TestPoint(683, 192, ConstructionColors.getIntersectionColor())));
	}

	@Test
	public void testMove() {
		TestUI testUI = new TestUI();
		testUI.ui.handleEvent(UserEvent.LEFT_CLICK_PRESSED, 500, 500);
		testUI.ui.handleEvent(UserEvent.MOUSE_DRAGGED, 510, 510);
		testUI.ui.draw();
		assertEquals(2, testUI.image.points.size());
		assertTrue(testUI.image.points.contains(new TestPoint(351, 394, ConstructionColors.getIntersectionColor())));
		assertTrue(testUI.image.points.contains(new TestPoint(693, 394, ConstructionColors.getIntersectionColor())));
	}

	@Test
	public void testZoomIn() {
		TestUI testUI = new TestUI();
		testUI.ui.handleEvent(UserEvent.SCROLL_UP, 500, 500);
		testUI.ui.draw();
		assertEquals(2, testUI.image.points.size());
		assertTrue(testUI.image.points.contains(new TestPoint(322, 384, ConstructionColors.getIntersectionColor())));
		assertTrue(testUI.image.points.contains(new TestPoint(702, 384, ConstructionColors.getIntersectionColor())));
	}

	@Test
	public void testZoomOut() {
		TestUI testUI = new TestUI();
		testUI.ui.handleEvent(UserEvent.SCROLL_DOWN, 500, 500);
		testUI.ui.draw();
		assertEquals(2, testUI.image.points.size());
		assertTrue(testUI.image.points.contains(new TestPoint(357, 384, ConstructionColors.getIntersectionColor())));
		assertTrue(testUI.image.points.contains(new TestPoint(667, 384, ConstructionColors.getIntersectionColor())));
	}

	@Test
	public void testFindIntersectionNearest() {
		TestUI ui = new TestUI();
		assertEquals(CPoint.newPoint(0, 0), ui.ui.findIntersectionNearest(511, 385));
		assertEquals(CPoint.newPoint(1, 0), ui.ui.findIntersectionNearest(513, 385));
	}
}
