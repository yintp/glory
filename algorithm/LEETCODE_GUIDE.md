# LeetCode 高效刷题指南

## 核心理念

刷题的本质是**模式识别**，不是刷数量。见过 50 个模式比刷 500 道随机题有效。

---

## 一、学习路径总览

```
第一阶段：基础数据结构（2周）
    ↓
第二阶段：基础算法专题（8周）
    ↓
第三阶段：综合强化（4周）
    ↓
第四阶段：模拟面试冲刺（持续）
```

---

## 二、各阶段详细计划

### 第一阶段：基础数据结构（第1-2周）

**目标**：能手写基本数据结构，理解时间/空间复杂度

| 数据结构 | 掌握要点 |
|---------|---------|
| 数组 | 增删改查、下标访问 O(1) |
| 哈希表 | 键值对存储，查找 O(1) |
| 链表 | 单/双向链表增删，注意空指针 |
| 栈 | LIFO，用数组或链表实现 |
| 队列 | FIFO，双端队列 Deque |
| 二叉树 | 节点结构，前/中/后序遍历 |

**Java 常用数据结构速查**
```java
// 哈希表
Map<Integer, Integer> map = new HashMap<>();
map.put(key, value);
map.getOrDefault(key, 0);
map.containsKey(key);

// 栈
Deque<Integer> stack = new ArrayDeque<>();
stack.push(val);   // 入栈
stack.pop();       // 出栈
stack.peek();      // 查顶

// 队列（BFS常用）
Queue<Integer> queue = new LinkedList<>();
queue.offer(val);  // 入队
queue.poll();      // 出队
queue.peek();      // 查头

// 优先队列（最小堆）
PriorityQueue<Integer> minHeap = new PriorityQueue<>();
// 最大堆
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
```

---

### 第二阶段：基础算法专题（第3-10周）

#### 专题1：数组 & 哈希表（第3周）

**核心思路**：空间换时间，用哈希表把 O(n²) 降到 O(n)

**必刷题目**
| 题号 | 题目 | 难度 | 核心知识点 |
|------|------|------|-----------|
| #1 | 两数之和 | 简单 | 哈希表 |
| #217 | 存在重复元素 | 简单 | HashSet |
| #242 | 有效的字母异位词 | 简单 | 字符频率统计 |
| #49 | 字母异位词分组 | 中等 | 排序+哈希 |
| #128 | 最长连续序列 | 中等 | HashSet 优化 |
| #53 | 最大子数组和 | 中等 | Kadane 算法 |
| #238 | 除自身以外数组的乘积 | 中等 | 前缀积+后缀积 |

**模板代码**
```java
// 两数之和模板（哈希表）
public int[] twoSum(int[] nums, int target) {
    Map<Integer, Integer> seen = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
        int complement = target - nums[i];
        if (seen.containsKey(complement)) {
            return new int[]{seen.get(complement), i};
        }
        seen.put(nums[i], i);
    }
    return new int[]{};
}
```

---

#### 专题2：双指针 & 滑动窗口（第4周）

**核心思路**：
- **双指针**：左右指针同向或相向移动，避免嵌套循环
- **滑动窗口**：维护一个动态窗口，解决子数组/子串问题

**必刷题目**
| 题号 | 题目 | 难度 | 核心知识点 |
|------|------|------|-----------|
| #283 | 移动零 | 简单 | 同向双指针 |
| #344 | 反转字符串 | 简单 | 相向双指针 |
| #167 | 两数之和 II | 简单 | 相向双指针 |
| #15 | 三数之和 | 中等 | 排序+双指针 |
| #11 | 盛最多水的容器 | 中等 | 相向双指针 |
| #3 | 无重复字符的最长子串 | 中等 | 滑动窗口 |
| #76 | 最小覆盖子串 | 困难 | 滑动窗口+哈希 |

**滑动窗口模板**
```java
// 固定/可变窗口通用模板
public int slidingWindow(String s) {
    Map<Character, Integer> window = new HashMap<>();
    int left = 0, right = 0;
    int result = 0;

    while (right < s.length()) {
        char c = s.charAt(right);
        right++;
        // 更新窗口数据
        window.merge(c, 1, Integer::sum);

        // 判断是否需要收缩窗口
        while (/* 窗口不满足条件 */ false) {
            char d = s.charAt(left);
            left++;
            // 更新窗口数据
            window.merge(d, -1, Integer::sum);
        }

        // 更新结果
        result = Math.max(result, right - left);
    }
    return result;
}
```

---

#### 专题3：链表（第5周）

**核心思路**：画图！链表题必须画图，搞清指针指向

**必刷题目**
| 题号 | 题目 | 难度 | 核心知识点 |
|------|------|------|-----------|
| #206 | 反转链表 | 简单 | 迭代/递归 |
| #21 | 合并两个有序链表 | 简单 | 虚拟头节点 |
| #141 | 环形链表 | 简单 | 快慢指针 |
| #19 | 删除链表的倒数第N个节点 | 中等 | 快慢指针 |
| #143 | 重排链表 | 中等 | 找中点+反转+合并 |
| #23 | 合并K个升序链表 | 困难 | 优先队列 |

**常用技巧**
```java
// 虚拟头节点（统一处理边界）
ListNode dummy = new ListNode(0);
dummy.next = head;

// 快慢指针找中点
ListNode slow = head, fast = head;
while (fast != null && fast.next != null) {
    slow = slow.next;
    fast = fast.next.next;
}
// slow 即为中点
```

---

#### 专题4：栈 & 队列（第6周）

**必刷题目**
| 题号 | 题目 | 难度 | 核心知识点 |
|------|------|------|-----------|
| #20 | 有效的括号 | 简单 | 栈 |
| #155 | 最小栈 | 简单 | 辅助栈 |
| #232 | 用栈实现队列 | 简单 | 双栈 |
| #739 | 每日温度 | 中等 | 单调栈 |
| #84 | 柱状图中最大的矩形 | 困难 | 单调栈 |

**单调栈模板**
```java
// 找下一个更大元素
public int[] nextGreaterElement(int[] nums) {
    int n = nums.length;
    int[] result = new int[n];
    Arrays.fill(result, -1);
    Deque<Integer> stack = new ArrayDeque<>(); // 存下标

    for (int i = 0; i < n; i++) {
        while (!stack.isEmpty() && nums[stack.peek()] < nums[i]) {
            result[stack.pop()] = nums[i];
        }
        stack.push(i);
    }
    return result;
}
```

---

#### 专题5：二叉树（第7周，最重要！）

**核心思路**：90% 的二叉树题都是递归，掌握「前/中/后序」思维

**必刷题目**
| 题号 | 题目 | 难度 | 核心知识点 |
|------|------|------|-----------|
| #104 | 二叉树的最大深度 | 简单 | DFS 后序 |
| #226 | 翻转二叉树 | 简单 | DFS 前序 |
| #101 | 对称二叉树 | 简单 | DFS |
| #102 | 二叉树的层序遍历 | 中等 | BFS |
| #98 | 验证二叉搜索树 | 中等 | 中序遍历 |
| #235 | 二叉搜索树的最近公共祖先 | 简单 | BST 性质 |
| #236 | 二叉树的最近公共祖先 | 中等 | 后序递归 |
| #105 | 从前序与中序遍历序列构造二叉树 | 中等 | 递归+哈希 |
| #124 | 二叉树中的最大路径和 | 困难 | 后序递归 |

**递归模板**
```java
// 后序遍历模板（大多数树题用这个）
public int solve(TreeNode root) {
    if (root == null) return 0; // base case

    int left = solve(root.left);   // 左子树结果
    int right = solve(root.right); // 右子树结果

    // 处理当前节点，利用左右子树结果
    return Math.max(left, right) + 1;
}

// BFS 层序遍历模板
public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    if (root == null) return result;

    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);

    while (!queue.isEmpty()) {
        int size = queue.size();
        List<Integer> level = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            TreeNode node = queue.poll();
            level.add(node.val);
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }
        result.add(level);
    }
    return result;
}
```

---

#### 专题6：图 & BFS/DFS（第8周）

**必刷题目**
| 题号 | 题目 | 难度 | 核心知识点 |
|------|------|------|-----------|
| #200 | 岛屿数量 | 中等 | DFS/BFS 网格 |
| #133 | 克隆图 | 中等 | DFS+哈希 |
| #207 | 课程表 | 中等 | 拓扑排序 |
| #417 | 太平洋大西洋水流问题 | 中等 | 多源 BFS |
| #130 | 被围绕的区域 | 中等 | 边界 DFS |

**网格 DFS 模板**
```java
int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};

public void dfs(char[][] grid, int r, int c) {
    if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length) return;
    if (grid[r][c] != '1') return;

    grid[r][c] = '0'; // 标记已访问

    for (int[] dir : dirs) {
        dfs(grid, r + dir[0], c + dir[1]);
    }
}
```

---

#### 专题7：二分搜索（第9周）

**核心思路**：二分不只用于有序数组，凡是「答案在某区间内单调变化」都可二分

**必刷题目**
| 题号 | 题目 | 难度 | 核心知识点 |
|------|------|------|-----------|
| #704 | 二分查找 | 简单 | 基础模板 |
| #74 | 搜索二维矩阵 | 中等 | 二维展开 |
| #153 | 寻找旋转排序数组中的最小值 | 中等 | 变形二分 |
| #33 | 搜索旋转排序数组 | 中等 | 变形二分 |
| #875 | 爱吃香蕉的珂珂 | 中等 | 二分答案 |

**二分模板（左闭右闭）**
```java
// 查找目标值（左闭右闭）
public int binarySearch(int[] nums, int target) {
    int left = 0, right = nums.length - 1;
    while (left <= right) {
        int mid = left + (right - left) / 2; // 防溢出
        if (nums[mid] == target) return mid;
        else if (nums[mid] < target) left = mid + 1;
        else right = mid - 1;
    }
    return -1;
}
```

---

#### 专题8：动态规划（第10周）

**核心思路**：找「状态」和「转移方程」，从暴力递归 → 记忆化 → DP 表

**必刷题目**
| 题号 | 题目 | 难度 | 核心知识点 |
|------|------|------|-----------|
| #70 | 爬楼梯 | 简单 | 入门 DP |
| #198 | 打家劫舍 | 简单 | 线性 DP |
| #322 | 零钱兑换 | 中等 | 完全背包 |
| #300 | 最长递增子序列 | 中等 | LIS |
| #1143 | 最长公共子序列 | 中等 | LCS，二维 DP |
| #72 | 编辑距离 | 中等 | 二维 DP |
| #416 | 分割等和子集 | 中等 | 0-1 背包 |

**DP 解题框架**
```
1. 定义 dp 数组含义（最难的一步）
2. 找 base case（初始值）
3. 写状态转移方程
4. 确定遍历顺序
```

```java
// 零钱兑换（完全背包）
public int coinChange(int[] coins, int amount) {
    int[] dp = new int[amount + 1];
    Arrays.fill(dp, amount + 1); // 初始化为不可能的大值
    dp[0] = 0;

    for (int i = 1; i <= amount; i++) {
        for (int coin : coins) {
            if (coin <= i) {
                dp[i] = Math.min(dp[i], dp[i - coin] + 1);
            }
        }
    }
    return dp[amount] > amount ? -1 : dp[amount];
}
```

---

#### 专题9：回溯（可穿插第8-9周）

**核心思路**：排列/组合/子集问题，暴力穷举所有可能

**必刷题目**
| 题号 | 题目 | 难度 | 核心知识点 |
|------|------|------|-----------|
| #46 | 全排列 | 中等 | 回溯基础 |
| #78 | 子集 | 中等 | 回溯 |
| #39 | 组合总和 | 中等 | 回溯+剪枝 |
| #17 | 电话号码的字母组合 | 中等 | 回溯 |
| #51 | N皇后 | 困难 | 回溯经典 |

**回溯模板**
```java
List<List<Integer>> result = new ArrayList<>();
List<Integer> path = new ArrayList<>();

public void backtrack(int[] nums, boolean[] used) {
    if (/* 终止条件 */ path.size() == nums.length) {
        result.add(new ArrayList<>(path)); // 注意要复制
        return;
    }

    for (int i = 0; i < nums.length; i++) {
        if (used[i]) continue; // 剪枝
        used[i] = true;
        path.add(nums[i]);
        backtrack(nums, used);   // 递归
        path.remove(path.size() - 1); // 回撤
        used[i] = false;
    }
}
```

---

### 第三阶段：综合强化（第11-14周）

**目标**：刷 LeetCode Hot 100 中未覆盖的题

| 方向 | 推荐题目 |
|------|---------|
| 贪心 | #55 跳跃游戏、#45 跳跃游戏II、#435 无重叠区间 |
| 堆 | #215 数组中的第K个最大元素、#347 前K个高频元素 |
| 前缀和 | #560 和为K的子数组、#238 除自身以外数组的乘积 |
| Trie | #208 实现 Trie、#212 单词搜索II |

---

### 第四阶段：模拟面试冲刺（第15周起）

1. 限时 45 分钟做 2 道题（模拟真实面试节奏）
2. 用 LeetCode 模拟面试功能
3. 每周至少做 1 套周赛

---

## 三、每日刷题节奏

```
┌─────────────────────────────────────────────────────┐
│  每日计划（约1.5-2小时）                              │
│                                                     │
│  复习昨日题目（20分钟）                               │
│    └─ 不看答案默写                                   │
│                                                     │
│  新题（60-90分钟）                                   │
│    ├─ 独立思考 ≤25分钟                               │
│    ├─ 没思路 → 看题解理解，关掉后默写                  │
│    └─ 写完后分析时间/空间复杂度                        │
│                                                     │
│  总结归纳（10分钟）                                   │
│    └─ 这题属于哪种模式？和哪道题类似？                  │
└─────────────────────────────────────────────────────┘
```

**每周安排**
| 时间 | 任务 |
|------|------|
| 周一至周五 | 按专题刷新题（1-2题/天） |
| 周六 | 复习本周所有题，整理笔记 |
| 周日 | 参加 LeetCode 周赛 或 综合练习 |

---

## 四、遇到不会的题怎么办

```
不会 → 思考25分钟 → 还不会?
                        ↓
                   看题解（只看思路，不看代码）
                        ↓
                   自己实现
                        ↓
                   实现不了？看代码，理解每一行
                        ↓
                   关掉代码，默写
                        ↓
                   默写不出来？重复上一步
                        ↓
                   3天后复习（不看答案重做）
```

**永远不要做的事**：看完题解直接关掉，去刷下一题。

---

## 五、题目复习系统（间隔重复）

| 复习时间 | 操作 |
|---------|------|
| 第1天 | 做题 |
| 第2天 | 不看答案重做 |
| 第7天 | 再做一次 |
| 第30天 | 最终巩固 |

可以用 **Anki** 或简单记录在文件里：
```
题号 | 第一次日期 | 第二次 | 第七天 | 第三十天
#1   | 2026-01-01 | ✅     | ✅     | ✅
#206 | 2026-01-02 | ✅     | ⬜     | ⬜
```

---

## 六、推荐题单

| 题单 | 题目数 | 适合阶段 | 链接 |
|------|-------|---------|------|
| LeetCode 75 | 75题 | 入门 | 官方精选，覆盖所有核心专题 |
| Neetcode 150 | 150题 | 进阶 | 分类清晰，配有视频讲解 |
| LeetCode Hot 100 | 100题 | 面试冲刺 | 高频面试题 |
| 剑指Offer | 75题 | 国内面试 | 国内大厂高频 |

**推荐顺序**：LeetCode 75 → Hot 100 → Neetcode 150

---

## 七、学习资源

| 资源 | 用途 |
|------|------|
| [代码随想录](https://programmercarl.com/) | 中文，分类详细，适合入门 |
| [Neetcode.io](https://neetcode.io/) | 英文，视频讲解清晰 |
| [labuladong 的算法笔记](https://labuladong.online/) | 模板化思维，质量高 |
| LeetCode 官方题解 | 权威，但有时过于简洁 |

---

## 八、常见误区

| 误区 | 正确做法 |
|------|---------|
| 追求刷题数量 | 少而精，理解每道题的模式 |
| 看完题解不动手 | 必须默写实现 |
| 只刷简单题 | 按专题混合刷，中等题是重点 |
| 不总结规律 | 每道题做完要归类 |
| 遇到不会的题钻牛角尖 | 25分钟没思路就看题解 |
| 一次性把题解全背下来 | 间隔重复，分散复习 |

---

## 九、入门30题清单（第一个月目标）

按顺序完成，每题要求：能独立写出，清楚时间复杂度

- [ ] #1 两数之和
- [ ] #217 存在重复元素
- [ ] #242 有效的字母异位词
- [ ] #53 最大子数组和
- [ ] #283 移动零
- [ ] #344 反转字符串
- [ ] #167 两数之和 II
- [ ] #15 三数之和
- [ ] #11 盛最多水的容器
- [ ] #3 无重复字符的最长子串
- [ ] #206 反转链表
- [ ] #21 合并两个有序链表
- [ ] #141 环形链表
- [ ] #19 删除链表的倒数第N个节点
- [ ] #20 有效的括号
- [ ] #155 最小栈
- [ ] #104 二叉树的最大深度
- [ ] #226 翻转二叉树
- [ ] #101 对称二叉树
- [ ] #102 二叉树的层序遍历
- [ ] #98 验证二叉搜索树
- [ ] #704 二分查找
- [ ] #153 寻找旋转排序数组中的最小值
- [ ] #33 搜索旋转排序数组
- [ ] #200 岛屿数量
- [ ] #70 爬楼梯
- [ ] #198 打家劫舍
- [ ] #322 零钱兑换
- [ ] #46 全排列
- [ ] #739 每日温度

---

*坚持最重要。每天1-2题，3个月后会有质的飞跃。*
