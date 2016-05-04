package gui;

public class DrawButton implements CButton {
	private final String text;
	private final double x0Percent;
	private final Runnable action;

	public DrawButton(String text, double x0Percent, Runnable action) {
		this.text = text;
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
	public void drawOn(ConstructionImage image, boolean highlight, boolean depressed) {
		ButtonDimension dimension = getDimension(image);
		// background
		image.setColor(ConstructionColors.getButtonBackgroundColor());
		image.fillRectangle(dimension.x, dimension.y, dimension.width, dimension.height);
		// border
		image.setColor(highlight ? ConstructionColors.getButtonHighlightColor() : ConstructionColors.getButtonForegroundColor());
		int x = highlight && depressed ? dimension.x + 1 : dimension.x;
		int y = highlight && depressed ? dimension.y + 1 : dimension.y;
		int width = highlight && depressed ? dimension.width - 2 : dimension.width;
		int height = highlight && depressed ? dimension.height - 2 : dimension.height;
		image.drawRectangle(x, y, width, height);
		// text
		int strX = ConstructionUI.round(dimension.x + (double) dimension.width / 2);
		int strY = ConstructionUI.round(dimension.y + (double) dimension.height / 2);
		image.drawCenteredString(strX, strY, text);
	}
}
