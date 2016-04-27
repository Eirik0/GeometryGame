package algebraic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import org.junit.Test;

public class CRationalTest {
	@Test
	public void testGcd() {
		assertEquals(BigInteger.valueOf(1), CRational.gcd(BigInteger.valueOf(12), BigInteger.valueOf(1)));
		assertEquals(BigInteger.valueOf(1), CRational.gcd(BigInteger.valueOf(12), BigInteger.valueOf(7)));
		assertEquals(BigInteger.valueOf(2), CRational.gcd(BigInteger.valueOf(12), BigInteger.valueOf(14)));
		assertEquals(BigInteger.valueOf(3), CRational.gcd(BigInteger.valueOf(12), BigInteger.valueOf(27)));
		assertEquals(BigInteger.valueOf(4), CRational.gcd(BigInteger.valueOf(12), BigInteger.valueOf(28)));
		assertEquals(BigInteger.valueOf(6), CRational.gcd(BigInteger.valueOf(12), BigInteger.valueOf(30)));
		assertEquals(BigInteger.valueOf(12), CRational.gcd(BigInteger.valueOf(12), BigInteger.valueOf(120)));
		assertEquals(BigInteger.valueOf(3), CRational.gcd(BigInteger.valueOf(-9), BigInteger.valueOf(3)));
	}

	@Test
	public void testQuotientOf_0_Over_3() {
		assertEquals(ZInteger.ZERO, CRational.quotientOf(ZInteger.ZERO, ZInteger.valueOf(3)));
	}

	@Test
	public void testQuotientOf_1_Over_Negative3() {
		CRational quotient = (CRational) CRational.quotientOf(ZInteger.ONE, ZInteger.valueOf(-3));
		assertEquals(ZInteger.NEGATIVE_ONE, quotient.numerator);
		assertEquals(ZInteger.valueOf(3), quotient.denominator);
	}

	@Test
	public void testQuotientOf_125_Over_5() {
		assertEquals(SquareRoot.of(5), CRational.quotientOf(SquareRoot.of(125), ZInteger.valueOf(5)));
	}

	@Test
	public void testQuotientOf_6Fiths_Over_3() {
		assertEquals(CRational.quotientOf(2, 5), CRational.quotientOf(CRational.quotientOf(6, 5), ZInteger.valueOf(3)));
	}

	@Test
	public void testQuotientOfSeriesAndNegative() {
		assertEquals(ZInteger.NEGATIVE_ONE.subtract(SquareRoot.of(2)), CRational.quotientOf(ZInteger.TWO.add(SquareRoot.of(8)), ZInteger.valueOf(-2)));
	}

	@Test
	public void testAddRationals() {
		assertEquals(CRational.quotientOf(1, 4), CRational.quotientOf(1, 5).add(CRational.quotientOf(1, 20)));
	}

	@Test
	public void testSubtractRationals() {
		assertEquals(CRational.quotientOf(1, 5), CRational.quotientOf(1, 4).subtract(CRational.quotientOf(1, 20)));
	}

	@Test
	public void testSignum() {
		assertEquals(-1, CRational.quotientOf(-1, 2).signum());
		assertEquals(1, CRational.quotientOf(1, 2).signum());
	}

	@Test
	public void testMultiplyRationals() {
		assertEquals(CRational.quotientOf(1, 3), CRational.quotientOf(2, 3).multiply(CRational.quotientOf(1, 2)));
	}

	@Test
	public void testDivideRationals() {
		assertEquals(CRational.quotientOf(3, 2), CRational.quotientOf(1, 2).divide(CRational.quotientOf(1, 3)));
	}

	@Test
	public void testReciprocate() {
		assertEquals(CRational.quotientOf(3, 2), CRational.quotientOf(2, 3).reciprocate());
	}

	@Test
	public void testDoubleValue() {
		assertEquals(0.5, CRational.quotientOf(1, 2).doubleValue(), 0.001);
	}

	@Test
	public void testEquals() {
		Constructible oneHalf = CRational.quotientOf(1, 2);
		assertTrue(oneHalf.equals(oneHalf));
		assertFalse(oneHalf.equals(CRational.quotientOf(3, 2)));
		assertFalse(oneHalf.equals(CRational.quotientOf(1, 3)));
		assertFalse(oneHalf.equals(null));
		assertFalse(oneHalf.equals(""));
	}

	@Test
	public void testToString() {
		assertEquals("1/2", CRational.quotientOf(1, 2).toString());
		assertEquals("(1+sqrt(2))/2", ZInteger.ONE.add(SquareRoot.of(2)).divide(ZInteger.TWO).toString());
	}
}
