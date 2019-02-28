package gg.algebraic;

import java.math.BigInteger;

/**
 * The Interval (start, end) represents all values x, with start < x < end.
 */
public class Interval {
    public final BigInteger start;
    public final BigInteger end;

    public static Interval valueOf(long start, long end) {
        return new Interval(BigInteger.valueOf(start), BigInteger.valueOf(end));
    }

    public static Interval findBounds(Constructible constructible) {
        switch (constructible.getType()) {
        case INTEGER:
            BigInteger value = ((ZInteger) constructible).value;
            return new Interval(value, value);
        case SQUARE_ROOT:
            SquareRoot squareRoot = (SquareRoot) constructible;
            ZInteger coefficient = squareRoot.coefficient;
            // n = a*sqrt(b)
            // s = a*a*b
            // b = bounds(s)
            // bounds(n) = [sqrt(b.start), sqrt(b.end) + 1],
            Interval boundsSquared = findBounds(squareRoot.squared());
            BigInteger sqrtStart = SquareRoot.iSqrt(boundsSquared.start);
            BigInteger sqrtEnd = SquareRoot.iSqrt(boundsSquared.end).add(BigInteger.ONE);
            // if n < 0 [start, end] else [-end, -start]
            return coefficient.value.signum() == 1 ? new Interval(sqrtStart, sqrtEnd) : new Interval(sqrtEnd.negate(), sqrtStart.negate());
        case SERIES:
            Series series = (Series) constructible;
            BigInteger seriesStart = series.integerPart.value;
            BigInteger seriesEnd = series.integerPart.value;
            for (SquareRoot root : series.rootList) {
                Interval interval = findBounds(root);
                seriesStart = seriesStart.add(interval.start);
                seriesEnd = seriesEnd.add(interval.end);
            }
            return new Interval(seriesStart, seriesEnd);
        case RATIONAL:
        default:
            CRational rational = (CRational) constructible;
            Interval numeratorBounds = findBounds(rational.numerator);
            return new Interval(numeratorBounds.start.divide(rational.denominator.value),
                    numeratorBounds.end.divide(rational.denominator.value).add(BigInteger.ONE));
        }
    }

    private Interval(BigInteger start, BigInteger end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        return prime * (prime + end.hashCode()) + start.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Interval other = (Interval) obj;
        return start.equals(other.start) && end.equals(other.end);
    }

    @Override
    public String toString() {
        return "(" + start + ", " + end + ")";
    }
}
