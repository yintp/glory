package com.yintp.algorithm.leetcode.topic8;

/**
 * #72 编辑距离 (Edit Distance) - Medium
 * 专题: 动态规划 | 第10周 Day3
 *
 * 给你两个单词 word1 和 word2，返回将 word1 转换成 word2 所使用的最少操作数（插入/删除/替换）。
 *
 * 示例: word1="horse", word2="ros" → 3
 *
 * 思路: 二维DP
 *   dp[i][j]: word1[0..i-1] 转换为 word2[0..j-1] 的最少操作数
 *   若 word1[i-1]==word2[j-1]: dp[i][j]=dp[i-1][j-1]
 *   否则: dp[i][j]=min(dp[i-1][j-1], dp[i-1][j], dp[i][j-1]) + 1
 *         (分别对应: 替换, 删除, 插入)
 */
public class Q72MinDistance {
    public int minDistance(String word1, String word2) {
        // TODO: 二维DP编辑距离
        return 0;
    }
}
