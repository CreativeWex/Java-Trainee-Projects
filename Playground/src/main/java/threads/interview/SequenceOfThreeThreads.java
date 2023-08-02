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
public class SequenceOfThreeThreads {
        Semaphore semaphoreOne = new Semaphore(1);
        Semaphore semaphoreTwo = new Semaphore(0);
        Semaphore semaphoreThree = new Semaphore(0);
        CopyOnWriteArrayList<String> out = new CopyOnWriteArrayList<>();

        class FirstThread implements Runnable {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    try {
                        semaphoreOne.acquire();
                        out.add("one");
                        semaphoreTwo.release();
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
                        semaphoreTwo.acquire();
                        out.add("two");
                        semaphoreThree.release();
                    } catch (InterruptedException e) {
                        log.debug("Second thread interrupt exception: " + e.getMessage());
                    }
                }
            }
        }

    class ThirdThread implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                try {
                    semaphoreThree.acquire();
                    out.add("three");
                    semaphoreOne.release();
                } catch (InterruptedException e) {
                    log.debug("Third thread interrupt exception: " + e.getMessage());
                }
            }
        }
    }


    public List<String> sequence() throws InterruptedException {
        Thread firstThread = new Thread(new FirstThread());
        Thread secondThread = new Thread(new SecondThread());
        Thread thirdThread = new Thread(new ThirdThread());

        firstThread.start();
        secondThread.start();
        thirdThread.start();

        firstThread.join();
        secondThread.join();
        thirdThread.join();

        return out;
    }

    public static void main(String[] args) throws InterruptedException {
        log.debug(new SequenceOfThreeThreads().sequence().toString());
    }
}
