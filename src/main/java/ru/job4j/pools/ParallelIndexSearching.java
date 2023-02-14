package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelIndexSearching<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final int from;
    private final int to;
    private final T elementToFind;

    public ParallelIndexSearching(T[] array, int from, int to, T elementToFind) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.elementToFind = elementToFind;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return linearSearchingOfIndex();
        }
        int mid = (from + to) / 2;
        ParallelIndexSearching<T> firstHalfSearcher = new ParallelIndexSearching(
                array, from, mid, elementToFind);
        ParallelIndexSearching<T> secondHalfSearcher = new ParallelIndexSearching(
                array, mid + 1, to, elementToFind);
        firstHalfSearcher.fork();
        secondHalfSearcher.fork();
        return Math.max(firstHalfSearcher.join(), secondHalfSearcher.join());
    }

    public int linearSearchingOfIndex() {
        int result = -1;
        for (int i = from; i <= to; i++) {
            if (array[i].equals(elementToFind)) {
                result = i;
                break;
            }
        }
        return result;
    }

    public static <T> int toSearch(T[] array, T element) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelIndexSearching<T>(
                array, 0, array.length - 1, element));
    }
}
