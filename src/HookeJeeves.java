import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class HookeJeeves {
    public HookeJeeves() {
        algorytm();
    }


    private final double[] x = new double[4];
    private double k = 1.5;
    private final double e = 0.02;

    private double f() {
        return IntStream.range(0, x.length).mapToDouble(i -> (i+1)*Math.pow(x[i], (i-1))).sum();
    }

    private void algorytm() {
        Arrays.fill(x, 1);
        for (int i = 0; i < 10; i++) {
            krokProbny();
        }


    }

    private void krokProbny() {
        double f1 = f();
        double f2;
        double temp;
        double v = k;
        for (int i = 0; i < x.length; i++) {
            k = v;
            do {
                temp = x[i];
                x[i] += k; //krok w jedną stronę
                f2 = f();
                if (f2 > f1) { //sprawdzenie warunku
                    x[i] -= 2 * k; //jeśli krok się nie powiódł to wykonaj krok w przeciwną stronę
                    f2 = f();
                    if (f2 > f1) { //sprawdzenie warunku
                        x[i] = temp; //jeśli krok w żadną ze stron się nie powiódł wróć do poprzedniej wartości
                        if (k>e) // sprawdzenie czy krok nie jest zbyt mały
                            k *= 0.2; //zmiejszenie kroku
                        else break;
                    }

                }
            } while (f2 > f1);
        }
    }

    private void krokRoboczy() {

    }

}
