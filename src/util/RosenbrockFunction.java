package src.util;

import src.algorithm.NelderMeadAlgorithm;

public class RosenbrockFunction implements NelderMeadAlgorithm.Function {
    @Override
    public double apply(NelderMeadAlgorithm.Point point) {
        double x = point.get(0);
        double y = point.get(1);
        return (1 - x) * (1 - x) + 100 * (y - x * x) * (y - x * x);
    }
}
