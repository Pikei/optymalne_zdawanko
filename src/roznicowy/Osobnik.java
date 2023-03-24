package roznicowy;

public class Osobnik {
    private double[] x;
    private double ocena;

    public Osobnik(double[] x, double ocena) {
        this.x = x;
        this.ocena = ocena;
    }

    public double[] getX() {
        return x;
    }

    public double getOcena() {
        return ocena;
    }
}