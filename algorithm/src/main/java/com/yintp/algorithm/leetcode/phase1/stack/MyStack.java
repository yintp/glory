package com.yintp.algorithm.leetcode.phase1.stack;

import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * 手写实现：栈（数组实现，自动扩容）
 * 掌握要点：LIFO，push/pop/peek 均为 O(1)，扩容策略（2倍）
 *
 * 时间复杂度：
 *   - push：均摊 O(1)
 *   - pop：O(1)
 *   - peek：O(1)
 * 空间复杂度：O(n)
 */
public class MyStack {
    private int[] data;
    private int top;

    public MyStack() {
        data = new int[8];
        top = -1;
    }

    public void push(int val) {
        if (top + 1 == data.length) {
            data = Arrays.copyOf(data, data.length * 2);
        }
        data[++top] = val;
    }

    public int pop() {
        if (isEmpty()) throw new EmptyStackException();
        return data[top--];
    }

    public int peek() {
        if (isEmpty()) throw new EmptyStackException();
        return data[top];
    }

    public boolean isEmpty() { return top == -1; }
    public int size() { return top + 1; }
}
