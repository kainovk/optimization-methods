package src;

import src.algorithm.NelderMeadAlgorithm;
import src.util.TwoDimensionalFunctions;
import src.util.Result;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double alpha;
        double beta;
        double gamma;

        do {
            System.out.println("Параметр альфа: ");
            alpha = sc.nextDouble();
            System.out.println("Параметр бета: ");
            beta = sc.nextDouble();
            System.out.println("Параметр гамма: ");
            gamma = sc.nextDouble();
        } while (!isValidInput(alpha, beta, gamma));

        System.out.println("Метод Нелдера-Мида:");
        double[][] simplex = {
                {10, 5},
                {5, 10},
                {7, 7}
        };
        Result result = new NelderMeadAlgorithm(alpha, beta, gamma)
                .run(TwoDimensionalFunctions::rosenbrock, simplex);
        System.out.println(result.toString());
    }

    private static boolean isValidInput(double alpha, double beta, double gamma) {
        if (alpha < 0 || beta < 0 || gamma < 0) {
            System.out.println("Параметры не должны быть отрицательными. Попробуйте еще раз :)");
            return false;
        }
        return true;
    }
}
