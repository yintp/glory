package com.yintp.algorithm.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title：串联所有单词的子串
 * Desc：给定一个字符串 s 和一些 长度相同 的单词 words 。找出 s 中恰好可以由 words 中所有单词串联形成的子串的起始位置。
 * 注意子串要与 words 中的单词完全匹配，中间不能有其他字符 ，但不需要考虑 words 中单词串联的顺序。
 * Demo1：
 * 输入：s = "barfoothefoobarman", words = ["foo","bar"]
 * 输出：[0,9]
 * Demo2：
 * 输入：s = "wordgoodgoodgoodbestword", words = ["word","good","best","word"]
 * 输出：[]
 * Demo3：
 * 输入：s = "barfoofoobarthefoobarman", words = ["bar","foo","the"]
 * 输出：[6,9,12]
 * 提示：
 * 1 <= s.length <= 10⁴
 * s 由小写英文字母组成
 * 1 <= words.length <= 5000
 * 1 <= words[i].length <= 30
 * words[i] 由小写英文字母组成
 *
 * @author yintp
 */
public class Q30FindSubstring {
    /**
     * 思路：
     * 滑动窗口判断是否符合所有子串
     */
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> result = new ArrayList<>();
        int wordsSize = words.length;
        int wordsLen = words[0].length();
        int c = (int) (words.length / 0.75) + 1;
        int totalWordsLen = wordsSize * wordsLen;
        Map<String, Integer> wordsMap = wordsCount(words);
        for (int i = 0; i <= s.length() - totalWordsLen; i++) {
            HashMap<String, Integer> hasWordsMap = new HashMap<>(c);
            int count = 0;
            while (count < wordsSize) {
                String word = s.substring(i + count * wordsLen, i + (count + 1) * wordsLen);
                if (wordsMap.containsKey(word)) {
                    Integer wordCount = hasWordsMap.getOrDefault(word, 0);
                    hasWordsMap.put(word, wordCount + 1);
                    if (wordCount + 1 > wordsMap.get(word)) {
                        break;
                    }
                } else {
                    break;
                }
                count++;
            }
            if (count == wordsSize) {
                result.add(i);
            }
        }
        return result;
    }

    private Map<String, Integer> wordsCount(String[] words) {
        int c = (int) (words.length / 0.75) + 1;
        HashMap<String, Integer> wordsMap = new HashMap(c);
        for (String word : words) {
            Integer count = wordsMap.getOrDefault(word, 0);
            wordsMap.put(word, count + 1);
        }
        return wordsMap;
    }
}
