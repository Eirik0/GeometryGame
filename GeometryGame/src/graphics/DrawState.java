package graphics;

import java.awt.Color;
import java.util.function.Consumer;

import algebraic.Pair;
import geometric.CPoint;
import input.UserInputBridge.UserEvent;

public class DrawState implements ConstructionUIState {
	private final ConstructionUI ui;
	private final Consumer<Pair<CPoint>> pointsConsumer;

	private CPoint nearestIntersection;
	private CPoint point1;

	public DrawState(ConstructionUI ui, Consumer<Pair<CPoint>> pointsConsumer) {
		this.ui = ui;
		this.pointsConsumer = pointsConsumer;
	}

	@Override
	public void handleEvent(UserEvent event, int x, int y) {
		switch (event) {
		case MOUSE_MOVED:
			nearestIntersection = ui.findIntersectionNearest(x, y);
			break;
		case LEFT_CLICK_RELEASED:
			CPoint point = ui.findIntersectionNearest(x, y);
			if (point1 == null) {
				point1 = point;
			} else {
				pointsConsumer.accept(Pair.valueOf(point1, point));
				ui.setState(new ReadyToDrawState(ui));
			}
			break;
		default:
		}
	}

	@Override
	public void draw() {
		ui.drawLinesAndCircles();
		ui.drawIntersections();
		highlightIntersection(nearestIntersection);
		highlightIntersection(point1);
	}

	private void highlightIntersection(CPoint intersection) {
		if (intersection != null) {
			ui.image.setColor(Color.RED);
			int x = ui.transformX(intersection.x.doubleValue());
			int y = ui.transformY(intersection.y.doubleValue());
			ui.image.drawCircle(x - 5, y - 5, 10, 10);
		}
	}
}
