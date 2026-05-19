package com.yintp.algorithm.leetcode.topic7;

/**
 * #875 爱吃香蕉的珂珂 (Koko Eating Bananas) - Medium
 * 专题: 二分答案 | 第9周 Day3
 *
 * 珂珂喜欢吃香蕉。有 n 堆香蕉，第 i 堆有 piles[i] 根香蕉。
 * 警卫 h 小时后回来，珂珂以速度 k 根/小时吃香蕉（每小时只能选一堆）。
 * 求珂珂能在 h 小时内吃完所有香蕉的最小速度 k。
 *
 * 思路: 二分答案
 *   - 答案范围: [1, max(piles)]
 *   - check(k): 以速度k是否能在h小时内吃完 → 二分找最小满足条件的k
 */
public class Q875MinEatingSpeed {
    public int minEatingSpeed(int[] piles, int h) {
        // TODO: 二分答案，check函数验证速度k是否可行
        return 0;
    }
}
