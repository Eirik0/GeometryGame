package geometric;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class IntersectionSetTest {
    @Test
    public void testEqualsUnOrdered() {
        IntersectionSet setWith_00 = new IntersectionSet(CPoint.newPoint(0, 0));
        IntersectionSet setWith_00_and_01 = new IntersectionSet(CPoint.newPoint(0, 0), CPoint.newPoint(0, 1));
        IntersectionSet setWith_01_and_00 = new IntersectionSet(CPoint.newPoint(0, 1), CPoint.newPoint(0, 0));
        IntersectionSet setWith_01_and_10 = new IntersectionSet(CPoint.newPoint(0, 1), CPoint.newPoint(1, 0));
        assertTrue(IntersectionSet.emptySet().equalsUnordered(IntersectionSet.emptySet()));
        assertFalse(setWith_00.equalsUnordered(IntersectionSet.emptySet()));
        assertTrue(setWith_00.equalsUnordered(setWith_00));
        assertTrue(setWith_00_and_01.equalsUnordered(setWith_00_and_01));
        assertTrue(setWith_01_and_00.equalsUnordered(setWith_00_and_01));
        assertFalse(setWith_01_and_10.equalsUnordered(setWith_00_and_01));
        assertFalse(setWith_00_and_01.equalsUnordered(setWith_01_and_10));
    }

    @Test
    public void testToString() {
        assertEquals("[]", IntersectionSet.emptySet().toString());
        assertEquals("[(0, 0)]", new IntersectionSet(CPoint.newPoint(0, 0)).toString());
        assertEquals("[(0, 0), (0, 1)]", new IntersectionSet(CPoint.newPoint(0, 0), CPoint.newPoint(0, 1)).toString());
    }
}
