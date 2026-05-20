package com.yintp.algorithm.leetcode.phase1.queue;

import java.util.NoSuchElementException;

/**
 * 手写实现：循环数组队列
 * 掌握要点：front/rear 指针用取模实现循环，区分空/满状态用 size 字段
 *
 * 时间复杂度：
 *   - offer：O(1)
 *   - poll：O(1)
 *   - peek：O(1)
 * 空间复杂度：O(capacity)
 */
public class MyQueue {
    private final int[] data;
    private int front, rear, size;

    public MyQueue(int capacity) {
        data = new int[capacity];
        front = 0; rear = 0; size = 0;
    }

    public boolean offer(int val) {
        if (isFull()) return false;
        data[rear] = val;
        rear = (rear + 1) % data.length;
        size++;
        return true;
    }

    public int poll() {
        if (isEmpty()) throw new NoSuchElementException();
        int val = data[front];
        front = (front + 1) % data.length;
        size--;
        return val;
    }

    public int peek() {
        if (isEmpty()) throw new NoSuchElementException();
        return data[front];
    }

    public boolean isEmpty() { return size == 0; }
    public boolean isFull() { return size == data.length; }
    public int size() { return size; }
}
