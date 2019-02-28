package gg.gui.test;

import gg.construction.Construction;
import gg.gui.ConstructionUI;
import gg.gui.UserEvent;
import gg.main.GeometryGame;

public class TestUI {
    public static final int LINE_BUTTON_X = 250;
    public static final int LINE_BUTTON_Y = 50;
    public static final int CIRCLE_BUTTON_X = 750;
    public static final int CIRCLE_BUTTON_Y = 50;

    public final Construction construction;
    public final TestImage image;
    public final ConstructionUI ui;

    public TestUI() {
        construction = new Construction();
        image = new TestImage();
        image.setSize(GeometryGame.DEFAULT_WIDTH, GeometryGame.DEFAULT_HEIGHT);
        ui = new ConstructionUI(construction, image);
    }

    public void moveLeftClickAndReleaseAt(int x, int y) {
        ui.handleEvent(UserEvent.MOUSE_MOVED, x, y);
        ui.handleEvent(UserEvent.LEFT_CLICK_PRESSED, x, y);
        ui.handleEvent(UserEvent.LEFT_CLICK_RELEASED, x, y);
    }
}