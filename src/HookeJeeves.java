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
        return IntStream.range(0, x.length).mapToDouble(i -> (i + 1) * Math.pow(x[i], (i - 1))).sum();
    }

    private void algorytm() {
        Arrays.fill(xStart, 1);
        Arrays.setAll(x, i -> xStart[i]);
        krokProbny();
        Arrays.setAll(xStart, i -> x[i]);
        krokRoboczy();
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
            }
        }
    }

    private void krokRoboczy() {
        double v = 1.5;
        for (int i = 0; i < x.length; i++) {
            x[i] = xStart[i] + v * (x[i] - xStart[i]);
        }
        // nowy x = stary + 1,5 * (nowy - stary)
    }

}
