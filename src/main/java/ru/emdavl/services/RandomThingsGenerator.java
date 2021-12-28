package ru.emdavl.services;

import java.util.Random;

public class RandomThingsGenerator {
    private static Random random = new Random();
    public static String getPhoneNumBody(){
        StringBuilder body = new StringBuilder("");
        for (int i = 0; i < 9; i++) {
            body.append(random.nextInt(10));
        }
        return body.toString();
    }

    public static double getSalary() {
        return random.nextInt(600);
    }
}
