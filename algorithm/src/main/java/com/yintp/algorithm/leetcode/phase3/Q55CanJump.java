package com.yintp.algorithm.leetcode.phase3;

/**
 * #55 跳跃游戏 (Jump Game) - Medium
 * 专题: 贪心 | 第11周 Day1
 *
 * 给定一个非负整数数组 nums，你初始位于数组的第一个下标。
 * 数组中的每个元素代表你在该位置可以跳跃的最大长度。判断你是否能够到达最后一个下标。
 *
 * 示例: [2,3,1,1,4] → true; [3,2,1,0,4] → false
 *
 * 思路: 贪心 - 维护当前能到达的最远位置 maxReach
 *   遍历每个位置，若当前位置超出maxReach则不可达；否则更新maxReach
 */
public class Q55CanJump {
    public boolean canJump(int[] nums) {
        // TODO: 贪心维护最远可达位置
        return false;
    }
}
