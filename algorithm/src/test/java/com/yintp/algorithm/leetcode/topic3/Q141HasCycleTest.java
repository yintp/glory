package com.yintp.algorithm.leetcode.topic3;

import com.yintp.algorithm.leetcode.structure.ListNode;
import org.junit.Assert;
import org.junit.Test;

public class Q141HasCycleTest {

    private final Q141HasCycle solution = new Q141HasCycle();

    @Test
    public void testNoCycle() {
        ListNode head = new ListNode(1, new ListNode(2, new ListNode(3)));
        Assert.assertFalse(solution.hasCycle(head));
    }

    @Test
    public void testWithCycle() {
        ListNode node3 = new ListNode(3);
        ListNode node2 = new ListNode(2, node3);
        ListNode node1 = new ListNode(1, node2);
        node3.next = node2; // cycle: 3 → 2
        Assert.assertTrue(solution.hasCycle(node1));
    }
}
