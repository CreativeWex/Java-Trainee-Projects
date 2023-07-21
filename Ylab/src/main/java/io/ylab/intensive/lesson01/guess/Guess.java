package io.ylab.intensive.lesson01.guess;/*
    =====================================
    @project Ylab
    @created 03/03/2023    
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

import java.util.Random;
import java.util.Scanner;

public class Guess {
    public static void main(String[] args) throws Exception {
        int number = new Random().nextInt(99) + 1;
        int maxAttempts = 10; // здесь задается количество попыток
        System.out.println("Я загадал число. У тебя " + maxAttempts + " попыток угадать.");

        try {
            Scanner scanner = new Scanner(System.in);
            while (maxAttempts-- > 0) {
                int userNum = scanner.nextInt();
                if (userNum == number) {
                    System.out.format("Ты угадал с %d попытки\n", 10 - maxAttempts);
                    System.exit(0);
                } else if (userNum < number) {
                    System.out.format("Мое число больше! Осталось %d попыток\n", maxAttempts);
                } else {
                    System.out.format("Мое число меньше! Осталось %d попыток\n", maxAttempts);
                }
            }
            System.out.println("Ты не угадал");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

