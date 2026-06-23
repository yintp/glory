package com.yintp.algorithm.leetcode.topic2;

/**
 * #76 最小覆盖子串 (Minimum Window Substring) - Hard
 * 专题: 滑动窗口 | 第4周 Day4
 *
 * 给你一个字符串 s、一个字符串 t，返回 s 中涵盖 t 所有字符的最小子串。
 * 如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 ""。
 *
 * 示例: s="ADOBECODEBANC", t="ABC" → "BANC"
 *
 * 思路: 滑动窗口 + need/window 两个哈希表，valid 计数器判断窗口是否满足
 */
import java.util.HashMap;
import java.util.Map;

public class Q76MinWindow {
    public String minWindow(String s, String t) {
        Map<Character, Integer> need = new HashMap<>();
        Map<Character, Integer> window = new HashMap<>();
        for (char c : t.toCharArray()) need.put(c, need.getOrDefault(c, 0) + 1);
        int left = 0, right = 0, valid = 0, start = 0, minLen = Integer.MAX_VALUE;
        while (right < s.length()) {
            char c = s.charAt(right++);
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (window.get(c).equals(need.get(c))) valid++;
            }
            while (valid == need.size()) {
                if (right - left < minLen) {
                    start = left;
                    minLen = right - left;
                }
                char d = s.charAt(left++);
                if (need.containsKey(d)) {
                    if (window.get(d).equals(need.get(d))) valid--;
                    window.put(d, window.get(d) - 1);
                }
            }
        }
        return minLen == Integer.MAX_VALUE ? "" : s.substring(start, start + minLen);
    }
}
