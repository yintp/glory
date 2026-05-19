package com.yintp.algorithm.leetcode.topic6;

import java.util.List;
import java.util.ArrayList;

/**
 * #207 课程表 (Course Schedule) - Medium
 * 专题: 图/拓扑排序 | 第8周 Day2
 *
 * 你这个学期必须选修 numCourses 门课程，课程之间有先修关系 prerequisites[i] = [a, b] 表示学a前必须先学b。
 * 判断是否可能完成所有课程的学习。
 *
 * 思路: 拓扑排序(BFS) + 入度数组
 *   - 构建邻接表和入度数组
 *   - 将入度为0的节点入队，BFS逐层减少入度，直到队列为空
 *   - 若所有节点都被处理则无环，否则有环
 */
public class Q207CanFinish {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // TODO: 拓扑排序，检测有向图中是否有环
        return false;
    }
}
