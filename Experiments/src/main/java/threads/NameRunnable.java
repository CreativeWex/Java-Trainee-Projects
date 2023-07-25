/*
    =====================================
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

public class NameRunnable implements Runnable{
    @Override
    public void run() {
        System.out.println("Thread is running with name " + Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        NameRunnable nameRunnable = new NameRunnable();

        Thread thread1 = new Thread(nameRunnable);
        thread1.setName("first");
        thread1.start();

        Thread thread2 = new Thread(nameRunnable);
        thread2.setName("second");
        thread2.start();
    }
}
