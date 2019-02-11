package construction;

import java.util.HashSet;
import java.util.Set;

import construction.Construction.ConstructionState;
import geometric.CPoint;
import geometric.LineOrCircle;

public class DrawStep {
	private final LineOrCircle lineOrCircle;
	private final Set<CPoint> intersections;

	public DrawStep(LineOrCircle lineOrCircle, ConstructionState constructionState) {
		this.lineOrCircle = lineOrCircle;
		intersections = new HashSet<>();
		for (LineOrCircle stateLineOrCircle : constructionState.linesAndCircles) {
			for (CPoint point : lineOrCircle.findIntersection(stateLineOrCircle).intersections) {
				if (!constructionState.intersections.contains(point)) {
					intersections.add(point);
				}
			}
		}
	}

	public void applyTo(ConstructionState constructionState) {
		constructionState.linesAndCircles.add(lineOrCircle);
		constructionState.intersections.addAll(intersections);
	}

	public void unapply(ConstructionState constructionState) {
		constructionState.linesAndCircles.remove(lineOrCircle);
		constructionState.intersections.removeAll(intersections);
	}
}
