package graphics;

import java.awt.Color;

public class DrawButton implements CButton {
	private final double x0Percent;
	private final Runnable action;

	public DrawButton(double x0Percent, Runnable action) {
		this.x0Percent = x0Percent;
		this.action = action;
	}

	@Override
	public Runnable getAction() {
		return action;
	}

	@Override
	public ButtonDimension getDimension(ConstructionImage image) {
		int x0 = ConstructionUI.round(x0Percent * image.getWidth());
		int width = ConstructionUI.round(image.getWidth() / 4.0);
		int height = ConstructionUI.round(image.getHeight() / 12.0);
		return new ButtonDimension(x0, 15, width, height);
	}

	@Override
	public void drawOn(ConstructionImage image) {
		ButtonDimension dimension = getDimension(image);
		image.setColor(Color.BLACK);
		image.drawRectangle(dimension.x, dimension.y, dimension.width, dimension.height);
	}
}
