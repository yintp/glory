package com.yintp.algorithm.leetcode;

import com.yintp.algorithm.leetcode.structure.ListNode;

/**
 * Title：两两交换链表中的节点
 * Desc：给你一个链表，两两交换其中相邻的节点，并返回交换后链表的头节点。你必须在不修改节点内部的值的情况下完成本题（即，只能进行节点交换）。
 * Demo1：
 * 输入：head = [1,2,3,4]
 * 输出：[2,1,4,3]
 * Demo2：
 * 输入：head = []
 * 输出：[]
 * Demo3：
 * 输入：head = [1]
 * 输出：[1]
 * 提示： 链表中节点的数目在范围 [0, 100] 内
 * 0 <= Node.val <= 100
 *
 * @author yintp
 */
public class Q24SwapPairs {
    /**
     * 思路：
     * 临时节点保存间隔节点，若间隔节点不为空，则连接当前节点和间隔节点
     */
    public ListNode swapPairs(ListNode head) {
        ListNode h = new ListNode(-1);
        ListNode pre = h;
        ListNode skip = null;
        while (head != null || skip != null) {
            if (skip == null) {
                skip = head;
                head = head.next;
            } else {
                if (head == null) {
                    pre.next = skip;
                } else {
                    pre.next = head;
                    head = head.next;
                    pre = pre.next;
                    pre.next = skip;
                }
                skip = null;
                pre = pre.next;
            }
        }
        pre.next = null;
        return h.next;
    }

    /**
     * 思路：
     * 递归，交换两个，用交换后的末尾连接递归方法
     */
    public ListNode swapPairs2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode next = head.next;
        head.next = swapPairs2(head.next.next);
        next.next = head;
        return next;
    }
}