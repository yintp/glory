# Phase 1 基础数据结构 Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 在 `algorithm` 模块新建 `phase1/` 前置学习模块，包含 6 种基础数据结构的手写实现 + 配套 LeetCode 题目及测试。

**Architecture:** 在 `com.yintp.algorithm.leetcode.phase1` 下按数据结构分 6 个子包，每个子包含手写实现类（`My*.java`）和对应 LeetCode 题目类（`Q*.java`）。测试文件镜像相同目录结构。二叉树和链表直接复用 `structure/` 包中的 `TreeNode` 和 `ListNode`。

**Tech Stack:** Java 1.8, JUnit 4.11, Maven 3 (`mvn test -Dtest=ClassName` 在 `algorithm/` 目录下执行)

---

## 文件清单

| 文件 | 类型 | 职责 |
|------|------|------|
| `phase1/WeekPlan.java` | 计划文档 | 两周学习日程 + 间隔重复表 |
| `phase1/array/MyArrayList.java` | 实现 | 动态数组，自动扩容 |
| `phase1/array/Q704BinarySearch.java` | 题目 | #704 二分查找 |
| `phase1/hashtable/MyHashMap.java` | 实现 | 链地址法哈希表 |
| `phase1/hashtable/Q705DesignHashSet.java` | 题目 | #705 设计哈希集合 |
| `phase1/hashtable/Q706DesignHashMap.java` | 题目 | #706 设计哈希映射 |
| `phase1/linkedlist/MyLinkedList.java` | 实现 | 双向链表（含虚拟头尾节点） |
| `phase1/linkedlist/Q707DesignLinkedList.java` | 题目 | #707 设计链表 |
| `phase1/stack/MyStack.java` | 实现 | 数组实现栈，自动扩容 |
| `phase1/stack/Q155MinStack.java` | 题目 | #155 最小栈 |
| `phase1/stack/Q232StackToQueue.java` | 题目 | #232 用栈实现队列 |
| `phase1/queue/MyQueue.java` | 实现 | 循环数组队列 |
| `phase1/queue/Q622DesignCircularQueue.java` | 题目 | #622 设计循环队列 |
| `phase1/binarytree/MyBinaryTree.java` | 实现 | BST插入 + 三种遍历（递归） |
| `phase1/binarytree/Q94InorderTraversal.java` | 题目 | #94 中序遍历（迭代） |
| `phase1/binarytree/Q144PreorderTraversal.java` | 题目 | #144 前序遍历（迭代） |
| `phase1/binarytree/Q145PostorderTraversal.java` | 题目 | #145 后序遍历（迭代） |

以上每个 `.java` 文件对应 `src/test/java/...` 下同路径的 `*Test.java`（WeekPlan 除外）。

---

## Task 1: WeekPlan.java

**Files:**
- Create: `algorithm/src/main/java/com/yintp/algorithm/leetcode/phase1/WeekPlan.java`

- [ ] **Step 1: 创建 WeekPlan.java**

```java
package com.yintp.algorithm.leetcode.phase1;

/**
 * Phase 1 - 基础数据结构（第1-2周）
 *
 * 目标：能手写基本数据结构，理解时间/空间复杂度
 *
 * ============================================================
 * 第一周：线性结构（数组、哈希表、链表）
 * ============================================================
 *
 * Day 1（周一）：数组
 *   新题：MyArrayList 手写实现 + Q704 二分查找
 *
 * Day 2（周二）：哈希表
 *   新题：MyHashMap 手写实现 + Q705 设计哈希集合 + Q706 设计哈希映射
 *
 * Day 3（周三）：链表
 *   新题：MyLinkedList 手写实现 + Q707 设计链表
 *   复习：Day 1 所有内容（不看代码重写）
 *
 * Day 4（周四）：
 *   复习：Day 1-2 所有内容
 *
 * Day 5（周五）：
 *   复习：Day 3 所有内容
 *
 * Day 6（周六）：全周总结
 *   - 完善各实现类复杂度注释
 *   - 确认可不看代码手写 MyArrayList、MyHashMap、MyLinkedList
 *
 * ============================================================
 * 第二周：栈、队列、二叉树
 * ============================================================
 *
 * Day 7（周一）：栈
 *   新题：MyStack 手写实现 + Q155 最小栈
 *
 * Day 8（周二）：栈进阶
 *   新题：Q232 用栈实现队列
 *   复习：Day 7 所有内容
 *
 * Day 9（周三）：队列
 *   新题：MyQueue 手写实现 + Q622 设计循环队列
 *
 * Day 10（周四）：二叉树
 *   新题：MyBinaryTree 手写实现 + Q94/Q144/Q145 三种遍历
 *
 * Day 11（周五）：
 *   复习：Day 7-10 所有内容
 *
 * Day 12（周六）：全阶段总结
 *   - 确认可流畅手写全部 6 种数据结构
 *   - 过渡 topic1（数组 & 哈希表专题）
 *
 * ============================================================
 * 间隔重复跟踪
 * ============================================================
 *
 * | 结构/题目       | Day1 | Day2 | Day7  | Day30 |
 * |----------------|------|------|-------|-------|
 * | MyArrayList    | W1D1 | W1D3 | W2D7  | —     |
 * | Q704           | W1D1 | W1D3 | W2D7  | —     |
 * | MyHashMap      | W1D2 | W1D4 | W2D7  | —     |
 * | Q705/Q706      | W1D2 | W1D4 | W2D7  | —     |
 * | MyLinkedList   | W1D3 | W1D5 | W2D8  | —     |
 * | Q707           | W1D3 | W1D5 | W2D8  | —     |
 * | MyStack        | W2D7 | W2D8 | W3D1  | —     |
 * | Q155/Q232      | W2D7 | W2D8 | W3D1  | —     |
 * | MyQueue        | W2D9 | W2D11| W3D1  | —     |
 * | Q622           | W2D9 | W2D11| W3D1  | —     |
 * | MyBinaryTree   | W2D10| W2D11| W3D1  | —     |
 * | Q94/Q144/Q145  | W2D10| W2D11| W3D1  | —     |
 */
public class WeekPlan {
    // 本文件仅作为计划文档，无需实现
}
```

- [ ] **Step 2: 提交**

```bash
git add algorithm/src/main/java/com/yintp/algorithm/leetcode/phase1/WeekPlan.java
git commit -m "feat(algorithm): add phase1 WeekPlan"
```

---

## Task 2: array/MyArrayList

**Files:**
- Create: `algorithm/src/main/java/com/yintp/algorithm/leetcode/phase1/array/MyArrayList.java`
- Create: `algorithm/src/test/java/com/yintp/algorithm/leetcode/phase1/array/MyArrayListTest.java`

- [ ] **Step 1: 写失败测试**

```java
package com.yintp.algorithm.leetcode.phase1.array;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MyArrayListTest {
    private MyArrayList list;

    @Before
    public void setUp() {
        list = new MyArrayList();
    }

    @Test
    public void testAddAndGet() {
        list.add(1); list.add(2); list.add(3);
        assertEquals(3, list.size());
        assertEquals(1, list.get(0));
        assertEquals(3, list.get(2));
    }

    @Test
    public void testAddAtIndex() {
        list.add(1); list.add(3);
        list.add(1, 2);
        assertEquals(3, list.size());
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));
    }

    @Test
    public void testSet() {
        list.add(1); list.add(2);
        list.set(0, 99);
        assertEquals(99, list.get(0));
    }

    @Test
    public void testRemove() {
        list.add(1); list.add(2); list.add(3);
        assertEquals(2, list.remove(1));
        assertEquals(2, list.size());
        assertEquals(3, list.get(1));
    }

    @Test
    public void testAutoResize() {
        for (int i = 0; i < 10; i++) list.add(i);
        assertEquals(10, list.size());
        assertEquals(9, list.get(9));
    }

    @Test
    public void testContains() {
        list.add(5);
        assertTrue(list.contains(5));
        assertFalse(list.contains(99));
    }
}
```

- [ ] **Step 2: 运行确认失败**

```bash
cd D:\code\glory\algorithm
mvn test -Dtest=MyArrayListTest
```

预期：编译错误（类不存在）

- [ ] **Step 3: 实现 MyArrayList.java**

```java
package com.yintp.algorithm.leetcode.phase1.array;

import java.util.Arrays;

/**
 * 手写实现：动态数组
 * 掌握要点：增删改查、下标访问 O(1)、扩容策略（2倍）
 *
 * 时间复杂度：
 *   - get(index)：O(1)
 *   - add(末尾)：均摊 O(1)
 *   - add(index)：O(n)
 *   - remove(index)：O(n)
 *   - contains(val)：O(n)
 * 空间复杂度：O(n)
 */
public class MyArrayList {
    private int[] data;
    private int size;

    public MyArrayList() {
        this.data = new int[4];
        this.size = 0;
    }

    public void add(int val) {
        ensureCapacity();
        data[size++] = val;
    }

    public void add(int index, int val) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        ensureCapacity();
        System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = val;
        size++;
    }

    public int get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        return data[index];
    }

    public void set(int index, int val) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        data[index] = val;
    }

    public int remove(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        int removed = data[index];
        System.arraycopy(data, index + 1, data, index, size - index - 1);
        size--;
        return removed;
    }

    public boolean contains(int val) {
        for (int i = 0; i < size; i++) {
            if (data[i] == val) return true;
        }
        return false;
    }

    public int size() { return size; }

    private void ensureCapacity() {
        if (size == data.length) {
            data = Arrays.copyOf(data, data.length * 2);
        }
    }
}
```

- [ ] **Step 4: 运行确认通过**

```bash
mvn test -Dtest=MyArrayListTest
```

预期：`Tests run: 6, Failures: 0, Errors: 0`

- [ ] **Step 5: 提交**

```bash
git add algorithm/src/main/java/com/yintp/algorithm/leetcode/phase1/array/MyArrayList.java
git add algorithm/src/test/java/com/yintp/algorithm/leetcode/phase1/array/MyArrayListTest.java
git commit -m "feat(algorithm): add phase1 MyArrayList with tests"
```

---

## Task 3: array/Q704BinarySearch

**Files:**
- Create: `algorithm/src/main/java/com/yintp/algorithm/leetcode/phase1/array/Q704BinarySearch.java`
- Create: `algorithm/src/test/java/com/yintp/algorithm/leetcode/phase1/array/Q704BinarySearchTest.java`

- [ ] **Step 1: 写失败测试**

```java
package com.yintp.algorithm.leetcode.phase1.array;

import org.junit.Test;
import static org.junit.Assert.*;

public class Q704BinarySearchTest {
    private Q704BinarySearch sol = new Q704BinarySearch();

    @Test
    public void testFound() {
        assertEquals(4, sol.search(new int[]{-1, 0, 3, 5, 9, 12}, 9));
    }

    @Test
    public void testNotFound() {
        assertEquals(-1, sol.search(new int[]{-1, 0, 3, 5, 9, 12}, 2));
    }

    @Test
    public void testSingleElementFound() {
        assertEquals(0, sol.search(new int[]{5}, 5));
    }

    @Test
    public void testSingleElementNotFound() {
        assertEquals(-1, sol.search(new int[]{5}, 3));
    }
}
```

- [ ] **Step 2: 运行确认失败**

```bash
mvn test -Dtest=Q704BinarySearchTest
```

预期：编译错误

- [ ] **Step 3: 实现 Q704BinarySearch.java**

```java
package com.yintp.algorithm.leetcode.phase1.array;

/**
 * #704 二分查找 (Binary Search) - 简单
 * Topic: 数组 | Phase1 Day1
 *
 * 给定升序整数数组 nums 和目标值 target，返回目标值的下标，不存在返回 -1。
 *
 * 示例：nums=[-1,0,3,5,9,12], target=9 → 4
 *
 * 思路：左闭右闭区间二分，mid = left + (right - left) / 2 防溢出
 */
public class Q704BinarySearch {
    public int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) return mid;
            else if (nums[mid] < target) left = mid + 1;
            else right = mid - 1;
        }
        return -1;
    }
}
```

- [ ] **Step 4: 运行确认通过**

```bash
mvn test -Dtest=Q704BinarySearchTest
```

预期：`Tests run: 4, Failures: 0, Errors: 0`

- [ ] **Step 5: 提交**

```bash
git add algorithm/src/main/java/com/yintp/algorithm/leetcode/phase1/array/Q704BinarySearch.java
git add algorithm/src/test/java/com/yintp/algorithm/leetcode/phase1/array/Q704BinarySearchTest.java
git commit -m "feat(algorithm): add phase1 Q704 binary search"
```

---

## Task 4: hashtable/MyHashMap

**Files:**
- Create: `algorithm/src/main/java/com/yintp/algorithm/leetcode/phase1/hashtable/MyHashMap.java`
- Create: `algorithm/src/test/java/com/yintp/algorithm/leetcode/phase1/hashtable/MyHashMapTest.java`

- [ ] **Step 1: 写失败测试**

```java
package com.yintp.algorithm.leetcode.phase1.hashtable;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MyHashMapTest {
    private MyHashMap map;

    @Before
    public void setUp() { map = new MyHashMap(); }

    @Test
    public void testPutAndGet() {
        map.put(1, 100);
        map.put(2, 200);
        assertEquals(100, map.get(1));
        assertEquals(200, map.get(2));
    }

    @Test
    public void testUpdateExistingKey() {
        map.put(1, 100);
        map.put(1, 999);
        assertEquals(999, map.get(1));
    }

    @Test
    public void testGetMissing() {
        assertEquals(-1, map.get(999));
    }

    @Test
    public void testRemove() {
        map.put(1, 100);
        map.remove(1);
        assertEquals(-1, map.get(1));
    }

    @Test
    public void testContainsKey() {
        map.put(3, 30);
        assertTrue(map.containsKey(3));
        assertFalse(map.containsKey(99));
    }

    @Test
    public void testResizeOnManyEntries() {
        for (int i = 0; i < 20; i++) map.put(i, i * 10);
        for (int i = 0; i < 20; i++) assertEquals(i * 10, map.get(i));
    }
}
```

- [ ] **Step 2: 运行确认失败**

```bash
mvn test -Dtest=MyHashMapTest
```

预期：编译错误

- [ ] **Step 3: 实现 MyHashMap.java**

```java
package com.yintp.algorithm.leetcode.phase1.hashtable;

/**
 * 手写实现：哈希表（链地址法）
 * 掌握要点：hash 函数、链地址法处理冲突、load factor 触发扩容
 *
 * 时间复杂度：
 *   - put(key, val)：均摊 O(1)
 *   - get(key)：均摊 O(1)
 *   - remove(key)：均摊 O(1)
 * 空间复杂度：O(n)
 */
public class MyHashMap {
    private static final double LOAD_FACTOR = 0.75;

    private Entry[] buckets;
    private int size;

    private static class Entry {
        int key, value;
        Entry next;
        Entry(int key, int value) { this.key = key; this.value = value; }
    }

    public MyHashMap() {
        buckets = new Entry[16];
        size = 0;
    }

    private int hash(int key) {
        return Math.abs(key % buckets.length);
    }

    public void put(int key, int value) {
        int idx = hash(key);
        for (Entry e = buckets[idx]; e != null; e = e.next) {
            if (e.key == key) { e.value = value; return; }
        }
        Entry newEntry = new Entry(key, value);
        newEntry.next = buckets[idx];
        buckets[idx] = newEntry;
        size++;
        if (size > buckets.length * LOAD_FACTOR) resize();
    }

    public int get(int key) {
        int idx = hash(key);
        for (Entry e = buckets[idx]; e != null; e = e.next) {
            if (e.key == key) return e.value;
        }
        return -1;
    }

    public void remove(int key) {
        int idx = hash(key);
        if (buckets[idx] == null) return;
        if (buckets[idx].key == key) { buckets[idx] = buckets[idx].next; size--; return; }
        for (Entry e = buckets[idx]; e.next != null; e = e.next) {
            if (e.next.key == key) { e.next = e.next.next; size--; return; }
        }
    }

    public boolean containsKey(int key) {
        int idx = hash(key);
        for (Entry e = buckets[idx]; e != null; e = e.next) {
            if (e.key == key) return true;
        }
        return false;
    }

    private void resize() {
        Entry[] old = buckets;
        buckets = new Entry[old.length * 2];
        size = 0;
        for (Entry e : old) {
            while (e != null) { put(e.key, e.value); e = e.next; }
        }
    }
}
```

- [ ] **Step 4: 运行确认通过**

```bash
mvn test -Dtest=MyHashMapTest
```

预期：`Tests run: 6, Failures: 0, Errors: 0`

- [ ] **Step 5: 提交**

```bash
git add algorithm/src/main/java/com/yintp/algorithm/leetcode/phase1/hashtable/MyHashMap.java
git add algorithm/src/test/java/com/yintp/algorithm/leetcode/phase1/hashtable/MyHashMapTest.java
git commit -m "feat(algorithm): add phase1 MyHashMap with tests"
```

---

## Task 5: hashtable/Q705 + Q706

**Files:**
- Create: `algorithm/src/main/java/com/yintp/algorithm/leetcode/phase1/hashtable/Q705DesignHashSet.java`
- Create: `algorithm/src/main/java/com/yintp/algorithm/leetcode/phase1/hashtable/Q706DesignHashMap.java`
- Create: `algorithm/src/test/java/com/yintp/algorithm/leetcode/phase1/hashtable/Q705DesignHashSetTest.java`
- Create: `algorithm/src/test/java/com/yintp/algorithm/leetcode/phase1/hashtable/Q706DesignHashMapTest.java`

- [ ] **Step 1: 写 Q705 失败测试**

```java
package com.yintp.algorithm.leetcode.phase1.hashtable;

import org.junit.Test;
import static org.junit.Assert.*;

public class Q705DesignHashSetTest {
    @Test
    public void testAddContainsRemove() {
        Q705DesignHashSet set = new Q705DesignHashSet();
        set.add(1); set.add(2);
        assertTrue(set.contains(1));
        assertFalse(set.contains(3));
        set.add(2);
        assertTrue(set.contains(2));
        set.remove(2);
        assertFalse(set.contains(2));
    }
}
```

- [ ] **Step 2: 写 Q706 失败测试**

```java
package com.yintp.algorithm.leetcode.phase1.hashtable;

import org.junit.Test;
import static org.junit.Assert.*;

public class Q706DesignHashMapTest {
    @Test
    public void testPutGetRemove() {
        Q706DesignHashMap map = new Q706DesignHashMap();
        map.put(1, 1); map.put(2, 2);
        assertEquals(1, map.get(1));
        assertEquals(-1, map.get(3));
        map.put(2, 1);
        assertEquals(1, map.get(2));
        map.remove(2);
        assertEquals(-1, map.get(2));
    }
}
```

- [ ] **Step 3: 运行确认失败**

```bash
mvn test -Dtest="Q705DesignHashSetTest+Q706DesignHashMapTest"
```

预期：编译错误

- [ ] **Step 4: 实现 Q705DesignHashSet.java**

```java
package com.yintp.algorithm.leetcode.phase1.hashtable;

/**
 * #705 设计哈希集合 (Design HashSet) - 简单
 * Topic: 哈希表 | Phase1 Day2
 *
 * 不使用任何内建的哈希表库，实现 MyHashSet 的 add/remove/contains 操作。
 *
 * 思路：链地址法，数组长度 769（质数减少碰撞）
 */
public class Q705DesignHashSet {
    private static final int SIZE = 769;
    private int[][] buckets;

    public Q705DesignHashSet() {
        buckets = new int[SIZE][];
    }

    public void add(int key) {
        int idx = key % SIZE;
        if (buckets[idx] == null) { buckets[idx] = new int[]{key}; return; }
        if (contains(key)) return;
        int[] old = buckets[idx];
        int[] neo = new int[old.length + 1];
        System.arraycopy(old, 0, neo, 0, old.length);
        neo[old.length] = key;
        buckets[idx] = neo;
    }

    public void remove(int key) {
        int idx = key % SIZE;
        if (buckets[idx] == null) return;
        int pos = -1;
        for (int i = 0; i < buckets[idx].length; i++) {
            if (buckets[idx][i] == key) { pos = i; break; }
        }
        if (pos == -1) return;
        int[] old = buckets[idx];
        int[] neo = new int[old.length - 1];
        System.arraycopy(old, 0, neo, 0, pos);
        System.arraycopy(old, pos + 1, neo, pos, old.length - pos - 1);
        buckets[idx] = neo;
    }

    public boolean contains(int key) {
        int idx = key % SIZE;
        if (buckets[idx] == null) return false;
        for (int v : buckets[idx]) if (v == key) return true;
        return false;
    }
}
```

- [ ] **Step 5: 实现 Q706DesignHashMap.java**

```java
package com.yintp.algorithm.leetcode.phase1.hashtable;

/**
 * #706 设计哈希映射 (Design HashMap) - 简单
 * Topic: 哈希表 | Phase1 Day2
 *
 * 不使用任何内建的哈希表库，实现 put/get/remove 操作。
 *
 * 思路：链地址法，数组长度 769（质数），每个桶存 [key, value] 对的链表
 */
public class Q706DesignHashMap {
    private static final int SIZE = 769;
    private int[][][] buckets;

    public Q706DesignHashMap() {
        buckets = new int[SIZE][][];
    }

    public void put(int key, int value) {
        int idx = key % SIZE;
        if (buckets[idx] == null) { buckets[idx] = new int[][]{{key, value}}; return; }
        for (int[] pair : buckets[idx]) {
            if (pair[0] == key) { pair[1] = value; return; }
        }
        int[][] old = buckets[idx];
        int[][] neo = new int[old.length + 1][];
        System.arraycopy(old, 0, neo, 0, old.length);
        neo[old.length] = new int[]{key, value};
        buckets[idx] = neo;
    }

    public int get(int key) {
        int idx = key % SIZE;
        if (buckets[idx] == null) return -1;
        for (int[] pair : buckets[idx]) if (pair[0] == key) return pair[1];
        return -1;
    }

    public void remove(int key) {
        int idx = key % SIZE;
        if (buckets[idx] == null) return;
        int pos = -1;
        for (int i = 0; i < buckets[idx].length; i++) {
            if (buckets[idx][i][0] == key) { pos = i; break; }
        }
        if (pos == -1) return;
        int[][] old = buckets[idx];
        int[][] neo = new int[old.length - 1][];
        System.arraycopy(old, 0, neo, 0, pos);
        System.arraycopy(old, pos + 1, neo, pos, old.length - pos - 1);
        buckets[idx] = neo;
    }
}
```

- [ ] **Step 6: 运行确认通过**

```bash
mvn test -Dtest="Q705DesignHashSetTest+Q706DesignHashMapTest"
```

预期：`Tests run: 2, Failures: 0, Errors: 0`

- [ ] **Step 7: 提交**

```bash
git add algorithm/src/main/java/com/yintp/algorithm/leetcode/phase1/hashtable/Q705DesignHashSet.java
git add algorithm/src/main/java/com/yintp/algorithm/leetcode/phase1/hashtable/Q706DesignHashMap.java
git add algorithm/src/test/java/com/yintp/algorithm/leetcode/phase1/hashtable/Q705DesignHashSetTest.java
git add algorithm/src/test/java/com/yintp/algorithm/leetcode/phase1/hashtable/Q706DesignHashMapTest.java
git commit -m "feat(algorithm): add phase1 Q705/Q706 hash structure design"
```

---

## Task 6: linkedlist/MyLinkedList

**Files:**
- Create: `algorithm/src/main/java/com/yintp/algorithm/leetcode/phase1/linkedlist/MyLinkedList.java`
- Create: `algorithm/src/test/java/com/yintp/algorithm/leetcode/phase1/linkedlist/MyLinkedListTest.java`

- [ ] **Step 1: 写失败测试**

```java
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
```

- [ ] **Step 2: 运行确认失败**

```bash
mvn test -Dtest=MyLinkedListTest
```

预期：编译错误

- [ ] **Step 3: 实现 MyLinkedList.java**

```java
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
```

- [ ] **Step 4: 运行确认通过**

```bash
mvn test -Dtest=MyLinkedListTest
```

预期：`Tests run: 5, Failures: 0, Errors: 0`

- [ ] **Step 5: 提交**

```bash
git add algorithm/src/main/java/com/yintp/algorithm/leetcode/phase1/linkedlist/MyLinkedList.java
git add algorithm/src/test/java/com/yintp/algorithm/leetcode/phase1/linkedlist/MyLinkedListTest.java
git commit -m "feat(algorithm): add phase1 MyLinkedList with tests"
```

---

## Task 7: linkedlist/Q707DesignLinkedList

**Files:**
- Create: `algorithm/src/main/java/com/yintp/algorithm/leetcode/phase1/linkedlist/Q707DesignLinkedList.java`
- Create: `algorithm/src/test/java/com/yintp/algorithm/leetcode/phase1/linkedlist/Q707DesignLinkedListTest.java`

- [ ] **Step 1: 写失败测试**

```java
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
```

- [ ] **Step 2: 运行确认失败**

```bash
mvn test -Dtest=Q707DesignLinkedListTest
```

预期：编译错误

- [ ] **Step 3: 实现 Q707DesignLinkedList.java**

```java
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
```

- [ ] **Step 4: 运行确认通过**

```bash
mvn test -Dtest=Q707DesignLinkedListTest
```

预期：`Tests run: 2, Failures: 0, Errors: 0`

- [ ] **Step 5: 提交**

```bash
git add algorithm/src/main/java/com/yintp/algorithm/leetcode/phase1/linkedlist/Q707DesignLinkedList.java
git add algorithm/src/test/java/com/yintp/algorithm/leetcode/phase1/linkedlist/Q707DesignLinkedListTest.java
git commit -m "feat(algorithm): add phase1 Q707 design linked list"
```

---

## Task 8: stack/MyStack

**Files:**
- Create: `algorithm/src/main/java/com/yintp/algorithm/leetcode/phase1/stack/MyStack.java`
- Create: `algorithm/src/test/java/com/yintp/algorithm/leetcode/phase1/stack/MyStackTest.java`

- [ ] **Step 1: 写失败测试**

```java
package com.yintp.algorithm.leetcode.phase1.stack;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MyStackTest {
    private MyStack stack;

    @Before
    public void setUp() { stack = new MyStack(); }

    @Test
    public void testPushAndPop() {
        stack.push(1); stack.push(2); stack.push(3);
        assertEquals(3, stack.pop());
        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());
    }

    @Test
    public void testPeek() {
        stack.push(5); stack.push(10);
        assertEquals(10, stack.peek());
        assertEquals(2, stack.size());
    }

    @Test
    public void testIsEmpty() {
        assertTrue(stack.isEmpty());
        stack.push(1);
        assertFalse(stack.isEmpty());
    }

    @Test
    public void testAutoResize() {
        for (int i = 0; i < 20; i++) stack.push(i);
        assertEquals(20, stack.size());
        assertEquals(19, stack.pop());
    }
}
```

- [ ] **Step 2: 运行确认失败**

```bash
mvn test -Dtest=MyStackTest
```

预期：编译错误

- [ ] **Step 3: 实现 MyStack.java**

```java
package com.yintp.algorithm.leetcode.phase1.stack;

import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * 手写实现：栈（数组实现，自动扩容）
 * 掌握要点：LIFO，push/pop/peek 均为 O(1)，扩容策略（2倍）
 *
 * 时间复杂度：
 *   - push：均摊 O(1)
 *   - pop：O(1)
 *   - peek：O(1)
 * 空间复杂度：O(n)
 */
public class MyStack {
    private int[] data;
    private int top;

    public MyStack() {
        data = new int[8];
        top = -1;
    }

    public void push(int val) {
        if (top + 1 == data.length) {
            data = Arrays.copyOf(data, data.length * 2);
        }
        data[++top] = val;
    }

    public int pop() {
        if (isEmpty()) throw new EmptyStackException();
        return data[top--];
    }

    public int peek() {
        if (isEmpty()) throw new EmptyStackException();
        return data[top];
    }

    public boolean isEmpty() { return top == -1; }
    public int size() { return top + 1; }
}
```

- [ ] **Step 4: 运行确认通过**

```bash
mvn test -Dtest=MyStackTest
```

预期：`Tests run: 4, Failures: 0, Errors: 0`

- [ ] **Step 5: 提交**

```bash
git add algorithm/src/main/java/com/yintp/algorithm/leetcode/phase1/stack/MyStack.java
git add algorithm/src/test/java/com/yintp/algorithm/leetcode/phase1/stack/MyStackTest.java
git commit -m "feat(algorithm): add phase1 MyStack with tests"
```

---

## Task 9: stack/Q155MinStack

**Files:**
- Create: `algorithm/src/main/java/com/yintp/algorithm/leetcode/phase1/stack/Q155MinStack.java`
- Create: `algorithm/src/test/java/com/yintp/algorithm/leetcode/phase1/stack/Q155MinStackTest.java`

- [ ] **Step 1: 写失败测试**

```java
package com.yintp.algorithm.leetcode.phase1.stack;

import org.junit.Test;
import static org.junit.Assert.*;

public class Q155MinStackTest {
    @Test
    public void testLeetCodeExample() {
        Q155MinStack minStack = new Q155MinStack();
        minStack.push(-2); minStack.push(0); minStack.push(-3);
        assertEquals(-3, minStack.getMin());
        minStack.pop();
        assertEquals(0, minStack.top());
        assertEquals(-2, minStack.getMin());
    }

    @Test
    public void testMinAfterPopMinElement() {
        Q155MinStack minStack = new Q155MinStack();
        minStack.push(5); minStack.push(3); minStack.push(7);
        assertEquals(3, minStack.getMin());
        minStack.pop();
        assertEquals(3, minStack.getMin());
        minStack.pop();
        assertEquals(5, minStack.getMin());
    }
}
```

- [ ] **Step 2: 运行确认失败**

```bash
mvn test -Dtest=Q155MinStackTest
```

预期：编译错误

- [ ] **Step 3: 实现 Q155MinStack.java**

```java
package com.yintp.algorithm.leetcode.phase1.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * #155 最小栈 (Min Stack) - 简单
 * Topic: 栈 | Phase1 Day7
 *
 * 设计一个支持 push/pop/top 和 getMin（O(1) 时间）的栈。
 *
 * 思路：双栈，minStack 同步维护当前最小值，入栈时仅当新值 <= 当前最小才压入 minStack
 */
public class Q155MinStack {
    private final Deque<Integer> stack = new ArrayDeque<>();
    private final Deque<Integer> minStack = new ArrayDeque<>();

    public void push(int val) {
        stack.push(val);
        if (minStack.isEmpty() || val <= minStack.peek()) {
            minStack.push(val);
        }
    }

    public void pop() {
        int val = stack.pop();
        if (val == minStack.peek()) minStack.pop();
    }

    public int top() { return stack.peek(); }

    public int getMin() { return minStack.peek(); }
}
```

- [ ] **Step 4: 运行确认通过**

```bash
mvn test -Dtest=Q155MinStackTest
```

预期：`Tests run: 2, Failures: 0, Errors: 0`

- [ ] **Step 5: 提交**

```bash
git add algorithm/src/main/java/com/yintp/algorithm/leetcode/phase1/stack/Q155MinStack.java
git add algorithm/src/test/java/com/yintp/algorithm/leetcode/phase1/stack/Q155MinStackTest.java
git commit -m "feat(algorithm): add phase1 Q155 min stack"
```

---

## Task 10: stack/Q232StackToQueue

**Files:**
- Create: `algorithm/src/main/java/com/yintp/algorithm/leetcode/phase1/stack/Q232StackToQueue.java`
- Create: `algorithm/src/test/java/com/yintp/algorithm/leetcode/phase1/stack/Q232StackToQueueTest.java`

- [ ] **Step 1: 写失败测试**

```java
package com.yintp.algorithm.leetcode.phase1.stack;

import org.junit.Test;
import static org.junit.Assert.*;

public class Q232StackToQueueTest {
    @Test
    public void testLeetCodeExample() {
        Q232StackToQueue queue = new Q232StackToQueue();
        queue.push(1); queue.push(2);
        assertEquals(1, queue.peek());
        assertEquals(1, queue.pop());
        assertFalse(queue.empty());
    }

    @Test
    public void testFIFOOrder() {
        Q232StackToQueue queue = new Q232StackToQueue();
        queue.push(3); queue.push(1); queue.push(2);
        assertEquals(3, queue.pop());
        assertEquals(1, queue.pop());
        assertEquals(2, queue.pop());
        assertTrue(queue.empty());
    }
}
```

- [ ] **Step 2: 运行确认失败**

```bash
mvn test -Dtest=Q232StackToQueueTest
```

预期：编译错误

- [ ] **Step 3: 实现 Q232StackToQueue.java**

```java
package com.yintp.algorithm.leetcode.phase1.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * #232 用栈实现队列 (Implement Queue using Stacks) - 简单
 * Topic: 栈 | Phase1 Day8
 *
 * 用两个栈实现 FIFO 队列的 push/pop/peek/empty 操作。
 *
 * 思路：inStack 负责入队，outStack 负责出队。
 *       outStack 空时才将 inStack 全部转移，实现均摊 O(1)。
 */
public class Q232StackToQueue {
    private final Deque<Integer> inStack = new ArrayDeque<>();
    private final Deque<Integer> outStack = new ArrayDeque<>();

    public void push(int x) { inStack.push(x); }

    public int pop() {
        move();
        return outStack.pop();
    }

    public int peek() {
        move();
        return outStack.peek();
    }

    public boolean empty() { return inStack.isEmpty() && outStack.isEmpty(); }

    private void move() {
        if (outStack.isEmpty()) {
            while (!inStack.isEmpty()) outStack.push(inStack.pop());
        }
    }
}
```

- [ ] **Step 4: 运行确认通过**

```bash
mvn test -Dtest=Q232StackToQueueTest
```

预期：`Tests run: 2, Failures: 0, Errors: 0`

- [ ] **Step 5: 提交**

```bash
git add algorithm/src/main/java/com/yintp/algorithm/leetcode/phase1/stack/Q232StackToQueue.java
git add algorithm/src/test/java/com/yintp/algorithm/leetcode/phase1/stack/Q232StackToQueueTest.java
git commit -m "feat(algorithm): add phase1 Q232 implement queue using stacks"
```

---

## Task 11: queue/MyQueue

**Files:**
- Create: `algorithm/src/main/java/com/yintp/algorithm/leetcode/phase1/queue/MyQueue.java`
- Create: `algorithm/src/test/java/com/yintp/algorithm/leetcode/phase1/queue/MyQueueTest.java`

- [ ] **Step 1: 写失败测试**

```java
package com.yintp.algorithm.leetcode.phase1.queue;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MyQueueTest {
    private MyQueue queue;

    @Before
    public void setUp() { queue = new MyQueue(5); }

    @Test
    public void testOfferAndPoll() {
        queue.offer(1); queue.offer(2); queue.offer(3);
        assertEquals(1, queue.poll());
        assertEquals(2, queue.poll());
        assertEquals(3, queue.poll());
    }

    @Test
    public void testPeek() {
        queue.offer(7);
        assertEquals(7, queue.peek());
        assertEquals(1, queue.size());
    }

    @Test
    public void testFullAndEmpty() {
        assertTrue(queue.isEmpty());
        for (int i = 0; i < 5; i++) queue.offer(i);
        assertTrue(queue.isFull());
        assertFalse(queue.offer(99));
    }

    @Test
    public void testCircularReuse() {
        queue.offer(1); queue.offer(2); queue.offer(3);
        queue.poll(); queue.poll();
        queue.offer(4); queue.offer(5);
        assertEquals(3, queue.size());
        assertEquals(3, queue.poll());
        assertEquals(4, queue.poll());
        assertEquals(5, queue.poll());
    }
}
```

- [ ] **Step 2: 运行确认失败**

```bash
mvn test -Dtest=MyQueueTest
```

预期：编译错误

- [ ] **Step 3: 实现 MyQueue.java**

```java
package com.yintp.algorithm.leetcode.phase1.queue;

import java.util.NoSuchElementException;

/**
 * 手写实现：循环数组队列
 * 掌握要点：front/rear 指针用取模实现循环，区分空/满状态用 size 字段
 *
 * 时间复杂度：
 *   - offer：O(1)
 *   - poll：O(1)
 *   - peek：O(1)
 * 空间复杂度：O(capacity)
 */
public class MyQueue {
    private final int[] data;
    private int front, rear, size;

    public MyQueue(int capacity) {
        data = new int[capacity];
        front = 0; rear = 0; size = 0;
    }

    public boolean offer(int val) {
        if (isFull()) return false;
        data[rear] = val;
        rear = (rear + 1) % data.length;
        size++;
        return true;
    }

    public int poll() {
        if (isEmpty()) throw new NoSuchElementException();
        int val = data[front];
        front = (front + 1) % data.length;
        size--;
        return val;
    }

    public int peek() {
        if (isEmpty()) throw new NoSuchElementException();
        return data[front];
    }

    public boolean isEmpty() { return size == 0; }
    public boolean isFull() { return size == data.length; }
    public int size() { return size; }
}
```

- [ ] **Step 4: 运行确认通过**

```bash
mvn test -Dtest=MyQueueTest
```

预期：`Tests run: 4, Failures: 0, Errors: 0`

- [ ] **Step 5: 提交**

```bash
git add algorithm/src/main/java/com/yintp/algorithm/leetcode/phase1/queue/MyQueue.java
git add algorithm/src/test/java/com/yintp/algorithm/leetcode/phase1/queue/MyQueueTest.java
git commit -m "feat(algorithm): add phase1 MyQueue circular array with tests"
```

---

## Task 12: queue/Q622DesignCircularQueue

**Files:**
- Create: `algorithm/src/main/java/com/yintp/algorithm/leetcode/phase1/queue/Q622DesignCircularQueue.java`
- Create: `algorithm/src/test/java/com/yintp/algorithm/leetcode/phase1/queue/Q622DesignCircularQueueTest.java`

- [ ] **Step 1: 写失败测试**

```java
package com.yintp.algorithm.leetcode.phase1.queue;

import org.junit.Test;
import static org.junit.Assert.*;

public class Q622DesignCircularQueueTest {
    @Test
    public void testLeetCodeExample() {
        Q622DesignCircularQueue cq = new Q622DesignCircularQueue(3);
        assertTrue(cq.enQueue(1)); assertTrue(cq.enQueue(2)); assertTrue(cq.enQueue(3));
        assertFalse(cq.enQueue(4));
        assertEquals(3, cq.Rear());
        assertTrue(cq.isFull());
        assertTrue(cq.deQueue());
        assertTrue(cq.enQueue(4));
        assertEquals(4, cq.Rear());
    }

    @Test
    public void testFrontRearOnEmpty() {
        Q622DesignCircularQueue cq = new Q622DesignCircularQueue(2);
        assertEquals(-1, cq.Front());
        assertEquals(-1, cq.Rear());
    }
}
```

- [ ] **Step 2: 运行确认失败**

```bash
mvn test -Dtest=Q622DesignCircularQueueTest
```

预期：编译错误

- [ ] **Step 3: 实现 Q622DesignCircularQueue.java**

```java
package com.yintp.algorithm.leetcode.phase1.queue;

/**
 * #622 设计循环队列 (Design Circular Queue) - 中等
 * Topic: 队列 | Phase1 Day9
 *
 * 实现循环队列，支持 enQueue/deQueue/Front/Rear/isEmpty/isFull。
 *
 * 思路：数组 + front/rear/size，用 size 区分空满，rear 指向下一个写入位置
 */
public class Q622DesignCircularQueue {
    private final int[] data;
    private int front, rear, size;

    public Q622DesignCircularQueue(int k) {
        data = new int[k];
        front = 0; rear = 0; size = 0;
    }

    public boolean enQueue(int value) {
        if (isFull()) return false;
        data[rear] = value;
        rear = (rear + 1) % data.length;
        size++;
        return true;
    }

    public boolean deQueue() {
        if (isEmpty()) return false;
        front = (front + 1) % data.length;
        size--;
        return true;
    }

    public int Front() { return isEmpty() ? -1 : data[front]; }

    public int Rear() { return isEmpty() ? -1 : data[(rear - 1 + data.length) % data.length]; }

    public boolean isEmpty() { return size == 0; }

    public boolean isFull() { return size == data.length; }
}
```

- [ ] **Step 4: 运行确认通过**

```bash
mvn test -Dtest=Q622DesignCircularQueueTest
```

预期：`Tests run: 2, Failures: 0, Errors: 0`

- [ ] **Step 5: 提交**

```bash
git add algorithm/src/main/java/com/yintp/algorithm/leetcode/phase1/queue/Q622DesignCircularQueue.java
git add algorithm/src/test/java/com/yintp/algorithm/leetcode/phase1/queue/Q622DesignCircularQueueTest.java
git commit -m "feat(algorithm): add phase1 Q622 design circular queue"
```

---

## Task 13: binarytree/MyBinaryTree

**Files:**
- Create: `algorithm/src/main/java/com/yintp/algorithm/leetcode/phase1/binarytree/MyBinaryTree.java`
- Create: `algorithm/src/test/java/com/yintp/algorithm/leetcode/phase1/binarytree/MyBinaryTreeTest.java`

- [ ] **Step 1: 写失败测试**

```java
package com.yintp.algorithm.leetcode.phase1.binarytree;

import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import static org.junit.Assert.*;

public class MyBinaryTreeTest {
    private MyBinaryTree bst;

    @Before
    public void setUp() {
        bst = new MyBinaryTree();
        // 插入顺序构造 BST: 4, 2, 6, 1, 3, 5, 7
        for (int v : new int[]{4, 2, 6, 1, 3, 5, 7}) bst.insert(v);
    }

    @Test
    public void testInorder() {
        // BST 中序遍历 = 升序
        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6, 7), bst.inorder());
    }

    @Test
    public void testPreorder() {
        // 根 → 左 → 右
        assertEquals(Arrays.asList(4, 2, 1, 3, 6, 5, 7), bst.preorder());
    }

    @Test
    public void testPostorder() {
        // 左 → 右 → 根
        assertEquals(Arrays.asList(1, 3, 2, 5, 7, 6, 4), bst.postorder());
    }
}
```

- [ ] **Step 2: 运行确认失败**

```bash
mvn test -Dtest=MyBinaryTreeTest
```

预期：编译错误

- [ ] **Step 3: 实现 MyBinaryTree.java**

```java
package com.yintp.algorithm.leetcode.phase1.binarytree;

import com.yintp.algorithm.leetcode.structure.TreeNode;
import java.util.ArrayList;
import java.util.List;

/**
 * 手写实现：二叉搜索树（BST）
 * 掌握要点：TreeNode 结构，BST 插入，前/中/后序递归遍历
 *
 * 时间复杂度（均衡 BST）：
 *   - insert：O(log n)
 *   - preorder/inorder/postorder：O(n)
 * 空间复杂度：O(n)
 *
 * 复用 structure.TreeNode（package: com.yintp.algorithm.leetcode.structure）
 */
public class MyBinaryTree {
    private TreeNode root;

    public void insert(int val) {
        root = insertRec(root, val);
    }

    private TreeNode insertRec(TreeNode node, int val) {
        if (node == null) return new TreeNode(val);
        if (val < node.val) node.left = insertRec(node.left, val);
        else if (val > node.val) node.right = insertRec(node.right, val);
        return node;
    }

    public List<Integer> preorder() {
        List<Integer> result = new ArrayList<>();
        preorderRec(root, result);
        return result;
    }

    private void preorderRec(TreeNode node, List<Integer> result) {
        if (node == null) return;
        result.add(node.val);
        preorderRec(node.left, result);
        preorderRec(node.right, result);
    }

    public List<Integer> inorder() {
        List<Integer> result = new ArrayList<>();
        inorderRec(root, result);
        return result;
    }

    private void inorderRec(TreeNode node, List<Integer> result) {
        if (node == null) return;
        inorderRec(node.left, result);
        result.add(node.val);
        inorderRec(node.right, result);
    }

    public List<Integer> postorder() {
        List<Integer> result = new ArrayList<>();
        postorderRec(root, result);
        return result;
    }

    private void postorderRec(TreeNode node, List<Integer> result) {
        if (node == null) return;
        postorderRec(node.left, result);
        postorderRec(node.right, result);
        result.add(node.val);
    }
}
```

- [ ] **Step 4: 运行确认通过**

```bash
mvn test -Dtest=MyBinaryTreeTest
```

预期：`Tests run: 3, Failures: 0, Errors: 0`

- [ ] **Step 5: 提交**

```bash
git add algorithm/src/main/java/com/yintp/algorithm/leetcode/phase1/binarytree/MyBinaryTree.java
git add algorithm/src/test/java/com/yintp/algorithm/leetcode/phase1/binarytree/MyBinaryTreeTest.java
git commit -m "feat(algorithm): add phase1 MyBinaryTree BST with recursive traversals"
```

---

## Task 14: binarytree/Q94 + Q144 + Q145（三种遍历迭代版）

**Files:**
- Create: `algorithm/src/main/java/com/yintp/algorithm/leetcode/phase1/binarytree/Q94InorderTraversal.java`
- Create: `algorithm/src/main/java/com/yintp/algorithm/leetcode/phase1/binarytree/Q144PreorderTraversal.java`
- Create: `algorithm/src/main/java/com/yintp/algorithm/leetcode/phase1/binarytree/Q145PostorderTraversal.java`
- Create: `algorithm/src/test/java/com/yintp/algorithm/leetcode/phase1/binarytree/Q94InorderTraversalTest.java`
- Create: `algorithm/src/test/java/com/yintp/algorithm/leetcode/phase1/binarytree/Q144PreorderTraversalTest.java`
- Create: `algorithm/src/test/java/com/yintp/algorithm/leetcode/phase1/binarytree/Q145PostorderTraversalTest.java`

- [ ] **Step 1: 写三个失败测试**

```java
// Q94InorderTraversalTest.java
package com.yintp.algorithm.leetcode.phase1.binarytree;

import com.yintp.algorithm.leetcode.structure.TreeNode;
import org.junit.Test;
import java.util.Arrays;
import static org.junit.Assert.*;

public class Q94InorderTraversalTest {
    private Q94InorderTraversal sol = new Q94InorderTraversal();

    private TreeNode buildTree() {
        // 构造: 1 -> right=2 -> left=3  即 [1,null,2,3]
        TreeNode root = new TreeNode(1);
        root.right = new TreeNode(2);
        root.right.left = new TreeNode(3);
        return root;
    }

    @Test
    public void testExample() {
        assertEquals(Arrays.asList(1, 3, 2), sol.inorderTraversal(buildTree()));
    }

    @Test
    public void testEmpty() {
        assertTrue(sol.inorderTraversal(null).isEmpty());
    }
}
```

```java
// Q144PreorderTraversalTest.java
package com.yintp.algorithm.leetcode.phase1.binarytree;

import com.yintp.algorithm.leetcode.structure.TreeNode;
import org.junit.Test;
import java.util.Arrays;
import static org.junit.Assert.*;

public class Q144PreorderTraversalTest {
    private Q144PreorderTraversal sol = new Q144PreorderTraversal();

    private TreeNode buildTree() {
        TreeNode root = new TreeNode(1);
        root.right = new TreeNode(2);
        root.right.left = new TreeNode(3);
        return root;
    }

    @Test
    public void testExample() {
        assertEquals(Arrays.asList(1, 2, 3), sol.preorderTraversal(buildTree()));
    }

    @Test
    public void testEmpty() {
        assertTrue(sol.preorderTraversal(null).isEmpty());
    }
}
```

```java
// Q145PostorderTraversalTest.java
package com.yintp.algorithm.leetcode.phase1.binarytree;

import com.yintp.algorithm.leetcode.structure.TreeNode;
import org.junit.Test;
import java.util.Arrays;
import static org.junit.Assert.*;

public class Q145PostorderTraversalTest {
    private Q145PostorderTraversal sol = new Q145PostorderTraversal();

    private TreeNode buildTree() {
        TreeNode root = new TreeNode(1);
        root.right = new TreeNode(2);
        root.right.left = new TreeNode(3);
        return root;
    }

    @Test
    public void testExample() {
        assertEquals(Arrays.asList(3, 2, 1), sol.postorderTraversal(buildTree()));
    }

    @Test
    public void testEmpty() {
        assertTrue(sol.postorderTraversal(null).isEmpty());
    }
}
```

- [ ] **Step 2: 运行确认失败**

```bash
mvn test -Dtest="Q94InorderTraversalTest+Q144PreorderTraversalTest+Q145PostorderTraversalTest"
```

预期：编译错误

- [ ] **Step 3: 实现 Q94InorderTraversal.java**

```java
package com.yintp.algorithm.leetcode.phase1.binarytree;

import com.yintp.algorithm.leetcode.structure.TreeNode;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * #94 二叉树的中序遍历 (Binary Tree Inorder Traversal) - 简单
 * Topic: 二叉树 | Phase1 Day10
 *
 * 返回二叉树节点值的中序遍历（左→根→右）。
 *
 * 思路：迭代法，用栈模拟递归。一路向左压栈，弹出时记录值，转向右子树。
 */
public class Q94InorderTraversal {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) { stack.push(cur); cur = cur.left; }
            cur = stack.pop();
            result.add(cur.val);
            cur = cur.right;
        }
        return result;
    }
}
```

- [ ] **Step 4: 实现 Q144PreorderTraversal.java**

```java
package com.yintp.algorithm.leetcode.phase1.binarytree;

import com.yintp.algorithm.leetcode.structure.TreeNode;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * #144 二叉树的前序遍历 (Binary Tree Preorder Traversal) - 简单
 * Topic: 二叉树 | Phase1 Day10
 *
 * 返回二叉树节点值的前序遍历（根→左→右）。
 *
 * 思路：迭代法，根节点入栈，弹出记录值，先压右子再压左子（保证左先弹出）。
 */
public class Q144PreorderTraversal {
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            result.add(node.val);
            if (node.right != null) stack.push(node.right);
            if (node.left != null) stack.push(node.left);
        }
        return result;
    }
}
```

- [ ] **Step 5: 实现 Q145PostorderTraversal.java**

```java
package com.yintp.algorithm.leetcode.phase1.binarytree;

import com.yintp.algorithm.leetcode.structure.TreeNode;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * #145 二叉树的后序遍历 (Binary Tree Postorder Traversal) - 简单
 * Topic: 二叉树 | Phase1 Day10
 *
 * 返回二叉树节点值的后序遍历（左→右→根）。
 *
 * 思路：迭代法，按"根→右→左"顺序压栈，结果用 addFirst 反转为"左→右→根"。
 */
public class Q145PostorderTraversal {
    public List<Integer> postorderTraversal(TreeNode root) {
        LinkedList<Integer> result = new LinkedList<>();
        if (root == null) return result;
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            result.addFirst(node.val);
            if (node.left != null) stack.push(node.left);
            if (node.right != null) stack.push(node.right);
        }
        return result;
    }
}
```

- [ ] **Step 6: 运行确认通过**

```bash
mvn test -Dtest="Q94InorderTraversalTest+Q144PreorderTraversalTest+Q145PostorderTraversalTest"
```

预期：`Tests run: 6, Failures: 0, Errors: 0`

- [ ] **Step 7: 运行全部 phase1 测试**

```bash
mvn test -Dtest="MyArrayListTest+Q704BinarySearchTest+MyHashMapTest+Q705DesignHashSetTest+Q706DesignHashMapTest+MyLinkedListTest+Q707DesignLinkedListTest+MyStackTest+Q155MinStackTest+Q232StackToQueueTest+MyQueueTest+Q622DesignCircularQueueTest+MyBinaryTreeTest+Q94InorderTraversalTest+Q144PreorderTraversalTest+Q145PostorderTraversalTest"
```

预期：全部通过，无 Failures

- [ ] **Step 8: 提交**

```bash
git add algorithm/src/main/java/com/yintp/algorithm/leetcode/phase1/binarytree/
git add algorithm/src/test/java/com/yintp/algorithm/leetcode/phase1/binarytree/Q94InorderTraversalTest.java
git add algorithm/src/test/java/com/yintp/algorithm/leetcode/phase1/binarytree/Q144PreorderTraversalTest.java
git add algorithm/src/test/java/com/yintp/algorithm/leetcode/phase1/binarytree/Q145PostorderTraversalTest.java
git commit -m "feat(algorithm): add phase1 Q94/Q144/Q145 iterative tree traversals"
```

---

## Task 15: 更新 README

**Files:**
- Modify: `algorithm/README.md`

- [ ] **Step 1: 在 README.md 中新增 phase1 章节**

在 `algorithm/README.md` 的目录结构代码块中，在 `├── topic1/` 之前追加一行：

```
├── phase1/             第1-2周 前置：基础数据结构（手写实现 + 设计题）
```

然后在"## 学习路径"章节的"第一阶段：基础数据结构（第1-2周）"行下方追加：

```markdown
### Phase 1 — 基础数据结构（第1-2周）

前置学习模块，手写 6 种基础数据结构 + 配套 LeetCode 设计题。

| 子包 | 手写实现 | 配套题目 |
|------|---------|---------|
| array | MyArrayList（动态数组） | Q704 二分查找 |
| hashtable | MyHashMap（链地址法） | Q705 设计哈希集合、Q706 设计哈希映射 |
| linkedlist | MyLinkedList（双向链表） | Q707 设计链表 |
| stack | MyStack（数组实现） | Q155 最小栈、Q232 用栈实现队列 |
| queue | MyQueue（循环数组） | Q622 设计循环队列 |
| binarytree | MyBinaryTree（BST+递归遍历） | Q94/Q144/Q145 三种遍历（迭代） |
```

- [ ] **Step 2: 提交**

```bash
git add algorithm/README.md
git commit -m "docs(algorithm): update README with phase1 data structures module"
```
