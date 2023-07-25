/*
    =====================================
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

public class JoinRunnable implements Runnable{

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.println("Thread " + threadName + " is running");
        for (int i = 0; i < 5; i++) {
            System.out.println(threadName + ": " + i);
        }
        System.out.println("Thread " + threadName + " is finished");
    }

    public static void main(String[] args) {
        JoinRunnable joinRunnable = new JoinRunnable();
        Thread threadA = new Thread(joinRunnable);
        Thread threadB = new Thread(joinRunnable);
        Thread threadC = new Thread(joinRunnable);

        threadA.start();
        try {
            threadA.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadB.start();
        threadC.start();
    }
}
