package construction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import algebraic.CRational;
import algebraic.Constructible;
import algebraic.SquareRoot;
import algebraic.ZInteger;
import geometric.CCircle;
import geometric.CLine;
import geometric.CPoint;
import geometric.LineOrCircle;

public class ConstructionTest {
	@Test
	public void testNewConstruction() {
		Construction construction = new Construction();
		Set<CPoint> intersections = construction.getIntersections();
		assertEquals(2, intersections.size());
		assertTrue(intersections.contains(CPoint.newPoint(0, 0)));
		assertTrue(intersections.contains(CPoint.newPoint(1, 0)));
	}

	@Test
	public void testDrawCircle() {
		Construction construction = new Construction();
		construction.draw(new CCircle(CPoint.newPoint(0, 0), CPoint.newPoint(1, 0)));
		Set<LineOrCircle> linesAndCircles = construction.getLinesAndCircles();
		assertEquals(1, linesAndCircles.size());
		assertTrue(linesAndCircles.contains(new CCircle(CPoint.newPoint(0, 0), CPoint.newPoint(1, 0))));
	}

	@Test
	public void testDrawLine() {
		Construction construction = new Construction();
		construction.draw(CLine.newLine(CPoint.newPoint(0, 0), CPoint.newPoint(1, 0)));
		Set<LineOrCircle> linesAndCircles = construction.getLinesAndCircles();
		assertEquals(1, linesAndCircles.size());
		assertTrue(linesAndCircles.contains(CLine.newLine(CPoint.newPoint(0, 0), CPoint.newPoint(1, 0))));
	}

	@Test
	public void testDrawTwoCircle() {
		Construction construction = new Construction();
		construction.draw(new CCircle(CPoint.newPoint(0, 0), CPoint.newPoint(1, 0)));
		construction.draw(new CCircle(CPoint.newPoint(1, 0), CPoint.newPoint(0, 0)));
		Set<LineOrCircle> linesAndCircles = construction.getLinesAndCircles();
		assertEquals(2, linesAndCircles.size());
		assertTrue(linesAndCircles.contains(new CCircle(CPoint.newPoint(0, 0), CPoint.newPoint(1, 0))));
		assertTrue(linesAndCircles.contains(new CCircle(CPoint.newPoint(1, 0), CPoint.newPoint(0, 0))));
		Set<CPoint> intersections = construction.getIntersections();
		assertEquals(4, intersections.size());
		assertTrue(intersections.contains(new CPoint(CRational.quotientOf(1, 2), SquareRoot.of(3).divide(ZInteger.TWO))));
		assertTrue(intersections.contains(new CPoint(CRational.quotientOf(1, 2), SquareRoot.of(3).divide(ZInteger.TWO).negate())));
	}

	@Test
	public void testBisectLine() {
		Construction construction = new Construction();
		construction.draw(CLine.newLine(CPoint.newPoint(0, 0), CPoint.newPoint(1, 0)));
		construction.draw(new CCircle(CPoint.newPoint(0, 0), CPoint.newPoint(1, 0)));
		assertTrue(construction.getIntersections().contains(CPoint.newPoint(-1, 0)));
		construction.draw(new CCircle(CPoint.newPoint(1, 0), CPoint.newPoint(0, 0)));
		assertTrue(construction.getIntersections().contains(CPoint.newPoint(2, 0)));
		Constructible cos60 = CRational.quotientOf(1, 2);
		Constructible sin60 = SquareRoot.of(3).divide(ZInteger.TWO);
		construction.draw(CLine.newLine(new CPoint(cos60, sin60), new CPoint(cos60, sin60.negate())));
		assertEquals(7, construction.getIntersections().size());
		assertTrue(construction.getIntersections().contains(new CPoint(CRational.quotientOf(1, 2), ZInteger.ZERO)));
	}
}
