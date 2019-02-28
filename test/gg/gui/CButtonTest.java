package gg.gui;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import gg.gui.ConstructionColors;
import gg.gui.UserEvent;
import gg.gui.test.TestCenteredString;
import gg.gui.test.TestUI;
import gg.gui.test.TestQuadruple.TestFilledRectangle;
import gg.gui.test.TestQuadruple.TestRectangle;

public class CButtonTest {
    @Test
    public void testDraw_DrawLineButton() {
        TestUI ui = new TestUI();
        ui.ui.draw();
        assertTrue(ui.image.filledRectangles.contains(new TestFilledRectangle(128, 15, 256, 64, ConstructionColors.getButtonBackgroundColor())));
        assertTrue(ui.image.rectangles.contains(new TestRectangle(128, 15, 256, 64, ConstructionColors.getButtonForegroundColor())));
        assertTrue(ui.image.centeredStrings.contains(new TestCenteredString(256, 47, "LINE", ConstructionColors.getButtonForegroundColor())));
    }

    @Test
    public void testDraw_DrawCircleButton() {
        TestUI ui = new TestUI();
        ui.ui.draw();
        assertTrue(ui.image.filledRectangles.contains(new TestFilledRectangle(640, 15, 256, 64, ConstructionColors.getButtonBackgroundColor())));
        assertTrue(ui.image.rectangles.contains(new TestRectangle(640, 15, 256, 64, ConstructionColors.getButtonForegroundColor())));
        assertTrue(ui.image.centeredStrings.contains(new TestCenteredString(768, 47, "CIRCLE", ConstructionColors.getButtonForegroundColor())));
    }

    @Test
    public void testHighlightButton() {
        TestUI ui = new TestUI();
        ui.ui.handleEvent(UserEvent.MOUSE_MOVED, TestUI.LINE_BUTTON_X, TestUI.LINE_BUTTON_Y);
        ui.ui.draw();
        assertTrue(ui.image.filledRectangles.contains(new TestFilledRectangle(128, 15, 256, 64, ConstructionColors.getButtonBackgroundColor())));
        assertTrue(ui.image.rectangles.contains(new TestRectangle(128, 15, 256, 64, ConstructionColors.getButtonHighlightColor())));
        assertTrue(ui.image.centeredStrings.contains(new TestCenteredString(256, 47, "LINE", ConstructionColors.getButtonHighlightColor())));
    }

    @Test
    public void testButtonDepressed() {
        TestUI ui = new TestUI();
        ui.ui.handleEvent(UserEvent.LEFT_CLICK_PRESSED, TestUI.LINE_BUTTON_X, TestUI.LINE_BUTTON_Y);
        ui.ui.draw();
        assertTrue(ui.image.filledRectangles.contains(new TestFilledRectangle(128, 15, 256, 64, ConstructionColors.getButtonBackgroundColor())));
        assertTrue(ui.image.rectangles.contains(new TestRectangle(129, 16, 254, 62, ConstructionColors.getButtonHighlightColor())));
        assertTrue(ui.image.centeredStrings.contains(new TestCenteredString(256, 47, "LINE", ConstructionColors.getButtonHighlightColor())));
    }

    @Test
    public void testMoveOffDepressedButton() {
        TestUI ui = new TestUI();
        ui.ui.handleEvent(UserEvent.LEFT_CLICK_PRESSED, TestUI.LINE_BUTTON_X, TestUI.LINE_BUTTON_Y);
        ui.ui.handleEvent(UserEvent.MOUSE_DRAGGED, 0, 0);
        ui.ui.draw();
        assertTrue(ui.image.filledRectangles.contains(new TestFilledRectangle(128, 15, 256, 64, ConstructionColors.getButtonBackgroundColor())));
        assertTrue(ui.image.rectangles.contains(new TestRectangle(128, 15, 256, 64, ConstructionColors.getButtonForegroundColor())));
        assertTrue(ui.image.centeredStrings.contains(new TestCenteredString(256, 47, "LINE", ConstructionColors.getButtonForegroundColor())));
    }
}
