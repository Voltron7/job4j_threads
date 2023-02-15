package ru.job4j.pools;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    public static Sums[] sum(int[][] matrix) {
        Sums[] sum = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sum[i] = sumByIndex(matrix, i);
        }
        return sum;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] sum = new Sums[matrix.length];
        return getTask(matrix, sum).get();
    }

    public static CompletableFuture<Sums[]> getTask(int[][] matrix, Sums[] sum) {
        return CompletableFuture.supplyAsync(() -> {
            for (int i = 0; i < matrix.length; i++) {
                sum[i] = sumByIndex(matrix, i);

            }
            return sum;
        });
    }

    public static Sums sumByIndex(int[][] matrix, int index) {
        int rowSum = 0;
        int colSum = 0;
        for (int i = 0; i < matrix.length; i++) {
            rowSum += matrix[index][i];
            colSum += matrix[i][index];
        }
        return new Sums(rowSum, colSum);
    }
}
