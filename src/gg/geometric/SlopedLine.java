package gg.geometric;

import gg.algebraic.Constructible;
import gg.algebraic.SquareRoot;
import gg.algebraic.ZInteger;
import gg.algebraic.Constructible.ConstructibleType;

/**
 * A Sloped Line<br>
 * y = mx + b
 */
public class SlopedLine implements CLine {
    public final Constructible m;
    public final Constructible b;

    public SlopedLine(Constructible m, Constructible b) {
        this.m = m;
        this.b = b;
    }

    @Override
    public LineType getType() {
        return LineType.SLOPED;
    }

    @Override
    public IntersectionSet findIntersection(CLine line) {
        switch (line.getType()) {
        case HORIZONTAL:
            // line.y = mx + b
            // x = (line.y - b)/m
            Constructible lineY = ((HorizontalLine) line).y;
            return new IntersectionSet(new CPoint(lineY.subtract(b).divide(m), lineY));
        case VERTICAL:
            // y = m*line.x + b
            Constructible lineX = ((VerticalLine) line).x;
            return new IntersectionSet(new CPoint(lineX, m.multiply(lineX).add(b)));
        case SLOPED:
        default:
            SlopedLine slopedLine = (SlopedLine) line;
            if (m.equals(slopedLine.m)) {
                return IntersectionSet.emptySet();
            } else {
                Constructible m1_sub_m2 = m.subtract(slopedLine.m);
                Constructible b2_sub_b1 = slopedLine.b.subtract(b);
                Constructible x = b2_sub_b1.divide(m1_sub_m2);
                Constructible y = (m.multiply(b2_sub_b1).add(b.multiply(m1_sub_m2))).divide(m1_sub_m2);
                return new IntersectionSet(new CPoint(x, y));
            }
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        return prime * (prime + b.hashCode()) + m.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        SlopedLine other = (SlopedLine) obj;
        return b.equals(other.b) && m.equals(other.m);
    }

    @Override
    public IntersectionSet findIntersection(CCircle circle) {
        return findIntersection(m, b, circle.h, circle.k, circle.rSquared);
    }

    @Override
    public String toString() {
        if (m.getType().equals(ConstructibleType.INTEGER)) {
            return "y = " + ZInteger.stringMultiply((ZInteger) m, "x") + " + " + b;
        }
        return "y = " + m + "x" + " + " + b;
    }

    public static IntersectionSet findIntersection(Constructible m, Constructible b, Constructible h, Constructible k, Constructible rSquared) {
        Constructible b_sub_k = b.subtract(k);
        Constructible mSquared_plus_one = m.squared().add(ZInteger.ONE);
        Constructible determinant = rSquared.multiply(mSquared_plus_one).subtract(m.multiply(h).add(b_sub_k).squared());
        int sign = determinant.signum();
        if (sign < 0) {
            return IntersectionSet.emptySet();
        } else if (sign == 0) {
            Constructible x = h.subtract(m.multiply(b_sub_k)).divide(mSquared_plus_one);
            return new IntersectionSet(new CPoint(x, m.multiply(x).add(b)));
        } else {
            Constructible detSqrt = SquareRoot.of(determinant);
            Constructible quadratic_b = h.subtract(m.multiply(b_sub_k));
            Constructible x1 = quadratic_b.add(detSqrt).divide(mSquared_plus_one);
            Constructible x2 = quadratic_b.subtract(detSqrt).divide(mSquared_plus_one);
            return new IntersectionSet(new CPoint(x1, m.multiply(x1).add(b)), new CPoint(x2, m.multiply(x2).add(b)));
        }
    }
}
