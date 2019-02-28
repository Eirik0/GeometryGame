package gg.geometric;

import gg.algebraic.Constructible;
import gg.algebraic.ZInteger;

/**
 * (x, y)
 */
public class CPoint {
    public final Constructible x;
    public final Constructible y;

    public static CPoint newPoint(long x, long y) {
        return new CPoint(ZInteger.valueOf(x), ZInteger.valueOf(y));
    }

    public CPoint(Constructible x, Constructible y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        return prime * (prime + x.hashCode()) + y.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CPoint other = (CPoint) obj;
        return x.equals(other.x) && y.equals(other.y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
