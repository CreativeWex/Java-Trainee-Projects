/*
    =====================================
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

public class JoinThread extends Thread {

    public JoinThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        String currentName = Thread.currentThread().getName();
        for (int i = 0; i < 10; i++) {
            System.out.println("Thread " + currentName + ": " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Thread " + currentName + " completed");
    }

    public static void main(String[] args) {
        JoinThread a = new JoinThread("A");
        JoinThread b = new JoinThread("B");
        JoinThread c = new JoinThread("C");

        a.start();
        try {
            a.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        b.start();
        c.start();
    }
}
