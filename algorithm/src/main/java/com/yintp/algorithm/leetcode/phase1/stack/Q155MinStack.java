package com.yintp.algorithm.leetcode.phase1.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * #155 最小栈 (Min Stack) - 简单
 * Topic: 栈 | Phase1 Day7
 *
 * 设计一个支持 push/pop/top 和 getMin（O(1) 时间）的栈。
 *
 * 思路：双栈，minStack 同步维护当前最小值，入栈时仅当新值 <= 当前最小才压入 minStack
 */
public class Q155MinStack {
    private final Deque<Integer> stack = new ArrayDeque<>();
    private final Deque<Integer> minStack = new ArrayDeque<>();

    public void push(int val) {
        stack.push(val);
        if (minStack.isEmpty() || val <= minStack.peek()) {
            minStack.push(val);
        }
    }

    public void pop() {
        int val = stack.pop();
        if (val == minStack.peek()) minStack.pop();
    }

    public int top() { return stack.peek(); }

    public int getMin() { return minStack.peek(); }
}
