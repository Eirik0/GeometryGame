package input;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;

import org.junit.Test;

import geometric.CCircle;
import geometric.CLine;
import geometric.CPoint;
import graphics.ConstructionUI;
import graphics.TestImage.TestCircle;
import graphics.TestImage.TestPoint;
import graphics.TestUI;
import input.UserInputBridge.UserEvent;

public class UserInputBridgeTest {
	private void leftClickPressAndRelease(UserInputBridge inputBridge) {
		inputBridge.handleEvent(UserEvent.LEFT_CLICK_PRESSED);
		inputBridge.handleEvent(UserEvent.LEFT_CLICK_RELEASED);
	}

	@Test
	public void testMove() { 
		TestUI ui = new TestUI();
		UserInputBridge inputBridge = new UserInputBridge(ui.ui);
		inputBridge.moveMouseTo(500, 500);
		inputBridge.handleEvent(UserEvent.LEFT_CLICK_PRESSED);
		inputBridge.moveMouseTo(510, 510);
		ui.ui.draw();
		assertEquals(2, ui.image.points.size());
		assertTrue(ui.image.points.contains(new TestPoint(351, 394, ConstructionUI.INTERSECTION_COLOR)));
		assertTrue(ui.image.points.contains(new TestPoint(693, 394, ConstructionUI.INTERSECTION_COLOR)));
	}

	@Test
	public void testZoomIn() {
		TestUI ui = new TestUI();
		UserInputBridge inputBridge = new UserInputBridge(ui.ui);
		inputBridge.handleEvent(UserEvent.SCROLL_UP);
		ui.ui.draw();
		assertEquals(2, ui.image.points.size());
		assertTrue(ui.image.points.contains(new TestPoint(322, 384, ConstructionUI.INTERSECTION_COLOR)));
		assertTrue(ui.image.points.contains(new TestPoint(702, 384, ConstructionUI.INTERSECTION_COLOR)));
	}

	@Test
	public void testZoomOut() {
		TestUI ui = new TestUI();
		UserInputBridge inputBridge = new UserInputBridge(ui.ui);
		inputBridge.handleEvent(UserEvent.SCROLL_DOWN);
		ui.ui.draw();
		assertEquals(2, ui.image.points.size());
		assertTrue(ui.image.points.contains(new TestPoint(357, 384, ConstructionUI.INTERSECTION_COLOR)));
		assertTrue(ui.image.points.contains(new TestPoint(667, 384, ConstructionUI.INTERSECTION_COLOR)));
	}

	@Test
	public void testDrawLine() {
		TestUI testUI = new TestUI();
		UserInputBridge inputBridge = new UserInputBridge(testUI.ui);
		inputBridge.moveMouseTo(200, 50);
		leftClickPressAndRelease(inputBridge);
		inputBridge.moveMouseTo(341, 384);
		leftClickPressAndRelease(inputBridge);
		inputBridge.moveMouseTo(683, 384);
		leftClickPressAndRelease(inputBridge);
		assertTrue(testUI.construction.getLinesAndCircles().contains((CLine.newLine(CPoint.newPoint(0, 0), CPoint.newPoint(1, 0)))));
	}

	@Test
	public void testDrawCircle() {
		TestUI testUI = new TestUI();
		UserInputBridge inputBridge = new UserInputBridge(testUI.ui);
		inputBridge.moveMouseTo(768, 50);
		leftClickPressAndRelease(inputBridge);
		inputBridge.moveMouseTo(341, 384);
		leftClickPressAndRelease(inputBridge);
		inputBridge.moveMouseTo(683, 384);
		leftClickPressAndRelease(inputBridge);
		assertTrue(testUI.construction.getLinesAndCircles().contains(new CCircle(CPoint.newPoint(0, 0), CPoint.newPoint(1, 0))));
	}

	@Test
	public void testDrawWhenInDrawingState() {
		TestUI ui = new TestUI();
		UserInputBridge inputBridge = new UserInputBridge(ui.ui);
		inputBridge.moveMouseTo(768, 50);
		leftClickPressAndRelease(inputBridge);
		inputBridge.moveMouseTo(341, 384);
		ui.ui.draw();
		assertEquals(2, ui.image.points.size());
		assertTrue(ui.image.points.contains(new TestPoint(341, 384, ConstructionUI.INTERSECTION_COLOR)));
		assertTrue(ui.image.points.contains(new TestPoint(683, 384, ConstructionUI.INTERSECTION_COLOR)));
		assertEquals(1, ui.image.circles.size());
		assertTrue(ui.image.circles.contains(new TestCircle(336, 379, 10, 10, Color.RED)));
		assertEquals(0, ui.image.rectangles.size());
	}

	@Test
	public void testHighlightSecondPointWhenInDrawingState() {
		TestUI ui = new TestUI();
		UserInputBridge inputBridge = new UserInputBridge(ui.ui);
		inputBridge.moveMouseTo(768, 50);
		leftClickPressAndRelease(inputBridge);
		inputBridge.moveMouseTo(341, 384);
		leftClickPressAndRelease(inputBridge);
		inputBridge.moveMouseTo(683, 384);
		ui.ui.draw();
		assertEquals(2, ui.image.circles.size());
		assertTrue(ui.image.circles.contains(new TestCircle(336, 379, 10, 10, Color.RED)));
		assertTrue(ui.image.circles.contains(new TestCircle(678, 379, 10, 10, Color.RED)));
	}
}
