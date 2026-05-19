package com.yintp.algorithm.leetcode.topic7;

/**
 * #74 搜索二维矩阵 (Search a 2D Matrix) - Medium
 * 专题: 二分搜索 | 第9周 Day1
 *
 * 给你一个满足下述两条属性的 m×n 整数矩阵：
 * - 每行中的整数从左到右按非严格递增顺序排列
 * - 每行的第一个整数大于前一行的最后一个整数
 * 给你一个整数 target，如果 target 在矩阵中，返回 true；否则返回 false。
 *
 * 思路: 将二维展开为一维，对 m*n 做标准二分
 *       mid_val = matrix[mid/n][mid%n]
 */
public class Q74SearchMatrix {
    public boolean searchMatrix(int[][] matrix, int target) {
        // TODO: 二维展开为一维二分
        return false;
    }
}
