package com.yintp.algorithm.leetcode.phase1.queue;

/**
 * #622 设计循环队列 (Design Circular Queue) - 中等
 * Topic: 队列 | Phase1 Day9
 *
 * 实现循环队列，支持 enQueue/deQueue/Front/Rear/isEmpty/isFull。
 *
 * 思路：数组 + front/rear/size，用 size 区分空满，rear 指向下一个写入位置
 */
public class Q622DesignCircularQueue {
    private final int[] data;
    private int front, rear, size;

    public Q622DesignCircularQueue(int k) {
        data = new int[k];
        front = 0; rear = 0; size = 0;
    }

    public boolean enQueue(int value) {
        if (isFull()) return false;
        data[rear] = value;
        rear = (rear + 1) % data.length;
        size++;
        return true;
    }

    public boolean deQueue() {
        if (isEmpty()) return false;
        front = (front + 1) % data.length;
        size--;
        return true;
    }

    public int Front() { return isEmpty() ? -1 : data[front]; }

    public int Rear() { return isEmpty() ? -1 : data[(rear - 1 + data.length) % data.length]; }

    public boolean isEmpty() { return size == 0; }

    public boolean isFull() { return size == data.length; }
}
