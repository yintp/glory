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
     * 思路：双指针遍历所有字符串判断是否有效
     */
    public int longestValidParentheses(String s) {
        int l = 0, result = 0;
        while (l <= s.length()) {
            int r = l + 1;
            while (r <= s.length()) {
                String substring = s.substring(l, r);
                boolean valid = isValidParentheses(substring);
                if (valid && substring.length() > result) {
                    result = substring.length();
                }
                r++;
            }
            l++;
        }
        return result;
    }

    private boolean isValidParentheses(String s) {
        boolean result = true;
        Deque<Character> stack = new LinkedList<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if ('(' == c) {
                stack.push(c);
            } else {
                if (stack.size() == 0) {
                    result = false;
                    break;
                }
                Character pop = stack.pop();
                if (pop == null) {
                    result = false;
                    break;
                }
            }
        }
        if (stack.size() > 0) {
            result = false;
        }
        return result;
    }
}
