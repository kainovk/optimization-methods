package src.util;

import java.util.Arrays;
import java.util.Comparator;

public class Point {

    private final double[] coordinates;

    public Point(double... coords) {
        this.coordinates = coords;
    }

    public int size() {
        return coordinates.length;
    }

    public double get(int i) {
        return coordinates[i];
    }

    public double[] getCopy() {
        return coordinates.clone();
    }

    public void set(int i, double value) {
        coordinates[i] = value;
    }

    @Override
    public String toString() {
        return Arrays.toString(coordinates);
    }

    public static class PointComparator implements Comparator<Point> {
        Function function;

        public PointComparator(Function function) {
            this.function = function;
        }

        @Override
        public int compare(Point o1, Point o2) {
            double diff = function.apply(o1) - function.apply(o2);
            int lessOrEqual = (diff < 0) ? -1 : 0;
            return (diff > 0) ? 1 : lessOrEqual;
        }
    }
}
