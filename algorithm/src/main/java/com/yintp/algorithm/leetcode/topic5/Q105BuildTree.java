package com.yintp.algorithm.leetcode.topic5;

import com.yintp.algorithm.leetcode.structure.TreeNode;
import java.util.HashMap;
import java.util.Map;

/**
 * #105 从前序与中序遍历序列构造二叉树 (Construct Binary Tree from Preorder and Inorder) - Medium
 * 专题: 二叉树 | 第7周 Day4
 *
 * 给定两个整数数组 preorder 和 inorder，其中 preorder 是二叉树的先序遍历，
 * inorder 是同一棵树的中序遍历，请构造二叉树并返回其根节点。
 *
 * 思路: 递归 + HashMap优化
 *   - preorder[0] 是根节点
 *   - 在inorder中找根节点位置，划分左右子树
 *   - 递归构建左右子树
 */
public class Q105BuildTree {
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        // TODO: 递归 + HashMap记录中序位置
        return null;
    }
}
