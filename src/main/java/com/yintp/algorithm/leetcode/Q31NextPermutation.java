package com.yintp.algorithm.leetcode;

/**
 * Title：下一个排列
 * Desc：整数数组的一个 排列 就是将其所有成员以序列或线性顺序排列。
 * 例如，arr = [1,2,3] ，以下这些都可以视作 arr 的排列：[1,2,3]、[1,3,2]、[3,1,2]、[2,3,1] 。
 * 整数数组的 下一个排列 是指其整数的下一个字典序更大的排列。更正式地，如果数组的所有排列根据其字典顺序从小到大排列在一个容器中，那么数组的 下一个排列 就
 * 是在这个有序容器中排在它后面的那个排列。如果不存在下一个更大的排列，那么这个数组必须重排为字典序最小的排列（即，其元素按升序排列）。
 * 例如，arr = [1,2,3] 的下一个排列是 [1,3,2] 。
 * 类似地，arr = [2,3,1] 的下一个排列是 [3,1,2] 。
 * 而 arr = [3,2,1] 的下一个排列是 [1,2,3] ，因为 [3,2,1] 不存在一个字典序更大的排列。
 * 给你一个整数数组 nums ，找出 nums 的下一个排列。
 * 必须 原地 修改，只允许使用额外常数空间。
 * Demo1：
 * 输入：nums = [1,2,3]
 * 输出：[1,3,2]
 * Demo2：
 * 输入：nums = [3,2,1]
 * 输出：[1,2,3]
 * Demo3：
 * 输入：nums = [1,1,5]
 * 输出：[1,5,1]
 * 提示：
 * 1 <= nums.length <= 100
 * 0 <= nums[i] <= 100
 *
 * @author yintp
 */
public class Q31NextPermutation {
    /**
     * 思路：逆序遍历比较，剩余则进行冒泡排序
     */
    public void nextPermutation(int[] nums) {
        int sl = nums.length - 1;
        while (sl > 0) {
            if (nums[sl] > nums[sl - 1]) {
                break;
            }
            sl--;
        }
        if (sl > 0) {
            int sr = sl;
            int srTmp = sr;
            int minMaxTmp = nums[sl];
            while (srTmp < nums.length) {
                if (nums[srTmp] > nums[sl - 1] && nums[srTmp] < minMaxTmp) {
                    minMaxTmp = nums[srTmp];
                    sr = srTmp;
                }
                srTmp++;
            }
            int tmp = nums[sl - 1];
            nums[sl - 1] = nums[sr];
            nums[sr] = tmp;
        }
        for (int i = sl; i < nums.length - 1; i++) {
            for (int j = sl; j < nums.length - 1 - i + sl; j++) {
                if (nums[j] > nums[j + 1]) {
                    int tmp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = tmp;
                }
            }
        }
    }
}
