package gg.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import gg.geometric.CCircle;
import gg.geometric.CLine;
import gg.geometric.CPoint;
import gg.gui.ConstructionColors;
import gg.gui.UserEvent;
import gg.gui.test.TestPoint;
import gg.gui.test.TestUI;
import gg.gui.test.TestQuadruple.TestCircle;
import gg.gui.test.TestQuadruple.TestLine;

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
        assertTrue(testUI.image.circles.contains(new TestCircle(336, 379, 10, 10, ConstructionColors.getIntersectionSelectionColor())));
        // no buttons
        assertEquals(0, testUI.image.rectangles.size());
    }

    @Test
    public void testDraw_DrawPreviewLine() {
        TestUI testUI = new TestUI();
        testUI.moveLeftClickAndReleaseAt(TestUI.LINE_BUTTON_X, TestUI.LINE_BUTTON_Y);
        testUI.moveLeftClickAndReleaseAt(341, 384);
        testUI.ui.handleEvent(UserEvent.MOUSE_MOVED, 683, 384); // move to 2nd point
        testUI.ui.draw();
        assertEquals(1, testUI.image.circles.size());
        assertTrue(testUI.image.circles.contains(new TestCircle(336, 379, 10, 10, ConstructionColors.getIntersectionSelectionColor())));
        assertTrue(testUI.image.lines.contains(new TestLine(0, 384, 1024, 384, ConstructionColors.getPreviewLineOrCircleColor())));
    }

    @Test
    public void testNoPreviewAtSamePoint() {
        TestUI testUI = new TestUI();
        testUI.moveLeftClickAndReleaseAt(TestUI.LINE_BUTTON_X, TestUI.LINE_BUTTON_Y);
        testUI.moveLeftClickAndReleaseAt(341, 384);
        testUI.ui.handleEvent(UserEvent.MOUSE_MOVED, 341, 341); // move to 2nd point
        testUI.ui.draw();
        assertEquals(1, testUI.image.circles.size());
        assertTrue(testUI.image.circles.contains(new TestCircle(336, 379, 10, 10, ConstructionColors.getIntersectionSelectionColor())));
        assertTrue(testUI.image.lines.isEmpty());
    }

    @Test
    public void testPreventAddingInvalidLine() {
        TestUI testUI = new TestUI();
        testUI.moveLeftClickAndReleaseAt(TestUI.LINE_BUTTON_X, TestUI.LINE_BUTTON_Y);
        testUI.moveLeftClickAndReleaseAt(341, 384);
        testUI.moveLeftClickAndReleaseAt(341, 384);
        testUI.ui.draw();
        assertEquals(1, testUI.image.circles.size()); // this means we are still in DrawState
        assertTrue(testUI.image.circles.contains(new TestCircle(336, 379, 10, 10, ConstructionColors.getIntersectionSelectionColor())));
    }

    @Test
    public void testCancelDrawing() {
        TestUI testUI = new TestUI();
        testUI.moveLeftClickAndReleaseAt(TestUI.LINE_BUTTON_X, TestUI.LINE_BUTTON_Y);
        testUI.moveLeftClickAndReleaseAt(341, 384);
        testUI.ui.handleEvent(UserEvent.RIGHT_CLICK_RELEASED, 341, 384);
        testUI.ui.draw();
        assertTrue(testUI.image.circles.isEmpty()); // this means we are in ReadyToDrawState
    }

    @Test
    public void testZoomIn() {
        TestUI testUI = new TestUI();
        testUI.moveLeftClickAndReleaseAt(TestUI.LINE_BUTTON_X, TestUI.LINE_BUTTON_Y);
        testUI.ui.handleEvent(UserEvent.SCROLL_UP, 500, 500);
        testUI.ui.draw();
        assertEquals(2, testUI.image.points.size());
        assertTrue(testUI.image.points.contains(new TestPoint(322, 384, ConstructionColors.getIntersectionColor())));
        assertTrue(testUI.image.points.contains(new TestPoint(702, 384, ConstructionColors.getIntersectionColor())));
    }

    @Test
    public void testZoomOut() {
        TestUI testUI = new TestUI();
        testUI.moveLeftClickAndReleaseAt(TestUI.LINE_BUTTON_X, TestUI.LINE_BUTTON_Y);
        testUI.ui.handleEvent(UserEvent.SCROLL_DOWN, 500, 500);
        testUI.ui.draw();
        assertEquals(2, testUI.image.points.size());
        assertTrue(testUI.image.points.contains(new TestPoint(357, 384, ConstructionColors.getIntersectionColor())));
        assertTrue(testUI.image.points.contains(new TestPoint(667, 384, ConstructionColors.getIntersectionColor())));
    }
}
