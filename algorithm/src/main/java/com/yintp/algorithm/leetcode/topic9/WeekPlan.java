package com.yintp.algorithm.leetcode.topic9;

/**
 * 专题9: 回溯 (穿插第9周)
 *
 * 核心思路: 排列/组合/子集问题，暴力穷举所有可能 + 剪枝
 *
 * 回溯模板:
 * List<List<Integer>> result = new ArrayList<>();
 * List<Integer> path = new ArrayList<>();
 *
 * void backtrack(int[] nums, boolean[] used) {
 *   if (终止条件) { result.add(new ArrayList<>(path)); return; }
 *   for (int i = 0; i < nums.length; i++) {
 *     if (used[i]) continue;       // 剪枝
 *     used[i] = true;
 *     path.add(nums[i]);
 *     backtrack(nums, used);       // 递归
 *     path.remove(path.size()-1);  // 回撤
 *     used[i] = false;
 *   }
 * }
 *
 * ============================================================
 * 每日计划 (穿插在第9周)
 * ============================================================
 *
 * W9 Day3 (周三) - 新题:
 *   Q46 全排列, Q78 子集
 *
 * W9 Day4 (周四) - 新题 + 复习:
 *   新题: Q39 组合总和, Q17 电话号码的字母组合
 *   复习: Q46, Q78
 *
 * W9 Day5 (周五) - 新题 + 复习:
 *   新题: Q51 N皇后
 *   复习: Q39, Q17
 */
public class WeekPlan {
    // 本文件仅作为计划文档，无需实现
}
