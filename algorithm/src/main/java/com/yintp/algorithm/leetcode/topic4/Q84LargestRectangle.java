package com.yintp.algorithm.leetcode.topic4;

/**
 * #84 柱状图中最大的矩形 (Largest Rectangle in Histogram) - Hard
 * 专题: 单调栈 | 第6周 Day4
 *
 * 给定 n 个非负整数，用来表示柱状图中各个柱子的高度，每个柱子彼此相邻且宽度为 1。
 * 求在该柱状图中，能够勾勒出来的矩形的最大面积。
 *
 * 示例: [2,1,5,6,2,3] → 10
 *
 * 思路: 单调递增栈 - 当前柱子比栈顶矮时，弹出计算以栈顶为高的矩形面积
 *       技巧: 两端各添加哨兵(高度为0)简化边界处理
 */
public class Q84LargestRectangle {
    public int largestRectangleArea(int[] heights) {
        // TODO: 单调递增栈 + 哨兵
        return 0;
    }
}
