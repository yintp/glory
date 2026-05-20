package com.yintp.algorithm.leetcode.phase1.binarytree;

import com.yintp.algorithm.leetcode.structure.TreeNode;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * #145 二叉树的后序遍历 (Binary Tree Postorder Traversal) - 简单
 * Topic: 二叉树 | Phase1 Day10
 *
 * 返回二叉树节点值的后序遍历（左→右→根）。
 *
 * 思路：迭代法，按"根→右→左"顺序压栈，结果用 addFirst 反转为"左→右→根"。
 */
public class Q145PostorderTraversal {
    public List<Integer> postorderTraversal(TreeNode root) {
        LinkedList<Integer> result = new LinkedList<>();
        if (root == null) return result;
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            result.addFirst(node.val);
            if (node.left != null) stack.push(node.left);
            if (node.right != null) stack.push(node.right);
        }
        return result;
    }
}
