import java.util.Arrays;
import java.util.stream.IntStream;

public class HookeJeeves {
    public HookeJeeves() {
        algorytm();
    }

    private final double[] xStart = new double[4];
    private final double[] x = new double[xStart.length];
    private double k = 1.5;
    private final double e = 0.02;

    private double f(double[] x) {
        return IntStream.range(0, x.length).mapToDouble(i -> (i + 2) * Math.pow(x[i], (i))).sum();
    }

    private void algorytm() {
        Arrays.fill(xStart, 1);
        Arrays.setAll(x, i -> xStart[i]);
        do {
            krokProbny();
            krokRoboczy();
            if (f(x) < f(xStart))
                Arrays.setAll(xStart, i -> x[i]);
            print();
        } while (k > e);
        printResult();
    }

    private void krokProbny() {
        double v = k;
        for (int i = 0; i < x.length; i++) {
            x[i] += k;
            if (f(x) < f(xStart)) {
                k = v;
                continue;
            }
            x[i] -= 2 * k;
            if (f(x) < f(xStart)) {
                k = v;
                continue;
            }
            x[i] = xStart[i];
            if (k > e) {
                k *= 0.2;
                i--;
            } else k = v;
        }
    }

    private void krokRoboczy() {
        double v = k;
        double[] tempArray = new double[x.length];
        Arrays.setAll(tempArray, i -> x[i]);
        Arrays.setAll(x, i -> xStart[i] + v * (x[i] - xStart[i]));
        if (f(x) < f(tempArray)) k *= 1.25;
        else {
            Arrays.setAll(x, i -> tempArray[i]);
            k *= 0.2;
        }
    }

    private void print() {
        System.out.println("==========================");
        System.out.print("aktualny punkt: [");
        for (double v : x) {
            System.out.print(v + " ");
        }
        System.out.print("]\n");
        System.out.println("aktualne przybliżenie wyniku: " + f(x));
        System.out.println("aktualny krok: " + k);
        System.out.println("==========================");
    }

    private void printResult() {
        System.out.println("==========WYNIK===========");
        System.out.print("Znaleziono minimum funkcji w punkcie: [");
        for (double v : x) {
            System.out.print(v + " ");
        }
        System.out.print("]\n");
        System.out.println("Wartość funkcji wynosi: " + f(x));
        System.out.println("==========================");
    }

}
