package src.util;

public class Result {
    private final double x;
    private final double y;
    private final double f;
    private final int count;

    public Result(double x, double y, double f, int count) {
        this.x = x;
        this.y = y;
        this.f = f;
        this.count = count;
    }

    public double getF() {
        return f;
    }

    @Override
    public String toString() {
        return String.format("Найден приближенный экстремум: x = %.3f, y = %.3f, f(x) = %.3f за %d итераций",
                x, y, f, count);
    }
}
