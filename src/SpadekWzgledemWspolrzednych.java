import java.util.Arrays;
import java.util.stream.IntStream;

public class SpadekWzgledemWspolrzednych {

    private final double[] x = new double[4];
    private final double[] k = new double[x.length];

    public SpadekWzgledemWspolrzednych() {
        start();
    }

    private double f(double[] x) {
        return IntStream.range(0, x.length - 1).mapToDouble(i -> Math.pow(x[i], 2) * Math.pow(x[i + 1], 3)).sum();
    }

    private double fPrim(double[] x, int i) {
        if (i == 0) return 2 * x[0] * Math.pow(x[1], 3);
        if (i == x.length - 1) return 3 * Math.pow(x[i], 2) * Math.pow(x[i - 1], 2);
        return 3 * Math.pow(x[i], 2) * Math.pow(x[i - 1], 2) + 2 * x[i] * Math.pow(x[i + 1], 3);
    }

    private void start() {
        Arrays.fill(x, 1);
        Arrays.fill(k, 1.5);
        double[] temp = new double[x.length];
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < x.length; j++) {
                Arrays.setAll(temp, k -> x[k]);
                k[j] = stefensen(j);
                x[j] += k[j];
                if (f(x) < f(temp)) continue;
                x[j] -= 2 * k[j];
                if (f(x) < f(temp)) continue;
                x[j] = temp[j];
            }
        }
        printResult();
    }

    private double stefensen(int i) {
        double[] tempArr = new double[x.length];
        double[] tempArr2 = new double[x.length];
        Arrays.setAll(tempArr, j -> x[j]);
        Arrays.setAll(tempArr2, j -> x[j]);

        tempArr[i] = x[i] + k[i];
        double licznik = Math.pow(fPrim(tempArr, i), 2);
        tempArr2[i] = k[i] + fPrim(tempArr, i);
        double mianownik = fPrim(tempArr2, i) - fPrim(tempArr, i);
        return k[i] - licznik / mianownik;
        // nowyKrok = k - (f'(k))^2 / f'(k+f'(k)) - f'(k)
    }

    private void printResult() {
        System.out.println("\n=============================");
        System.out.println("Wartość funkcji: " + f(x));
        System.out.println("Współrzędne punktu: ");
        IntStream.range(0, x.length).mapToObj(i -> "x[" + i + "] = " + x[i]).forEach(System.out::println);
        System.out.println("\nAktualny krok: ");
        IntStream.range(0, k.length).mapToObj(i -> "dla x[" + i + "] krok wynosi: " + k[i]).forEach(System.out::println);
        System.out.println("=============================");
    }
}