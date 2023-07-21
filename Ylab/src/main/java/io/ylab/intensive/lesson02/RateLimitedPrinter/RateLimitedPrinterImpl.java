package io.ylab.intensive.lesson02.RateLimitedPrinter;
/*
    =====================================
    @project 2-Ylab-OOP
    @created 12/03/2023    
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

public class RateLimitedPrinterImpl implements RateLimitedPrinter{
    private final int interval;
    private long lastPrintTime = 0;

    public RateLimitedPrinterImpl(int interval) {
        this.interval = interval;
    }

    @Override
    public void print(String message) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastPrintTime > interval) {
            System.out.println(message);
            lastPrintTime = currentTime;
        }
    }
}
