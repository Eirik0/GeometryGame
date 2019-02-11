package geometric;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import algebraic.CRational;
import algebraic.ZInteger;
import geometric.CLine.LineType;

public class CLineTest {
    /** Horizontal **/
    private CLine yEquals(long y) {
        return CLine.newLine(CPoint.newPoint(0, y), CPoint.newPoint(1, y));
    }

    /** Vertical **/
    private CLine xEquals(long x) {
        return CLine.newLine(CPoint.newPoint(x, 0), CPoint.newPoint(x, 1));
    }

    /** Sloped **/
    private CLine yEquals_xPlusOne() {
        return CLine.newLine(CPoint.newPoint(0, 1), CPoint.newPoint(1, 2));
    }

    // Construction
    @Test
    public void testHorizontalLine() {
        CLine line = yEquals(1);
        assertEquals(LineType.HORIZONTAL, line.getType());
        assertEquals(ZInteger.ONE, ((HorizontalLine) line).y);
    }

    @Test
    public void testVerticalLine() {
        CLine line = xEquals(1);
        assertEquals(LineType.VERTICAL, line.getType());
        assertEquals(ZInteger.ONE, ((VerticalLine) line).x);
    }

    @Test
    public void testNewSlopedLine() {
        CLine line = yEquals_xPlusOne();
        assertEquals(LineType.SLOPED, line.getType());
        assertEquals(ZInteger.valueOf(1), ((SlopedLine) line).m);
        assertEquals(ZInteger.ONE, ((SlopedLine) line).b);
    }

    @Test
    public void testToString() {
        assertEquals("x = 1", xEquals(1).toString());
        assertEquals("y = 2", yEquals(2).toString());
        assertEquals("y = x + 1", yEquals_xPlusOne().toString());
    }

    // Intersections
    @Test
    public void testHorizontalWithHoriztonal() {
        assertEquals(0, yEquals(1).findIntersection(yEquals(2)).intersections.length);
    }

    @Test
    public void testHorizontalWithVertical() {
        IntersectionSet intersectionSet = yEquals(1).findIntersection(xEquals(2));
        assertEquals(1, intersectionSet.intersections.length);
        assertEquals(CPoint.newPoint(2, 1), intersectionSet.intersections[0]);
    }

    @Test
    public void testHorizontalWithSloped() {
        IntersectionSet intersectionSet = yEquals(3).findIntersection(yEquals_xPlusOne());
        assertEquals(1, intersectionSet.intersections.length);
        assertEquals(CPoint.newPoint(2, 3), intersectionSet.intersections[0]);
    }

    @Test
    public void testVerticalWithHoriztonal() {
        assertEquals(0, xEquals(1).findIntersection(xEquals(2)).intersections.length);
    }

    @Test
    public void testVerticalWithVertical() {
        IntersectionSet intersectionSet = xEquals(1).findIntersection(yEquals(2));
        assertEquals(1, intersectionSet.intersections.length);
        assertEquals(CPoint.newPoint(1, 2), intersectionSet.intersections[0]);
    }

    @Test
    public void testVerticalWithSloped() {
        IntersectionSet intersectionSet = xEquals(3).findIntersection(yEquals_xPlusOne());
        assertEquals(1, intersectionSet.intersections.length);
        assertEquals(CPoint.newPoint(3, 4), intersectionSet.intersections[0]);
    }

    @Test
    public void testSlopedWithHoriztonal() {
        IntersectionSet intersectionSet = yEquals_xPlusOne().findIntersection(yEquals(4));
        assertEquals(1, intersectionSet.intersections.length);
        assertEquals(CPoint.newPoint(3, 4), intersectionSet.intersections[0]);
    }

    @Test
    public void testSlopedWithVertical() {
        IntersectionSet intersectionSet = yEquals_xPlusOne().findIntersection(xEquals(4));
        assertEquals(1, intersectionSet.intersections.length);
        assertEquals(CPoint.newPoint(4, 5), intersectionSet.intersections[0]);
    }

    @Test
    public void testParallelLines() {
        CLine line1 = CLine.newLine(CPoint.newPoint(0, 1), CPoint.newPoint(1, 0));
        CLine line2 = CLine.newLine(CPoint.newPoint(0, 0), CPoint.newPoint(1, -1));
        assertEquals(0, line1.findIntersection(line2).intersections.length);
    }

    @Test
    public void testLinesIntersect() {
        CLine line1 = CLine.newLine(CPoint.newPoint(0, 1), CPoint.newPoint(1, 0));
        CLine line2 = CLine.newLine(CPoint.newPoint(0, 0), CPoint.newPoint(1, 1));
        CPoint[] intersectionSet = line1.findIntersection(line2).intersections;
        assertEquals(1, intersectionSet.length);
        assertEquals(new CPoint(CRational.quotientOf(1, 2), CRational.quotientOf(1, 2)), intersectionSet[0]);
    }

    @Test
    public void testLinesIntersectAtOrigin() {
        CLine line1 = CLine.newLine(CPoint.newPoint(1, 1), CPoint.newPoint(2, 2));
        CLine line2 = CLine.newLine(CPoint.newPoint(1, -1), CPoint.newPoint(2, -2));
        CPoint[] intersectionSet = line1.findIntersection(line2).intersections;
        assertEquals(1, intersectionSet.length);
        assertEquals(CPoint.newPoint(0, 0), intersectionSet[0]);
    }
}
