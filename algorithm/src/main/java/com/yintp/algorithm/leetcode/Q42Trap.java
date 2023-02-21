package com.yintp.algorithm.leetcode;

/**
 * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
 * <p>
 * 示例 1：
 * 输入：height = [0,1,0,2,1,0,1,3,2,1,2,1]
 * 输出：6
 * 解释：上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度图，在这种情况下，可以接 6 个单位的雨水（蓝色部分表示雨水）。
 * <p>
 * 示例 2：
 * 输入：height = [4,2,0,3,2,5]
 * 输出：9
 * <p>
 * 提示：
 * n == height.length
 * 1 <= n <= 2 * 10⁴
 * 0 <= height[i] <= 10⁵
 * Related Topics 栈 数组 双指针 动态规划 单调栈
 *
 * @author yintp
 */
public class Q42Trap {
    /**
     * 思路：双指针取小的一边算可存储水量
     */
    public int trap(int[] height) {
        int result = 0;
        int left = 0, right = height.length - 1, leftMaxHeight = 0, rightMaxHeight = 0;
        while (left < right) {
            if (height[left] < height[right]) {
                if (height[left] > leftMaxHeight) {
                    leftMaxHeight = height[left];
                } else {
                    result += (leftMaxHeight - height[left]);
                }
                left++;
            } else {
                if (height[right] > rightMaxHeight) {
                    rightMaxHeight = height[right];
                } else {
                    result += (rightMaxHeight - height[right]);
                }
                right--;
            }
        }
        return result;
    }
}

