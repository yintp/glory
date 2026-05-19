package com.yintp.algorithm.leetcode.phase3;

/**
 * #208 实现 Trie (前缀树) (Implement Trie) - Medium
 * 专题: Trie | 第11周 Day5
 *
 * 实现 Trie 类，支持 insert, search, startsWith 操作。
 *
 * 思路: 用数组(26个子节点) 或 HashMap 实现字典树
 *   每个节点: children[26] + isEnd标志
 */
public class Q208Trie {
    private TrieNode root;

    private static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isEnd = false;
    }

    public Q208Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        // TODO: 逐字符在Trie中插入
    }

    public boolean search(String word) {
        // TODO: 在Trie中查找完整单词
        return false;
    }

    public boolean startsWith(String prefix) {
        // TODO: 在Trie中查找是否有以prefix开头的单词
        return false;
    }
}
