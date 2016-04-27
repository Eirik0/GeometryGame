package algebraic;

import java.math.BigInteger;

public class ZInteger implements Constructible {
	public static final ZInteger NEGATIVE_ONE = ZInteger.valueOf(-1);
	public static final ZInteger ZERO = ZInteger.valueOf(0);
	public static final ZInteger ONE = ZInteger.valueOf(1);
	public static final ZInteger TWO = ZInteger.valueOf(2);

	public final BigInteger value;

	public static ZInteger valueOf(long value) {
		return ZInteger.valueOf(BigInteger.valueOf(value));
	}

	public static ZInteger valueOf(BigInteger value) {
		return new ZInteger(value);
	}

	private ZInteger(BigInteger value) {
		this.value = value;
	}

	@Override
	public ConstructibleType getType() {
		return ConstructibleType.INTEGER;
	}

	@Override
	public Constructible add(Constructible addend) {
		switch (addend.getType()) {
		case INTEGER:
			return valueOf(value.add(((ZInteger) addend).value));
		default:
			return addend.add(this); // Commutativity of addition
		}
	}

	@Override
	public Constructible subtract(Constructible subtrahend) {
		switch (subtrahend.getType()) {
		case INTEGER:
			return valueOf(value.subtract(((ZInteger) subtrahend).value));
		default:
			return subtrahend.negate().add(this); // a - b = -b + a
		}
	}

	@Override
	public Constructible negate() {
		return valueOf(value.negate());
	}

	@Override
	public int signum() {
		return value.signum();
	}

	@Override
	public Constructible multiply(Constructible multiplier) {
		switch (multiplier.getType()) {
		case INTEGER:
			return valueOf(value.multiply(((ZInteger) multiplier).value));
		default:
			return multiplier.multiply(this); // Commutativity of multiplication
		}
	}

	@Override
	public Constructible divide(Constructible divisor) {
		switch (divisor.getType()) {
		case INTEGER:
			return CRational.quotientOf(this, (ZInteger) divisor);
		default:
			return divisor.reciprocate().multiply(this); // a / b = (1 / b) * a
		}
	}

	@Override
	public Constructible squared() {
		return valueOf(value.multiply(value));
	}

	@Override
	public Constructible reciprocate() {
		return CRational.quotientOf(ONE, this);
	}

	@Override
	public double doubleValue() {
		return value.doubleValue();
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		ZInteger other = (ZInteger) obj;
		return value.equals(other.value);
	}

	@Override
	public String toString() {
		return value.toString();
	}

	public static String stringMultiply(ZInteger multiplicand, String multiplier) {
		return multiplicand.equals(ONE) ? multiplier : multiplicand.equals(NEGATIVE_ONE) ? "-" + multiplier : multiplicand + multiplier;
	}
}
