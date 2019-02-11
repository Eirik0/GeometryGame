package geometric;

import java.util.function.Function;

import algebraic.Constructible;
import algebraic.Pair;

/**
 * A Line, constructible geometrically by the straightedge.<br>
 * <br>
 * y = mx + b is not sufficient to represent all lines, as vertical lines have infinite slope. CLine has three implementations: HorizontalLine, VerticalLine,
 * and SlopedLine.
 */
public interface CLine extends LineOrCircle {
    public static enum LineType {
        HORIZONTAL, VERTICAL, SLOPED;
    }

    public static final Function<Pair<CPoint>, CLine> NEW_LINE_FUNCTION = points -> newLine(points.first, points.second);

    /**
     * Given two points, this method finds and returns the appropriate type of line.
     *
     * @param p1
     * @param p2
     * @return a horizontal, vertical, or sloped line
     */
    public static CLine newLine(CPoint p1, CPoint p2) {
        if (p1.y.equals(p2.y)) {
            return new HorizontalLine(p1.y);
        } else if (p1.x.equals(p2.x)) {
            return new VerticalLine(p1.x);
        } else {
            Constructible x2_sub_x1 = p2.x.subtract(p1.x);
            Constructible y2_sub_y1 = p2.y.subtract(p1.y);
            Constructible m = y2_sub_y1.divide(x2_sub_x1);
            Constructible b = p1.y.subtract(p1.x.multiply(m));
            return new SlopedLine(m, b);
        }
    }

    /**
     * @return LineType.HORIZONTAL,<br>
     *         LineType.VERTICAL, or<br>
     *         LineType.SLOPED
     */
    public abstract LineType getType();

    /**
     * Finds the intersection of this line and a circle, unless they do not intersect, in which case empty set is returned.
     *
     * @param circle
     * @return an IntersectionSet of size 0, 1, or 2
     */
    @Override
    public IntersectionSet findIntersection(CCircle circle);
}
