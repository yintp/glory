package com.yintp.algorithm.leetcode.phase1.hashtable;

/**
 * 手写实现：哈希表（链地址法）
 * 掌握要点：hash 函数、链地址法处理冲突、load factor 触发扩容
 *
 * 时间复杂度：
 *   - put(key, val)：均摊 O(1)
 *   - get(key)：均摊 O(1)
 *   - remove(key)：均摊 O(1)
 * 空间复杂度：O(n)
 */
public class MyHashMap {
    private static final double LOAD_FACTOR = 0.75;

    private Entry[] buckets;
    private int size;

    private static class Entry {
        int key, value;
        Entry next;
        Entry(int key, int value) { this.key = key; this.value = value; }
    }

    public MyHashMap() {
        buckets = new Entry[16];
        size = 0;
    }

    private int hash(int key) {
        return Math.abs(key % buckets.length);
    }

    public void put(int key, int value) {
        int idx = hash(key);
        for (Entry e = buckets[idx]; e != null; e = e.next) {
            if (e.key == key) { e.value = value; return; }
        }
        Entry newEntry = new Entry(key, value);
        newEntry.next = buckets[idx];
        buckets[idx] = newEntry;
        size++;
        if (size > buckets.length * LOAD_FACTOR) resize();
    }

    public int get(int key) {
        int idx = hash(key);
        for (Entry e = buckets[idx]; e != null; e = e.next) {
            if (e.key == key) return e.value;
        }
        return -1;
    }

    public void remove(int key) {
        int idx = hash(key);
        if (buckets[idx] == null) return;
        if (buckets[idx].key == key) { buckets[idx] = buckets[idx].next; size--; return; }
        for (Entry e = buckets[idx]; e.next != null; e = e.next) {
            if (e.next.key == key) { e.next = e.next.next; size--; return; }
        }
    }

    public boolean containsKey(int key) {
        int idx = hash(key);
        for (Entry e = buckets[idx]; e != null; e = e.next) {
            if (e.key == key) return true;
        }
        return false;
    }

    private void resize() {
        Entry[] old = buckets;
        buckets = new Entry[old.length * 2];
        size = 0;
        for (Entry e : old) {
            while (e != null) { put(e.key, e.value); e = e.next; }
        }
    }
}
