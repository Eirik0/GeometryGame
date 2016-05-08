package geometric;

import java.util.function.Function;

import algebraic.Constructible;
import algebraic.Pair;
import algebraic.ZInteger;

/**
 * (x - h)^2 + (y - k)^2 = r^2
 */
public class CCircle implements LineOrCircle {
	public static final Function<Pair<CPoint>, CCircle> NEW_CIRCLE_FUNCTION = points -> new CCircle(points.first, points.second);

	public final Constructible h;
	public final Constructible k;
	public final Constructible rSquared;

	public CCircle(CPoint center, CPoint radial) {
		h = center.x;
		k = center.y;
		Constructible x2_sub_x1 = radial.x.subtract(center.x);
		Constructible y2_sub_y1 = radial.y.subtract(center.y);
		rSquared = x2_sub_x1.squared().add(y2_sub_y1.squared());
	}

	@Override
	public IntersectionSet findIntersection(CLine line) {
		return line.findIntersection(this);
	}

	@Override
	public IntersectionSet findIntersection(CCircle circle) {
		Constructible h1_sub_h2 = h.subtract(circle.h);
		Constructible k2_sub_k1 = circle.k.subtract(k);
		Constructible r1Sq_sub_r2Sq = rSquared.subtract(circle.rSquared);
		if (h1_sub_h2.equals(ZInteger.ZERO)) {
			if (k2_sub_k1.equals(ZInteger.ZERO)) {
				return IntersectionSet.emptySet();
			} else {
				Constructible y = r1Sq_sub_r2Sq.add(circle.k.squared()).subtract(k.squared()).divide(ZInteger.TWO.multiply(k2_sub_k1));
				return HorizontalLine.findIntersection(y, h, k, rSquared);
			}
		} else if (k2_sub_k1.equals(ZInteger.ZERO)) {
			Constructible x = r1Sq_sub_r2Sq.add(circle.h.squared()).subtract(h.squared()).divide(ZInteger.TWO.multiply(h1_sub_h2.negate()));
			return VerticalLine.findIntersection(x, h, k, rSquared);
		} else {
			Constructible squares = r1Sq_sub_r2Sq.add(circle.h.squared()).subtract(h.squared()).add(circle.k.squared()).subtract(k.squared());
			Constructible yIntercept = squares.divide(ZInteger.TWO.multiply(k2_sub_k1));
			Constructible slope = h1_sub_h2.divide(k2_sub_k1);
			return SlopedLine.findIntersection(slope, yIntercept, h, k, rSquared);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		return prime * (prime * (prime + h.hashCode()) + k.hashCode()) + rSquared.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		CCircle other = (CCircle) obj;
		return h.equals(other.h) && k.equals(other.k) && rSquared.equals(other.rSquared);
	}

	@Override
	public String toString() {
		return "(x - " + h + ")^2 + (y - " + k + ")^2 = " + rSquared;
	}
}
