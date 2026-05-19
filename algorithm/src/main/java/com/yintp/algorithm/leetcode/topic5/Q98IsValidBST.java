package com.yintp.algorithm.leetcode.topic5;

import com.yintp.algorithm.leetcode.structure.TreeNode;

/**
 * #98 验证二叉搜索树 (Validate Binary Search Tree) - Medium
 * 专题: 二叉树/BST | 第7周 Day3
 *
 * 给你一个二叉树的根节点 root，判断其是否是一个有效的二叉搜索树。
 * BST定义: 左子树所有节点 < 根节点 < 右子树所有节点，且左右子树也都是BST。
 *
 * 思路: 递归传递合法范围 (min, max)，或利用BST中序遍历有序性
 */
public class Q98IsValidBST {
    public boolean isValidBST(TreeNode root) {
        // TODO: 递归传递合法值范围
        return false;
    }

    private boolean validate(TreeNode node, long min, long max) {
        // TODO: 判断节点值是否在(min, max)范围内
        return false;
    }
}
