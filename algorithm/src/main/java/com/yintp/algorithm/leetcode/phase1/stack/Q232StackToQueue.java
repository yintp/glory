package com.yintp.algorithm.leetcode.phase1.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * #232 用栈实现队列 (Implement Queue using Stacks) - 简单
 * Topic: 栈 | Phase1 Day8
 *
 * 用两个栈实现 FIFO 队列的 push/pop/peek/empty 操作。
 *
 * 思路：inStack 负责入队，outStack 负责出队。
 *       outStack 空时才将 inStack 全部转移，实现均摊 O(1)。
 */
public class Q232StackToQueue {
    private final Deque<Integer> inStack = new ArrayDeque<>();
    private final Deque<Integer> outStack = new ArrayDeque<>();

    public void push(int x) { inStack.push(x); }

    public int pop() {
        move();
        return outStack.pop();
    }

    public int peek() {
        move();
        return outStack.peek();
    }

    public boolean empty() { return inStack.isEmpty() && outStack.isEmpty(); }

    private void move() {
        if (outStack.isEmpty()) {
            while (!inStack.isEmpty()) outStack.push(inStack.pop());
        }
    }
}
