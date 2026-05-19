package com.yintp.algorithm.leetcode.phase3;

import java.util.List;
import java.util.ArrayList;

/**
 * #212 单词搜索 II (Word Search II) - Hard
 * 专题: Trie + 回溯 | 第11周 Day5
 *
 * 给定一个 m×n 网格字母板 board 和一个单词列表 words，返回所有在板上可以找到的单词。
 *
 * 思路: Trie + DFS回溯
 *   - 将所有words构建为Trie
 *   - 对board每个格子出发DFS，同步在Trie中检查
 *   - 到达Trie单词节点则加入结果
 */
public class Q212FindWords {
    public List<String> findWords(char[][] board, String[] words) {
        // TODO: 构建Trie + DFS回溯遍历board
        return new ArrayList<>();
    }
}
