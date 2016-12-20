package algebraic;

import static org.junit.Assert.assertEquals;

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

	private void testDoesNotDenest(Constructible radicand, Constructible expectedCoefficient, Constructible expectedRadicant) {
		Constructible constructible = SquareRoot.of(radicand);
		assertEquals(ConstructibleType.SQUARE_ROOT, constructible.getType());
		SquareRoot sqrt = (SquareRoot) constructible;
		assertEquals(expectedCoefficient, sqrt.coefficient);
		assertEquals(expectedRadicant, sqrt.radicand);
	}

	@Test
	public void testTwoPlusRootTwo() {
		testDoesNotDenest(ZInteger.TWO.add(SquareRoot.of(2)));
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
	public void testIntegerPlusTwoRootsSquaredDoesNotDenest() {
		Constructible series = ZInteger.TWO.add(SquareRoot.of(2)).add(SquareRoot.of(3));
		SquareRoot sqrt = (SquareRoot) SquareRoot.of(series);
		assertEquals(ZInteger.ONE, sqrt.coefficient);
		assertEquals(series, sqrt.radicand);
	}

	@Test
	public void testIntegerPlusThreeRootsSquared() {
		Constructible series = ZInteger.ONE.add(SquareRoot.of(2)).add(SquareRoot.of(3)).add(SquareRoot.of(5));
		assertEquals(series, SquareRoot.of(series.squared()));
	}
}
