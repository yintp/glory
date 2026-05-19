package com.yintp.algorithm.leetcode.topic5;

import com.yintp.algorithm.leetcode.structure.TreeNode;

/**
 * #124 二叉树中的最大路径和 (Binary Tree Maximum Path Sum) - Hard
 * 专题: 二叉树 | 第7周 Day5
 *
 * 二叉树中的路径被定义为一条从树中任意节点出发，沿父节点-子节点连接，达到任意节点的序列。
 * 同一个节点在一条路径序列中至多出现一次。
 * 给你一个二叉树的根节点 root，返回其最大路径和。
 *
 * 思路: 后序递归
 *   - 对每个节点，计算「经过该节点的最大路径」更新全局最大值
 *   - 返回「包含该节点向下延伸的最大贡献值」给父节点
 */
public class Q124MaxPathSum {
    private int maxSum = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        // TODO: 后序递归，维护全局最大值
        return 0;
    }

    private int dfs(TreeNode node) {
        // TODO: 返回以node为端点向下延伸的最大路径和
        return 0;
    }
}
