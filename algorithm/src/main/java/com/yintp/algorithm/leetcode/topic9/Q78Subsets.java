package com.yintp.algorithm.leetcode.topic9;

import java.util.List;
import java.util.ArrayList;

/**
 * #78 子集 (Subsets) - Medium
 * 专题: 回溯 | 第9周 Day3
 *
 * 给你一个整数数组 nums（元素互不相同），返回该数组所有可能的子集（幂集）。
 *
 * 示例: [1,2,3] → [[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
 *
 * 思路: 回溯 - 每个元素选或不选，start指针避免重复
 */
public class Q78Subsets {
    public List<List<Integer>> subsets(int[] nums) {
        // TODO: 回溯生成所有子集
        return new ArrayList<>();
    }

    private void backtrack(int[] nums, int start, List<Integer> path, List<List<Integer>> result) {
        // TODO: 递归回溯，每步都收集结果
    }
}
