package com.yintp.algorithm.leetcode.topic1;

import java.util.HashSet;
import java.util.Set;

/**
 * #128 最长连续序列 (Longest Consecutive Sequence) - Medium
 * 专题: 数组 & 哈希表 | 第3周 Day3
 *
 * 给定一个未排序的整数数组 nums，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。
 * 要求时间复杂度 O(n)。
 *
 * 示例: [100,4,200,1,3,2] → 4 (序列 [1,2,3,4])
 *
 * 思路: HashSet + 只从序列起点开始计数
 *   若 num-1 不在 set 中，则 num 是序列起点，向后延伸计数
 */
public class Q128LongestConsecutive {
    public int longestConsecutive(int[] nums) {
        // TODO: HashSet去重，从每个序列起点向后延伸
        return 0;
    }
}
