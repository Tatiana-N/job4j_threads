package ru.job4j.pool;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
  public static class Sums {
    private int rowSum = 0;
    private int colSum = 0;

    public int getColSum() {
      return colSum;
    }

    public void setColSum(int colSum) {
      this.colSum = colSum;
    }

    public int getRowSum() {
      return rowSum;
    }

    public void setRowSum(int rowSum) {
      this.rowSum = rowSum;
    }
  }

  public static Sums[] sum(int[][] matrix) {
    Sums[] result = new Sums[matrix.length];
    for (int i = 0; i < matrix.length; i++) {
      Sums sums = new Sums();
      for (int j = 0; j < matrix.length; j++) {
        sums.setColSum(sums.getColSum() + matrix[i][j]);
        sums.setRowSum(sums.getRowSum() + matrix[j][i]);
      }
      result[i] = sums;
    }
    return result;
  }

  public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
    int n = matrix.length;
    Sums[] sums = new Sums[n];
    Map<Integer, CompletableFuture<Sums>> futures = new HashMap<>();
    for (int i = 0; i < n; i++) {
      futures.put(i, getSums(matrix, 0, n - 1, i));
    }
    for (Integer key : futures.keySet()) {
      sums[key] = futures.get(key).get();
    }
    return sums;
  }

  private static CompletableFuture<Sums> getSums(int[][] data, int startRow, int endRow, int index) {
    return CompletableFuture.supplyAsync(() -> {
      Sums sums = new Sums();
      int sumCol = 0;
      int sumRow = 0;
      for (int i = startRow; i <= endRow; i++) {
        sumRow += data[i][index];
        sumCol += data[index][i];
      }
      sums.setColSum(sumCol);
      sums.setRowSum(sumRow);
      return sums;
    });
  }
}