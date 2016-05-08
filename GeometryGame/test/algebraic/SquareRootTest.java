package algebraic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

public class SquareRootTest {
	@Test
	public void testSquareRoot_of_4() {
		ZInteger sqrt = (ZInteger) SquareRoot.of(4);
		assertEquals(ZInteger.TWO, sqrt);
	}

	@Test
	public void testSquareRoot_of_2() {
		SquareRoot sqrt = (SquareRoot) SquareRoot.of(2);
		assertEquals(ZInteger.ONE, sqrt.coefficient);
		assertEquals(ZInteger.TWO, sqrt.radicand);
	}

	@Test
	public void testSquareRoot_of_8() {
		SquareRoot sqrt = (SquareRoot) SquareRoot.of(8);
		assertEquals(ZInteger.TWO, sqrt.coefficient);
		assertEquals(ZInteger.TWO, sqrt.radicand);
	}

	@Test
	public void testSquareRoot_of_1800() {
		SquareRoot sqrt = (SquareRoot) SquareRoot.of(1800);
		assertEquals(ZInteger.valueOf(30), sqrt.coefficient);
		assertEquals(ZInteger.TWO, sqrt.radicand);
	}

	@Test
	public void testSquareRoot_of_4Thirds() {
		CRational rational = (CRational) SquareRoot.of(CRational.quotientOf(4, 3));
		SquareRoot sqrt = (SquareRoot) rational.numerator;
		assertEquals(ZInteger.TWO, sqrt.coefficient);
		assertEquals(ZInteger.valueOf(3), sqrt.radicand);
		assertEquals(ZInteger.valueOf(3), rational.denominator);
	}

	@Test
	public void testSquareRoot_of_Root8() {
		SquareRoot sqrt = (SquareRoot) SquareRoot.of(SquareRoot.of(8));
		assertEquals(ZInteger.valueOf(1), sqrt.coefficient);
		assertEquals(SquareRoot.of(8), sqrt.radicand);
	}

	@Test
	public void testSquareRoot_of_Root32() {
		SquareRoot sqrt = (SquareRoot) SquareRoot.of(SquareRoot.of(32));
		assertEquals(ZInteger.TWO, sqrt.coefficient);
		assertEquals(SquareRoot.of(2), sqrt.radicand);
	}

	@Test
	public void testSquareRoot_of_3Plus2Root2() {
		Series sqrt = (Series) SquareRoot.of(ZInteger.valueOf(3).add(SquareRoot.of(8)));
		assertEquals(ZInteger.ONE, sqrt.integerPart);
		SeriesTest.assertContainsAll(Collections.singletonList(SquareRoot.of(2)), sqrt.rootList);
	}

	// Add
	@Test
	public void testAddSquareRootReduces() {
		SquareRoot sum = (SquareRoot) SquareRoot.of(2).add(SquareRoot.of(8));
		assertEquals(ZInteger.valueOf(3), sum.coefficient);
		assertEquals(ZInteger.TWO, sum.radicand);
	}

	@Test
	public void testAddRootIsSeries() {
		Series sum = (Series) SquareRoot.of(2).add(SquareRoot.of(3));
		assertEquals(ZInteger.ZERO, sum.integerPart);
		assertEquals(2, sum.rootList.size());
		SeriesTest.assertContainsAll(Arrays.asList(SquareRoot.of(2), SquareRoot.of(3)), sum.rootList);
	}

	@Test
	public void testAddSeries() {
		Series sum = (Series) SquareRoot.of(3).add(ZInteger.ONE.add(SquareRoot.of(2)));
		assertEquals(ZInteger.ONE, sum.integerPart);
		SeriesTest.assertContainsAll(Arrays.asList(SquareRoot.of(2), SquareRoot.of(3)), sum.rootList);
	}

	@Test
	public void testAddNestedRootsReduce() {
		SquareRoot augend = (SquareRoot) SquareRoot.of(ZInteger.valueOf(3).add(SquareRoot.of(2)));
		SquareRoot addend = (SquareRoot) SquareRoot.of(ZInteger.valueOf(3).subtract(SquareRoot.of(2)));
		SquareRoot sum = (SquareRoot) augend.add(addend);
		assertEquals(ZInteger.ONE, sum.coefficient);
		assertEquals(ZInteger.valueOf(6).add(SquareRoot.of(28)), sum.radicand);
	}

	// Subtract
	@Test
	public void testSubtractSquareRootReduces() {
		SquareRoot sum = (SquareRoot) SquareRoot.of(2).subtract(SquareRoot.of(18));
		assertEquals(ZInteger.valueOf(-2), sum.coefficient);
		assertEquals(ZInteger.TWO, sum.radicand);
	}

	@Test
	public void testSubtractRootIsZero() {
		assertEquals(ZInteger.ZERO, SquareRoot.of(2).subtract(SquareRoot.of(2)));
	}

	@Test
	public void testSubtractRootIsSeries() {
		Series sum = (Series) SquareRoot.of(2).subtract(SquareRoot.of(3));
		assertEquals(ZInteger.ZERO, sum.integerPart);
		assertEquals(2, sum.rootList.size());
		SeriesTest.assertContainsAll(Arrays.asList(SquareRoot.of(2), SquareRoot.of(3).negate()), sum.rootList);
	}

	@Test
	public void testSignumPositive() {
		assertEquals(1, SquareRoot.of(2).signum());
	}

	@Test
	public void testSignumNegative() {
		assertEquals(-1, SquareRoot.of(2).negate().signum());
	}

	// Multiply
	@Test
	public void testMultiplySquareRootIsInteger() {
		assertEquals(ZInteger.TWO, SquareRoot.of(2).multiply(SquareRoot.of(2)));
	}

	@Test
	public void testMultiplySquareRootIsRoot() {
		assertEquals(SquareRoot.of(6), SquareRoot.of(2).multiply(SquareRoot.of(3)));
	}

	@Test
	public void testMultiplyZeroIsZero() {
		assertEquals(ZInteger.ZERO, SquareRoot.of(2).multiply(ZInteger.ZERO));
	}

	// Divide
	@Test
	public void testDivideSquareRootIsRoot() {
		assertEquals(SquareRoot.of(2), SquareRoot.of(6).divide(SquareRoot.of(3)));
	}

	@Test
	public void testDivideSquareRootIsInteger() {
		assertEquals(ZInteger.TWO, SquareRoot.of(12).divide(SquareRoot.of(3)));
	}

	@Test
	public void testDivideRationalIsRoot() {
		assertEquals(SquareRoot.of(8), SquareRoot.of(2).divide(CRational.quotientOf(1, 2)));
	}

	@Test
	public void testReciprocate() {
		assertEquals(CRational.quotientOf(SquareRoot.of(7), ZInteger.valueOf(7)), SquareRoot.of(7).reciprocate());
	}

	@Test
	public void testDoubleValue() {
		assertEquals(1.414, SquareRoot.of(2).doubleValue(), 0.001);
		assertEquals(2.828, SquareRoot.of(8).doubleValue(), 0.001);
		assertEquals(-2.828, SquareRoot.of(8).negate().doubleValue(), 0.001);
	}

	@Test
	public void testEquals() {
		SquareRoot rootTwo = (SquareRoot) SquareRoot.of(2);
		assertTrue(rootTwo.equals(rootTwo));
		assertFalse(rootTwo.equals(SquareRoot.of(3)));
		assertFalse(rootTwo.equals(SquareRoot.of(8)));
		assertFalse(rootTwo.equals(null));
		assertFalse(rootTwo.equals(""));
	}

	@Test
	public void testToString() {
		assertEquals("sqrt(2)", SquareRoot.of(2).toString());
		assertEquals("-sqrt(2)", SquareRoot.of(2).negate().toString());
		assertEquals("2sqrt(2)", SquareRoot.of(8).toString());
	}
}
