package com.yintp.algorithm.leetcode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Title：最长有效括号
 * Desc：给你一个只包含 '(' 和 ')' 的字符串，找出最长有效（格式正确且连续）括号子串的长度。
 * Demo1：
 * 输入：s = "(()"
 * 输出：2
 * 解释：最长有效括号子串是 "()"
 * Demo2：
 * 输入：s = ")()())"
 * 输出：4
 * 解释：最长有效括号子串是 "()()"
 * Demo3：
 * 输入：s = ""
 * 输出：0
 * 提示：
 * 0 <= s.length <= 3 * 10⁴
 * s[i] 为 '(' 或 ')'
 *
 * @author yintp
 */
public class Q32LongestValidParentheses {
    /**
     * 思路：遍历所有字符串判断是否有效
     */
    public int longestValidParentheses(String s) {
        int result = 0;
        Deque<Integer> stack = new LinkedList<>();
        stack.push(-1);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.isEmpty()) {
                    stack.push(i);
                } else {
                    result = Math.max(result, i - stack.peek());
                }
            }
        }
        return result;
    }
}
