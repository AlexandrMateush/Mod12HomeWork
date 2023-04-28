package org.example;

import java.util.concurrent.Semaphore;

public class FourStream {
    private final int n;
    private final Semaphore fizzSem = new Semaphore(0);
    private final Semaphore buzzSem = new Semaphore(0);
    private final Semaphore fizzBuzzSem = new Semaphore(0);
    private final Semaphore numSem = new Semaphore(1);

    public FourStream(int n) {
        this.n = n;
    }

    public void fizz() throws InterruptedException {
        for (int i = 3; i <= n; i +=3) {
            if (i % 5 != 0) {
                fizzSem.acquire();
                System.out.println("fizz");
                numSem.release();
            }
        }
    }

    public void buzz() throws InterruptedException {
        for (int i = 5; i <= n; i +=5) {
            if (i % 3 != 0) {
                buzzSem.acquire();
                System.out.println("buzz");
                numSem.release();
            }
        }
    }

    public void fizzbuzz() throws InterruptedException {
        for (int i = 15; i <= n; i += 15) {
            fizzBuzzSem.acquire();
            System.out.println("fizzbuzz");
            numSem.release();
        }
    }

    public void number() throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            numSem.acquire();
            if (i % 3 == 0 && i % 5 == 0) {
                fizzBuzzSem.release();
            } else if (i % 3 == 0) {
                fizzSem.release();
            } else if (i % 5 == 0) {
                buzzSem.release();
            } else {
                System.out.println(i);
                numSem.release();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        FourStream fb = new FourStream(100);

        Thread t1 = new Thread(() -> {
            try {
                fb.fizz();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                fb.buzz();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t3 = new Thread(() -> {
            try {
                fb.fizzbuzz();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t4 = new Thread(() -> {
            try {
                fb.number();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();

    }
}

