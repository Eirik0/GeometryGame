package gg.geometric;

public interface LineOrCircle {
    public IntersectionSet findIntersection(CLine line);

    public IntersectionSet findIntersection(CCircle circle);

    public default IntersectionSet findIntersection(LineOrCircle lineOrCircle) {
        return lineOrCircle instanceof CLine ? findIntersection((CLine) lineOrCircle) : findIntersection((CCircle) lineOrCircle);
    }
}
