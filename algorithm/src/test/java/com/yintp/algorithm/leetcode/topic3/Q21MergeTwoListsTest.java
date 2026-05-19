package com.yintp.algorithm.leetcode.topic3;

import com.yintp.algorithm.leetcode.structure.ListNode;
import org.junit.Assert;
import org.junit.Test;

public class Q21MergeTwoListsTest {

    private final Q21MergeTwoLists solution = new Q21MergeTwoLists();

    private ListNode build(int... vals) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        for (int v : vals) { cur.next = new ListNode(v); cur = cur.next; }
        return dummy.next;
    }

    @Test
    public void testCase1() {
        ListNode result = solution.mergeTwoLists(build(1, 2, 4), build(1, 3, 4));
        Assert.assertArrayEquals(new int[]{1, 1, 2, 3, 4, 4}, result.toArray());
    }

    @Test
    public void testOneEmpty() {
        ListNode result = solution.mergeTwoLists(null, build(1));
        Assert.assertArrayEquals(new int[]{1}, result.toArray());
    }
}
