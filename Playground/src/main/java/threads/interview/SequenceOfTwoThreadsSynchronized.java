package threads.interview;
/*
    =====================================
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

import lombok.extern.log4j.Log4j;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Semaphore;

@Log4j
public class SequenceOfTwoThreadsSynchronized {
        Object mutex = new Object();
        Boolean isOnesTurn = true;
        CopyOnWriteArrayList<String> out = new CopyOnWriteArrayList<>();

        class FirstThread implements Runnable {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    try {
                        synchronized (mutex) {
                            while (!isOnesTurn) {
                                mutex.wait();
                            }
                            out.add("first");
                            isOnesTurn = !isOnesTurn;
                            mutex.notify();
                        }
                    } catch (InterruptedException e) {
                        log.debug("First thread interrupt exception: " + e.getMessage());
                    }
                }
            }
        }

        class SecondThread implements Runnable {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    try {
                        synchronized (mutex) {
                            while (isOnesTurn) {
                                mutex.wait();
                            }
                            out.add("second");
                            isOnesTurn = !isOnesTurn;
                            mutex.notify();
                        }
                    } catch (InterruptedException e) {
                        log.debug("Second thread interrupt exception: " + e.getMessage());
                    }
                }
            }
        }

    public List<String> sequence() throws InterruptedException {
        Thread firstThread = new Thread(new FirstThread());
        Thread secondThread = new Thread(new SecondThread());

        firstThread.start();
        secondThread.start();

        firstThread.join();
        secondThread.join();

        return out;
    }

    public static void main(String[] args) throws InterruptedException {
        log.debug(new SequenceOfTwoThreadsSynchronized().sequence().toString());
    }
}
