package com.yintp.algorithm.leetcode.phase1.linkedlist;

/**
 * 手写实现：双向链表（虚拟头尾节点）
 * 掌握要点：增删操作更新前后指针、虚拟节点简化边界处理
 *
 * 时间复杂度：
 *   - get(index)：O(n)
 *   - addAtHead/Tail：O(1)
 *   - addAtIndex(index)：O(n)
 *   - deleteAtIndex(index)：O(n)
 * 空间复杂度：O(n)
 */
public class MyLinkedList {
    private static class Node {
        int val;
        Node prev, next;
        Node(int val) { this.val = val; }
    }

    private final Node dummy;
    private final Node tail;
    private int size;

    public MyLinkedList() {
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

    public int size() { return size; }
}
