package com.yintp.algorithm.search;

/**
 * 插入搜索
 *
 * @author yintp
 */
public class InsertionSearch {
    /**
     * 思路：
     * 递归循环插入查找
     */
    public int insertionSearch(final int[] arr, int value) {
        return insertionSearch(arr, 0, arr.length - 1, value);
    }

    private int insertionSearch(final int[] arr, int start, int end, int value) {
        if (start > end) {
            return -1;
        }
        if (arr[start] != arr[end] && value >= arr[start] && value <= arr[end]) {
            int mid = start + ((value - arr[start]) * (end - start) / (arr[end] - arr[start]));
            if (value < arr[mid]) {
                return insertionSearch(arr, start, mid - 1, value);
            } else if (value > arr[mid]) {
                return insertionSearch(arr, mid + 1, end, value);
            } else {
                return mid;
            }
        }
        if (value == arr[start]) {
            return start;
        } else {
            return -1;
        }
    }

    /**
     * 思路：
     * while循环插入查找
     */
    public int insertionSearch2(final int[] arr, int value) {
        int start = 0;
        int end = arr.length - 1;
        int mid;
        while (arr[start] != arr[end] && value >= arr[start] && value <= arr[end]) {
            mid = start + ((value - arr[start]) * (end - start) / (arr[end] - arr[start]));
            if (value < arr[mid]) {
                end = mid - 1;
            } else if (value > arr[mid]) {
                start = mid + 1;
            } else {
                return mid;
            }
        }
        if (value == arr[start]) {
            return start;
        } else {
            return -1;
        }
    }
}
