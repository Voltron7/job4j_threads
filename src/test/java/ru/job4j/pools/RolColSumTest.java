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
        assertThat(RolColSum.sumByIndex(matrix, 1).getRowSum()).isEqualTo(15);
        assertThat(RolColSum.sumByIndex(matrix, 1).getColSum()).isEqualTo(15);
    }

    @Test
    public void whenSum() {
        int[][] matrix = {{1, 2, 3},
                          {4, 5, 6},
                          {7, 8, 9}};
        assertThat(RolColSum.sum(matrix).length).isEqualTo(3);
        assertThat(RolColSum.sum(matrix)[2].getRowSum()).isEqualTo(24);
        assertThat(RolColSum.sum(matrix)[2].getColSum()).isEqualTo(18);
    }

    @Test
    public void whenAsyncSum() throws ExecutionException, InterruptedException {
        int[][] matrix = {{1, 2, 3},
                          {4, 5, 6},
                          {7, 8, 9}};
        assertThat(RolColSum.asyncSum(matrix).length).isEqualTo(3);
        assertThat(RolColSum.asyncSum(matrix)[0].getRowSum()).isEqualTo(6);
        assertThat(RolColSum.asyncSum(matrix)[0].getColSum()).isEqualTo(12);
    }
}