package com.yintp.algorithm.leetcode.phase1.array;

import java.util.Arrays;

/**
 * 手写实现：动态数组
 * 掌握要点：增删改查、下标访问 O(1)、扩容策略（2倍）
 *
 * 时间复杂度：
 *   - get(index)：O(1)
 *   - add(末尾)：均摊 O(1)
 *   - add(index)：O(n)
 *   - remove(index)：O(n)
 *   - contains(val)：O(n)
 * 空间复杂度：O(n)
 */
public class MyArrayList {
    private int[] data;
    private int size;

    public MyArrayList() {
        this.data = new int[4];
        this.size = 0;
    }

    public void add(int val) {
        ensureCapacity();
        data[size++] = val;
    }

    public void add(int index, int val) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        ensureCapacity();
        System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = val;
        size++;
    }

    public int get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        return data[index];
    }

    public void set(int index, int val) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        data[index] = val;
    }

    public int remove(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        int removed = data[index];
        System.arraycopy(data, index + 1, data, index, size - index - 1);
        size--;
        return removed;
    }

    public boolean contains(int val) {
        for (int i = 0; i < size; i++) {
            if (data[i] == val) return true;
        }
        return false;
    }

    public int size() { return size; }

    private void ensureCapacity() {
        if (size == data.length) {
            data = Arrays.copyOf(data, data.length * 2);
        }
    }
}
