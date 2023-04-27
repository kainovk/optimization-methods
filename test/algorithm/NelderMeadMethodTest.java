package test.algorithm;

import org.junit.jupiter.api.Test;
import src.algorithm.NelderMeadMethod;
import src.util.Function;
import src.util.Logger;
import src.util.Point;
import src.util.RosenbrockFunction;

import static org.junit.jupiter.api.Assertions.*;

class NelderMeadMethodTest {

    @Test
    void shouldFindMinimum() {
        Function f = point -> {
            double x = point.get(0);
            return x * x;
        };

        NelderMeadMethod nelderMeadMethod = prepareNelderMeadMethod();
        Point actualPoint = nelderMeadMethod.optimize(f, new Point(0, 0),
                0.001, 100, 1, 2, 0.5, 0.5);
        Point expectedPoint = new Point(0, 0);
        double actual = f.apply(actualPoint);
        double expected = f.apply(expectedPoint);

        assertEquals(actual, expected);
    }

    @Test
    void shouldGetAroundLocalMinimum() {
        Function f = point -> {
            double x = point.get(0);
            return x * x * x;
        };

        NelderMeadMethod nelderMeadMethod = prepareNelderMeadMethod();
        Point actualPoint = nelderMeadMethod.optimize(f, new Point(0, 0),
                0.001, 100, 1, 2, 0.5, 0.5);
        Point expectedPoint = new Point(0, 0);
        double actual = f.apply(actualPoint);
        double expected = f.apply(expectedPoint);

        assertNotEquals(actual, expected);
    }

    @Test
    void shouldFindOptimum() {
        Function f = new RosenbrockFunction();

        NelderMeadMethod nelderMeadMethod = prepareNelderMeadMethod();
        Point actualPoint = nelderMeadMethod.optimize(f, new Point(0, 0),
                0, 1000, 1, 2, 0.5, 0.7);
        Point expectedPoint = new Point(1, 1);
        double actual = f.apply(actualPoint);
        double expected = f.apply(expectedPoint);

        assertEquals(actual, expected);
    }

    @Test
    void shouldNotFindOptimum() {
        Function f = new RosenbrockFunction();

        NelderMeadMethod nelderMeadMethod = prepareNelderMeadMethod();
        Point actualPoint = nelderMeadMethod.optimize(f, new Point(0, 0),
                1e-14, 1000, 1, 2, 0.5, 0.4);
        Point expectedPoint = new Point(1, 1);
        double actual = f.apply(actualPoint);
        double expected = f.apply(expectedPoint);

        assertNotEquals(actual, expected);
    }

    private static NelderMeadMethod prepareNelderMeadMethod() {
        return new NelderMeadMethod(new Logger());
    }
}