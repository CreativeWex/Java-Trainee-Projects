public class Program {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Error: wrong args number");
            System.exit(-1);
        }
        String[] input = args[0].split("=");
        if (!input[0].equals("--count")) {
            System.err.println("Error: wrong flag");
            System.exit(-1);
        }
        try {
            int count = Integer.parseInt(input[1]);

            EggThread eggThread = new EggThread(count);
            HenThread henThread = new HenThread(count);
            eggThread.start();
            henThread.start();
            henThread.join();
            eggThread.join();

            for (int i = 0; i < count; i++) {
                System.out.println("Human");
            }
        } catch (NumberFormatException numberFormatException) {
            System.out.println("Not a integer");
        } catch (InterruptedException interruptedException) {
            System.out.println("Something interrupt tread");
        }
    }
}