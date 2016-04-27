package graphics;

import construction.Construction;

public class TestUI {
	public final Construction construction;
	public final TestImage image;
	public final ConstructionUI ui;

	public TestUI() {
		construction = new Construction();
		image = new TestImage();
		image.setSize(ConstructionUI.DEFAULT_IMAGE_WIDTH, ConstructionUI.DEFAULT_IMAGE_HEIGHT);
		ui = new ConstructionUI(construction, image);
	}
}