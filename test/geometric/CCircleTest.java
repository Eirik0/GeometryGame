package geometric;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import algebraic.SquareRoot;
import algebraic.ZInteger;

public class CCircleTest {
    /**
     * Twelve integer valued points lie on this circle.
     *
     * @param centerX
     * @param centerY
     *
     * @return a circle centered at (x, y) with radius 5
     */
    public CCircle radiusFiveCircleAt(long centerX, long centerY) {
        return new CCircle(CPoint.newPoint(centerX, centerY), CPoint.newPoint(centerX, centerY + 5));
    }

    private static void checkEqualIntersections(IntersectionSet expected, IntersectionSet actual) {
        assertTrue("Expected: " + expected + " Actual: " + actual, actual.equalsUnordered(expected));
    }

    // Construction
    @Test
    public void testUnitCircle() {
        CPoint center = new CPoint(ZInteger.ZERO, ZInteger.ZERO);
        // (cos(pi/6), sin(pi/6))
        CPoint pointOnRadius = new CPoint(SquareRoot.of(3).divide(ZInteger.TWO), ZInteger.ONE.divide(ZInteger.TWO));
        CCircle circle = new CCircle(center, pointOnRadius);
        assertEquals(ZInteger.ZERO, circle.h);
        assertEquals(ZInteger.ZERO, circle.k);
        assertEquals(ZInteger.ONE, circle.rSquared);
    }

    // Intersections
    @Test
    public void testHorizontalLine_NoPoints() {
        CCircle circle = radiusFiveCircleAt(3, 4);
        CLine line = CLine.newLine(CPoint.newPoint(0, 10), CPoint.newPoint(1, 10));
        checkEqualIntersections(IntersectionSet.emptySet(), circle.findIntersection(line));
    }

    @Test
    public void testHorizontalLine_OnePoint() {
        CCircle circle = radiusFiveCircleAt(3, 4);
        CLine line = CLine.newLine(CPoint.newPoint(0, -1), CPoint.newPoint(1, -1));
        checkEqualIntersections(new IntersectionSet(CPoint.newPoint(3, -1)), circle.findIntersection(line));
    }

    @Test
    public void testHorizontalLine_TwoPoints() {
        CCircle circle = radiusFiveCircleAt(3, 4);
        CLine line = CLine.newLine(CPoint.newPoint(0, 7), CPoint.newPoint(1, 7));
        checkEqualIntersections(new IntersectionSet(CPoint.newPoint(-1, 7), CPoint.newPoint(7, 7)), circle.findIntersection(line));
    }

    @Test
    public void testVerticalLine_NoPoints() {
        CCircle circle = radiusFiveCircleAt(3, 4);
        CLine line = CLine.newLine(CPoint.newPoint(9, 0), CPoint.newPoint(9, 1));
        checkEqualIntersections(IntersectionSet.emptySet(), circle.findIntersection(line));
    }

    @Test
    public void testVerticalLine_OnePoint() {
        CCircle circle = radiusFiveCircleAt(3, 4);
        CLine line = CLine.newLine(CPoint.newPoint(8, 0), CPoint.newPoint(8, 1));
        checkEqualIntersections(new IntersectionSet(CPoint.newPoint(8, 4)), circle.findIntersection(line));
    }

    @Test
    public void testVerticalLine_TwoPoints() {
        CCircle circle = radiusFiveCircleAt(3, 4);
        CLine line = CLine.newLine(CPoint.newPoint(6, 0), CPoint.newPoint(6, 1));
        checkEqualIntersections(new IntersectionSet(CPoint.newPoint(6, 0), CPoint.newPoint(6, 8)), circle.findIntersection(line));
    }

    @Test
    public void testSlopedLine_NoPoints() {
        CCircle circle = radiusFiveCircleAt(3, 4);
        CLine line = CLine.newLine(CPoint.newPoint(7, 0), CPoint.newPoint(8, 1));
        checkEqualIntersections(IntersectionSet.emptySet(), circle.findIntersection(line));
    }

    @Test
    public void testSlopedLine_OnePoint() {
        CCircle circle = new CCircle(CPoint.newPoint(1, 1), CPoint.newPoint(-1, -1));
        CLine line = CLine.newLine(CPoint.newPoint(2, 4), CPoint.newPoint(4, 2));
        checkEqualIntersections(new IntersectionSet(CPoint.newPoint(3, 3)), circle.findIntersection(line));
    }

    @Test
    public void testSlopedLine_TwoPoints() {
        CCircle circle = radiusFiveCircleAt(3, 4);
        CLine line = CLine.newLine(CPoint.newPoint(-3, -3), CPoint.newPoint(0, -2));
        checkEqualIntersections(new IntersectionSet(CPoint.newPoint(3, -1), CPoint.newPoint(6, 0)), circle.findIntersection(line));
    }

    @Test
    public void testCircle_NoPoints() {
        checkEqualIntersections(IntersectionSet.emptySet(), radiusFiveCircleAt(0, 0).findIntersection(radiusFiveCircleAt(10, 10)));
    }

    @Test
    public void testCircle_OnePoints() {
        checkEqualIntersections(new IntersectionSet(CPoint.newPoint(6, 8)), radiusFiveCircleAt(3, 4).findIntersection(radiusFiveCircleAt(9, 12)));
    }

    @Test
    public void testCircle_TwoPoints() {
        checkEqualIntersections(new IntersectionSet(CPoint.newPoint(6, 8), CPoint.newPoint(7, 7)),
                radiusFiveCircleAt(3, 4).findIntersection(radiusFiveCircleAt(10, 11)));
    }

    @Test
    public void testCircle_TwoPoints_Horizontal() {
        checkEqualIntersections(new IntersectionSet(CPoint.newPoint(0, 8), CPoint.newPoint(6, 8)),
                radiusFiveCircleAt(3, 4).findIntersection(radiusFiveCircleAt(3, 12)));
    }

    @Test
    public void testCircle_TwoPoints_Vertical() {
        checkEqualIntersections(new IntersectionSet(CPoint.newPoint(6, 0), CPoint.newPoint(6, 8)),
                radiusFiveCircleAt(3, 4).findIntersection(radiusFiveCircleAt(9, 4)));
    }

    @Test
    public void findIntersectionSameCenter() {
        checkEqualIntersections(IntersectionSet.emptySet(),
                radiusFiveCircleAt(3, 4).findIntersection(new CCircle(CPoint.newPoint(3, 4), CPoint.newPoint(3, 10))));
    }
}
