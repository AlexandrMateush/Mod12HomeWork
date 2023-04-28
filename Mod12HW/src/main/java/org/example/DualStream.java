package org.example;

public class DualStream extends Thread {
    static boolean a = true;

    public boolean isA() {
        return a;
    }

    public void setA(boolean a) {
        this.a = a;
    }

    @Override
    public void run() {
        while (true){
            try {
                DualStream.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (a) {
                System.out.println("One second passed");
            }

        }
    }

    public static void main(String[] args) throws InterruptedException {
        DualStream dualStream = new DualStream();
        dualStream.start();

        Thread secondThread = new Thread(() -> {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {

                while (a) {
                    Thread.sleep(4000);
                    System.out.println("Five second passed");
                    dualStream.setA(false);
                    dualStream.join(1000);
                    dualStream.setA(true);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
        secondThread.start();
        dualStream.join();

    }


}

