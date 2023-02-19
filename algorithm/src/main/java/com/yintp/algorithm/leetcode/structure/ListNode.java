package com.yintp.algorithm.leetcode.structure;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yintp
 */
public class ListNode {
    public int val;
    public ListNode next;

    public ListNode() {
    }

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    @Override
    public String toString() {
        return "ListNode{" +
                "val=" + val +
                ", next=" + next +
                '}';
    }

    public int[] toArray() {
        ListNode pre = this;
        List<Integer> list = new ArrayList<>();
        while (pre != null) {
            list.add(pre.val);
            pre = pre.next;
        }
        return list.stream().mapToInt(Integer::valueOf).toArray();
    }
}