package com.yintp.forkjoin;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * @author yintp
 */
public class ForkJoinTest {

    @Test
    public void testCalc() {
        List<Long> list = new ArrayList<>();
        list.add(1L);
        list.add(6L);
        list.add(5L);
        list.add(4L);
        list.add(3L);
        list.add(2L);
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Long> taskFuture = pool.submit(new CalcTask(list));
        try {
            Long aLong = taskFuture.get();
            Assert.assertEquals(21L, aLong.longValue());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
