import java.util.Arrays;
import java.util.stream.IntStream;

public class GradientProsty {
    public GradientProsty() {
        Arrays.fill(x,1);
        Arrays.setAll(xStart, i -> x[i]);
        algorytm();
        print();
    }

    private final double[] x = new double[100];
    private final double[] xStart = new double[x.length];
    private final double[] g = new double[x.length];
    private double k = 1.5;
    private final double a = 0.8;
    private final double limitRedukcjiKroku = 5;
    private final double e = 0.02;

    private double f(double x[]) {
        return IntStream.range(0, x.length).mapToDouble(i -> Math.pow(x[i],3) + 3 * Math.pow(x[i], i+2)).sum();
    }

    private double fPrim(double x[], int i) {
        return 3 * Math.pow(x[i], 2) + 3 * (i+2) * Math.pow(x[i], i+1);
    }

    private void algorytm() {
        Arrays.setAll(g, i -> fPrim(x, i)*(-1));
        Arrays.setAll(x, i-> k*g[i]);
        while (Math.abs(f(x) - f(xStart)) > e) {
            Arrays.setAll(xStart, j -> x[j]);
            Arrays.setAll(g, i -> fPrim(x, i)*(-1));
            Arrays.setAll(x, i-> k*g[i]);
            if (f(x) < f(xStart)) {
                continue;
            }
            for (int i = 0; i < limitRedukcjiKroku; i++) {
                k *= a;
                Arrays.setAll(x, j -> xStart[j] + k * g[j]);
                if (f(x) < f(xStart)) break;
            }
        }
    }

    private void print() {
        System.out.println("wartość funkcji: " + f(x));
        System.out.println("Współrzędne punktu: ");
        IntStream.range(0, x.length).mapToObj(i -> "x[" + i + "] = " + x[i]).forEach(System.out::println);
    }
}