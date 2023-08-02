package threads.interview;
/*
    =====================================
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

import java.util.concurrent.CopyOnWriteArrayList;

public class Lighter {
    static class Foo {
        private final CopyOnWriteArrayList<String> out;

        Foo(CopyOnWriteArrayList<String> out) {
            this.out = out;
        }

        void first() { // should be last
            out.add("first");
        }

        void second() { // should be 2nd
            out.add("second");
        }

        void third() { // should be 1st
            out.add("third");
        }
    }


    public static void main(String[] args) throws InterruptedException {
        CopyOnWriteArrayList<String> out = new CopyOnWriteArrayList<>();
        Foo foo = new Foo(out);

        Thread firstThread = new Thread(() -> foo.third());
        Thread secondThread = new Thread(
                () -> {
                    try {
                        firstThread.join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    foo.second();
                }
        );
        Thread thirdThread = new Thread(
                () -> {
                    try {
                        secondThread.join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    foo.first();
                }
        );
        firstThread.start();
        secondThread.start();
        thirdThread.start();
        firstThread.join();
        secondThread.join();
        thirdThread.join();
        System.out.println(foo.out);
    }
}
