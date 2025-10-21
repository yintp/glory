package com.yintp.algorithm.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 给你一个字符串数组，请你将 字母异位词 组合在一起。可以按任意顺序返回结果列表。
 * <p>
 * <p>
 * <p>
 * 示例 1:
 * <p>
 * 输入: strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
 * <p>
 * 输出: [["bat"],["nat","tan"],["ate","eat","tea"]]
 * <p>
 * 解释：
 * <p>
 * 在 strs 中没有字符串可以通过重新排列来形成 "bat"。
 * 字符串 "nat" 和 "tan" 是字母异位词，因为它们可以重新排列以形成彼此。
 * 字符串 "ate" ，"eat" 和 "tea" 是字母异位词，因为它们可以重新排列以形成彼此。
 * 示例 2:
 * <p>
 * 输入: strs = [""]
 * <p>
 * 输出: [[""]]
 * <p>
 * 示例 3:
 * <p>
 * 输入: strs = ["a"]
 * <p>
 * 输出: [["a"]]
 * <p>
 * <p>
 * <p>
 * 提示：
 * <p>
 * 1 <= strs.length <= 104
 * 0 <= strs[i].length <= 100
 * strs[i] 仅包含小写字母
 *
 * @author zihao.yin
 * @since 2025/10/20 16:25
 */
public class Q49GroupAnagrams {
    public List<List<String>> groupAnagrams(String[] strs) {
        int cap = (int) (strs.length / 0.75) + 1;
        Map<String, List<String>> groupMap = new HashMap<>(cap);
        for (String str : strs) {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);
            List<String> groupList = groupMap.getOrDefault(key, new ArrayList<>());
            groupList.add(str);
            groupMap.putIfAbsent(key, groupList);
        }
        return new ArrayList<>(groupMap.values());
    }
}
