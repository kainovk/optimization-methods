package src.util;

public final class TwoDimensionalFunctions {

    private TwoDimensionalFunctions() {
    }

    public static double rosenbrock(double x, double y) {
        return (1 - x) * (1 - x) + 100 * (y - x * x) * (y - x * x);
    }
}
