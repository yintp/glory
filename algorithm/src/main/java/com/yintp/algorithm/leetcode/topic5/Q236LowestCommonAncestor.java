package com.yintp.algorithm.leetcode.topic5;

import com.yintp.algorithm.leetcode.structure.TreeNode;

/**
 * #236 二叉树的最近公共祖先 (LCA of Binary Tree) - Medium
 * 专题: 二叉树 | 第7周 Day4
 *
 * 给定一个二叉树，找到该树中两个指定节点的最近公共祖先。
 *
 * 思路: 后序递归
 *   - 若root为null或等于p/q，返回root
 *   - 递归左右子树
 *   - 若左右都非null，root就是LCA
 *   - 否则返回非null的一侧
 */
public class Q236LowestCommonAncestor {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // TODO: 后序递归
        return null;
    }
}
