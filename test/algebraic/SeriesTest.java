package algebraic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

public class SeriesTest {
	public static void assertContainsAll(List<Constructible> expected, List<SquareRoot> actual) {
		ArrayList<Constructible> expectedCopy = new ArrayList<>(expected);
		List<SquareRoot> actualCopy = new ArrayList<>(actual);
		Iterator<Constructible> expectedIterator = expectedCopy.iterator();
		while (expectedIterator.hasNext()) {
			SquareRoot sqrt = (SquareRoot) expectedIterator.next();
			if (actualCopy.remove(sqrt)) {
				expectedIterator.remove();
			}
		}
		assertTrue("Missing: " + Arrays.toString(expectedCopy.toArray()) + " Unexpected: " + Arrays.toString(actualCopy.toArray()), expectedCopy.isEmpty() && actualCopy.isEmpty());
	}

	@Test
	public void testOneAddRootTwo() {
		Series sum = (Series) ZInteger.ONE.add(SquareRoot.of(2));
		assertEquals(ZInteger.ONE, sum.integerPart);
		assertContainsAll(Arrays.asList(SquareRoot.of(2)), sum.rootList);
	}

	// Add
	@Test
	public void testAddInteger() {
		Series sum = (Series) ZInteger.ONE.add(SquareRoot.of(2)).add(ZInteger.ONE);
		assertEquals(ZInteger.TWO, sum.integerPart);
		assertContainsAll(Arrays.asList(SquareRoot.of(2)), sum.rootList);
	}

	@Test
	public void testAddIntegerIsRoot() {
		assertEquals(SquareRoot.of(2), ZInteger.ONE.add(SquareRoot.of(2)).add(ZInteger.NEGATIVE_ONE));
	}

	@Test
	public void testAddIntegerIsSeries() {
		Series sum = (Series) ZInteger.ONE.add(SquareRoot.of(2)).add(SquareRoot.of(3)).add(ZInteger.NEGATIVE_ONE);
		assertEquals(ZInteger.ZERO, sum.integerPart);
		assertContainsAll(Arrays.asList(SquareRoot.of(2), SquareRoot.of(3)), sum.rootList);
	}

	@Test
	public void testAddRootReduces() {
		Series sum = (Series) ZInteger.ONE.add(SquareRoot.of(2)).add(SquareRoot.of(2));
		assertEquals(ZInteger.ONE, sum.integerPart);
		assertContainsAll(Arrays.asList(SquareRoot.of(8)), sum.rootList);
	}

	@Test
	public void testAddSeries() {
		Series sum = (Series) ZInteger.ONE.add(SquareRoot.of(2)).add(SquareRoot.of(2).add(SquareRoot.of(3)));
		assertEquals(ZInteger.ONE, sum.integerPart);
		assertContainsAll(Arrays.asList(SquareRoot.of(8), SquareRoot.of(3)), sum.rootList);
	}

	@Test
	public void testAddRational() {
		CRational sum = (CRational) ZInteger.ONE.add(SquareRoot.of(2)).add(CRational.quotientOf(1, 2));
		Series sumNumerator = (Series) sum.numerator;
		assertEquals(ZInteger.valueOf(3), sumNumerator.integerPart);
		assertContainsAll(Arrays.asList(SquareRoot.of(8)), sumNumerator.rootList);
		assertEquals(ZInteger.TWO, sum.denominator);
	}

	// Subtract
	@Test
	public void testSubtractInteger() {
		Series diff = (Series) ZInteger.ONE.add(SquareRoot.of(2)).subtract(ZInteger.TWO);
		assertEquals(ZInteger.NEGATIVE_ONE, diff.integerPart);
		assertContainsAll(Arrays.asList(SquareRoot.of(2)), diff.rootList);
	}

	@Test
	public void testSubtractIntegerIsRoot() {
		assertEquals(SquareRoot.of(2), ZInteger.ONE.add(SquareRoot.of(2)).subtract(ZInteger.ONE));
	}

	@Test
	public void testSubtractRoot() {
		Series diff = (Series) ZInteger.ONE.add(SquareRoot.of(2)).subtract(SquareRoot.of(3));
		assertEquals(ZInteger.ONE, diff.integerPart);
		assertContainsAll(Arrays.asList(SquareRoot.of(2), SquareRoot.of(3).negate()), diff.rootList);
	}

	@Test
	public void testSubtractRootTwo() {
		Series diff = (Series) ZInteger.ONE.add(SquareRoot.of(2)).add(SquareRoot.of(3)).subtract(SquareRoot.of(3));
		assertEquals(ZInteger.ONE, diff.integerPart);
		assertContainsAll(Arrays.asList(SquareRoot.of(2)), diff.rootList);
	}

	@Test
	public void testSubtractRootIsInteger() {
		assertEquals(ZInteger.ONE, ZInteger.ONE.add(SquareRoot.of(2)).subtract(SquareRoot.of(2)));
	}

	@Test
	public void testSubtractRootReduces() {
		Series diff = (Series) ZInteger.ONE.add(SquareRoot.of(8)).subtract(SquareRoot.of(2));
		assertEquals(ZInteger.ONE, diff.integerPart);
		assertContainsAll(Arrays.asList(SquareRoot.of(2)), diff.rootList);
	}

	@Test
	public void testSubtractSeries() {
		Series diff = (Series) ZInteger.ONE.add(SquareRoot.of(2)).subtract(SquareRoot.of(2).add(SquareRoot.of(3)));
		assertEquals(ZInteger.ONE, diff.integerPart);
		assertContainsAll(Arrays.asList(SquareRoot.of(3).negate()), diff.rootList);
	}

	@Test
	public void testSubtractSeriesIsInteger() {
		assertEquals(ZInteger.ONE, ZInteger.ONE.add(SquareRoot.of(2)).add(SquareRoot.of(3)).subtract(SquareRoot.of(2).add(SquareRoot.of(3))));
	}

	@Test
	public void testSubtractSeriesIsRoot() {
		assertEquals(SquareRoot.of(2), ZInteger.ONE.add(SquareRoot.of(2)).add(SquareRoot.of(3)).subtract(ZInteger.ONE.add(SquareRoot.of(3))));
	}

	// Negation and Sign
	@Test
	public void testNegate() {
		Series diff = (Series) ZInteger.ONE.add(SquareRoot.of(2)).add(SquareRoot.of(3)).add(SquareRoot.of(6)).negate();
		assertEquals(ZInteger.NEGATIVE_ONE, diff.integerPart);
		assertContainsAll(Arrays.asList(SquareRoot.of(2).negate(), SquareRoot.of(3).negate(), SquareRoot.of(6).negate()), diff.rootList);
	}

	@Test
	public void testSignumPositive() {
		assertEquals(1, ZInteger.NEGATIVE_ONE.add(SquareRoot.of(5)).signum());
	}

	@Test
	public void testSignumNegative() {
		assertEquals(-1, ZInteger.ONE.subtract(SquareRoot.of(5)).signum());
	}

	@Test
	public void testSignumPositive_TwoRoots() {
		assertEquals(1, ZInteger.ONE.add(SquareRoot.of(2)).subtract(SquareRoot.of(3)).signum());
	}

	@Test
	public void testSignumNegative_TwoRoots() {
		assertEquals(-1, ZInteger.valueOf(3).subtract(SquareRoot.of(2)).subtract(SquareRoot.of(3)).signum());
	}

	@Test
	public void testSignum_SixRootsSmallSum() {
		// sqrt(3)+sqrt(5)+sqrt(11)-sqrt(2)-sqrt(7)-sqrt(10) = 0.0625...
		assertEquals(1, SquareRoot.of(3).add(SquareRoot.of(5)).add(SquareRoot.of(11)).subtract(SquareRoot.of(2)).subtract(SquareRoot.of(7)).subtract(SquareRoot.of(10)).signum());
	}

	// Multiply
	@Test
	public void testMultiplyInteger() {
		Series product = (Series) ZInteger.ONE.add(SquareRoot.of(2)).multiply(ZInteger.TWO);
		assertEquals(ZInteger.TWO, product.integerPart);
		assertContainsAll(Arrays.asList(SquareRoot.of(2).multiply(ZInteger.TWO)), product.rootList);
	}

	@Test
	public void testMultiplyZeroIsZero() {
		assertEquals(ZInteger.ZERO, ZInteger.ONE.add(SquareRoot.of(2)).multiply(ZInteger.ZERO));
	}

	@Test
	public void testMultiplyRoot() {
		Constructible series = ZInteger.ONE.add(SquareRoot.of(2)).add(SquareRoot.of(3));
		Series product = (Series) series.multiply(SquareRoot.of(2));
		assertEquals(ZInteger.TWO, product.integerPart);
		assertContainsAll(Arrays.asList(SquareRoot.of(2), SquareRoot.of(6)), product.rootList);
	}

	@Test
	public void testMultiplyRootInSeries() {
		assertEquals(ZInteger.TWO.add(SquareRoot.of(6)), SquareRoot.of(2).add(SquareRoot.of(3)).multiply(SquareRoot.of(2)));
	}

	@Test
	public void testMultiplySeriesIsSeries() {
		Series product = (Series) ZInteger.ONE.add(SquareRoot.of(2)).multiply(SquareRoot.of(2).add(SquareRoot.of(3)));
		assertEquals(ZInteger.TWO, product.integerPart);
		assertContainsAll(Arrays.asList(SquareRoot.of(2), SquareRoot.of(3), SquareRoot.of(6)), product.rootList);
	}

	// Divide
	@Test
	public void testDivideIntegerIsSeries() {
		Series quotient = (Series) ZInteger.TWO.add(SquareRoot.of(8)).divide(ZInteger.TWO);
		assertEquals(ZInteger.ONE, quotient.integerPart);
		assertContainsAll(Arrays.asList(SquareRoot.of(2)), quotient.rootList);
	}

	@Test
	public void testDivideIntegerIsRationalSeries() {
		CRational quotient = (CRational) ZInteger.TWO.add(SquareRoot.of(3)).divide(ZInteger.TWO);
		Series numerator = (Series) quotient.numerator;
		assertEquals(ZInteger.TWO, numerator.integerPart);
		assertContainsAll(Arrays.asList(SquareRoot.of(3)), numerator.rootList);
		assertEquals(ZInteger.TWO, quotient.denominator);
	}

	@Test
	public void testDivideRootIsSeries() {
		Series quotient = (Series) ZInteger.TWO.add(SquareRoot.of(8)).divide(SquareRoot.of(2));
		assertEquals(ZInteger.TWO, quotient.integerPart);
		assertContainsAll(Arrays.asList(SquareRoot.of(2)), quotient.rootList);
	}

	@Test
	public void testDivideRootIsRationalSeries() {
		CRational quotient = (CRational) ZInteger.ONE.add(SquareRoot.of(2)).divide(SquareRoot.of(3));
		Series numerator = (Series) quotient.numerator;
		assertEquals(ZInteger.ZERO, numerator.integerPart);
		assertContainsAll(Arrays.asList(SquareRoot.of(3), SquareRoot.of(6)), numerator.rootList);
		assertEquals(ZInteger.valueOf(3), quotient.denominator);
	}

	@Test
	public void testDivideSeriesIsInteger() {
		assertEquals(ZInteger.TWO, ZInteger.TWO.add(SquareRoot.of(8)).divide(ZInteger.ONE.add(SquareRoot.of(2))));
	}

	@Test
	public void testDivideSeriesIsRoot() {
		assertEquals(SquareRoot.of(2), ZInteger.TWO.add(SquareRoot.of(2)).divide(ZInteger.ONE.add(SquareRoot.of(2))));
	}

	@Test
	public void testDivideSeriesIsSeries() {
		Series quotient = (Series) ZInteger.ONE.add(SquareRoot.of(2)).add(SquareRoot.of(3)).add(SquareRoot.of(6)).divide(ZInteger.ONE.add(SquareRoot.of(2)));
		assertEquals(ZInteger.ONE, quotient.integerPart);
		assertContainsAll(Arrays.asList(SquareRoot.of(3)), quotient.rootList);
	}

	@Test
	public void testDivideSeriesIsRationalSeries() {
		CRational quotient = (CRational) ZInteger.ONE.add(SquareRoot.of(2)).add(SquareRoot.of(3)).add(SquareRoot.of(6)).divide(ZInteger.TWO.add(SquareRoot.of(8)));
		Series numerator = (Series) quotient.numerator;
		assertEquals(ZInteger.ONE, numerator.integerPart);
		assertContainsAll(Arrays.asList(SquareRoot.of(3)), numerator.rootList);
		assertEquals(ZInteger.TWO, quotient.denominator);
	}

	// Reciprocate
	@Test
	public void testReciprocate_OnePlusRootTwo() {
		Series reciprocal = (Series) ZInteger.ONE.add(SquareRoot.of(2)).reciprocate();
		assertEquals(ZInteger.NEGATIVE_ONE, reciprocal.integerPart);
		assertContainsAll(Collections.singletonList(SquareRoot.of(2)), reciprocal.rootList);
	}

	@Test
	public void testReciprocate_RootTwoPlusRootThree() {
		Series reciprocal = (Series) SquareRoot.of(2).add(SquareRoot.of(3)).reciprocate();
		// 1/(sqrt(2)+sqrt(3)) = sqrt(3)-sqrt(2)
		assertEquals(SquareRoot.of(3).subtract(SquareRoot.of(2)), reciprocal);
	}

	@Test
	public void testReciprocate_OnePlusRootTwoPlusRootThree() {
		CRational reciprocal = (CRational) ZInteger.ONE.add(SquareRoot.of(2)).add(SquareRoot.of(3)).reciprocate();
		// 1/(1+sqrt(2)+sqrt(3)) = (2+sqrt(2)-sqrt(6))/4
		assertEquals(ZInteger.TWO.add(SquareRoot.of(2)).subtract(SquareRoot.of(6)).divide(ZInteger.FOUR), reciprocal);
	}

	@Test
	public void testReciprocate_NestedRoot() {
		CRational reciprocal = (CRational) ZInteger.ONE.add(SquareRoot.of(ZInteger.ONE.add(SquareRoot.of(2)))).reciprocate();
		// 1/(1+sqrt(1+sqrt(2))) = (sqrt(2+2*sqrt(2))-sqrt(2))/2
		assertEquals(SquareRoot.of(ZInteger.TWO.add(SquareRoot.of(8))).subtract(SquareRoot.of(2)).divide(ZInteger.TWO), reciprocal);
	}

	@Test
	public void testMultiplyNested() {
		Constructible nested = SquareRoot.of(ZInteger.ONE.add(SquareRoot.of(2)));
		assertEquals(nested.add(ZInteger.ONE).add(SquareRoot.of(2)), ZInteger.ONE.add(nested).multiply(nested));
	}

	@Test
	public void testDoubleValue() {
		assertEquals(1.881, SquareRoot.of(5).add(SquareRoot.of(7).subtract(ZInteger.valueOf(3))).doubleValue(), 0.001);
	}

	@Test
	public void testEquals() {
		Constructible onePlusRootTwo = ZInteger.ONE.add(SquareRoot.of(2));
		assertTrue(onePlusRootTwo.equals(onePlusRootTwo));
		assertTrue(onePlusRootTwo.equals(ZInteger.ONE.add(SquareRoot.of(2))));
		assertFalse(onePlusRootTwo.equals(ZInteger.ONE.add(SquareRoot.of(3))));
		assertFalse(onePlusRootTwo.equals(ZInteger.TWO.add(SquareRoot.of(2))));
		assertFalse(onePlusRootTwo.equals(ZInteger.ONE.add(SquareRoot.of(2)).add(SquareRoot.of(3))));
		assertFalse(onePlusRootTwo.equals(null));
		assertFalse(onePlusRootTwo.equals(""));
	}

	@Test
	public void testToString() {
		assertEquals("1+" + SquareRoot.SQRT_CHAR + "(2)-" + SquareRoot.SQRT_CHAR + "(3)", ZInteger.ONE.add(SquareRoot.of(2)).subtract(SquareRoot.of(3)).toString());
		assertEquals(SquareRoot.SQRT_CHAR + "(2)+" + SquareRoot.SQRT_CHAR + "(3)", SquareRoot.of(2).add(SquareRoot.of(3)).toString());
	}
}
