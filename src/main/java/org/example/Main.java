package org.example;

import org.example.pair.PairGenerator;

public class Main {
    public static void main(String[] args) {
        PairGenerator pairGenerator = new PairGenerator();
        try {
            for (int i = 0; i < 3; i++) {
                System.out.println(pairGenerator.getPair());
            }
        } catch (RuntimeException e) {
            System.out.println("Refresh the pairs");
            pairGenerator.refresh();
        }
    }
}