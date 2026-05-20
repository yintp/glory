package com.yintp.algorithm.leetcode.phase1.binarytree;

import com.yintp.algorithm.leetcode.structure.TreeNode;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * #94 二叉树的中序遍历 (Binary Tree Inorder Traversal) - 简单
 * Topic: 二叉树 | Phase1 Day10
 *
 * 返回二叉树节点值的中序遍历（左→根→右）。
 *
 * 思路：迭代法，用栈模拟递归。一路向左压栈，弹出时记录值，转向右子树。
 */
public class Q94InorderTraversal {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) { stack.push(cur); cur = cur.left; }
            cur = stack.pop();
            result.add(cur.val);
            cur = cur.right;
        }
        return result;
    }
}
