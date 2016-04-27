package input;

import graphics.ConstructionUI;

public class UserInputBridge {

	public static enum UserEvent {
		LEFT_CLICK_PRESSED, LEFT_CLICK_RELEASED, MOUSE_MOVED, SCROLL_UP, SCROLL_DOWN;
	}

	private int x;
	private int y;

	private final ConstructionUI constructionUI;

	public UserInputBridge(ConstructionUI constructionUI) {
		this.constructionUI = constructionUI;
	}

	public void moveMouseTo(int x, int y) {
		this.x = x;
		this.y = y;
		handleEvent(UserEvent.MOUSE_MOVED);
	}

	public void handleEvent(UserEvent event) {
		constructionUI.handleEvent(event, x, y);
	}
}
