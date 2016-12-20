package algebraic;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SquareRoot implements Constructible {
	public static final String SQRT_CHAR = "sqrt"; // "\u221A"

	private static final BigInteger TWO = BigInteger.valueOf(2);
	private static final BigInteger FOUR = BigInteger.valueOf(4);

	public final ZInteger coefficient;
	public final Constructible radicand;

	public static Constructible of(long l) {
		return ofInteger(ZInteger.valueOf(l));
	}

	/**
	 * @param radicand
	 * @return A constructible whose value is the square root of the radicand
	 */
	public static Constructible of(Constructible radicand) {
		switch (radicand.getType()) {
		case INTEGER:
			return ofInteger((ZInteger) radicand);
		case SQUARE_ROOT:
			return ofSquareRoot((SquareRoot) radicand);
		case SERIES:
			return ofSeries((Series) radicand);
		case RATIONAL:
		default:
			return ofRational((CRational) radicand);
		}
	}

	private static Constructible ofInteger(ZInteger radicand) {
		if (radicand.signum() == 0) {
			return ZInteger.ZERO;
		}
		Pair<BigInteger> coefficientSqrt = findSquareDivisors(radicand.value);
		BigInteger sqrt = coefficientSqrt.second;
		if (sqrt.equals(BigInteger.ONE)) {
			return ZInteger.valueOf(coefficientSqrt.first);
		} else {
			return new SquareRoot(ZInteger.valueOf(coefficientSqrt.first), ZInteger.valueOf(sqrt));
		}
	}

	private static Constructible ofSquareRoot(SquareRoot radicand) {
		Pair<BigInteger> coefficientSqrt = findSquareDivisors(radicand.coefficient.value);
		return new SquareRoot(ZInteger.valueOf(coefficientSqrt.first), new SquareRoot(ZInteger.valueOf(coefficientSqrt.second), radicand.radicand));
	}

	private static Constructible ofSeries(Series radicand) {
		if (radicand.rootList.size() == 1) {
			return ofOneRootSeries(radicand);
		} else {
			for (int i = 0; i < radicand.rootList.size() - 1; ++i) {
				Constructible denested = tryDenest(radicand, new HashSet<SquareRoot>(), new HashSet<>(radicand.rootList), 0, i);
				if (denested != null) {
					return denested;
				}
			}
		}
		return ofSeriesDoesNotDenest(radicand);
	}

	private static Constructible ofOneRootSeries(Series radicand) {
		if (radicand.integerPart.signum() < 0) {
			// (a + sqrt(c))^2 = a*2 + c + 2*a*sqrt(c)
			// a*2 + c can only be negative if c is negative
			return ofSeriesDoesNotDenest(radicand);
		} else {
			// Let x = a + b*sqrt(c)
			// Let norm(x) = a^2 - (b^2)*c
			// Let d = (a + sqrt(norm(x)))/2
			// Let e = (a - sqrt(norm(x)))/2
			// sqrt(x) = sqrt(d) + sqrt(e)
			Constructible norm = radicand.integerPart.squared().subtract(radicand.rootList.get(0).squared());
			if (norm.signum() < 0) {
				return ofSeriesDoesNotDenest(radicand);
			} else {
				Constructible sqrt_n = SquareRoot.of(norm);
				if (sqrt_n.getType() == ConstructibleType.INTEGER) {
					Constructible sqrt_d = SquareRoot.of(radicand.integerPart.add(sqrt_n).divide(ZInteger.TWO));
					Constructible sqrt_e = SquareRoot.of(radicand.integerPart.subtract(sqrt_n).divide(ZInteger.TWO));
					// if x = a - b*sqrt(c) then sqrt(x) = (sqrt(d) - sqrt(e)) or (-sqrt(d) + sqrt(e))
					// but because we are interested in the positive root it must be the former since d > e
					return radicand.rootList.get(0).coefficient.signum() > 0 ? sqrt_d.add(sqrt_e) : sqrt_d.subtract(sqrt_e);
				} else {
					return ofSeriesDoesNotDenest(radicand);
				}
			}
		}
	}

	private static Constructible ofSeriesDoesNotDenest(Series radicand) {
		BigInteger gcd = radicand.integerPart.value;
		for (SquareRoot root : radicand.rootList) {
			gcd = CRational.gcd(gcd, root.coefficient.value.abs());
		}
		Pair<BigInteger> coefficientSqrt = findSquareDivisors(gcd);
		return new SquareRoot(ZInteger.valueOf(coefficientSqrt.first), radicand.divide(ZInteger.valueOf(gcd)).multiply(ZInteger.valueOf(coefficientSqrt.second)));
	}

	private static Constructible tryDenest(Series series, Set<SquareRoot> xRoots, Set<SquareRoot> yRoots, int start, int depthRemaining) {
		for (int i = start; i < series.rootList.size(); ++i) {
			xRoots.add(series.rootList.get(i));
			yRoots.remove(series.rootList.get(i));
			if (depthRemaining == 0) {
				// Let x + y = s where s is the series
				// If we can find (x, y) such that x^2 - y^2 is sufficiently "simpler" than s, we will find a denesting
				Constructible x = Series.constructibleValue(series.integerPart, new ArrayList<>(xRoots));
				Constructible y = Series.constructibleValue(ZInteger.ZERO, new ArrayList<>(yRoots));
				Constructible n = x.squared().subtract(y.squared());
				if (n.getType() == ConstructibleType.INTEGER || n.getType() == ConstructibleType.SQUARE_ROOT || ((Series) n).rootList.size() <= series.rootList.size() / 2) {
					Constructible sqrt_n = SquareRoot.of(n);
					if (findNestedDepth(sqrt_n) <= findNestedDepth(series)) {
						return SquareRoot.of(x.add(sqrt_n).divide(ZInteger.TWO)).add(SquareRoot.of(x.subtract(sqrt_n).divide(ZInteger.TWO)));
					}
				}
			} else {
				Constructible denested = tryDenest(series, xRoots, yRoots, i + 1, depthRemaining - 1);
				if (denested != null) {
					return denested;
				}
			}
			yRoots.add(series.rootList.get(i));
			xRoots.remove(series.rootList.get(i));
		}
		return null;
	}

	private static Constructible ofRational(CRational radicand) {
		return CRational.quotientOf(SquareRoot.of(radicand.denominator.multiply(radicand.numerator)), radicand.denominator);
	}

	SquareRoot(ZInteger coefficient, Constructible radicand) {
		this.coefficient = coefficient;
		this.radicand = radicand;
	}

	@Override
	public ConstructibleType getType() {
		return ConstructibleType.SQUARE_ROOT;
	}

	@Override
	public Constructible add(Constructible addend) {
		switch (addend.getType()) {
		case INTEGER:
			ZInteger integerAddend = (ZInteger) addend;
			return integerAddend.value.signum() == 0 ? this : new Series(integerAddend, Collections.singletonList(this));
		case SQUARE_ROOT:
			// Let x = c*sqrt(a) + d*sqrt(b)
			// x^2 = c*c*a + d*d*b + 2*c*d*sqrt(ab)
			// x = sqrt(c*c*a + d*d*b + 2*c*d*sqrt(ab))
			SquareRoot addendSqrt = (SquareRoot) addend;
			Constructible sqrt_ab = SquareRoot.of(radicand.multiply(addendSqrt.radicand));
			if (findNestedDepth(sqrt_ab) <= Math.max(findNestedDepth(radicand), findNestedDepth(addendSqrt.radicand))) {
				Constructible cca_add_ddb = coefficient.squared().multiply(radicand).add(addendSqrt.coefficient.squared().multiply(addendSqrt.radicand));
				Constructible cd_add_sqrt_ab = coefficient.multiply(addendSqrt.coefficient).multiply(sqrt_ab);
				Constructible sqrt = SquareRoot.of(cca_add_ddb.add(ZInteger.TWO.multiply(cd_add_sqrt_ab)));
				// It is possible that x = -sqrt(...) in which case observe:
				// c*sqrt(a) + d*sqrt(b) = (c + d*sqrt(ab)/a)*sqrt(a)
				// so if c + d*sqrt(ab)/a < 0 then so is x
				Constructible c_plus_d_sqrt_ab_over_a = coefficient.add(addendSqrt.coefficient.multiply(sqrt_ab).divide(radicand));
				return c_plus_d_sqrt_ab_over_a.signum() == -1 ? sqrt.negate() : sqrt;
			} else {
				return new Series(ZInteger.ZERO, Arrays.asList(this, addendSqrt));
			}
		default:
			return addend.add(this); // Commutativity of addition
		}
	}

	@Override
	public Constructible subtract(Constructible subtrahend) {
		return add(subtrahend.negate()); // a - b  = a + (-b)
	}

	@Override
	public Constructible negate() {
		return new SquareRoot((ZInteger) coefficient.negate(), radicand);
	}

	@Override
	public int signum() {
		return coefficient.value.signum();
	}

	@Override
	public Constructible multiply(Constructible multiplier) {
		switch (multiplier.getType()) {
		case INTEGER:
			return multiplier.signum() == 0 ? ZInteger.ZERO : new SquareRoot((ZInteger) coefficient.multiply(multiplier), radicand);
		case SQUARE_ROOT:
			SquareRoot sqrtMultiplier = (SquareRoot) multiplier;
			return coefficient.multiply(sqrtMultiplier.coefficient).multiply(SquareRoot.of(radicand.multiply(sqrtMultiplier.radicand))); // a*sqrt(b)*c*sqrt(d) = a*c*sqrt(b*d)
		default:
			return multiplier.multiply(this); // Commutativity of multiplication
		}
	}

	@Override
	public Constructible divide(Constructible divisor) {
		switch (divisor.getType()) {
		case INTEGER:
			return CRational.quotientOf(this, (ZInteger) divisor);
		case SQUARE_ROOT:
			SquareRoot sqrtDivisor = (SquareRoot) divisor;
			return CRational.quotientOf(coefficient.multiply(SquareRoot.of(radicand.divide(sqrtDivisor.radicand))), sqrtDivisor.coefficient); // a*sqrt(b)/(c*sqrt(d)) = a*sqrt(b/d)/c
		default:
			return divisor.reciprocate().multiply(this); // a / b = (1 / b) * a
		}
	}

	@Override
	public Constructible squared() {
		return coefficient.squared().multiply(radicand);
	}

	@Override
	public Constructible reciprocate() {
		return divide(coefficient.squared().multiply(radicand)); // 1/(a*sqrt(b)) = a*sqrt(b)/(a*a*b)
	}

	@Override
	public double doubleValue() {
		double magnitude = Math.sqrt(coefficient.squared().multiply(radicand).doubleValue());
		return signum() == -1 ? -magnitude : magnitude;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		return prime * (prime + coefficient.hashCode()) + radicand.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		SquareRoot other = (SquareRoot) obj;
		return coefficient.equals(other.coefficient) && radicand.equals(other.radicand);
	}

	@Override
	public String toString() {
		return ZInteger.stringMultiply(coefficient, SQRT_CHAR + "(" + radicand.toString() + ")");
	}

	/**
	 * Finds the integer square root of n using Newton's method to find the positive root of <br>
	 * f(x) = x^2 - n<br>
	 * with initial guess (n+1)/2<br>
	 * <br>
	 * f'(x) = 2x<br>
	 * x1 = x0 - f(x0)/f'(x0) = x0 - (x0*x0 - n)/(2x0) = (x0 + n/x0)/2<br>
	 *
	 * @param n
	 * @return The integer square root of n
	 */
	public static BigInteger iSqrt(BigInteger n) {
		BigInteger x0 = n;
		BigInteger x1 = n.add(BigInteger.ONE).divide(TWO);
		do {
			x0 = x1;
			x1 = (x0.add(n.divide(x0)).divide(TWO));
		} while (x0.compareTo(x1) > 0);
		return x0;
	}

	/**
	 * Let a^2 * b = n where a is the largest integer that when squared divides n.
	 *
	 * @param n
	 * @return (a, b)
	 */
	private static Pair<BigInteger> findSquareDivisors(BigInteger n) {
		BigInteger sqrt = iSqrt(n);
		BigInteger coefficient = BigInteger.ONE;
		BigInteger divisor = TWO;
		BigInteger divisorSquared = FOUR;
		while (divisor.compareTo(sqrt) <= 0) {
			if (n.mod(divisorSquared).signum() == 0) { // if d^2 | n
				n = n.divide(divisorSquared);
				sqrt = iSqrt(n);
				coefficient = coefficient.multiply(divisor);
				divisor = TWO;
				divisorSquared = FOUR;
			} else {
				divisor = divisor.add(BigInteger.ONE);
				divisorSquared = divisorSquared.add(TWO.multiply(divisor).subtract(BigInteger.ONE)); // (x + 1)^2 = x^2 + (2(x + 1) - 1)
			}
		}
		return Pair.valueOf(coefficient, n);
	}

	/**
	 * Returns how many nested square roots are in the expression of this. For example n = 1/2 would return 0, n = sqrt(2) would return 1,
	 * n = 1 + sqrt(2 + * sqrt(2)) would return 2, etc.
	 *
	 * @param n
	 * @return the number of nested square roots required to represent n
	 */
	private static int findNestedDepth(Constructible n) {
		switch (n.getType()) {
		case INTEGER:
			return 0;
		case SQUARE_ROOT:
			return 1 + findNestedDepth(((SquareRoot) n).radicand);
		case SERIES:
		default: // We are concerned with the nested depth of radicands and multiplications radicands, which will never be a Rational
			int max = 0;
			for (SquareRoot squareRoot : ((Series) n).rootList) {
				max = Math.max(max, findNestedDepth(squareRoot));
			}
			return max;
		}
	}
}
