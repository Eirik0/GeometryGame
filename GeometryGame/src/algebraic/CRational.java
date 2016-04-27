package algebraic;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class CRational implements Constructible {
	public final Constructible numerator;
	public final ZInteger denominator;

	public static Constructible quotientOf(long numerator, long denominator) {
		return quotientOfInteger(ZInteger.valueOf(numerator), ZInteger.valueOf(denominator));
	}

	public static Constructible quotientOf(Constructible dividend, ZInteger divisor) {
		switch (dividend.getType()) {
		case INTEGER:
			return quotientOfInteger((ZInteger) dividend, divisor);
		case SQUARE_ROOT:
			return quotientOfSquareRoot((SquareRoot) dividend, divisor);
		case SERIES:
			return quotientOfSeries((Series) dividend, divisor);
		case RATIONAL:
		default:
			return quotientOfRational((CRational) dividend, divisor);
		}
	}

	private static Constructible quotientOfInteger(ZInteger dividend, ZInteger divisor) {
		BigInteger numerator = dividend.value;
		if (numerator.signum() == 0) { // 0/n = 0
			return ZInteger.ZERO;
		} else {
			BigInteger denominator = divisor.value;
			if (denominator.signum() < 0) {
				denominator = denominator.abs();
				numerator = numerator.negate();
			}
			BigInteger gcd = gcd(numerator, denominator);
			ZInteger reducedNumerator = ZInteger.valueOf(numerator.divide(gcd));
			BigInteger reducedDenominator = denominator.divide(gcd);
			if (reducedDenominator.equals(BigInteger.ONE)) {
				return reducedNumerator;
			} else {
				return new CRational(reducedNumerator, ZInteger.valueOf(reducedDenominator));
			}
		}
	}

	private static Constructible quotientOfSquareRoot(SquareRoot dividend, ZInteger divisor) {
		Constructible quotient = quotientOf(dividend.coefficient, divisor);
		switch (quotient.getType()) {
		case INTEGER:
			return new SquareRoot((ZInteger) quotient, dividend.radicand);
		case RATIONAL:
		default: // If the quotient is not an integer it is a rational number
			CRational rational = (CRational) quotient;
			return new CRational(new SquareRoot((ZInteger) rational.numerator, dividend.radicand), rational.denominator);
		}
	}

	private static Constructible quotientOfSeries(Series dividend, ZInteger divisor) {
		BigInteger divisorInt = divisor.value;
		if (divisorInt.signum() == -1) {
			dividend = (Series) dividend.negate();
			divisorInt = divisorInt.negate();
		}
		boolean nonZeroIntegerPart = dividend.integerPart.value.signum() != 0;
		BigInteger gcd = nonZeroIntegerPart ? gcd(dividend.integerPart.value, divisorInt) : gcd(dividend.rootList.get(0).coefficient.value, divisorInt);
		for (int i = nonZeroIntegerPart ? 0 : 1; !gcd.equals(BigInteger.ONE) && i < dividend.rootList.size(); ++i) {
			gcd = gcd(dividend.rootList.get(i).coefficient.value, gcd);
		}
		List<SquareRoot> quotientRootList;
		if (gcd.equals(BigInteger.ONE)) {
			quotientRootList = dividend.rootList;
		} else {
			quotientRootList = new ArrayList<>();
			for (SquareRoot squareRoot : dividend.rootList) {
				quotientRootList.add(new SquareRoot(ZInteger.valueOf(squareRoot.coefficient.value.divide(gcd)), squareRoot.radicand));
			}
		}
		BigInteger reducedDenominator = divisorInt.divide(gcd);
		Series quotientSeries = new Series(ZInteger.valueOf(dividend.integerPart.value.divide(gcd)), quotientRootList);
		if (reducedDenominator.equals(BigInteger.ONE)) {
			return quotientSeries;
		} else {
			return new CRational(quotientSeries, ZInteger.valueOf(reducedDenominator));
		}
	}

	private static Constructible quotientOfRational(CRational dividend, ZInteger divisor) {
		return quotientOf(dividend.numerator, (ZInteger) dividend.denominator.multiply(divisor));// (a/b)/c = a/(b*c)
	}

	private CRational(Constructible numerator, ZInteger denominator) {
		this.numerator = numerator;
		this.denominator = denominator;
	}

	@Override
	public ConstructibleType getType() {
		return ConstructibleType.RATIONAL;
	}

	@Override
	public Constructible add(Constructible addend) {
		return numerator.add(addend.multiply(denominator)).divide(denominator); // a/b + c = (a + bc)/b
	}

	@Override
	public Constructible subtract(Constructible subtrahend) {
		return numerator.subtract(subtrahend.multiply(denominator)).divide(denominator); // a/b - c = (a - bc)/b
	}

	@Override
	public Constructible negate() {
		return new CRational(numerator.negate(), denominator);
	}

	@Override
	public int signum() {
		return numerator.signum();
	}

	@Override
	public Constructible multiply(Constructible multiplier) {
		return quotientOf(numerator.multiply(multiplier), denominator);
	}

	@Override
	public Constructible divide(Constructible divisor) {
		return multiply(divisor.reciprocate()); // (a/b)/c = (a/b)*(1/c)
	}

	@Override
	public Constructible squared() {
		return quotientOf(numerator.squared(), (ZInteger) denominator.squared());
	}

	@Override
	public Constructible reciprocate() {
		return numerator.reciprocate().multiply(denominator); // 1/(a/b) = (1/a)*b
	}

	@Override
	public double doubleValue() {
		return numerator.doubleValue() / denominator.doubleValue();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		return prime * (prime + numerator.hashCode()) + numerator.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		CRational other = (CRational) obj;
		return numerator.equals(other.numerator) && denominator.equals(other.denominator);
	}

	@Override
	public String toString() {
		return (numerator.getType() == ConstructibleType.SERIES ? "(" + numerator.toString() + ")" : numerator.toString()) + "/" + denominator.toString();
	}

	/**
	 * Euclid's algorithm modified to use modulus rather than subtraction.
	 *
	 * @param a
	 * @param b
	 * @return the greatest common divisor of a and b
	 */
	public static BigInteger gcd(BigInteger a, BigInteger b) {
		// XXX consider BigInteger.gcd
		BigInteger r = a.mod(b);
		while (!r.equals(BigInteger.ZERO)) {
			a = b;
			b = r;
			r = a.mod(b);
		}
		return b;
	}
}
