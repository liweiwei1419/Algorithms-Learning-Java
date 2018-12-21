import java.util.*;

public class ShortestPath {

    private Graph graph;
    private int s; // source
    private boolean[] visited;
    private int[] from; // 记录了从哪个节点访问而来
    private int[] ord;     // 记录了层序

    public ShortestPath(Graph graph, int s) {
        this.graph = graph;
        assert s >= 0 && s < graph.V();

        visited = new boolean[graph.V()];
        from = new int[graph.V()];
        ord = new int[graph.V()];

        for (int i = 0; i < graph.V(); i++) {
            visited[i] = false;
            from[i] = -1;
            ord[i] = -1;
        }
        this.s = s;

        // 特别要注意：使用链表作为队列的时候，出队和入队不能混淆
        // 如果入队的方向是从右到左，出队也是从右到左
        // 如果入队的方向是从左到右，出队也是从左到右
        LinkedList<Integer> queue = new LinkedList<>();
        queue.push(s);
        visited[s] = true;
        ord[s] = 0;
        while (!queue.isEmpty()) {
            // peek 的意思是：我只是瞅了一眼，并没有真的把它拿出来
            int v = queue.pollLast();
            for (Integer i : graph.adj(v)) {
                if (!visited[i]) {
                    // 注意：LinkedList 有 push 和 add 两种方法
                    // 这一点区别是我在使用测试用例的过程中发现了错误，使用 IDEA 的 debug 调试代码的过程中发现的
                    // push 的源代码点开，你会看到 addFirst
                    // add 的源代码点开，你会看到 linkLast
                    queue.push(i);
                    visited[i] = true;
                    from[i] = v;
                    ord[i] = ord[v] + 1;
                }
            }
        }
    }

    // 图的广度优先遍历可以回答的问题 1 : 判断到节点 w 是否有路径
    public boolean hasPath(int w) {
        assert w >= 0 && w < graph.V();
        return visited[w];
    }

    // 图的广度优先遍历可以回答的问题 2 ：打印从 s 到 w 的最短路径
    public void showPath(int w) {
        assert hasPath(w);
        List<Integer> paths = path(w);
        for (int i = 0; i < paths.size(); i++) {
            if (i == paths.size() - 1) {
                System.out.println(paths.get(i));
            } else {
                System.out.print(paths.get(i) + " -> ");
            }
        }
    }

    // 图的广度优先遍历可以回答的问题 3 ：计算从 s 到 w 的最短路径
    public List<Integer> path(int w) {
        Stack<Integer> stack = new Stack<>();
        int cur = w;
        while (cur != -1) {
            stack.push(cur);
            cur = from[cur];
        }
        List<Integer> ret = new ArrayList<>();
        while (!stack.isEmpty()) {
            ret.add(stack.pop());
        }
        return ret;
    }

    // 图的广度优先遍历可以回答的问题 4 ：查看从 s 点到 w 点的最短路径长度，若从 s 到 w 不可达，返回 -1
    public int length(int w) {
        assert w >= 0 && w < graph.V();
        return ord[w];
    }
}
