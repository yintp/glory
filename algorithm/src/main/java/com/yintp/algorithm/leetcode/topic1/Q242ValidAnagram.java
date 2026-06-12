package com.yintp.algorithm.leetcode.topic1;

/**
 * #242 有效的字母异位词 (Valid Anagram) - Easy
 * 专题: 数组 & 哈希表 | 第3周 Day2
 *
 * 给定两个字符串 s 和 t，编写一个函数来判断 t 是否是 s 的字母异位词。
 * 字母异位词: 两个字符串包含相同的字符，但排列不同。
 *
 * 示例: s="anagram", t="nagaram" → true; s="rat", t="car" → false
 *
 * 思路: 统计字符频率（int[26] 数组 或 HashMap）
 */
public class Q242ValidAnagram {
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) return false;
        int[] count = new int[26];
        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i) - 'a']++;
            count[t.charAt(i) - 'a']--;
        }
        for (int c : count) {
            if (c != 0) return false;
        }
        return true;
    }
}
