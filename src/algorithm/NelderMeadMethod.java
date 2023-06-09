package src.algorithm;

import src.util.Function;
import src.util.Logger;
import src.util.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NelderMeadMethod {
    private static final Random rand = new Random();

    private final Logger log;

    public NelderMeadMethod(Logger log) {
        this.log = log;
    }

    public Point optimize(Function function, Point initialPoint, double epsilon, int maxIter,
                          double reflection, double expansion, double contraction, double shrink) {
        int dimension = initialPoint.size();

        // Simplex
        List<Point> simplex = createInitialSimplex(initialPoint);

        int iterCount = 0;
        while (iterCount != maxIter && !stopCondition(simplex, epsilon)) {
            log.both("Итерация: " + (iterCount + 1));

            // Сортируем точки симплекса по значению функции
            simplex.sort(new Point.PointComparator(function));

            Point bestPoint = simplex.get(0);
            Point goodPoint = simplex.get(simplex.size() - 2);
            Point worstPoint = simplex.get(dimension);
            double bestValue = function.apply(bestPoint);
            double goodValue = function.apply(goodPoint);
            double worstValue = function.apply(worstPoint);

            log.both("Simplex: " + simplex);

            // Вычисляем центр тяжести вершин, кроме самой удаленной
            Point centroid = calculateCentroidPoint(simplex, dimension);

            // Отражение
            Point reflectedPoint = computeReflectedPoint(centroid, worstPoint, reflection, dimension);
            double reflectedValue = function.apply(reflectedPoint);

            // Поиск места reflectedValue
            if (bestValue <= reflectedValue && reflectedValue < goodValue) {
                // Замена самой удаленной точки отраженной точкой
                simplex.set(dimension, reflectedPoint);
            } else if (reflectedValue < bestValue) {
                // Растяжение
                Point expandedPoint = computeExpansionPoint(centroid, reflectedPoint, expansion, dimension);
                double expandedValue = function.apply(expandedPoint);

                if (expandedValue < reflectedValue) {
                    // Замена самой удаленной точки точкой растяжения
                    simplex.set(dimension, expandedPoint);
                } else {
                    // Замена самой удаленной точки отраженной точкой
                    simplex.set(dimension, reflectedPoint);
                }
            } else {
                // Сжатие
                if (reflectedValue < worstValue) {
                    // Внешнее сжатие
                    Point contractedPoint = computeOutsideContractionPoint(centroid, reflectedPoint, contraction, dimension);
                    double outContractedValue = function.apply(contractedPoint);
                    if (outContractedValue <= reflectedValue) {
                        // Замена самой удаленной точки новой точкой внешнего сжатия
                        simplex.set(dimension, contractedPoint);
                        iterCount++;
                        continue;
                    }
                } else {
                    // Внутреннее сжатие
                    Point contractionPoint = computeInsideContractionPoint(centroid, worstPoint, contraction, dimension);
                    double inContracted = function.apply(contractionPoint);
                    if (inContracted < worstValue) {
                        // Замена самой удаленной точки новой точкой внутреннего сжатия
                        simplex.set(dimension, contractionPoint);
                        iterCount++;
                        continue;
                    }
                }

                // Глобальное сжатие
                shrink(simplex, dimension, shrink, bestPoint);
            }
            iterCount++;
        }

        return simplex.get(0);
    }

    private static void shrink(List<Point> simplex, int dimension, double shrink, Point bestPoint) {
        for (int i = 1; i <= dimension; i++) {
            Point p = simplex.get(i);
            for (int j = 0; j < dimension; j++) {
                p.set(j, bestPoint.get(j) + shrink * (p.get(j) - bestPoint.get(j)));
            }
            simplex.set(i, p);
        }
    }

    private static Point computeInsideContractionPoint(Point centroid, Point worstPoint, double contraction, int size) {
        Point contractionPoint = new Point(new double[size]);

        for (int i = 0; i < size; i++) {
            contractionPoint.set(i, centroid.get(i) - contraction * (centroid.get(i) - worstPoint.get(i)));
        }

        return contractionPoint;
    }

    private static Point computeOutsideContractionPoint(Point centroid, Point reflectedPoint, double contraction, int size) {
        Point contractionPoint = new Point(new double[size]);

        for (int i = 0; i < size; i++) {
            contractionPoint.set(i, centroid.get(i) + contraction * (reflectedPoint.get(i) - centroid.get(i)));
        }

        return contractionPoint;
    }

    private static Point computeExpansionPoint(Point centroid, Point reflectedPoint, double expansion, int size) {
        Point expandedPoint = new Point(new double[size]);

        for (int i = 0; i < size; i++) {
            expandedPoint.set(i, (1 - expansion) * centroid.get(i) + expansion * reflectedPoint.get(i));
        }

        return expandedPoint;
    }

    private static Point computeReflectedPoint(Point centroid, Point worstPoint, double reflection, int size) {
        Point reflectedPoint = new Point(new double[size]);

        for (int i = 0; i < size; i++) {
            reflectedPoint.set(i, (1 + reflection) * centroid.get(i) - reflection * worstPoint.get(i));
        }

        return reflectedPoint;
    }

    private static Point calculateCentroidPoint(List<Point> simplex, int size) {
        Point centroid = new Point(new double[size]);

        for (int i = 0; i < size; i++) {
            Point p = simplex.get(i);
            for (int j = 0; j < p.size(); j++) {
                centroid.set(j, centroid.get(j) + p.get(j));
            }
        }

        double scaling = 1.0 / size;
        for (int j = 0; j < centroid.size(); j++) {
            centroid.set(j, centroid.get(j) * scaling);
        }

        return centroid;
    }

    private static boolean stopCondition(List<Point> simplex, double epsilon) {
        double norm = 0;

        for (int i = 0; i < simplex.size(); i++) {
            Point p1 = simplex.get(i);
            Point p2 = simplex.get((i + 1) % simplex.size());
            for (int j = 0; j < p1.size(); j++) {
                norm += Math.pow(p1.get(j) - p2.get(j), 2);
            }
        }
        norm = Math.sqrt(norm);

        return norm < epsilon;
    }

    private static List<Point> createInitialSimplex(Point initialPoint) {
        List<Point> simplex = new ArrayList<>();
        simplex.add(initialPoint);

        for (int i = 0; i < initialPoint.size(); i++) {
            Point point = new Point(initialPoint.getCopy());
            double offset = rand.nextGaussian();
            if (point.get(i) != 0) {
                point.set(i, point.get(i) * offset);
            } else {
                point.set(i, offset);
            }
            simplex.add(point);
        }

        return simplex;
    }
}