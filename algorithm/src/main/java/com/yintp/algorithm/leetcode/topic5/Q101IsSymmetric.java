package com.yintp.algorithm.leetcode.topic5;

import com.yintp.algorithm.leetcode.structure.TreeNode;

/**
 * #101 对称二叉树 (Symmetric Tree) - Easy
 * 专题: 二叉树 | 第7周 Day2
 *
 * 给你一个二叉树的根节点 root，检查它是否轴对称。
 *
 * 思路: 递归比较 - 检查左子树和右子树是否镜像对称
 *       isMirror(left, right): left.val==right.val && left.left镜像right.right && left.right镜像right.left
 */
public class Q101IsSymmetric {
    public boolean isSymmetric(TreeNode root) {
        // TODO: 递归判断是否镜像
        return false;
    }

    private boolean isMirror(TreeNode left, TreeNode right) {
        // TODO: 递归镜像判断
        return false;
    }
}
