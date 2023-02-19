package com.yintp.algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Title：解数独
 * Desc：编写一个程序，通过填充空格来解决数独问题。
 * 数独的解法需 遵循如下规则：
 * 数字 1-9 在每一行只能出现一次。
 * 数字 1-9 在每一列只能出现一次。
 * 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。（请参考示例图）
 * 数独部分空格内已填入了数字，空白格用 '.' 表示。
 * 示例 1：
 * 输入：board =
 * [["5","3",".",".","7",".",".",".","."]
 * ,["6",".",".","1","9","5",".",".","."]
 * ,[".","9","8",".",".",".",".","6","."]
 * ,["8",".",".",".","6",".",".",".","3"]
 * ,["4",".",".","8",".","3",".",".","1"]
 * ,["7",".",".",".","2",".",".",".","6"]
 * ,[".","6",".",".",".",".","2","8","."]
 * ,[".",".",".","4","1","9",".",".","5"]
 * ,[".",".",".",".","8",".",".","7","9"]]
 * 输出：
 * [["5","3","4","6","7","8","9","1","2"]
 * ,["6","7","2","1","9","5","3","4","8"]
 * ,["1","9","8","3","4","2","5","6","7"]
 * ,["8","5","9","7","6","1","4","2","3"]
 * ,["4","2","6","8","5","3","7","9","1"]
 * ,["7","1","3","9","2","4","8","5","6"]
 * ,["9","6","1","5","3","7","2","8","4"]
 * ,["2","8","7","4","1","9","6","3","5"]
 * ,["3","4","5","2","8","6","1","7","9"]]
 * 提示：
 * board.length == 9
 * board[i].length == 9
 * board[i][j] 是一位数字（1-9）或者 '.'
 * 题目数据 保证 输入数独仅有一个解
 *
 * @author yintp
 */
public class Q37SolveSudoku {
    public void solveSudoku(char[][] board) {
        solve(board);
    }

    /**
     * 思路：依次填入数字，如果不满足条件则回溯
     */
    private boolean solve(char[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') {
                    List<Character> nums = remainNums(i, j, board);
                    for (int k = 0; k < nums.size(); k++) {
                        board[i][j] = nums.get(k);
                        if (solve(board)) {
                            return true;
                        } else {
                            // 回溯
                            board[i][j] = '.';
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 获取当前位置的剩余可填入数字
     */
    private List<Character> remainNums(int i, int j, char[][] board) {
        List<Character> nums = new ArrayList<>();
        // 行、列
        for (int k = 0; k < 9; k++) {
            nums.add(board[i][k]);
            nums.add(board[k][j]);
        }
        // 3*3
        int start = i / 3 * 3;
        int end = j / 3 * 3;
        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 3; l++) {
                nums.add(board[k + start][l + end]);
            }
        }
        char count = '1';
        List<Character> allNums = new ArrayList<>();
        while (count <= '9') {
            allNums.add(count);
            count++;
        }
        allNums.removeAll(nums);
        return allNums;
    }
}
