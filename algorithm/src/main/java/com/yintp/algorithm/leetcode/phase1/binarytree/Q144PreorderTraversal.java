package com.yintp.algorithm.leetcode.phase1.binarytree;

import com.yintp.algorithm.leetcode.structure.TreeNode;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * #144 二叉树的前序遍历 (Binary Tree Preorder Traversal) - 简单
 * Topic: 二叉树 | Phase1 Day10
 *
 * 返回二叉树节点值的前序遍历（根→左→右）。
 *
 * 思路：迭代法，根节点入栈，弹出记录值，先压右子再压左子（保证左先弹出）。
 */
public class Q144PreorderTraversal {
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            result.add(node.val);
            if (node.right != null) stack.push(node.right);
            if (node.left != null) stack.push(node.left);
        }
        return result;
    }
}
