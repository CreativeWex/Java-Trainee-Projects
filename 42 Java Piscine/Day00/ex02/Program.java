import java.util.Scanner;

public class Program {
    static int coffeeAmount = 0;
    static int EOF = 42;

    static public int sqrt(int num) {
        int i = 1;
        while (i * i < num) {
            i++;
        }
        return i;
    }

    static public void calculate(long num) {
        int sumDigits = 0;
        if (num == 1 || num == 0) {
            return;
        }
        while (num > 0) {
            sumDigits += num % 10;
            num /= 10;
        }
        for (int i = 2; i < sqrt(sumDigits); i++) {
            if (sumDigits % i == 0) {
                return;
            }
        }
        coffeeAmount++;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        long num = 0;

        do {
            if (!input.hasNextInt()) {
                System.err.println("IllegalArgument");
                System.exit(-1);
            }
            else {
                num = input.nextInt();
                calculate(num);
            }
        }
        while (num != EOF);
        System.out.println("Count of coffee - request - " + coffeeAmount);
        System.exit(0);
    }
}