package com.yintp.algorithm.leetcode.topic2;

/**
 * #11 盛最多水的容器 (Container With Most Water) - Medium
 * 专题: 双指针 | 第4周 Day3
 *
 * 给定一个长度为 n 的整数数组 height，找出其中两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
 *
 * 示例: [1,8,6,2,5,4,8,3,7] → 49
 *
 * 思路: 相向双指针 - 每次移动较矮的一侧，贪心保留可能更大的面积
 */
public class Q11MaxArea {
    public int maxArea(int[] height) {
        int left = 0, right = height.length - 1, maxArea = 0;
        while (left < right) {
            int area = Math.min(height[left], height[right]) * (right - left);
            maxArea = Math.max(maxArea, area);
            if (height[left] < height[right]) left++;
            else right--;
        }
        return maxArea;
    }
}
