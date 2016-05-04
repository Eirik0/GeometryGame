package main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import gui.ConstructionUI;
import gui.UserEvent;

public class ConstructionMouseAdapter extends MouseAdapter {
	private final ConstructionUI userInput;
	private final JPanel panel;

	public ConstructionMouseAdapter(ConstructionUI userInput, JPanel panel) {
		this.userInput = userInput;
		this.panel = panel;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			userInput.handleEvent(UserEvent.LEFT_CLICK_PRESSED, e.getX(), e.getY());
			panel.repaint();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			userInput.handleEvent(UserEvent.LEFT_CLICK_RELEASED, e.getX(), e.getY());
			panel.repaint();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		userInput.handleEvent(e.getWheelRotation() < 0 ? UserEvent.SCROLL_UP : UserEvent.SCROLL_DOWN, e.getX(), e.getY());
		panel.repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		userInput.handleEvent(UserEvent.MOUSE_DRAGGED, e.getX(), e.getY());
		panel.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		userInput.handleEvent(UserEvent.MOUSE_MOVED, e.getX(), e.getY());
		panel.repaint();
	}
}
