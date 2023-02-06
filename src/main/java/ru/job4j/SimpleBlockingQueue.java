package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    private final int  limit;

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();

    public SimpleBlockingQueue(int limit) {
        this.limit = limit;
    }

    public synchronized void offer(T value) {
        System.out.println("Queue is trying to add"
                + " the element: " + value);
        while (queue.size() == limit) {
            try {
                System.out.println("Queue is full."
                        + " Waiting until the space will be free.");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        queue.offer(value);
        System.out.println("Queue is successfully"
                + " added the element: " + value);
        notifyAll();
    }

    public synchronized T poll() {
        System.out.println("Queue trying to take the element.");
        while (queue.size() == 0) {
            try {
                System.out.println("Queue is empty. "
                        + "Waiting until the element will be added.");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        T result = queue.poll();
        System.out.println("Queue is successfully "
                + "removed the element: " + result);
        notifyAll();
        return result;
    }
}
