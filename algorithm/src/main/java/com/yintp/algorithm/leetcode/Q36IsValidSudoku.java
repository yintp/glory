package com.yintp.algorithm.leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * Title：有效的数独
 * Desc：请你判断一个 9 x 9 的数独是否有效。只需要 根据以下规则 ，验证已经填入的数字是否有效即可。
 * 数字 1-9 在每一行只能出现一次。
 * 数字 1-9 在每一列只能出现一次。
 * 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。（请参考示例图）
 * 注意：
 * 一个有效的数独（部分已被填充）不一定是可解的。
 * 只需要根据以上规则，验证已经填入的数字是否有效即可。
 * 空白格用 '.' 表示。
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
 * 输出：true
 * 示例 2：
 * 输入：board =
 * [["8","3",".",".","7",".",".",".","."]
 * ,["6",".",".","1","9","5",".",".","."]
 * ,[".","9","8",".",".",".",".","6","."]
 * ,["8",".",".",".","6",".",".",".","3"]
 * ,["4",".",".","8",".","3",".",".","1"]
 * ,["7",".",".",".","2",".",".",".","6"]
 * ,[".","6",".",".",".",".","2","8","."]
 * ,[".",".",".","4","1","9",".",".","5"]
 * ,[".",".",".",".","8",".",".","7","9"]]
 * 输出：false
 * 解释：除了第一行的第一个数字从 5 改为 8 以外，空格内其他数字均与 示例1 相同。 但由于位于左上角的 3x3 宫内有两个 8 存在, 因此这个数独是无效的。
 * 提示：
 * board.length == 9
 * board[i].length == 9
 * board[i][j] 是一位数字（1-9）或者 '.'
 *
 * @author yintp
 */
public class Q36IsValidSudoku {
    /**
     * 思路：依次判断行、列、3*3是否满足条件
     */
    public boolean isValidSudoku(char[][] board) {
        for (int i = 0; i < 9; i++) {
            if (!rowIsValidSudoku(i, board)) {
                return false;
            }
            if (!columnIsValidSudoku(i, board)) {
                return false;
            }
        }
        for (int i = 0; i < 9; i = i + 3) {
            for (int j = 0; j < 9; j = j + 3) {
                if (!isAllValidSudoku(i, j, board)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isAllValidSudoku(int i, int j, char[][] board) {
        Set<Character> set = new HashSet<>();
        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 3; l++) {
                char c = board[i + k][j + l];
                if ('.' != c && !set.add(c)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean columnIsValidSudoku(int i, char[][] board) {
        Set<Character> set = new HashSet<>();
        for (int j = 0; j < 9; j++) {
            char c = board[j][i];
            if ('.' != c && !set.add(c)) {
                return false;
            }
        }
        return true;
    }

    private boolean rowIsValidSudoku(int i, char[][] board) {
        Set<Character> set = new HashSet<>();
        for (char c : board[i]) {
            if ('.' != c && !set.add(c)) {
                return false;
            }
        }
        return true;
    }
}
