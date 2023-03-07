package pso;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.IntStream;

public class Pso {

    private final HashMap<Integer, Particle> swarm = new HashMap<>();
    private final Random rand = new Random();
    private final int swarmSize;
    private final int iterations;
    private final int dimensions;


    public Pso(int swarmSize, int iterations, int dimensions) {
        this.swarmSize = swarmSize;
        this.iterations = iterations;
        this.dimensions = dimensions;
        createSwarm();
        IntStream.range(0, swarm.size()).forEach(i -> swarm.get(i).setGlobalBest(findGlobalBest()));
//        do {
        for (int k = 0; k < iterations; k++) {
            IntStream.range(0, swarm.size()).forEach(i -> swarm.get(i).setV(velocity(i)));
            moveParticle();
        }
//        while (!exit()) ;

        print();
    }

    private double func(double[] x) {
        return IntStream.rangeClosed(1,x.length).mapToDouble(i -> Math.pow(x[i-1],2*i-1)).sum();
    }

    private void createSwarm() {
        for (int i = 0; i < swarmSize; i++) {
            double[]  x = new double[dimensions];
            double[] personalBest = new double[x.length];
            double[] globalBest = new double[x.length];
            double[] v = new double[x.length];
            Arrays.setAll(x, j -> randomize(-100, 100));
            Arrays.setAll(personalBest, j -> randomize(-100, 100));
            Arrays.setAll(v, j -> randomize(-1, 1));
            Arrays.fill(globalBest,1);
            swarm.put(i, new Particle(func(x), x, personalBest, globalBest, v));
        }
    }

    private double[] findGlobalBest() {
        double max = swarm.get(0).getRating();
        double[] best = new double[dimensions];
        for (int i = 0; i < swarm.size(); i++) {
            if (swarm.get(i).getRating() < max) {
                max = swarm.get(i).getRating();
                for (int j = 0; j < swarm.get(i).getX().length; j++) {
                    best[j] = swarm.get(i).getX()[j];
                }
            }
        }
        return best;
    }

    private void moveParticle() {
        updatePosition();
        updatePersonalBest();
        updateGlobalBest();
    }

    private void updatePosition() {


        IntStream.range(0, swarm.size()).forEach(i -> swarm.get(i).getX());
        IntStream.range(0, swarm.size()).forEach(i -> swarm.get(i).setX(swarm.get(i).getX() + swarm.get(i).getV()[0]));
        IntStream.range(0, swarm.size()).forEach(i -> swarm.get(i).setY(swarm.get(i).getY() + swarm.get(i).getV()[1]));
        IntStream.range(0, swarm.size()).forEach(i -> swarm.get(i).setRating(func(swarm.get(i).getX(), swarm.get(i).getY())));
    }

    private void updatePersonalBest() {
        IntStream.range(0, swarm.size()).filter(i -> func(swarm.get(i).getPersonalBest()[0], swarm.get(i).getPersonalBest()[1]) > swarm.get(i).getRating()).forEach(i -> {
            double[] temp = new double[2];
            temp[0] = swarm.get(i).getX();
            temp[1] = swarm.get(i).getY();
            swarm.get(i).setPersonalBest(temp);
        });
    }

    private void updateGlobalBest() {
        for (int i = 0; i < swarm.size(); i++) {
            if (func(swarm.get(i).getGlobalBest()[0], swarm.get(i).getGlobalBest()[1]) > func(swarm.get(i).getPersonalBest()[0], swarm.get(i).getPersonalBest()[1])) {
                for (int j = 0; j < swarm.size(); j++) {
                    swarm.get(j).setGlobalBest(swarm.get(i).getPersonalBest());
                }
            }
        }
    }

    private double[] velocity(int i) {
        double[] v = new double[dimensions];
        Arrays.setAll(v, j -> Velocity(i, j));
        return v;
    }

    private double Velocity(int i, int j) {
        return (0.8 * swarm.get(i).getV()[j]) + (0.2 * Math.random() * (swarm.get(i).getPersonalBest()[j] - swarm.get(i).getX()[j])) +
                (0.2 * Math.random() * (swarm.get(i).getGlobalBest()[j] - swarm.get(i).getX()[j]));
    }

    private boolean exit() {
        int counter = 0;
//        for (int i = 0; i < swarm.size(); i++) {
////            if (Math.abs(swarm.get(i).getX() - swarm.get(i).getGlobalBest()[0]) < 0.02 && Math.abs(swarm.get(i).getY() - swarm.get(i).getGlobalBest()[1]) < 0.02) {
//                counter ++;
//            } else {
//                counter --;
//            }
//        }
        return counter == swarm.size();
    }

    private double randomize(double min, double max) {
        return rand.nextDouble() * (max - min) + min;
    }



    private void print() {
        IntStream.range(0, swarm.size()).forEach(i -> {
            System.out.println("---------------------------");
            System.out.println("Particle: " + (i+1));
            IntStream.range(0, swarm.get(i).getX().length).mapToObj(j -> "x[" + j + "] = " + swarm.get(i).getX()[j]).forEach(System.out::println);
            System.out.println("Grade: " + swarm.get(i).getRating());
            System.out.println("V: [" + swarm.get(i).getV()[0] + ", " + swarm.get(i).getV()[1] + "]");
            IntStream.range(0, swarm.get(i).getPersonalBest().length).mapToObj(j -> "Personal best [" + j + "] = " + swarm.get(i).getPersonalBest()[j]).forEach(System.out::println);
            IntStream.range(0, swarm.get(i).getGlobalBest().length).mapToObj(j -> "Global best [" + j + "] = " + swarm.get(i).getGlobalBest()[j]).forEach(System.out::println);
            System.out.println("Global best grade: " + func(swarm.get(i).getGlobalBest()));
            System.out.println("---------------------------");
        });
    }
}