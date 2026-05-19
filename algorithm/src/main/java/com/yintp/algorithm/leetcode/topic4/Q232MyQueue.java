package com.yintp.algorithm.leetcode.topic4;

/**
 * #232 用栈实现队列 (Implement Queue using Stacks) - Easy
 * 专题: 栈 & 队列 | 第6周 Day2
 *
 * 请你仅使用两个栈实现先入先出队列，支持 push, pop, peek, empty 操作。
 *
 * 思路: 双栈 - 输入栈 inStack + 输出栈 outStack
 *       push → inStack; pop/peek → 若outStack空则将inStack全部倒入outStack
 */
public class Q232MyQueue {
    // TODO: 声明两个栈 inStack, outStack

    public Q232MyQueue() {
        // TODO: 初始化
    }

    public void push(int x) {
        // TODO: 入 inStack
    }

    public int pop() {
        // TODO: 从 outStack 弹出，若为空先从 inStack 倒入
        return 0;
    }

    public int peek() {
        // TODO: 查看队首元素
        return 0;
    }

    public boolean empty() {
        // TODO: 两个栈都为空才为空
        return false;
    }
}
