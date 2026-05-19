package com.yintp.algorithm.leetcode.topic3;

import com.yintp.algorithm.leetcode.structure.ListNode;
import org.junit.Assert;
import org.junit.Test;

public class Q23MergeKListsTest {

    private final Q23MergeKLists solution = new Q23MergeKLists();

    private ListNode build(int... vals) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        for (int v : vals) { cur.next = new ListNode(v); cur = cur.next; }
        return dummy.next;
    }

    @Test
    public void testCase1() {
        ListNode[] lists = {build(1, 4, 5), build(1, 3, 4), build(2, 6)};
        ListNode result = solution.mergeKLists(lists);
        Assert.assertArrayEquals(new int[]{1, 1, 2, 3, 4, 4, 5, 6}, result.toArray());
    }

    @Test
    public void testEmpty() {
        Assert.assertNull(solution.mergeKLists(new ListNode[]{}));
    }
}
