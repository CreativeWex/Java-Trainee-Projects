public class Program {
    public static void main (String[] args) {
        int num = 479598;
        // По условиям задачи
        int sum = (num / 100000) + ((num / 10000) % 10) + ((num / 1000) % 10) + ((num % 1000) / 100)
            + ((num % 100) / 10) + (num % 10);

        System.out.println(sum);
        System.exit(0);
    }
}
