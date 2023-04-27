package src.util;

public class RosenbrockFunction implements Function {
    @Override
    public double apply(Point point) {
        double x = point.get(0);
        double y = point.get(1);
        return (1 - x) * (1 - x) + 100 * (y - x * x) * (y - x * x);
    }
}
