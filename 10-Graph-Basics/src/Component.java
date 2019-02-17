// 我们将与图有关的算法都实现在这个类中
public class Component {

    private Graph graph;
    private boolean[] visited;
    private int[] id;

    // 联通分量的个数，同时可以在遍历的过程中，为各个联通分量所在的集合进行标记
    private int ccount;

    private void dfs(int i) {
        // 记录是否被访问过，这个非常关键，深度遍历一开始就要记录这些节点被访问过
        visited[i] = true;
        // 在计算联通分量的同时，给每一个顶点编号，编号的规则是：属于一个联通分量的应该编号一样。
        id[i] = ccount;
        Iterable<Integer> adj = graph.adj(i);
        for (Integer v : adj) {
            if (!visited[v]) {
                dfs(v);
            }
        }
    }

    // 构造方法，通过传入一个图，经过了深度优先遍历，计算无权图的联通分量
    public Component(Graph graph) {
        this.graph = graph;
        // 图中顶点的个数
        int vCount = graph.V();
        // 不用显式赋值，因为默认都是 false
        visited = new boolean[vCount];
        id = new int[vCount];

        for (int i = 0; i < vCount; i++) {
            visited[i] = false;
            id[i] = -1;
        }

        for (int i = 0; i < vCount; i++) {
            if (!visited[i]){// 如果没有遍历过，就进行一次深度优先遍历
                // depth first search 深度优先遍历
                // 注意：深度优先遍历，不是遍历这个节点的所有邻居
                // 而是把所有与该点向量的所有节点都遍历一遍
                dfs(i);
                ccount++; // 经过了一次深度优先遍历以后，连通分量 +1
            }
        }
    }

    // 返回这个图的联通分量的个数
    public int getCcount() {
        return ccount;
    }

    // 判断两个节点是否同连通
    public boolean isConnected(int v, int w) {
        assert v >= 0 && v < graph.V();
        assert w >= 0 && w < graph.V();
        return id[v] == id[w];
    }
}
