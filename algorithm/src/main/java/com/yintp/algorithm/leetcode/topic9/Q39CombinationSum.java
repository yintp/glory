package com.yintp.algorithm.leetcode.topic9;

import java.util.List;
import java.util.ArrayList;

/**
 * #39 组合总和 (Combination Sum) - Medium
 * 专题: 回溯 | 第9周 Day4
 *
 * 给你一个无重复元素的整数数组 candidates 和一个目标整数 target，
 * 找出 candidates 中可以使数字和等于 target 的所有不同组合（同一数字可无限选取）。
 *
 * 示例: candidates=[2,3,6,7], target=7 → [[2,2,3],[7]]
 *
 * 思路: 回溯 + 剪枝（先排序，当前和超过target就剪掉）
 *       允许重复选取：递归时start不+1
 */
public class Q39CombinationSum {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        // TODO: 回溯，允许重复选取元素
        return new ArrayList<>();
    }

    private void backtrack(int[] candidates, int target, int start, List<Integer> path, List<List<Integer>> result) {
        // TODO: 递归回溯，剩余目标为0时收集结果
    }
}
