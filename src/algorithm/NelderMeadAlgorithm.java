package src.algorithm;

import src.util.Result;

import java.util.function.DoubleBinaryOperator;

public class NelderMeadAlgorithm {
    private final double alpha;
    private final double beta;
    private final double gamma;

    public NelderMeadAlgorithm(double alpha, double beta, double gamma) {
        this.alpha = alpha;
        this.beta = beta;
        this.gamma = gamma;
    }

    public Result run(DoubleBinaryOperator f, double[][] simplex) {
        return new Result(0, 0, 0, 0);
    }
}
