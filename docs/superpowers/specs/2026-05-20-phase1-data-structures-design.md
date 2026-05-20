# Phase 1：基础数据结构模块设计文档

**日期**：2026-05-20  
**范围**：algorithm 模块新增 `phase1/` 前置学习模块  
**目标**：能手写 6 种基础数据结构，理解时间/空间复杂度

---

## 一、背景

`LEETCODE_GUIDE.md` 定义了 4 阶段学习路径，第一阶段（第 1-2 周）专注基础数据结构。现有 `topic1-9` 以 LeetCode 算法专题为主，缺少前置的数据结构手写实现环节。本模块作为独立前置模块，在进入 topic1 之前完成。

---

## 二、目录结构

```
algorithm/src/main/java/com/yintp/algorithm/leetcode/phase1/
├── WeekPlan.java
├── array/
│   ├── MyArrayList.java
│   └── Q704BinarySearch.java
├── hashtable/
│   ├── MyHashMap.java
│   ├── Q705DesignHashSet.java
│   └── Q706DesignHashMap.java
├── linkedlist/
│   ├── MyLinkedList.java
│   └── Q707DesignLinkedList.java
├── stack/
│   ├── MyStack.java
│   ├── Q155MinStack.java
│   └── Q232StackToQueue.java
├── queue/
│   ├── MyQueue.java
│   └── Q622DesignCircularQueue.java
└── binarytree/
    ├── MyBinaryTree.java
    ├── Q94InorderTraversal.java
    ├── Q144PreorderTraversal.java
    └── Q145PostorderTraversal.java

algorithm/src/test/java/com/yintp/algorithm/leetcode/phase1/
└── 同结构，每个实现/题目文件对应一个 *Test.java
```

包名：`com.yintp.algorithm.leetcode.phase1.[子包]`

---

## 三、两周学习计划

### 第一周：线性结构（数组、哈希表、链表）

| 天 | 任务 | 文件 |
|----|------|------|
| Day 1 | 手写动态数组 + 二分查找 | MyArrayList + Q704 |
| Day 2 | 手写哈希表 + 设计题 | MyHashMap + Q705/Q706 |
| Day 3 | 手写链表 + 设计链表 | MyLinkedList + Q707 |
| Day 4 | 复习 Day 1-2（不看代码重写） | — |
| Day 5 | 复习 Day 3 + 预习栈概念 | — |
| Day 6 | 全周总结，完善复杂度注释 | — |

### 第二周：栈、队列、二叉树

| 天 | 任务 | 文件 |
|----|------|------|
| Day 7 | 手写栈（数组/链表两种实现）+ 最小栈 | MyStack + Q155 |
| Day 8 | 用栈模拟队列 | Q232 |
| Day 9 | 手写循环队列 + 设计循环队列 | MyQueue + Q622 |
| Day 10 | 手写二叉树 + 三种遍历题 | MyBinaryTree + Q94/Q144/Q145 |
| Day 11 | 复习 Day 7-9 | — |
| Day 12 | 全阶段总结，确认可流畅手写全部 6 种结构 | — |

### 间隔重复表（WeekPlan.java 内）

| 结构/题目 | Day1 | Day2 | Day7 | Day30 |
|----------|------|------|------|-------|
| MyArrayList | W1D1 | W1D4 | W2D7 | — |
| MyHashMap | W1D2 | W1D4 | W2D7 | — |
| MyLinkedList | W1D3 | W1D5 | W2D8 | — |
| MyStack | W2D7 | W2D11 | W3D1 | — |
| MyQueue | W2D9 | W2D11 | W3D1 | — |
| MyBinaryTree | W2D10 | W2D11 | W3D1 | — |

---

## 四、文件内容规范

### 4.1 手写实现文件（`My*.java`）

```java
package com.yintp.algorithm.leetcode.phase1.array;

/**
 * 手写实现：动态数组
 * 掌握要点：增删改查、下标访问 O(1)、扩容策略
 *
 * 时间复杂度：
 *   - get(index)：O(1)
 *   - add(末尾)：均摊 O(1)
 *   - add(index)：O(n)
 *   - remove(index)：O(n)
 * 空间复杂度：O(n)
 */
public class MyArrayList {
    // TODO: 实现动态数组
}
```

规范：
- 头部 Javadoc 说明数据结构名、掌握要点、完整的时间/空间复杂度分析
- 核心操作全部实现（不省略），每个方法含 TODO 提示
- 不依赖 Java 标准库中对应的容器类（如不能用 `ArrayList`、`HashMap`、`LinkedList`）

### 4.2 LeetCode 题目文件（`Q*.java`）

与现有 topic1-9 完全一致：
- 头部注释含题号、题名（中英文）、难度、对应的数据结构
- 方法签名 + TODO 提示
- 默认返回值

### 4.3 测试文件（`*Test.java`）

- `My*Test.java`：验证手写实现的正确性，覆盖增/删/查/改及边界 case
- `Q*Test.java`：与现有 topic 一致，覆盖 LeetCode 示例用例

---

## 五、各数据结构掌握要点

| 数据结构 | 手写要点 | 配套 LeetCode 题 |
|---------|---------|----------------|
| 数组 | 动态扩容（2倍），下标增删移位 | Q704 二分查找 |
| 哈希表 | hash 函数、链地址法处理冲突、load factor | Q705 设计哈希集合、Q706 设计哈希映射 |
| 链表 | 单向 + 双向，虚拟头节点，空指针处理 | Q707 设计链表 |
| 栈 | 数组实现 + 链表实现两种，LIFO | Q155 最小栈、Q232 用栈实现队列 |
| 队列 | 循环数组，front/rear 指针，FIFO | Q622 设计循环队列 |
| 二叉树 | TreeNode 结构，前/中/后序递归遍历 | Q94/Q144/Q145 三种遍历 |

---

## 六、与现有模块的关系

- **前置于 topic1-9**：phase1 完成后进入 topic1（数组 & 哈希表），此时对底层结构已有手写级别的理解
- **共用 structure/**：`TreeNode`、`ListNode` 已在 `structure/` 包中定义，`binarytree/` 和 `linkedlist/` 直接复用，不重复定义
- **包结构独立**：phase1 有自己的子包，不影响现有 topic1-9

---

## 七、不在本阶段范围内

- 堆（PriorityQueue）— 第二阶段使用即可
- 图的邻接表/邻接矩阵 — topic6 覆盖
- 高级树结构（AVL、红黑树）— 不在刷题范围
