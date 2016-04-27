package geometric;

import algebraic.Constructible;
import algebraic.SquareRoot;

/**
 * A Vertical Line<br>
 * y = n
 */
public class VerticalLine implements CLine {
	public final Constructible x;

	public VerticalLine(Constructible x) {
		this.x = x;
	}

	@Override
	public LineType getType() {
		return LineType.VERTICAL;
	}

	@Override
	public IntersectionSet findIntersection(CLine line) {
		switch (line.getType()) {
		case HORIZONTAL:
			return new IntersectionSet(new CPoint(x, ((HorizontalLine) line).y));
		case VERTICAL:
			return IntersectionSet.emptySet();
		default: // The line is sloped
			return line.findIntersection(this);
		}
	}

	@Override
	public IntersectionSet findIntersection(CCircle circle) {
		return findIntersection(x, circle.h, circle.k, circle.rSquared);
	}

	@Override
	public int hashCode() {
		return x.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		VerticalLine other = (VerticalLine) obj;
		return x.equals(other.x);
	}

	@Override
	public String toString() {
		return "x = " + x;
	}

	public static IntersectionSet findIntersection(Constructible x, Constructible h, Constructible k, Constructible rSquared) {
		Constructible determinant = rSquared.subtract(x.subtract(h).squared());
		int sign = determinant.signum();
		if (sign < 0) {
			return IntersectionSet.emptySet();
		} else if (sign == 0) {
			return new IntersectionSet(new CPoint(x, k));
		} else {
			Constructible detSqrt = SquareRoot.of(determinant);
			return new IntersectionSet(new CPoint(x, k.add(detSqrt)), new CPoint(x, k.subtract(detSqrt)));
		}
	}
}
