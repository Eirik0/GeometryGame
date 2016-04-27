package graphics;

import input.UserInputBridge.UserEvent;

public interface ConstructionUIState {
	public void handleEvent(UserEvent event, int x, int y);

	public void draw();
}
