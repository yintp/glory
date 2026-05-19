package com.yintp.algorithm.leetcode.topic6;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * #417 太平洋大西洋水流问题 (Pacific Atlantic Water Flow) - Medium
 * 专题: 图/BFS | 第8周 Day3
 *
 * 给定 m×n 的非负整数矩阵，水从高处流向低处或等高，返回所有能流向太平洋和大西洋的单元格坐标。
 * 太平洋: 上边界和左边界；大西洋: 下边界和右边界。
 *
 * 思路: 反向BFS - 从边界出发，逆向找能到达的格子（高度 >= 当前高度）
 *   - pacificReachable: 从太平洋边界出发的BFS
 *   - atlanticReachable: 从大西洋边界出发的BFS
 *   - 两个集合的交集即为答案
 */
public class Q417PacificAtlantic {
    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        // TODO: 两次BFS从边界逆向扩展
        return new ArrayList<>();
    }
}
