/*
    =====================================
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

public class SleepRunnable implements Runnable{

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("Thread " + Thread.currentThread().getName() + ", i = " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SleepRunnable sleepRunnable = new SleepRunnable();
        Thread thread1 = new Thread(sleepRunnable);
        thread1.setName("Phill");

        Thread thread2 = new Thread(sleepRunnable);
        thread2.setName("Ken");

        thread1.start();
        thread2.start();
    }
}
