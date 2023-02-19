package com.yintp.algorithm.leetcode;

import com.yintp.algorithm.leetcode.structure.ListNode;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author yintp
 */
public class Q25ReverseKGroupTest {
    private ListNode case1Node;
    private ListNode case2Node;

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
        case2Node = n5;
    }

    @After
    public void destroy() {
        case1Node = null;
        case2Node = null;
    }

    @Test
    public void testCase1ForReverse() {
        Q25ReverseKGroup q25ReverseKGroup = new Q25ReverseKGroup();
        ListNode listNode = q25ReverseKGroup.reverse(case1Node);
        int[] expect = {5, 4, 3, 2, 1};
        Assert.assertArrayEquals(expect, listNode.toArray());
    }

    @Test
    public void testCase1() {
        Q25ReverseKGroup q25ReverseKGroup = new Q25ReverseKGroup();
        ListNode listNode = q25ReverseKGroup.reverseKGroup(case1Node, 3);
        int[] expect = {3, 2, 1, 4, 5};
        Assert.assertArrayEquals(expect, listNode.toArray());
    }

    @Test
    public void testCase1For2() {
        Q25ReverseKGroup q25ReverseKGroup = new Q25ReverseKGroup();
        ListNode listNode = q25ReverseKGroup.reverseKGroup2(case1Node, 3);
        int[] expect = {3, 2, 1, 4, 5};
        Assert.assertArrayEquals(expect, listNode.toArray());
    }
}
