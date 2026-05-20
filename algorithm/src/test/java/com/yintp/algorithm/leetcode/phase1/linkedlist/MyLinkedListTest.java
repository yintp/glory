package com.yintp.algorithm.leetcode.phase1.linkedlist;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MyLinkedListTest {
    private MyLinkedList list;

    @Before
    public void setUp() { list = new MyLinkedList(); }

    @Test
    public void testAddAndGet() {
        list.addAtTail(1); list.addAtTail(2); list.addAtTail(3);
        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));
    }

    @Test
    public void testAddAtHead() {
        list.addAtTail(2); list.addAtHead(1);
        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
    }

    @Test
    public void testAddAtIndex() {
        list.addAtTail(1); list.addAtTail(3);
        list.addAtIndex(1, 2);
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));
    }

    @Test
    public void testDeleteAtIndex() {
        list.addAtTail(1); list.addAtTail(2); list.addAtTail(3);
        list.deleteAtIndex(1);
        assertEquals(2, list.size());
        assertEquals(3, list.get(1));
    }

    @Test
    public void testGetInvalid() {
        assertEquals(-1, list.get(0));
    }
}
