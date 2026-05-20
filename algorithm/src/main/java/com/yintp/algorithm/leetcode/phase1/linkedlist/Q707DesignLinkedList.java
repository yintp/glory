package com.yintp.algorithm.leetcode.phase1.linkedlist;

/**
 * #707 设计链表 (Design Linked List) - 中等
 * Topic: 链表 | Phase1 Day3
 *
 * 实现 get/addAtHead/addAtTail/addAtIndex/deleteAtIndex，可选单链表或双链表。
 *
 * 思路：复用 MyLinkedList 的双向链表实现（虚拟头尾节点）
 */
public class Q707DesignLinkedList {
    private static class Node {
        int val;
        Node prev, next;
        Node(int val) { this.val = val; }
    }

    private final Node dummy;
    private final Node tail;
    private int size;

    public Q707DesignLinkedList() {
        dummy = new Node(0);
        tail = new Node(0);
        dummy.next = tail;
        tail.prev = dummy;
        size = 0;
    }

    public int get(int index) {
        if (index < 0 || index >= size) return -1;
        Node cur = dummy.next;
        for (int i = 0; i < index; i++) cur = cur.next;
        return cur.val;
    }

    public void addAtHead(int val) { addAtIndex(0, val); }
    public void addAtTail(int val) { addAtIndex(size, val); }

    public void addAtIndex(int index, int val) {
        if (index < 0 || index > size) return;
        Node pred = dummy;
        for (int i = 0; i < index; i++) pred = pred.next;
        Node newNode = new Node(val);
        newNode.next = pred.next;
        newNode.prev = pred;
        pred.next.prev = newNode;
        pred.next = newNode;
        size++;
    }

    public void deleteAtIndex(int index) {
        if (index < 0 || index >= size) return;
        Node pred = dummy;
        for (int i = 0; i < index; i++) pred = pred.next;
        pred.next = pred.next.next;
        pred.next.prev = pred;
        size--;
    }
}
