package com.yintp.algorithm.leetcode;

import com.yintp.algorithm.leetcode.structure.ListNode;

/**
 * Title：K个一组翻转链表
 * Desc：给你链表的头节点 head ，每 k 个节点一组进行翻转，请你返回修改后的链表。
 * k 是一个正整数，它的值小于或等于链表的长度。如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
 * 你不能只是单纯的改变节点内部的值，而是需要实际进行节点交换。
 * Demo1：
 * 输入：head = [1,2,3,4,5], k = 2
 * 输出：[2,1,4,3,5]
 * Demo2：
 * 输入：head = [1,2,3,4,5], k = 3
 * 输出：[3,2,1,4,5]
 * 提示：链表中的节点数目为 n
 * 1 <= k <= n <= 5000
 * 0 <= Node.val <= 1000
 *
 * @author yintp
 */
public class Q25ReverseKGroup {
    /**
     * 思路：
     * 依次遍历获取k个则断开，翻转k个，拼接链表
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode r = new ListNode(-1);
        ListNode pre = r;

        ListNode tmp;
        ListNode n;
        ListNode tail;
        while (head != null) {
            tmp = head;
            n = tmp;
            tail = head;
            int i = k;
            while (i > 0 && head != null) {
                if (i != 1) {
                    n = n.next;
                }
                head = head.next;
                i--;
            }
            if (n != null) {
                n.next = null;
            }
            if (i == 0) {
                ListNode newSub = reverse(tmp);
                pre.next = newSub;
                pre = tail;
                pre.next = null;
            } else {
                pre.next = tmp;
            }
        }
        return r.next;
    }

    /**
     * 翻转链表
     * 思路：
     * 遍历列表，使用头插法得到翻转链表
     */
    public ListNode reverse(ListNode head) {
        ListNode newHead = null;
        ListNode tmp;
        while (head != null) {
            tmp = head;
            head = head.next;
            tmp.next = newHead;
            newHead = tmp;
        }
        return newHead;
    }
}
