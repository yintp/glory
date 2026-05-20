package com.yintp.algorithm.leetcode.phase1.hashtable;

/**
 * #706 设计哈希映射 (Design HashMap) - 简单
 * Topic: 哈希表 | Phase1 Day2
 *
 * 不使用任何内建的哈希表库，实现 put/get/remove 操作。
 *
 * 思路：链地址法，数组长度 769（质数），每个桶存 [key, value] 对的链表
 */
public class Q706DesignHashMap {
    private static final int SIZE = 769;
    private int[][][] buckets;

    public Q706DesignHashMap() {
        buckets = new int[SIZE][][];
    }

    public void put(int key, int value) {
        int idx = key % SIZE;
        if (buckets[idx] == null) { buckets[idx] = new int[][]{{key, value}}; return; }
        for (int[] pair : buckets[idx]) {
            if (pair[0] == key) { pair[1] = value; return; }
        }
        int[][] old = buckets[idx];
        int[][] neo = new int[old.length + 1][];
        System.arraycopy(old, 0, neo, 0, old.length);
        neo[old.length] = new int[]{key, value};
        buckets[idx] = neo;
    }

    public int get(int key) {
        int idx = key % SIZE;
        if (buckets[idx] == null) return -1;
        for (int[] pair : buckets[idx]) if (pair[0] == key) return pair[1];
        return -1;
    }

    public void remove(int key) {
        int idx = key % SIZE;
        if (buckets[idx] == null) return;
        int pos = -1;
        for (int i = 0; i < buckets[idx].length; i++) {
            if (buckets[idx][i][0] == key) { pos = i; break; }
        }
        if (pos == -1) return;
        int[][] old = buckets[idx];
        int[][] neo = new int[old.length - 1][];
        System.arraycopy(old, 0, neo, 0, pos);
        System.arraycopy(old, pos + 1, neo, pos, old.length - pos - 1);
        buckets[idx] = neo;
    }
}
