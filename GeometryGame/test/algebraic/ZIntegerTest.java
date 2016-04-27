package algebraic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ZIntegerTest {
	@Test
	public void testAddInteger() {
		assertEquals(ZInteger.valueOf(7), ZInteger.TWO.add(ZInteger.valueOf(5)));
	}

	@Test
	public void testAddRootIsSeries() {
		Series sum = (Series) ZInteger.valueOf(8).add(SquareRoot.of(2));
		assertEquals(ZInteger.valueOf(8), sum.integerPart);
		assertEquals(1, sum.rootList.size());
		assertEquals(SquareRoot.of(2), sum.rootList.get(0));
	}

	@Test
	public void testSubtractInteger() {
		assertEquals(ZInteger.TWO, ZInteger.valueOf(7).subtract(ZInteger.valueOf(5)));
	}

	@Test
	public void testSubtractRootIsSeries() {
		Series sum = (Series) ZInteger.valueOf(8).subtract(SquareRoot.of(2));
		assertEquals(ZInteger.valueOf(8), sum.integerPart);
		assertEquals(1, sum.rootList.size());
		assertEquals(SquareRoot.of(2).negate(), sum.rootList.get(0));
	}

	@Test
	public void testNegate() {
		assertEquals(ZInteger.valueOf(-2), ZInteger.TWO.negate());
	}

	@Test
	public void testSignum() {
		assertEquals(-1, ZInteger.valueOf(-5).signum());
		assertEquals(0, ZInteger.ZERO.signum());
		assertEquals(1, ZInteger.valueOf(5).signum());
	}

	@Test
	public void testMultiplyInteger() {
		assertEquals(ZInteger.valueOf(15), ZInteger.valueOf(3).multiply(ZInteger.valueOf(5)));
	}

	@Test
	public void testMultiplyRoot() {
		SquareRoot sum = (SquareRoot) ZInteger.valueOf(3).multiply(SquareRoot.of(2));
		assertEquals(ZInteger.valueOf(3), sum.coefficient);
		assertEquals(ZInteger.TWO, sum.radicand);
	}

	@Test
	public void testDivideInteger() {
		assertEquals(CRational.quotientOf(3, 5), ZInteger.valueOf(3).divide(ZInteger.valueOf(5)));
		assertEquals(CRational.quotientOf(3, 5), ZInteger.valueOf(9).divide(ZInteger.valueOf(15)));
	}

	@Test
	public void testDivideRoot() {
		SquareRoot sum = (SquareRoot) ZInteger.TWO.divide(SquareRoot.of(2));
		assertEquals(ZInteger.ONE, sum.coefficient);
		assertEquals(ZInteger.TWO, sum.radicand);
	}

	@Test
	public void testDivideRootIsRational() {
		CRational sum = (CRational) ZInteger.TWO.divide(SquareRoot.of(3));
		assertEquals(ZInteger.valueOf(3), sum.denominator);
		SquareRoot sqrt = (SquareRoot) sum.numerator;
		assertEquals(ZInteger.TWO, sqrt.coefficient);
		assertEquals(ZInteger.valueOf(3), sqrt.radicand);
	}

	@Test
	public void testReciprocate() {
		assertEquals(CRational.quotientOf(1, 3), ZInteger.valueOf(3).reciprocate());
	}

	@Test
	public void testDoubleValue() {
		assertEquals(1, ZInteger.ONE.doubleValue(), 0.001);
	}

	@Test
	public void testEquals() {
		assertTrue(ZInteger.ONE.equals(ZInteger.ONE));
		assertFalse(ZInteger.ONE.equals(null));
		assertFalse(ZInteger.ONE.equals(""));
	}

	@Test
	public void testToString() {
		assertEquals("1", ZInteger.ONE.toString());
	}
}
