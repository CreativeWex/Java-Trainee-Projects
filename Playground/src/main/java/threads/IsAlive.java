/*
    =====================================
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

public class IsAlive extends Thread {

    public IsAlive(String name) {
        super(name);
    }

    @Override
    public void run() {
        super.run();
    }

    public static void main(String[] args) {
        Thread thread = new IsAlive("TestThread");
        System.out.println("Before starting, is thread alive: " + thread.isAlive());

        thread.start();
        System.out.println("After starting, is thread alive: " + thread.isAlive());

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("After thread is completed, is thread alive: " + thread.isAlive());
    }
}
