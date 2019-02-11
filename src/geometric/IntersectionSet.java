package geometric;

import java.util.Arrays;

public class IntersectionSet {
    private static IntersectionSet emptySet;

    public final CPoint[] intersections;

    public static IntersectionSet emptySet() {
        if (emptySet == null) {
            emptySet = new IntersectionSet();
        }
        return emptySet;
    }

    private IntersectionSet() {
        intersections = new CPoint[0];
    }

    public IntersectionSet(CPoint p1) {
        intersections = new CPoint[] { p1 };
    }

    public IntersectionSet(CPoint p1, CPoint p2) {
        intersections = new CPoint[] { p1, p2 };
    }

    public boolean equalsUnordered(IntersectionSet other) {
        if (intersections.length == other.intersections.length) {
            if (intersections.length == 0) {
                return true;
            } else if (intersections.length == 1) {
                return intersections[0].equals(other.intersections[0]);
            } else if (intersections[0].equals(other.intersections[0])) {
                return intersections[1].equals(other.intersections[1]);
            } else {
                return intersections[0].equals(other.intersections[1]) && intersections[1].equals(other.intersections[0]);
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return Arrays.toString(intersections);
    }
}
