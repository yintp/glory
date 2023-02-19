package com.yintp.algorithm.search;

/**
 * 二分搜索
 *
 * @author yintp
 */
public class BinarySearch {
    /**
     * 思路：
     * 递归二分查找
     */
    public int binarySearch(final int[] arr, int value) {
        return binarySearch(arr, 0, arr.length - 1, value);
    }

    private int binarySearch(final int[] arr, int start, int end, int value) {
        if (start > end) {
            return -1;
        }
        int mid = start + (end - start) / 2;
        if (value < arr[mid]) {
            return binarySearch(arr, start, mid - 1, value);
        } else if (value > arr[mid]) {
            return binarySearch(arr, mid + 1, end, value);
        } else {
            return mid;
        }
    }

    /**
     * 思路：
     * 循环二分查找
     */
    public int binarySearch2(final int[] arr, int value) {
        int result = -1;
        int start = 0;
        int end = arr.length - 1;
        int mid;
        while (start <= end) {
            mid = start + (end - start) / 2;
            if (value < arr[mid]) {
                end = mid - 1;
            } else if (value > arr[mid]) {
                start = mid + 1;
            } else {
                result = mid;
                break;
            }
        }
        return result;
    }
}
