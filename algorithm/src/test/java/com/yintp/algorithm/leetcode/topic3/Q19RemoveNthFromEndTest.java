package com.yintp.algorithm.leetcode.topic3;

import com.yintp.algorithm.leetcode.structure.ListNode;
import org.junit.Assert;
import org.junit.Test;

public class Q19RemoveNthFromEndTest {

    private final Q19RemoveNthFromEnd solution = new Q19RemoveNthFromEnd();

    private ListNode build(int... vals) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        for (int v : vals) { cur.next = new ListNode(v); cur = cur.next; }
        return dummy.next;
    }

    @Test
    public void testCase1() {
        ListNode result = solution.removeNthFromEnd(build(1, 2, 3, 4, 5), 2);
        Assert.assertArrayEquals(new int[]{1, 2, 3, 5}, result.toArray());
    }

    @Test
    public void testRemoveHead() {
        ListNode result = solution.removeNthFromEnd(build(1, 2), 2);
        Assert.assertArrayEquals(new int[]{2}, result.toArray());
    }
}
