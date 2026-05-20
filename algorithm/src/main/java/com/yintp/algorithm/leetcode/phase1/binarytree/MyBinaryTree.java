package com.yintp.algorithm.leetcode.phase1.binarytree;

import com.yintp.algorithm.leetcode.structure.TreeNode;
import java.util.ArrayList;
import java.util.List;

/**
 * 手写实现：二叉搜索树（BST）
 * 掌握要点：TreeNode 结构，BST 插入，前/中/后序递归遍历
 *
 * 时间复杂度（均衡 BST）：
 *   - insert：O(log n)
 *   - preorder/inorder/postorder：O(n)
 * 空间复杂度：O(n)
 *
 * 复用 structure.TreeNode（package: com.yintp.algorithm.leetcode.structure）
 */
public class MyBinaryTree {
    private TreeNode root;

    public void insert(int val) {
        root = insertRec(root, val);
    }

    private TreeNode insertRec(TreeNode node, int val) {
        if (node == null) return new TreeNode(val);
        if (val < node.val) node.left = insertRec(node.left, val);
        else if (val > node.val) node.right = insertRec(node.right, val);
        return node;
    }

    public List<Integer> preorder() {
        List<Integer> result = new ArrayList<>();
        preorderRec(root, result);
        return result;
    }

    private void preorderRec(TreeNode node, List<Integer> result) {
        if (node == null) return;
        result.add(node.val);
        preorderRec(node.left, result);
        preorderRec(node.right, result);
    }

    public List<Integer> inorder() {
        List<Integer> result = new ArrayList<>();
        inorderRec(root, result);
        return result;
    }

    private void inorderRec(TreeNode node, List<Integer> result) {
        if (node == null) return;
        inorderRec(node.left, result);
        result.add(node.val);
        inorderRec(node.right, result);
    }

    public List<Integer> postorder() {
        List<Integer> result = new ArrayList<>();
        postorderRec(root, result);
        return result;
    }

    private void postorderRec(TreeNode node, List<Integer> result) {
        if (node == null) return;
        postorderRec(node.left, result);
        postorderRec(node.right, result);
        result.add(node.val);
    }
}
