package graphics;

import input.UserInputBridge.UserEvent;

public class ReadyToDrawState implements ConstructionUIState {
	private final ConstructionUI ui;

	ClickPressed clickPressed;

	public ReadyToDrawState(ConstructionUI ui) {
		this.ui = ui;
	}

	@Override
	public void handleEvent(UserEvent event, int x, int y) {
		switch (event) {
		case LEFT_CLICK_PRESSED:
			clickPressed = new ClickPressed(x, y, findButtonAt(x, y));
			break;
		case MOUSE_MOVED:
			if (clickPressed != null) {
				ui.move(x - clickPressed.x, y - clickPressed.y);
				clickPressed.setXY(x, y);
			}
			break;
		case LEFT_CLICK_RELEASED:
			if (clickPressed != null && clickPressed.button != null && clickPressed.button == findButtonAt(x, y)) {
				clickPressed.button.getAction().run();
				clickPressed = null;
			}
			break;
		case SCROLL_UP:
			ui.zoom(0.9);
			break;
		case SCROLL_DOWN:
			ui.zoom(1.1);
			break;
		default:
		}
	}

	private CButton findButtonAt(int x, int y) {
		for (CButton button : ui.buttons) {
			if (button.contains(ui.image, x, y)) {
				return button;
			}
		}
		return null;
	}

	@Override
	public void draw() {
		ui.drawLinesAndCircles();
		ui.drawIntersections();
		ui.drawButtons();
	}

	private static class ClickPressed {
		public int x;
		public int y;
		public final CButton button;

		public ClickPressed(int x, int y, CButton button) {
			this.x = x;
			this.y = y;
			this.button = button;
		}

		public void setXY(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
}
