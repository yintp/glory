package com.yintp.algorithm.leetcode.topic3;

import com.yintp.algorithm.leetcode.structure.ListNode;
import org.junit.Assert;
import org.junit.Test;

public class Q143ReorderListTest {

    private final Q143ReorderList solution = new Q143ReorderList();

    private ListNode build(int... vals) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        for (int v : vals) { cur.next = new ListNode(v); cur = cur.next; }
        return dummy.next;
    }

    @Test
    public void testCase1() {
        ListNode head = build(1, 2, 3, 4);
        solution.reorderList(head);
        Assert.assertArrayEquals(new int[]{1, 4, 2, 3}, head.toArray());
    }

    @Test
    public void testCase2() {
        ListNode head = build(1, 2, 3, 4, 5);
        solution.reorderList(head);
        Assert.assertArrayEquals(new int[]{1, 5, 2, 4, 3}, head.toArray());
    }
}
