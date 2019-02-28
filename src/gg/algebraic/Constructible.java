package gg.algebraic;

/**
 * Starting with 1, every number which can be formed using a finite number of any combination of +, -, *, /, and sqrt()
 */
public interface Constructible {
    /**
     * One type for each class which implements Constructible<br>
     * These are: Rational, Square Root
     */
    public static enum ConstructibleType {
        INTEGER, SQUARE_ROOT, SERIES, RATIONAL;
    }

    /**
     * @return the type of Constructible
     */
    public ConstructibleType getType();

    /**
     * this = augend
     *
     * @param addend
     * @return sum = augend + addend
     */
    public Constructible add(Constructible addend);

    /**
     * this = minuend
     *
     * @param subtrahend
     * @return difference = minuend - subtrahend
     */
    public Constructible subtract(Constructible subtrahend);

    /**
     * Returns the additive inverse of this.
     *
     * @return -1 * this
     */
    public Constructible negate();

    /**
     * The sign function.
     *
     * @return -1, 0, or 1 depending on whether the number is positive, zero, or negative respectively.
     */
    public int signum();

    /**
     * this = multiplicand
     *
     * @param multiplier
     * @return product = multiplicand * multiplier
     */
    public Constructible multiply(Constructible multiplier);

    /**
     * this = dividend
     *
     * @param divisor
     * @return quotient = dividend / divisor
     */
    public Constructible divide(Constructible divisor);

    /**
     * this * this
     *
     * @return the square of this
     */
    public Constructible squared();

    /**
     * Returns the multiplicative inverse of this.
     *
     * @return 1 / this
     */
    public Constructible reciprocate();

    /**
     * Calculates and returns the value of this as a double
     *
     * @return an approximation of this as a double
     */
    public double doubleValue();

    @Override
    public int hashCode();

    @Override
    public boolean equals(Object obj);
}
