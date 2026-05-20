package com.yintp.algorithm.leetcode.phase1.linkedlist;

import org.junit.Test;
import static org.junit.Assert.*;

public class Q707DesignLinkedListTest {
    @Test
    public void testLeetCodeExample() {
        Q707DesignLinkedList list = new Q707DesignLinkedList();
        list.addAtHead(1); list.addAtTail(3); list.addAtIndex(1, 2);
        assertEquals(2, list.get(1));
        list.deleteAtIndex(1);
        assertEquals(3, list.get(1));
    }

    @Test
    public void testGetInvalid() {
        Q707DesignLinkedList list = new Q707DesignLinkedList();
        assertEquals(-1, list.get(0));
    }
}
