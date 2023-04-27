package src;

import src.algorithm.NelderMeadMethod;
import src.util.Logger;
import src.util.Point;
import src.util.RosenbrockFunction;

public class Main {
    public static void main(String[] args) {
        RosenbrockFunction f = new RosenbrockFunction();
        NelderMeadMethod nelderMeadMethod = new NelderMeadMethod(new Logger());
        Point result = nelderMeadMethod.optimize(
                f, new Point(10, 5), 1e-16, 1000, 1, 2, 0.5, 0.5);

        System.out.println();
        System.out.println("Result point:" + result);
        System.out.println("Function value: " + f.apply(result));
    }
}
