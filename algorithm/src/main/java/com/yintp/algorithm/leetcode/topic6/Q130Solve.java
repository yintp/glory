package com.yintp.algorithm.leetcode.topic6;

/**
 * #130 被围绕的区域 (Surrounded Regions) - Medium
 * 专题: 图/DFS | 第8周 Day4
 *
 * 给你一个 m×n 的矩阵 board，由字符 'X' 和 'O' 组成。
 * 捕获所有被 'X' 围绕的 'O' 区域（将它们替换为 'X'），不被围绕的'O'保留。
 *
 * 思路: 边界DFS
 *   - 从四条边界上的'O'出发DFS，将可达的'O'标记为临时符号(如'S')
 *   - 遍历矩阵：'O'变'X'，'S'还原为'O'
 */
public class Q130Solve {
    public void solve(char[][] board) {
        // TODO: 边界DFS标记 + 最终转换
    }

    private void dfs(char[][] board, int r, int c) {
        // TODO: 将边界可达的'O'标记为'S'
    }
}
