package com.yintp.algorithm.leetcode.test;

import com.yintp.algorithm.leetcode.Q24SwapPairs;
import com.yintp.algorithm.leetcode.structure.ListNode;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author yintp
 */
public class Q24SwapPairsTest {
    private ListNode case1Node;

    @Before
    public void prepare() {
        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(3);
        ListNode n4 = new ListNode(4);
        ListNode n5 = new ListNode(5);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        case1Node = n1;
    }

    @After
    public void destroy() {
        case1Node = null;
    }

    @Test
    public void case1() {
        Q24SwapPairs q24SwapPairs = new Q24SwapPairs();
        ListNode listNode = q24SwapPairs.swapPairs(case1Node);
        int[] expect = {2, 1, 4, 3, 5};
        Assert.assertArrayEquals(expect, listNode.toArray());
    }
}
