import java.util.ArrayList;
import java.util.List;

public class Program {
    private static final int MAX_MODULO = 1000;
    public static volatile int threadSum = 0;

    private static short [] initArr(int size) {
        short[] arr = new short[size];
        while (size > 0) {
            arr[--size] = (short)(-MAX_MODULO + (Math.random() * (2 * MAX_MODULO)));
        }
        return arr;
    }

    private static void validateArgs(String[] args) {
        if (args.length != 2) {
            System.err.println("Error: wrong args number");
            System.exit(-1);
        }
        if ((!args[0].matches("--arraySize=\\d+")) || (!args[1].matches("--threadsCount=\\d+"))){
            System.err.println("Error: wrong flag(s)\n Usage: --arraySize=<int> --threadsCount=<int>");
            System.exit(-1);
        }
    }

    private static void displayArr(short[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }

    private static void displayCalculatedSum(short[] arr) {
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        System.out.println("Sum: " + sum);
    }

    public static void main(String[] args) {
        validateArgs(args);
        int arraySize = Integer.parseInt(args[0].split("=")[1]);
        int threadsCount = Integer.parseInt(args[1].split("=")[1]);
        short[] arr = initArr(arraySize);
        displayCalculatedSum(arr);
        int cellSize = arraySize / threadsCount;
        int start = 0;
        int end = cellSize - 1;
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < threadsCount - 1; i++) {
            threads.add(new Thread(new ThreadsCalculation(i, start, end, arr)));
            start += cellSize;
            end += cellSize;
        }
        threads.add(new Thread(new ThreadsCalculation(threadsCount - 1, start, arraySize - 1, arr)));
        for (Thread thread: threads) {
            thread.start();
        }
        for (Thread th: threads) {
            try {
                th.join();
            } catch (InterruptedException interruptedException) {
                System.out.println("Something interrupt tread");
            }
        }
        System.out.println("Sum by threads: " + threadSum);
    }
}