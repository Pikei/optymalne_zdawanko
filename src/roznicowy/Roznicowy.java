package roznicowy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.IntStream;

public class Roznicowy {
    private int populationSize;
    private int dimensions;
    private final double F = 0.6;
    private double randCriteria;
    private final Random random = new Random();
    private final HashMap<Integer, Osobnik> population = new HashMap<>(populationSize);
    private final HashMap<Integer, Osobnik> newPopulation = new HashMap<>(populationSize);


    public Roznicowy(int popululation, int dimensions, double randCriteria) {
        this.populationSize = popululation;
        this.dimensions = dimensions;
        this.randCriteria = randCriteria;
        createPopulation();
        int count = 0;
        do {
            algorytm();
            if (exit()) {
                population.putAll(newPopulation);
                break;
            }
            else {
                population.putAll(newPopulation);
                int i1 = silnaMaupa1();
                int i2 = silnaMaupa2();
                if (population.get(i1).getOcena() < newPopulation.get(i2).getOcena()){
                    newPopulation.put(i2, population.get(i1));
                }
                count++;
            }
        } while (count < 1000);
//        printPopulation();
        System.out.println("Znaleziono minimum = " + population.get(silnaMaupa1()).getOcena());
    }

    private double f(double[] x) {
        return IntStream.range(0, x.length).mapToDouble(i -> (i + 2) * Math.pow(x[i], (i))).sum();
    }

    private void algorytm(){
        for (int i = 0; i < population.size(); i++) {
            if(random.nextDouble() > randCriteria){
                newPopulation.put(i, population.get(i));
                continue;
            }
            double[] o1;
            double[] o2;
            boolean exit;
            do {
                o1 = population.get(random.nextInt(populationSize)).getX();
                o2 = population.get(random.nextInt(populationSize)).getX();
                exit = !Arrays.equals(o1, population.get(i).getX()) && !Arrays.equals(o2, population.get(i).getX()) && !Arrays.equals(o1, o2);
            } while (!exit);
            double[] temp = nowyOsobnik(o1,o2,i);
            newPopulation.put(i, new Osobnik(temp, f(temp)));
        }
    }

    private boolean exit(){
        int count = 0;
        for (int i = 0; i < population.size(); i++) {
                if (Math.abs(population.get(i).getOcena() - newPopulation.get(i).getOcena()) < 0.02 && Math.abs(population.get(i).getOcena() - newPopulation.get(i).getOcena()) != 0) count++;
                else return false;
        }
        return count == population.size();
    }

    private Integer silnaMaupa1(){
        int index = 0;
        double best = population.get(0).getOcena();
        for (int i = 0; i < population.size(); i++) {
            if (population.get(i).getOcena() < best){
                best = population.get(i).getOcena();
                index = i;
            }
        }
        return index;
    }

    private Integer silnaMaupa2(){
        int index = 0;
        double best = newPopulation.get(0).getOcena();
        for (int i = 0; i < newPopulation.size(); i++) {
            if (newPopulation.get(i).getOcena() < best){
                best = newPopulation.get(i).getOcena();
                index = i;
            }
        }
        return index;
    }

    private double[] nowyOsobnik(double[] o1, double[] o2, int i) {
        double[] temp = new double[o1.length];
        Arrays.setAll(temp, j-> population.get(i).getX()[j] + (o1[j] - o2[j]) * F);
        return temp;
    }

    private void createPopulation() {
        for (int i = 0; i < populationSize; i++) {
            double[] x = new double[dimensions];
            Arrays.setAll(x, j -> generateRandom(-100, 100));
            population.put(i, new Osobnik(x, f(x)));
        }
    }

    private double generateRandom(double min, double max) {
        return random.nextDouble() * (max - min) + min;
    }

    private void printPopulation() {
        IntStream.range(0, population.size()).forEach(i -> {
            System.out.println("---------------------------");
            System.out.println("Osobnik: " + (i+1));
            System.out.println("Grade: " + population.get(i).getOcena());
            System.out.println();
            IntStream.range(0, population.get(i).getX().length).mapToObj(j -> "x[" + j + "] = " + population.get(i).getX()[j]).forEach(System.out::println);
            System.out.println("---------------------------");
        });
    }
}
