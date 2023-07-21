package io.ylab.intensive.lesson02.Sequences;
/*
    =====================================
    @project ylabOOP
    @created 11/03/2023    
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

public class SequencesImpl implements Sequences {
    @Override
    public void a(int n) {
        if (n <= 0 || n > 10) {
            System.out.println("Wrong param: " + n);
            return;
        }
        for (int i = 1; i <= n; i++) {
            System.out.print(2 * i + " ");
        }
        System.out.println();
    }

    @Override
    public void b(int n) {
        if (n <= 0 || n > 10) {
            System.out.println("Wrong param: " + n);
            return;
        }
        int num = 1;
        for (int i = 0; i < n; i++) {
            System.out.print(num + " ");
            num += 2;
        }
        System.out.println();
    }

    @Override
    public void c(int n) {
        if (n <= 0 || n > 10) {
            System.out.println("Wrong param: " + n);
            return;
        }
        for (int i = 1; i <= n; i++) {
            System.out.print(i * i + " ");
        }
        System.out.println();
    }

    @Override
    public void d(int n) {
        if (n <= 0 || n > 10) {
            System.out.println("Wrong param: " + n);
            return;
        }
        for (int i = 1; i <= n; i++) {
            System.out.print(i * i * i + " ");
        }
        System.out.println();
    }

    @Override
    public void e(int n) {
        if (n <= 0 || n > 10) {
            System.out.println("Wrong param: " + n);
            return;
        }
        for (int i = 1; i <= n; i++) {
            if (i % 2 != 0) {
                System.out.print(1 + " ");
            } else {
                System.out.print(-1 + " ");
            }
        }
        System.out.println();
    }

    @Override
    public void f(int n) {
        if (n <= 0 || n > 10) {
            System.out.println("Wrong param: " + n);
            return;
        }
        for (int i = 1; i <= n; i++) {
            if (i % 2 != 0) {
                System.out.print(i + " ");
            } else {
                System.out.print(-i + " ");
            }
        }
        System.out.println();
    }

    @Override
    public void g(int n) {
        if (n <= 0 || n > 10) {
            System.out.println("Wrong param: " + n);
            return;
        }
        for (int i = 1; i <= n; i++) {
            if (i % 2 != 0) {
                System.out.print(i * i + " ");
            } else {
                System.out.print(-i * i + " ");
            }
        }
        System.out.println();
    }

    @Override
    public void h(int n) {
        if (n <= 0 || n > 10) {
            System.out.println("Wrong param: " + n);
            return;
        }
        int num = 1;
        for (int i = 1; i <= n; i++) {
            if (i % 2 != 0) {
                System.out.print(num++ + " ");
            } else {
                System.out.print(0 + " ");
            }
        }
        System.out.println();
    }

    @Override
    public void i(int n) {
        if (n <= 0 || n > 10) {
            System.out.println("Wrong param: " + n);
            return;
        }
        int num = 1;
        for (int i = 1; i <= n; i++) {
            num *= i;
            System.out.print(num + " ");
        }
        System.out.println();
    }

    @Override
    public void j(int n) {
        if (n <= 0 || n > 10) {
            System.out.println("Wrong param: " + n);
            return;
        }
        int prev = 1;
        int next = 1;
        for (int i = 1; i <= n; i++) {
            int tmp = prev + next;
            System.out.print(prev + " ");
            prev = next;
            next = tmp;
        }
        System.out.println();
    }
}
