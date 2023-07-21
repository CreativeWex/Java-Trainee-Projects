package io.ylab.intensive.lesson02.RateLimitedPrinter;
/*
    =====================================
    @project 2-Ylab-OOP
    @created 12/03/2023    
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

public class RateLimitedPrinterTest {
    public static void main(String[] args) {

//         Принт раз в 1 секунду
        RateLimitedPrinter rateLimitedPrinter1Sec = new RateLimitedPrinterImpl(1000);
        for (int i = 0; i < 1_000_000_000; i++) {
            rateLimitedPrinter1Sec.print(String.valueOf(i));
        }

        System.out.println("5 sec");

//         Принт раз в 5 секунд
        RateLimitedPrinter rateLimitedPrinter5Sec = new RateLimitedPrinterImpl(5000);
        for (int i = 0; i < 1_000_000_000; i++) {
            rateLimitedPrinter5Sec.print(String.valueOf(System.currentTimeMillis()));
        }

        System.out.println("10  sec");

        // Принт раз в 10 секунд
        RateLimitedPrinter rateLimitedPrinter10Sec = new RateLimitedPrinterImpl(10000);
        for (int i = 0; i < 1_000_000_000; i++) {
            rateLimitedPrinter10Sec.print(String.valueOf(System.currentTimeMillis()));
        }
    }
}
