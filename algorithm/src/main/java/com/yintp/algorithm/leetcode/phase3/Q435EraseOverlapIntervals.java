package com.yintp.algorithm.leetcode.phase3;

/**
 * #435 无重叠区间 (Non-overlapping Intervals) - Medium
 * 专题: 贪心/区间 | 第11周 Day2
 *
 * 给你一个区间的集合 intervals，找到需要移除区间的最小数量，使剩余区间互不重叠。
 *
 * 示例: [[1,2],[2,3],[3,4],[1,3]] → 1
 *
 * 思路: 贪心 - 按区间结束时间升序排列
 *   优先保留结束时间早的区间，冲突时移除结束时间晚的（即当前区间）
 *   等价于: 求最多不重叠区间数，答案 = 总数 - 最多不重叠数
 */
public class Q435EraseOverlapIntervals {
    public int eraseOverlapIntervals(int[][] intervals) {
        // TODO: 按结束时间排序 + 贪心
        return 0;
    }
}
