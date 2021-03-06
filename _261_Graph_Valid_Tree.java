import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Junyan Zhang on 1/29/2018.
 */
public class _261_Graph_Valid_Tree {
    /**
     Given n nodes labeled from 0 to n - 1 and a list of undirected edges (each edge is a pair of nodes),
     write a function to check whether these edges make up a valid tree.

     For example:

     Given n = 5 and edges = [[0, 1], [0, 2], [0, 3], [1, 4]], return true.

     Given n = 5 and edges = [[0, 1], [1, 2], [2, 3], [1, 3], [1, 4]], return false.

     2 -- 0 -- 1 -- 4
     |
     3

          4
          |
     0 -- 1 -- 2
           \  /
            3

     题意：
         用0 ~ n-1表示n个节点，每一对数组表示两个点之间存在一条无向边，写一个函数检查是否这些边构成一棵合法的树


     思路：
        是否是一棵合法的树，主要判断图中是否存在环。判断图里是否包含环有两种方法：
        1，DFS（深度优先搜索）
        2. Union Find（并查集）

        DFS（深度优先搜索）
            题目给的图是一个所有边的列表，先将给出的图转化成邻接矩阵的表示法。再初始化一个HashSet记录某个点是否已经被访问过。
            然后，递归的对这个图进行深度优先搜索，如果某个节点重复被访问过，就说明图中有环。

        Union Find （并查集）
            新建一个n大小的数组并全部初始化为-1。定义find函数：roots[i]表示节点i所指向的节点，每次调用find函数，相当于向上
            查找目前已知的该节点的根节点。每次遇到一条新的边用roots[x] = y来将新的边加入，如果某两个节点有相同的根节点，
            则返回false。


     复杂度：
        time : O(edges * nodes)
        space : O(n)

     视频：
        https://www.youtube.com/watch?v=vsIb9B84Rt8

     * @param n
     * @param edges
     * @return
     */

    public boolean validTree(int n, int[][] edges) {
        if (n == 1 && edges.length == 0) return true;
        if (n < 1 || edges == null || edges.length != n - 1) return false;

        int[] roots = new int[n];
        // Arrays.fill(roots, -1);
        for (int i = 0; i < n; i++) {
            roots[i] = -1;
        }

        for (int[] pair : edges) {
            int x = find(roots, pair[0]);
            int y = find(roots, pair[1]);
            if (x == y) return false;
            roots[x] = y;
        }
        return true;
    }

    private int find(int[] roots, int i) {
        while (roots[i] != -1) {
            i = roots[i];
        }
        return i;
    }

    public boolean validTree2(int n, int[][] edges) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int i = 0; i < edges.length; i++) {
            graph.get(edges[i][0]).add(edges[i][1]);
            graph.get(edges[i][1]).add(edges[i][0]);
        }

        HashSet<Integer> visited = new HashSet<>();
        visited.add(0);

        boolean res = helper(graph, visited, 0, -1);
        if (res == false) return false;
        return visited.size() == n ? true : false;

        //return helper(graph, visited, 0, -1) && visited.size() == n;
    }

    private boolean helper(List<List<Integer>> graph, HashSet<Integer> visited, int node, int parent) {
        List<Integer> sub = graph.get(node);
        for (int v : sub) {
            if (v == parent) continue;
            if (visited.contains(v))return false;
            visited.add(v);
            boolean res = helper(graph, visited, v, node);
            if (res == false) return false;
        }
        return true;
    }
}
