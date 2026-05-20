package com.yintp.algorithm.leetcode.phase1.hashtable;

/**
 * #705 设计哈希集合 (Design HashSet) - 简单
 * Topic: 哈希表 | Phase1 Day2
 *
 * 不使用任何内建的哈希表库，实现 MyHashSet 的 add/remove/contains 操作。
 *
 * 思路：链地址法，数组长度 769（质数减少碰撞）
 */
public class Q705DesignHashSet {
    private static final int SIZE = 769;
    private int[][] buckets;

    public Q705DesignHashSet() {
        buckets = new int[SIZE][];
    }

    public void add(int key) {
        int idx = key % SIZE;
        if (buckets[idx] == null) { buckets[idx] = new int[]{key}; return; }
        if (contains(key)) return;
        int[] old = buckets[idx];
        int[] neo = new int[old.length + 1];
        System.arraycopy(old, 0, neo, 0, old.length);
        neo[old.length] = key;
        buckets[idx] = neo;
    }

    public void remove(int key) {
        int idx = key % SIZE;
        if (buckets[idx] == null) return;
        int pos = -1;
        for (int i = 0; i < buckets[idx].length; i++) {
            if (buckets[idx][i] == key) { pos = i; break; }
        }
        if (pos == -1) return;
        int[] old = buckets[idx];
        int[] neo = new int[old.length - 1];
        System.arraycopy(old, 0, neo, 0, pos);
        System.arraycopy(old, pos + 1, neo, pos, old.length - pos - 1);
        buckets[idx] = neo;
    }

    public boolean contains(int key) {
        int idx = key % SIZE;
        if (buckets[idx] == null) return false;
        for (int v : buckets[idx]) if (v == key) return true;
        return false;
    }
}
