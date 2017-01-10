package algebraic;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

import algebraic.Constructible.ConstructibleType;

public class SquareRootDenestTest {
	@Test
	public void testThreePlusTwoRootTwo() {
		assertEquals(ZInteger.ONE.add(SquareRoot.of(2)), SquareRoot.of(ZInteger.valueOf(3).add(SquareRoot.of(8))));
	}

	@Test
	public void testTwentyFourMinusEightRootFive() {
		assertEquals(ZInteger.valueOf(-2).add(SquareRoot.of(4 * 5)), SquareRoot.of(ZInteger.valueOf(24).subtract(SquareRoot.of(64 * 5))));
	}

	private void testDoesNotDenest(Constructible radicand) {
		testDoesNotDenest(radicand, ZInteger.ONE, radicand);
	}

	private void testDoesNotDenest(Constructible radicand, Constructible expectedCoefficient, Constructible expectedRadicand) {
		Constructible constructible = SquareRoot.of(radicand);
		assertEquals(ConstructibleType.SQUARE_ROOT, constructible.getType());
		SquareRoot sqrt = (SquareRoot) constructible;
		assertEquals(expectedCoefficient, sqrt.coefficient);
		assertEquals(expectedRadicand, sqrt.radicand);
	}

	@Test
	public void testTwoPlusRootTwo() {
		testDoesNotDenest(ZInteger.TWO.add(SquareRoot.of(2)));
	}

	@Test
	public void testRootTwoPlusRootThree() {
		testDoesNotDenest(SquareRoot.of(2).add(SquareRoot.of(3)));
	}

	@Test
	public void testTwoMinusRootTwo() {
		testDoesNotDenest(ZInteger.TWO.subtract(SquareRoot.of(2)));
	}

	@Test
	public void testFourPlusFourRootSeven() {
		testDoesNotDenest(ZInteger.FOUR.add(SquareRoot.of(16 * 7)), ZInteger.TWO, ZInteger.ONE.add(SquareRoot.of(7)));
	}

	@Test
	public void testNegativeOnePlusRootTwo() {
		testDoesNotDenest(ZInteger.NEGATIVE_ONE.add(SquareRoot.of(2)));
	}

	@Test
	public void testFortyMinusEightRootFive() {
		testDoesNotDenest(ZInteger.valueOf(40).subtract(SquareRoot.of(64 * 5)), ZInteger.valueOf(2), ZInteger.valueOf(10).subtract(SquareRoot.of(4 * 5)));
	}

	@Test
	public void testIntegerPlusTwoRootsSquared() {
		Constructible series = ZInteger.ONE.add(SquareRoot.of(2)).add(SquareRoot.of(3));
		assertEquals(series, SquareRoot.of(series.squared()));
	}

	@Test
	public void testIntegerMinusTwoRootsSquared() {
		Constructible series = ZInteger.valueOf(10).subtract(SquareRoot.of(2)).subtract(SquareRoot.of(3));
		assertEquals(series, SquareRoot.of(series.squared()));
	}

	@Test
	public void testIntegerPlusTwoRootsSquaredDoesNotDenest() {
		testDoesNotDenest(ZInteger.TWO.add(SquareRoot.of(2)).add(SquareRoot.of(3)));
	}

	@Test
	public void testIntegerPlusThreeRootsSquared() {
		Constructible series = ZInteger.ONE.add(SquareRoot.of(2)).add(SquareRoot.of(3)).add(SquareRoot.of(5));
		assertEquals(series, SquareRoot.of(series.squared()));
	}

	@Test
	@Ignore
	public void testIntegerPlusFourRootsSquared() {
		Constructible series = ZInteger.ONE.add(SquareRoot.of(2)).add(SquareRoot.of(3)).add(SquareRoot.of(5).add(SquareRoot.of(7)));
		assertEquals(series, SquareRoot.of(series.squared()));
	}

	@Test
	@Ignore
	public void testNestedDenesting() {
		Constructible series = ZInteger.ONE.add(SquareRoot.of(2)).add(SquareRoot.of(ZInteger.ONE.add(SquareRoot.of(2)))).add(SquareRoot.of(SquareRoot.of(2).add(SquareRoot.of(3))));
		assertEquals(series, SquareRoot.of(series.squared()));
	}

	@Test
	public void testNestedDenestingDoesNotDenest() {
		testDoesNotDenest(ZInteger.valueOf(9).subtract(SquareRoot.of(9 * 5)).add(SquareRoot.of(ZInteger.valueOf(121).subtract(SquareRoot.of(52 * 52 * 5)))));
	}

	@Test
	public void testThreeRootsDoesNotDenest() {
		Constructible series = ZInteger.valueOf(16).add(SquareRoot.of(12 * 12 * 3)).add(SquareRoot.of(8 * 8 * 6)).add(SquareRoot.of(12 * 12 * 2));
		Constructible expectedRadicand = ZInteger.valueOf(4).add(SquareRoot.of(3 * 3 * 3)).add(SquareRoot.of(2 * 2 * 6)).add(SquareRoot.of(3 * 3 * 2));
		testDoesNotDenest(series, ZInteger.TWO, expectedRadicand);
	}

	@Test
	public void testDenestIfCancellationsWhenSquaring_1() { // Project Euler problem 585 3rd example
		Constructible series = ZInteger.valueOf(3).add(SquareRoot.of(6)).add(SquareRoot.of(3)).subtract(SquareRoot.of(2));
		assertEquals(series, SquareRoot.of(series.squared()));
	}

	@Test
	public void testDenestIfCancellationsWhenSquaring_2() { // Project Euler problem 585 4th example
		Constructible series = SquareRoot.of(15).add(SquareRoot.of(6)).add(SquareRoot.of(5)).subtract(SquareRoot.of(2));
		assertEquals(series, SquareRoot.of(series.squared()));
	}
}
