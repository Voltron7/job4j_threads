package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> System.out.println("First thread name: "
                        + Thread.currentThread().getName())
        );
        Thread second = new Thread(
                () -> System.out.println("Second thread name: "
                        + Thread.currentThread().getName())
        );
        System.out.println("First thread start state: " + first.getState());
        System.out.println("Second thread start state: " + second.getState());
        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED
                || second.getState() != Thread.State.TERMINATED) {
            System.out.println("First thread state: " + first.getState());
            System.out.println("Second thread state: " + second.getState());
        }
        System.out.println("First thread final state: " + first.getState());
        System.out.println("Second thread final state: " + second.getState());
        System.out.println(Thread.currentThread().getName() + " thread is terminated");
    }
}
