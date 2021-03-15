package ru.job4j.pool;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

public class RolColSumTest {
    @Test
    public void whenSerial() {
        int[][] matrix = {{1, 0, 1}, {0, 1, 0}, {1, 1, 1}};
        RolColSum.Sums[] res = RolColSum.sum(matrix);
        RolColSum.Sums[] expect = {new RolColSum.Sums(2, 2),
                new RolColSum.Sums(1, 2),
                new RolColSum.Sums(3, 2) };
        assertThat(res, is(expect));
    }

    @Test
    public void whenAsync() throws ExecutionException, InterruptedException {
        int[][] matrix = {{1, 0, 1}, {0, 1, 0}, {1, 1, 1}};
        RolColSum.Sums[] res = RolColSum.asyncSum(matrix);
        RolColSum.Sums[] expect = {new RolColSum.Sums(2, 2),
                new RolColSum.Sums(1, 2),
                new RolColSum.Sums(3, 2) };
        assertThat(res, is(expect));
    }
}