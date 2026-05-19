package com.yintp.algorithm.leetcode.topic6;

/**
 * #200 岛屿数量 (Number of Islands) - Medium
 * 专题: 图/DFS | 第8周 Day1
 *
 * 给你一个由 '1'(陆地) 和 '0'(水) 组成的二维网格，计算网格中岛屿的数量。
 * 岛屿被水包围，通过水平或垂直连接相邻陆地形成。
 *
 * 示例: [["1","1","0","0","0"],["1","1","0","0","0"],["0","0","1","0","0"],["0","0","0","1","1"]] → 3
 *
 * 思路: DFS - 遍历网格，遇到'1'就DFS将整个岛屿标记为'0'，计数+1
 */
public class Q200NumIslands {
    public int numIslands(char[][] grid) {
        // TODO: DFS淹没岛屿，计数
        return 0;
    }

    private void dfs(char[][] grid, int r, int c) {
        // TODO: 四方向DFS，越界或非'1'返回
    }
}
