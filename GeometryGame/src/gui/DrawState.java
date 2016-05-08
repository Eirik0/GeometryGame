package gui;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import algebraic.Pair;
import geometric.CPoint;
import geometric.LineOrCircle;

public class DrawState implements ConstructionUIState {
	private final ConstructionUI ui;
	private final Function<Pair<CPoint>, ? extends LineOrCircle> lineOrCircleFunction;

	private CPoint nearestIntersection;
	private CPoint point1;

	private Set<CPoint> possibleSecondIntersections;

	// XXX remove when nested square roots are implemented
	private final Set<String> errorMessages = new HashSet<>();

	public DrawState(ConstructionUI ui, Function<Pair<CPoint>, ? extends LineOrCircle> lineOrCircleFunction) {
		this.ui = ui;
		this.lineOrCircleFunction = lineOrCircleFunction;
	}

	@Override
	public void handleEvent(UserEvent event, int x, int y) {
		switch (event) {
		case MOUSE_MOVED:
		case MOUSE_DRAGGED:
			nearestIntersection = ui.findIntersectionNearest(x, y);
			break;
		case LEFT_CLICK_RELEASED:
			nearestIntersection = ui.findIntersectionNearest(x, y);
			if (point1 == null) {
				point1 = nearestIntersection;
				possibleSecondIntersections = ui.findAllowableSecondIntersections(point1, lineOrCircleFunction);
			} else {
				if (possibleSecondIntersections.contains(nearestIntersection)) {
					ui.addLineOrCircle(lineOrCircleFunction.apply(Pair.valueOf(point1, nearestIntersection)));
					ui.setState(new ReadyToDrawState(ui));
				}
			}
			break;
		case RIGHT_CLICK_RELEASED:
			ui.setState(new ReadyToDrawState(ui));
			break;
		default:
		}
	}

	@Override
	public void draw() {
		ui.drawLinesAndCircles();
		ui.drawIntersections();
		if (point1 == null) {
			highlightIntersection(nearestIntersection);
		} else {
			highlightIntersection(point1);
			if (possibleSecondIntersections.contains(nearestIntersection)) {
				ui.image.setColor(ConstructionColors.getPreviewLineOrCircleColor());
				try {
					ui.drawLineOrCircle(lineOrCircleFunction.apply(Pair.valueOf(point1, nearestIntersection)));
				} catch (Exception e) {
					if (errorMessages.add(e.getMessage())) {
						System.out.println("Err: " + e.getMessage());
					}
				}
			}
		}
	}

	private void highlightIntersection(CPoint intersection) {
		if (intersection != null) {
			ui.image.setColor(ConstructionColors.getIntersectionSelectionColor());
			int x = ui.transformX(intersection.x.doubleValue());
			int y = ui.transformY(intersection.y.doubleValue());
			ui.image.drawCircle(x - 5, y - 5, 10, 10);
		}
	}
}
