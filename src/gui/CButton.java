package gui;

public interface CButton {
    public Runnable getAction();

    public ButtonDimension getDimension(ConstructionImage image);

    public void drawOn(ConstructionImage image, boolean highlight, boolean depressed);

    public default boolean contains(ConstructionImage image, int x, int y) {
        ButtonDimension location = getDimension(image);
        return x >= location.x && x <= (location.x + location.width) && y >= location.y && y <= (location.y + location.height);
    }

    public static class ButtonDimension {
        public final int x;
        public final int y;
        public final int width;
        public final int height;

        public ButtonDimension(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
    }
}
