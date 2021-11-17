package ru.job4j.pool;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class RolColSumTest {

  @Test
  void sum() {
    RolColSum.Sums[] sum = RolColSum.sum(new int[][]{{1, 2, 34},
      {1, 24, 3},
      {14, 2, 3}});
    Assertions.assertEquals(sum[0].getColSum(), 37);
    Assertions.assertEquals(sum[1].getColSum(), 28);
    Assertions.assertEquals(sum[2].getColSum(), 19);
    Assertions.assertEquals(sum[0].getRowSum(), 16);
    Assertions.assertEquals(sum[1].getRowSum(), 28);
    Assertions.assertEquals(sum[2].getRowSum(), 40);
  }

  @Test
  void asyncSum() throws ExecutionException, InterruptedException {
    RolColSum.Sums[] sum = RolColSum.asyncSum(new int[][]{{1, 2, 9},
      {1, 8, 3},
      {1, 2, 3}});
    Assertions.assertEquals(sum[0].getColSum(), 12);
    Assertions.assertEquals(sum[1].getColSum(), 12);
    Assertions.assertEquals(sum[2].getColSum(), 6);
    Assertions.assertEquals(sum[0].getRowSum(), 3);
    Assertions.assertEquals(sum[1].getRowSum(), 12);
    Assertions.assertEquals(sum[2].getRowSum(), 15);
  }

  @Test
  void compareTime() throws ExecutionException, InterruptedException {
    int[][] ints = new int[5000][5000];
    for (int i = 0; i < 5000; i++) {
      for (int j = 0; j < 5000; j++) {
        ints[i][j] = (int) (Math.random() * 100);
      }
    }
    long l1 = System.currentTimeMillis();
    RolColSum.Sums[] sum1 = RolColSum.asyncSum(ints);
    long l2 = System.currentTimeMillis();
    RolColSum.Sums[] sum2 = RolColSum.sum(ints);
    long l3 = System.currentTimeMillis();
    Assertions.assertEquals(sum1[25].getRowSum(), sum2[25].getRowSum());
    Assertions.assertEquals(sum1[250].getRowSum(), sum2[250].getRowSum());
    Assertions.assertTrue(l3 - l2 > l2 - l1);


  }
}