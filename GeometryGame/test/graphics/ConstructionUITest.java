package graphics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import geometric.CCircle;
import geometric.CLine;
import geometric.CPoint;
import graphics.TestImage.TestCircle;
import graphics.TestImage.TestLine;
import graphics.TestImage.TestPoint;

public class ConstructionUITest {
	@Test
	public void testDrawPoints() {
		TestUI ui = new TestUI();
		ui.ui.draw();
		assertEquals(2, ui.image.points.size());
		assertTrue(ui.image.points.contains(new TestPoint(341, 384, ConstructionUI.INTERSECTION_COLOR)));
		assertTrue(ui.image.points.contains(new TestPoint(683, 384, ConstructionUI.INTERSECTION_COLOR)));
	}

	@Test
	public void testDrawHorizontalLine() {
		TestUI ui = new TestUI();
		ui.construction.draw(CLine.newLine(CPoint.newPoint(0, 0), CPoint.newPoint(1, 0)));
		ui.ui.draw();
		assertEquals(1, ui.image.lines.size());
		assertTrue(ui.image.lines.contains(new TestLine(0, 384, 1024, 384, ConstructionUI.LINE_AND_CIRCLE_COLOR)));
	}

	@Test
	public void testDrawVerticalLine() {
		TestUI ui = new TestUI();
		ui.construction.draw(CLine.newLine(CPoint.newPoint(0, 0), CPoint.newPoint(0, 1)));
		ui.ui.draw();
		assertEquals(1, ui.image.lines.size());
		assertTrue(ui.image.lines.contains(new TestLine(341, 0, 341, 768, ConstructionUI.LINE_AND_CIRCLE_COLOR)));
	}

	@Test
	public void testDrawSlopedLine_WS() {
		TestUI ui = new TestUI();
		ui.construction.draw(CLine.newLine(CPoint.newPoint(-1, -1), CPoint.newPoint(0, -2)));
		ui.ui.draw();
		assertEquals(1, ui.image.lines.size());
		assertTrue(ui.image.lines.contains(new TestLine(0, 43, 43, -43, ConstructionUI.LINE_AND_CIRCLE_COLOR)));
	}

	@Test
	public void testDrawSlopedLine_WE() {
		TestUI ui = new TestUI();
		ui.construction.draw(CLine.newLine(CPoint.newPoint(-1, -1), CPoint.newPoint(2, 0)));
		ui.ui.draw();
		assertEquals(1, ui.image.lines.size());
		assertTrue(ui.image.lines.contains(new TestLine(0, 43, 1024, 341, ConstructionUI.LINE_AND_CIRCLE_COLOR)));
	}

	@Test
	public void testDrawSlopedLine_WN() {
		TestUI ui = new TestUI();
		ui.construction.draw(CLine.newLine(CPoint.newPoint(0, 0), CPoint.newPoint(1, 1)));
		ui.ui.draw();
		assertEquals(1, ui.image.lines.size());
		assertTrue(ui.image.lines.contains(new TestLine(0, 43, 725, 725, ConstructionUI.LINE_AND_CIRCLE_COLOR)));
	}

	@Test
	public void testDrawSlopedLine_SE() {
		TestUI ui = new TestUI();
		ui.construction.draw(CLine.newLine(CPoint.newPoint(1, -1), CPoint.newPoint(2, 0)));
		ui.ui.draw();
		assertEquals(1, ui.image.lines.size());
		assertTrue(ui.image.lines.contains(new TestLine(640, 0, 384, 384, ConstructionUI.LINE_AND_CIRCLE_COLOR)));
	}

	@Test
	public void testDrawSlopedLine_SN() {
		TestUI ui = new TestUI();
		ui.construction.draw(CLine.newLine(CPoint.newPoint(0, -1), CPoint.newPoint(1, 1)));
		ui.ui.draw();
		assertEquals(1, ui.image.lines.size());
		assertTrue(ui.image.lines.contains(new TestLine(320, 0, 384, 768, ConstructionUI.LINE_AND_CIRCLE_COLOR)));
	}

	@Test
	public void testDrawSlopedLine_EN() {
		TestUI ui = new TestUI();
		ui.construction.draw(CLine.newLine(CPoint.newPoint(1, 1), CPoint.newPoint(2, 0)));
		ui.ui.draw();
		assertEquals(1, ui.image.lines.size());
		assertTrue(ui.image.lines.contains(new TestLine(640, 768, 384, -384, ConstructionUI.LINE_AND_CIRCLE_COLOR)));
	}

	@Test
	public void testDrawSlopedLine_DoesntIntersect() {
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
		assertTrue(ui.image.circles.contains(new TestCircle(0, 43, 683, 682, ConstructionUI.LINE_AND_CIRCLE_COLOR)));
	}

	@Test
	public void testFindIntersectionNearest() {
		TestUI ui = new TestUI();
		assertEquals(CPoint.newPoint(0, 0), ui.ui.findIntersectionNearest(511, 385));
		assertEquals(CPoint.newPoint(1, 0), ui.ui.findIntersectionNearest(513, 385));
	}
}
