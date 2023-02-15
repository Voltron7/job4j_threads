package ru.job4j.pools;

import org.junit.jupiter.api.Test;
import java.util.concurrent.ExecutionException;
import static org.assertj.core.api.Assertions.*;

class RolColSumTest {

    @Test
    public void whenGetSumByIndex() {
        int[][] matrix = {{1, 2, 3},
                          {4, 5, 6},
                          {7, 8, 9}};
        Sums expected = new Sums(15, 15);
        assertThat(RolColSum.sumByIndex(matrix, 1)).isEqualTo(expected);
    }

    @Test
    public void whenSum() {
        int[][] matrix = {{1, 2, 3},
                          {4, 5, 6},
                          {7, 8, 9}};
        Sums expected = new Sums(24, 18);
        assertThat(RolColSum.sum(matrix)[2]).isEqualTo(expected);
    }

    @Test
    public void whenAsyncSum() throws ExecutionException, InterruptedException {
        int[][] matrix = {{1, 2, 3},
                          {4, 5, 6},
                          {7, 8, 9}};
        Sums expected = new Sums(6, 12);
        assertThat(RolColSum.asyncSum(matrix)[0]).isEqualTo(expected);
    }
}