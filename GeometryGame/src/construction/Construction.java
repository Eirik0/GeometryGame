package construction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import geometric.CPoint;
import geometric.LineOrCircle;

public class Construction {
	private final ConstructionState constructionState;
	private final List<DrawStep> steps;

	public Construction() {
		constructionState = new ConstructionState();
		constructionState.intersections.add(CPoint.newPoint(0, 0));
		constructionState.intersections.add(CPoint.newPoint(1, 0));
		steps = new ArrayList<>();
	}

	public Set<CPoint> getIntersections() {
		return constructionState.intersections;
	}

	public Set<LineOrCircle> getLinesAndCircles() {
		return constructionState.linesAndCircles;
	}

	public void draw(LineOrCircle lineOrCircle) {
		DrawStep step = new DrawStep(lineOrCircle, constructionState);
		step.applyTo(constructionState);
		steps.add(step);
	}

	public static class ConstructionState {
		public final Set<CPoint> intersections;
		public final Set<LineOrCircle> linesAndCircles;

		public ConstructionState() {
			intersections = new HashSet<>();
			linesAndCircles = new HashSet<>();
		}
	}
}
