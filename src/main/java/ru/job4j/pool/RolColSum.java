package ru.job4j.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private final int rowSum;
        private final int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Sums sums = (Sums) o;
            return rowSum == sums.rowSum
                    && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = calculateSums(matrix, i);
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        List<CompletableFuture<Sums>> sumsFutures = new ArrayList<>(matrix.length);
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            int j = i;
            sumsFutures.add(CompletableFuture.supplyAsync(() -> calculateSums(matrix, j)));
        }
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = sumsFutures.get(i).get();
        }
        return sums;
    }

    private static int calculateSumRow(int[] row) {
        int sum = 0;
        for (int i: row) {
            sum += i;
        }
        return sum;
    }

    private static int calculateSumColumns(int[][] matrix, int index) {
        int sum = 0;
        for (int[] row : matrix) {
            sum += row[index];
        }
        return sum;
    }

    private static Sums calculateSums(int[][] matrix, int index) {
        return new Sums(calculateSumRow(matrix[index]), calculateSumColumns(matrix, index));
    }
}