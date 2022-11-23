package com.github.kumo0621.gobichat;

import java.util.Random;

public class RandomCount {
    public static int random() {
        Random rand = new Random();
        int num = rand.nextInt(100);
        System.out.println(num);
        return num;
    }
}
