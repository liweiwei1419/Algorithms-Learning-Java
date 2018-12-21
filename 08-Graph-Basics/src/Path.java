import java.util.Stack;
import java.util.Vector;

// 寻路算法，只能得到无权图的一条路径，该路径并不是最短路径
// 该算法对有向图依然有效
public class Path {
    private Graph graph;
    private boolean[] visited;
    private int[] from;

    private void dfs(Integer v) {
        visited[v] = true;
        Iterable<Integer> adj = graph.adj(v);
        for (Integer i : adj) {
            if (!visited[i]) {
                from[i] = v;
                dfs(i);
            }
        }
    }

    public Path(Graph graph, int s) {
        assert s >= 0 && s < graph.V();
        this.graph = graph;
        int vCount = graph.V();
        visited = new boolean[vCount];
        from = new int[vCount];
        for (int i = 0; i < vCount; i++) {
            visited[i] = false;
            from[i] = -1;
        }
        // 从 source 开始，做一次深度优先遍历
        dfs(s);
    }

    // 查询从 s 点到 w 点是否有路径
    public boolean hasPath(int w) {
        assert w >= 0 && w < graph.V();
        return visited[w];
    }

    // 得到从 s 到 w 的一条路径
    public void path(int w, Vector<Integer> vec) {
        assert hasPath(w);
        // 我们要倒着把路径得到，所以应该借助栈来完成该工作
        Stack<Integer> stack = new Stack<>();
        // 这一段逻辑比较绕，但不难
        int p = w;
        while (p != -1) {
            stack.add(p);
            p = from[p];
        }
        while (!stack.isEmpty()) {
            vec.add(stack.pop());
        }
    }

    // 显示从 s 到 w 的一条路径
    public void showPath(int w) {
        Vector<Integer> vec = new Vector<>();
        path(w, vec);
        for (int i = 0; i < vec.size(); i++) {
            System.out.print(vec.get(i));
            if (i == vec.size() - 1) {
                System.out.println();
            } else {
                System.out.print(" -> ");
            }
        }
    }
}
