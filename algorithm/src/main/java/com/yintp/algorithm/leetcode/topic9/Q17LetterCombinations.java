package com.yintp.algorithm.leetcode.topic9;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * #17 电话号码的字母组合 (Letter Combinations of a Phone Number) - Medium
 * 专题: 回溯 | 第9周 Day4
 *
 * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。
 *
 * 示例: "23" → ["ad","ae","af","bd","be","bf","cd","ce","cf"]
 *
 * 思路: 回溯 - 按位置逐一选择当前数字对应的字母
 */
public class Q17LetterCombinations {
    private static final Map<Character, String> PHONE_MAP = new HashMap<>();
    static {
        PHONE_MAP.put('2', "abc"); PHONE_MAP.put('3', "def");
        PHONE_MAP.put('4', "ghi"); PHONE_MAP.put('5', "jkl");
        PHONE_MAP.put('6', "mno"); PHONE_MAP.put('7', "pqrs");
        PHONE_MAP.put('8', "tuv"); PHONE_MAP.put('9', "wxyz");
    }

    public List<String> letterCombinations(String digits) {
        // TODO: 回溯枚举字母组合
        return new ArrayList<>();
    }

    private void backtrack(String digits, int index, StringBuilder path, List<String> result) {
        // TODO: 按位置选择字母，递归回溯
    }
}
