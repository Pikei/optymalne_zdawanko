import java.util.Arrays;
import java.util.stream.IntStream;

public class SpadekWzgledemWspolrzednych {

    private final double[] x = new double[4];
    private double krok;

    public SpadekWzgledemWspolrzednych() {
        start();
    }

    private double f() {
        return IntStream.range(0, x.length-1).mapToDouble(i -> Math.pow(x[i], 2) * Math.pow(x[i+1], 3)).sum();
    }

    private double fPrim(double[] x, int i) {
        return  2 * x[i] * Math.pow(x[i+1], 3);
    }

    private void start() {
        Arrays.fill(x,2);
        print();
        krok = 1.5;
        double f1;
        double f2 = f();
        double temp;
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < x.length; j++) {
                temp = x[j];
                x[j] += krok;
                f1 = f2;
                f2 = f();
                if (!(f2 < f1)) {
                    x[j] -= 2 * krok;
                    f1 = f2;
                    f2 = f();
                    if (!(f2 < f1)) {
                        x[j] = temp;
                    }
                }
            }
            print();
        }

    }

    private void stefan(){
        double nowyKrok = 0;
    }

    private void print() {
        System.out.println("\n============================");
        System.out.println("Wartość funkcji: " + f());
        System.out.println("Współrzędne punktu: ");
        Arrays.stream(x).forEach(System.out::println);
        System.out.println("Aktualny krok: " + krok);
        System.out.println("============================");
    }
}
