package gg.geometric;

import gg.algebraic.Constructible;
import gg.algebraic.SquareRoot;

/**
 * A Horizontal Line<br>
 * y = n
 */
public class HorizontalLine implements CLine {
    public final Constructible y;

    public HorizontalLine(Constructible y) {
        this.y = y;
    }

    @Override
    public LineType getType() {
        return LineType.HORIZONTAL;
    }

    @Override
    public IntersectionSet findIntersection(CLine line) {
        switch (line.getType()) {
        case HORIZONTAL:
            return IntersectionSet.emptySet();
        case VERTICAL:
            return new IntersectionSet(new CPoint(((VerticalLine) line).x, y));
        default: // The line is sloped
            return line.findIntersection(this);
        }
    }

    @Override
    public IntersectionSet findIntersection(CCircle circle) {
        return findIntersection(y, circle.h, circle.k, circle.rSquared);
    }

    @Override
    public int hashCode() {
        return y.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        HorizontalLine other = (HorizontalLine) obj;
        return y.equals(other.y);
    }

    @Override
    public String toString() {
        return "y = " + y;
    }

    public static IntersectionSet findIntersection(Constructible y, Constructible h, Constructible k, Constructible rSquared) {
        Constructible determinant = rSquared.subtract(y.subtract(k).squared());
        int sign = determinant.signum();
        if (sign < 0) {
            return IntersectionSet.emptySet();
        } else if (sign == 0) {
            return new IntersectionSet(new CPoint(h, y));
        } else {
            Constructible detSqrt = SquareRoot.of(determinant);
            return new IntersectionSet(new CPoint(h.add(detSqrt), y), new CPoint(h.subtract(detSqrt), y));
        }
    }
}
