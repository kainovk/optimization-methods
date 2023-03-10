package src;

import src.algorithm.NelderMeadAlgorithm;
import src.util.RosenbrockFunction;

import static src.algorithm.NelderMeadAlgorithm.optimize;

public class Main {
    public static void main(String[] args) {
        RosenbrockFunction f = new RosenbrockFunction();
        NelderMeadAlgorithm.Point result =
                optimize(f, new NelderMeadAlgorithm.Point(10, 5), 0.01, 1000, 1, 2, 0.5, 0.5);

        System.out.println();
        System.out.println("Result point:" + result);
        System.out.println("Function value: " + f.apply(result));
    }
}
