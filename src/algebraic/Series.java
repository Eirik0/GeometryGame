package algebraic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Series implements Constructible {
    public final ZInteger integerPart;
    public final List<SquareRoot> rootList;

    public static Constructible constructibleValue(ZInteger integerPart, List<SquareRoot> rootList) {
        if (rootList.isEmpty()) {
            return integerPart;
        } else if (rootList.size() == 1 && integerPart.signum() == 0) {
            return rootList.get(0);
        } else {
            return new Series(integerPart, Collections.unmodifiableList(rootList));
        }
    }

    Series(ZInteger integerPart, List<SquareRoot> rootList) {
        this.integerPart = integerPart;
        this.rootList = rootList;
    }

    @Override
    public ConstructibleType getType() {
        return ConstructibleType.SERIES;
    }

    @Override
    public Constructible add(Constructible addend) {
        switch (addend.getType()) {
        case INTEGER:
            return addInteger((ZInteger) addend);
        case SQUARE_ROOT:
            return addSquareRoot((SquareRoot) addend);
        case SERIES:
            return addSeries((Series) addend);
        default:
            return addend.add(this); // Commutativity of addition
        }
    }

    private Constructible addInteger(ZInteger addend) {
        return constructibleValue((ZInteger) integerPart.add(addend), rootList);
    }

    private Constructible addSquareRoot(SquareRoot addend) {
        List<SquareRoot> sumRootList = new ArrayList<>(rootList);
        Constructible addendSum = tryAdd(sumRootList, addend);
        if (sumRootList.isEmpty()) {
            return integerPart.add(addendSum);
        } else if (addendSum.getType() == ConstructibleType.INTEGER) {
            return new Series((ZInteger) integerPart.add(addendSum), Collections.unmodifiableList(sumRootList));
        } else { // If addendSum is not an Integer then it is a SquareRoot
            sumRootList.add((SquareRoot) addendSum);
            return new Series(integerPart, Collections.unmodifiableList(sumRootList));
        }
    }

    private Constructible addSeries(Series addend) {
        ZInteger sumIntegerPart = (ZInteger) integerPart.add(addend.integerPart);
        List<SquareRoot> sumRootList = new ArrayList<>(rootList);
        List<SquareRoot> addendRootList = new ArrayList<>();
        for (SquareRoot squareRoot : addend.rootList) {
            Constructible addendSum = tryAdd(sumRootList, squareRoot);
            if (addendSum.getType() == ConstructibleType.INTEGER) {
                sumIntegerPart = (ZInteger) sumIntegerPart.add(addendSum);
            } else {
                addendRootList.add((SquareRoot) addendSum);
            }
        }
        sumRootList.addAll(addendRootList);
        return constructibleValue(sumIntegerPart, sumRootList);
    }

    @Override
    public Constructible subtract(Constructible subtrahend) {
        return add(subtrahend.negate()); // a - b = a + (-b)
    }

    @Override
    public Constructible negate() {
        List<SquareRoot> rootListNegated = new ArrayList<>();
        for (SquareRoot squareRoot : rootList) {
            rootListNegated.add((SquareRoot) squareRoot.negate());
        }
        return new Series((ZInteger) integerPart.negate(), rootListNegated);
    }

    @Override
    public int signum() {
        // a + b + c + ... - d - e - f - ... > 0
        // a + b + c + ... > d + e + f + ...
        List<SquareRoot> positiveRoots = new ArrayList<>();
        List<SquareRoot> negativeRoots = new ArrayList<>();
        for (SquareRoot squareRoot : rootList) {
            if (squareRoot.signum() > 0) {
                positiveRoots.add(squareRoot);
            } else {
                negativeRoots.add((SquareRoot) squareRoot.negate());
            }
        }
        Constructible positivePart = constructibleValue(integerPart.signum() > 0 ? integerPart : ZInteger.ZERO, positiveRoots);
        Constructible negativePart = constructibleValue(integerPart.signum() > 0 ? ZInteger.ZERO : (ZInteger) integerPart.negate(), negativeRoots);
        Interval positiveBounds = Interval.findBounds(positivePart);
        Interval negativeBounds = Interval.findBounds(negativePart);
        // a > d => [a, b] > [c, d]
        // b < c => [a, b] < [c, d]
        boolean isPositive = positiveBounds.start.compareTo(negativeBounds.end) > 0;
        boolean isNegative = positiveBounds.end.compareTo(negativeBounds.start) < 0;
        while (!isPositive && !isNegative) {
            positivePart = positivePart.squared();
            negativePart = negativePart.squared();
            positiveBounds = Interval.findBounds(positivePart);
            negativeBounds = Interval.findBounds(negativePart);
            isPositive = positiveBounds.start.compareTo(negativeBounds.end) > 0;
            isNegative = positiveBounds.end.compareTo(negativeBounds.start) < 0;
        }
        return isPositive ? 1 : -1;
    }

    @Override
    public Constructible multiply(Constructible multiplier) {
        switch (multiplier.getType()) {
        case INTEGER:
            return multiplyInteger((ZInteger) multiplier);
        case SQUARE_ROOT:
            return multiplySquareRoot((SquareRoot) multiplier);
        case SERIES:
            return multiplySeries((Series) multiplier);
        default:
            return multiplier.multiply(this); // Commutativity of multiplication
        }
    }

    private Constructible multiplyInteger(ZInteger multiplier) {
        if (multiplier.signum() == 0) {
            return ZInteger.ZERO;
        } else {
            List<SquareRoot> productRootList = new ArrayList<>();
            for (SquareRoot squareRoot : rootList) {
                productRootList.add(new SquareRoot((ZInteger) squareRoot.coefficient.multiply(multiplier), squareRoot.radicand));
            }
            return new Series((ZInteger) integerPart.multiply(multiplier), productRootList);
        }
    }

    private Constructible multiplySquareRoot(SquareRoot multiplier) {
        ZInteger productIntegerPart = ZInteger.ZERO;
        List<SquareRoot> productRootList = new ArrayList<>();
        List<Series> additionalSeries = new ArrayList<>();
        if (integerPart.signum() != 0) { // nonzero coefficient
            productRootList.add(new SquareRoot((ZInteger) integerPart.multiply(multiplier.coefficient), multiplier.radicand));
        }
        for (SquareRoot squareRoot : rootList) {
            Constructible sqrtProduct = squareRoot.multiply(multiplier);
            if (sqrtProduct.getType() == ConstructibleType.SQUARE_ROOT) {
                productRootList.add((SquareRoot) sqrtProduct);
            } else { // If the product of two square roots is not a square root, it is either an integer or a series
                if (sqrtProduct.getType() == ConstructibleType.INTEGER) {
                    productIntegerPart = (ZInteger) productIntegerPart.add(sqrtProduct);
                } else {
                    additionalSeries.add((Series) sqrtProduct);
                }
            }
        }
        Constructible product = productIntegerPart.equals(ZInteger.ZERO) && productRootList.size() == 1 ? productRootList.get(0)
                : new Series(productIntegerPart, productRootList);
        for (Series series : additionalSeries) {
            product = product.add(series);
        }
        return product;
    }

    private Constructible multiplySeries(Series multiplier) {
        // (a + b)*c = c*a + c*b
        Constructible product = multiplier.multiply(integerPart);
        for (SquareRoot squareRoot : rootList) {
            product = product.add(multiplier.multiply(squareRoot));
        }
        return product;
    }

    @Override
    public Constructible divide(Constructible divisor) {
        return multiply(divisor.reciprocate()); // a/b = a*(1/b)
    }

    @Override
    public Constructible squared() {
        return multiply(this);
    }

    @Override
    public Constructible reciprocate() {
        List<List<SquareRoot>> conjugateRootLists = new ArrayList<>();
        if (integerPart.value.signum() == 0) {
            List<SquareRoot> initialRootList = new ArrayList<>();
            initialRootList.add(rootList.get(0));
            conjugateRootLists.add(initialRootList);
            populateConjugates(conjugateRootLists, rootList.subList(1, rootList.size()));
        } else {
            populateConjugates(conjugateRootLists, rootList);
        }
        // using the first element would result in a new Series which was this
        Constructible conjugateProduct = new Series(integerPart, conjugateRootLists.get(1));
        for (int i = 2; i < conjugateRootLists.size(); ++i) {
            conjugateProduct = conjugateProduct.multiply(new Series(integerPart, conjugateRootLists.get(i)));
        }
        // 1/(a+b*sqrt(c)) = (a-b*sqrt(c))/((a+b*sqrt(c))(a=b*sqrt(c))) = (a-b*sqrt(c))/(a^2-c*b^2)
        return conjugateProduct.divide(multiply(conjugateProduct));
    }

    private void populateConjugates(List<List<SquareRoot>> conjugateRootLists, List<SquareRoot> rootList) {
        // XXX consider using an array
        if (rootList.size() == 0) {
            return;
        } else if (conjugateRootLists.size() == 0) {
            List<SquareRoot> posRootList = new ArrayList<>();
            List<SquareRoot> negRootList = new ArrayList<>();
            posRootList.add(rootList.get(0));
            negRootList.add((SquareRoot) rootList.get(0).negate());
            conjugateRootLists.add(posRootList);
            conjugateRootLists.add(negRootList);
            if (rootList.size() > 1) {
                populateConjugates(conjugateRootLists, rootList.subList(1, rootList.size()));
            }
        } else {
            List<List<SquareRoot>> copies = new ArrayList<>();
            for (List<SquareRoot> conjugateRootList : conjugateRootLists) {
                List<SquareRoot> copy = new ArrayList<>(conjugateRootList);
                conjugateRootList.add(rootList.get(0));
                copy.add((SquareRoot) rootList.get(0).negate());
                copies.add(copy);
            }
            conjugateRootLists.addAll(copies);
            populateConjugates(conjugateRootLists, rootList.subList(1, rootList.size()));
        }
    }

    @Override
    public double doubleValue() {
        double doubleValue = integerPart.doubleValue();
        for (SquareRoot squareRoot : rootList) {
            doubleValue += squareRoot.doubleValue();
        }
        return doubleValue;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        return prime * (prime + integerPart.hashCode()) + rootList.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Series other = (Series) obj;
        return integerPart.value.equals(other.integerPart.value) && rootList.size() == other.rootList.size() && rootList.containsAll(other.rootList);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        boolean nonZeroIntegerPart = integerPart.signum() != 0;
        sb.append(nonZeroIntegerPart ? integerPart.toString() : rootList.get(0));
        for (int i = nonZeroIntegerPart ? 0 : 1; i < rootList.size(); ++i) {
            if (rootList.get(i).coefficient.value.signum() == 1) {
                sb.append("+");
            }
            sb.append(rootList.get(i).toString());
        }
        return sb.toString();
    }

    /**
     * This method attempts to find a reduction when adding the addend to all the elements of rootList. If no reduction is found rootList will be unmodified and
     * addend will be returned. If a particular element added with the addend sums to be either an integer or a square root, that element will be removed from
     * rootList and the resulting sum will returned.
     *
     * @param rootList
     * @param addend
     * @return addend or root.add(addend) for some root in rootList
     */
    private static Constructible tryAdd(List<SquareRoot> rootList, SquareRoot addend) {
        Iterator<SquareRoot> rootListIterator = rootList.iterator();
        while (rootListIterator.hasNext()) {
            SquareRoot root = rootListIterator.next();
            Constructible rootSum = root.add(addend);
            if (rootSum.getType() == ConstructibleType.INTEGER || rootSum.getType() == ConstructibleType.SQUARE_ROOT) {
                rootListIterator.remove();
                return rootSum;
            }
        }
        return addend;
    }
}
