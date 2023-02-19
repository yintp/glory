package com.yintp.algorithm.search;

/**
 * 顺序搜索
 *
 * @author yintp
 */
public class SequenceSearch {
    /**
     * 思路：
     * 循环顺序搜索
     */
    public int sequenceSearch(final int[] arr, int value) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == value) {
                return i;
            }
        }
        return -1;
    }
}
