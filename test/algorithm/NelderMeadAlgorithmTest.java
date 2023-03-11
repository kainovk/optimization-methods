package test.algorithm;

import org.junit.jupiter.api.Test;
import src.algorithm.NelderMeadAlgorithm;
import src.util.RosenbrockFunction;

import static org.junit.jupiter.api.Assertions.*;

class NelderMeadAlgorithmTest {

    @Test
    void shouldFindMinimum() {
        NelderMeadAlgorithm.Function f = point -> {
            double x = point.get(0);
            return x * x;
        };

        NelderMeadAlgorithm.Point actualPoint = NelderMeadAlgorithm.optimize(f, new NelderMeadAlgorithm.Point(0, 0),
                0.001, 100, 1, 2, 0.5, 0.5);
        NelderMeadAlgorithm.Point expectedPoint = new NelderMeadAlgorithm.Point(0, 0);
        double actual = f.apply(actualPoint);
        double expected = f.apply(expectedPoint);

        assertEquals(actual, expected);
    }

    @Test
    void shouldGetAroundLocalMinimum() {
        NelderMeadAlgorithm.Function f = point -> {
            double x = point.get(0);
            return x * x * x;
        };

        NelderMeadAlgorithm.Point actualPoint = NelderMeadAlgorithm.optimize(f, new NelderMeadAlgorithm.Point(0, 0),
                0.001, 100, 1, 2, 0.5, 0.5);
        NelderMeadAlgorithm.Point expectedPoint = new NelderMeadAlgorithm.Point(0, 0);
        double actual = f.apply(actualPoint);
        double expected = f.apply(expectedPoint);

        assertNotEquals(actual, expected);
    }

    @Test
    void shouldFindOptimum() {
        NelderMeadAlgorithm.Function f = new RosenbrockFunction();

        NelderMeadAlgorithm.Point actualPoint = NelderMeadAlgorithm.optimize(f, new NelderMeadAlgorithm.Point(0, 0),
                0, 1000, 1, 2, 0.5, 0.7);
        NelderMeadAlgorithm.Point expectedPoint = new NelderMeadAlgorithm.Point(1, 1);
        double actual = f.apply(actualPoint);
        double expected = f.apply(expectedPoint);

        assertEquals(actual, expected);
    }

    @Test
    void shouldNotFindOptimum() {
        NelderMeadAlgorithm.Function f = new RosenbrockFunction();

        NelderMeadAlgorithm.Point actualPoint = NelderMeadAlgorithm.optimize(f, new NelderMeadAlgorithm.Point(0, 0),
                1e-14, 1000, 1, 2, 0.5, 0.4);
        NelderMeadAlgorithm.Point expectedPoint = new NelderMeadAlgorithm.Point(1, 1);
        double actual = f.apply(actualPoint);
        double expected = f.apply(expectedPoint);

        assertNotEquals(actual, expected);
    }
}