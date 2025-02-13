package com.yintp.forkjoin;

import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * @author yintp
 */
public class CalcTask extends RecursiveTask<Long> {

    private int size;
    private List<Long> data;

    public CalcTask(List<Long> list) {
        this.data = list;
        this.size = 4;
    }

    public CalcTask(List<Long> list, int size) {
        this.data = list;
        this.size = size;
    }

    @Override
    protected Long compute() {
        if (data.size() <= size) {
            return data.stream()
                    .peek(System.out::print)
                    .reduce(0L, (x, y) -> x + y);
        } else {
            int middle = data.size() / 2;
            CalcTask subTask1 = new CalcTask(data.subList(0, middle));
            subTask1.fork();
            CalcTask subTask2 = new CalcTask(data.subList(middle, data.size()));
            subTask2.fork();
            return subTask1.join() + subTask2.join();
        }
    }
}
