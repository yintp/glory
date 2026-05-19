package com.yintp.algorithm.leetcode.topic8;

/**
 * #1143 最长公共子序列 (Longest Common Subsequence) - Medium
 * 专题: 动态规划/LCS | 第10周 Day3
 *
 * 给定两个字符串 text1 和 text2，返回这两个字符串的最长公共子序列的长度。
 *
 * 示例: text1="abcde", text2="ace" → 3 ("ace")
 *
 * 思路: 二维DP
 *   dp[i][j]: text1[0..i-1] 和 text2[0..j-1] 的LCS长度
 *   若 text1[i-1]==text2[j-1]: dp[i][j]=dp[i-1][j-1]+1
 *   否则: dp[i][j]=max(dp[i-1][j], dp[i][j-1])
 */
public class Q1143LongestCommonSubsequence {
    public int longestCommonSubsequence(String text1, String text2) {
        // TODO: 二维DP
        return 0;
    }
}
