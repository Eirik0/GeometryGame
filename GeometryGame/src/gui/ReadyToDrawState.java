package gui;

import java.util.ArrayList;
import java.util.List;

import geometric.CCircle;
import geometric.CLine;

public class ReadyToDrawState implements ConstructionUIState {
	private final ConstructionUI ui;

	private final UserState userState;

	public final List<CButton> buttons;

	public ReadyToDrawState(ConstructionUI ui) {
		this.ui = ui;
		userState = new UserState();
		buttons = new ArrayList<>();
		// Draw Line button
		buttons.add(new DrawButton("LINE", 1.0 / 8, () -> ui.setState(new DrawState(ui, points -> ui.addLineOrCircle(CLine.newLine(points.first, points.second))))));
		// Draw Circle button
		buttons.add(new DrawButton("CIRCLE", 5.0 / 8, () -> ui.setState(new DrawState(ui, points -> ui.addLineOrCircle(new CCircle(points.first, points.second))))));
	}

	@Override
	public void handleEvent(UserEvent event, int x, int y) {
		switch (event) {
		case LEFT_CLICK_PRESSED:
			userState.setState(x, y, findButtonAt(x, y));
			break;
		case MOUSE_DRAGGED:
			if (userState.buttonPressed == null) {
				ui.move(x - userState.x, y - userState.y);
			}
			userState.setState(x, y, userState.buttonPressed);
			break;
		case MOUSE_MOVED:
			userState.setState(x, y, userState.buttonPressed);
			break;
		case LEFT_CLICK_RELEASED:
			if (userState.buttonPressed != null && userState.buttonPressed == findButtonAt(x, y)) {
				userState.buttonPressed.getAction().run();
			}
			userState.buttonPressed = null;
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
		for (CButton button : buttons) {
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
		drawButtons();
	}

	public void drawButtons() {
		for (CButton button : buttons) {
			button.drawOn(ui.image, button.contains(ui.image, userState.x, userState.y), button == userState.buttonPressed);
		}
	}

	private static class UserState {
		public int x = -1;
		public int y = -1;
		public CButton buttonPressed = null;

		public void setState(int x, int y, CButton buttonPressed) {
			this.x = x;
			this.y = y;
			this.buttonPressed = buttonPressed;
		}
	}
}
