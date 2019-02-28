package gg.algebraic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import gg.algebraic.CRational;
import gg.algebraic.Interval;
import gg.algebraic.SquareRoot;
import gg.algebraic.ZInteger;

public class IntervalTest {
    @Test
    public void testFindBounds_Integer() {
        assertEquals(Interval.valueOf(100, 100), Interval.findBounds(ZInteger.valueOf(100)));
    }

    @Test
    public void testFindBounds_SquareRoot_of_2() {
        assertEquals(Interval.valueOf(1, 2), Interval.findBounds(SquareRoot.of(2)));
    }

    @Test
    public void testFindBounds_SquareRoot_of_5() {
        assertEquals(Interval.valueOf(2, 3), Interval.findBounds(SquareRoot.of(5)));
    }

    @Test
    public void testFindBounds_SquareRoot_of_8() {
        assertEquals(Interval.valueOf(2, 3), Interval.findBounds(SquareRoot.of(8)));
    }

    @Test
    public void testFindBounds__Negative_SquareRoot_of_8() {
        assertEquals(Interval.valueOf(-3, -2), Interval.findBounds(SquareRoot.of(8).negate()));
    }

    @Test
    public void testFindBounds_ManySquareRoots() {
        for (int n = 0; n < 1000; ++n) {
            double sqrt = Math.sqrt(n);
            long longSqrt = (long) sqrt;
            Interval sqrtInterval = Interval.findBounds(SquareRoot.of(n));
            assertEquals(Integer.toString(n), Interval.valueOf(longSqrt, sqrt == longSqrt ? longSqrt : longSqrt + 1), sqrtInterval);
        }
    }

    @Test
    public void findBounds_Series() {
        // bounds(sqrt(2)) = [1, 2]
        // bounds(-sqrt(3)) = [-2, -1]
        // bounds(1 + sqrt(2) - sqrt(3)) = [0, 2]
        assertEquals(Interval.valueOf(0, 2), Interval.findBounds(ZInteger.ONE.add(SquareRoot.of(2)).subtract(SquareRoot.of(3))));
    }

    @Test
    public void findBounds_Rational() {
        assertEquals(Interval.valueOf(3, 4), Interval.findBounds(CRational.quotientOf(157, 50)));
    }

    @Test
    public void testEquals() {
        Interval oneToThree = Interval.valueOf(1, 3);
        assertTrue(oneToThree.equals(oneToThree));
        assertTrue(oneToThree.equals(Interval.valueOf(1, 3)));
        assertFalse(oneToThree.equals(Interval.valueOf(0, 2)));
        assertFalse(oneToThree.equals(Interval.valueOf(1, 2)));
        assertFalse(oneToThree.equals(null));
        assertFalse(oneToThree.equals(""));
    }

    @Test
    public void testToString() {
        assertEquals("(1, 3)", Interval.valueOf(1, 3).toString());
    }
}
