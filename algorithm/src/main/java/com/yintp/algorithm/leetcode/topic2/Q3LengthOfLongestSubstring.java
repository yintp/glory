package com.yintp.algorithm.leetcode.topic2;

/**
 * #3 无重复字符的最长子串 (Longest Substring Without Repeating Characters) - Medium
 * 专题: 滑动窗口 | 第4周 Day3
 *
 * 给定一个字符串 s，找出其中不含有重复字符的最长子串的长度。
 *
 * 示例: "abcabcbb" → 3 ("abc"); "bbbbb" → 1 ("b")
 *
 * 思路: 滑动窗口 + HashMap 记录字符最近出现位置
 */
import java.util.HashMap;
import java.util.Map;

public class Q3LengthOfLongestSubstring {
    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> window = new HashMap<>();
        int left = 0, right = 0, maxLen = 0;
        while (right < s.length()) {
            char c = s.charAt(right++);
            window.put(c, window.getOrDefault(c, 0) + 1);
            while (window.get(c) > 1) {
                char d = s.charAt(left++);
                window.put(d, window.get(d) - 1);
            }
            maxLen = Math.max(maxLen, right - left);
        }
        return maxLen;
    }
}
