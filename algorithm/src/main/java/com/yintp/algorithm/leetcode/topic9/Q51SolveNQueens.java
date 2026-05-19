package com.yintp.algorithm.leetcode.topic9;

import java.util.List;
import java.util.ArrayList;

/**
 * #51 N 皇后 (N-Queens) - Hard
 * 专题: 回溯 | 第9周 Day5
 *
 * 按照国际象棋的规则，皇后可以攻击与之处在同一行或同一列或同一斜线上的棋子。
 * n 皇后问题：将 n 个皇后放置在 n×n 的棋盘上，且互不相互攻击，返回所有解决方案。
 *
 * 思路: 逐行放置皇后（回溯）
 *   - 用 boolean[] cols, diag1, diag2 记录已占用的列、主对角线、副对角线
 *   - 主对角线特征: row-col 相同; 副对角线特征: row+col 相同
 */
public class Q51SolveNQueens {
    public List<List<String>> solveNQueens(int n) {
        // TODO: 逐行回溯放置皇后
        return new ArrayList<>();
    }

    private void backtrack(int row, int n, int[] queens, boolean[] cols, boolean[] diag1, boolean[] diag2,
                           List<List<String>> result) {
        // TODO: 尝试在当前行每列放置皇后，验证合法性，递归下一行
    }

    private List<String> buildBoard(int[] queens, int n) {
        // TODO: 根据queens数组构造棋盘字符串
        return new ArrayList<>();
    }
}
