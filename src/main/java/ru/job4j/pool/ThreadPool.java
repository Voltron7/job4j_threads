package ru.job4j.pool;

import ru.job4j.SimpleBlockingQueue;
import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final int size = Runtime.getRuntime()
            .availableProcessors();
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks
            = new SimpleBlockingQueue<>(size);

    public ThreadPool() {
        for (int i = 0; i < size; i++) {
            threads.add(new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        Runnable runnable = tasks.poll();
                        runnable.run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
            ));
        }
        for (Thread thread : threads) {
            thread.start();
        }
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool pool = new ThreadPool();
        pool.work(() -> System.out.println("JobNo1 is done"));
        pool.work(() -> System.out.println("JobNo2 is done"));
        pool.work(() -> System.out.println("JobNo3 is done"));
        pool.work(() -> System.out.println("JobNo4 is done"));
        pool.work(() -> System.out.println("JobNo5 is done"));
        pool.work(() -> System.out.println("JobNo6 is done"));
        pool.work(() -> System.out.println("JobNo7 is done"));
        pool.shutdown();
    }
}
