package com.yintp.algorithm.leetcode.topic6;

import com.yintp.algorithm.leetcode.structure.Node;
import java.util.HashMap;
import java.util.Map;

/**
 * #133 克隆图 (Clone Graph) - Medium
 * 专题: 图/DFS | 第8周 Day1
 *
 * 给你无向连通图中一个节点的引用，返回该图的深拷贝（克隆）。
 *
 * 思路: DFS + HashMap
 *   - visited: 原节点 → 克隆节点的映射（防止重复克隆）
 *   - DFS遍历，先克隆当前节点，再递归克隆邻居
 */
public class Q133CloneGraph {
    private Map<Node, Node> visited = new HashMap<>();

    public Node cloneGraph(Node node) {
        // TODO: DFS + HashMap克隆
        return null;
    }
}
