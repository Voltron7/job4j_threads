package ru.job4j;

public class CountBarrier {
    private final Object monitor = this;
    private final int total;
    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            count++;
            monitor.notifyAll();
        }
    }

    public void await() {
        synchronized (monitor) {
            while (count < total) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) {
        CountBarrier barrier = new CountBarrier(3);
        Thread awaiter1 = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName()
                            + " is sleeping.");
                    barrier.await();
                    System.out.println(Thread.currentThread().getName()
                            + " get started.");
                }
        );
        Thread awaiter2 = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName()
                            + " is sleeping.");
                    barrier.await();
                    System.out.println(Thread.currentThread().getName()
                            + " get started.");
                }
        );
        Thread counter1 = new Thread(
                () -> {
                    barrier.count();
                    System.out.println(Thread.currentThread().getName()
                            + " is increasing counter.");
                }
        );
        Thread counter2 = new Thread(
                () -> {
                    barrier.count();
                    System.out.println(Thread.currentThread().getName()
                            + " is increasing counter.");
                }
        );
        Thread counter3 = new Thread(
                () -> {
                    barrier.count();
                    System.out.println(Thread.currentThread().getName()
                            + " is increasing counter.");
                }
        );
        awaiter1.start();
        awaiter2.start();
        counter1.start();
        counter2.start();
        counter3.start();
    }
}
