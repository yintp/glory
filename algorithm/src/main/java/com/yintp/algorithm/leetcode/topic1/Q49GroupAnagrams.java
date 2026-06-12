package com.yintp.algorithm.leetcode.topic1;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * #49 字母异位词分组 (Group Anagrams) - Medium
 * 专题: 数组 & 哈希表 | 第3周 Day2
 *
 * 给你一个字符串数组，将字母异位词组合在一起，可以按任意顺序返回结果列表。
 * 字母异位词: 重新排列字母可以得到另一个单词，字母种类和数量相同。
 *
 * 示例: ["eat","tea","tan","ate","nat","bat"] → [["bat"],["nat","tan"],["ate","eat","tea"]]
 *
 * 思路: 排序后的字符串作为 HashMap 的 key，相同 key 的单词是异位词
 */
public class Q49GroupAnagrams {
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for (String s : strs) {
            char[] chars = s.toCharArray();
            java.util.Arrays.sort(chars);
            String key = new String(chars);
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
        }
        return new ArrayList<>(map.values());
    }
}
