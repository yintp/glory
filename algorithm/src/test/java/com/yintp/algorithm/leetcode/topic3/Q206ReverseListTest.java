package com.yintp.algorithm.leetcode.topic3;

import com.yintp.algorithm.leetcode.structure.ListNode;
import org.junit.Assert;
import org.junit.Test;

public class Q206ReverseListTest {

    private final Q206ReverseList solution = new Q206ReverseList();

    private ListNode build(int... vals) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        for (int v : vals) {
            cur.next = new ListNode(v);
            cur = cur.next;
        }
        return dummy.next;
    }

    @Test
    public void testCase1() {
        ListNode head = build(1, 2, 3, 4, 5);
        ListNode result = solution.reverseList(head);
        Assert.assertArrayEquals(new int[]{5, 4, 3, 2, 1}, result.toArray());
    }

    @Test
    public void testSingleNode() {
        ListNode head = build(1);
        ListNode result = solution.reverseList(head);
        Assert.assertArrayEquals(new int[]{1}, result.toArray());
    }
}
