import java.util.Vector;

// 稀疏图的邻接表表示
public class SparseGraph implements Graph {

    private int n; // 有多少个顶点
    private int m; // 有多少条边
    private boolean directed; // 是否为有向图

    private Vector<Integer>[] g; // 邻接表是一个 Vector 的数组

    public Vector<Integer>[] getG() {
        return g;
    }

    public SparseGraph(int n, boolean directed) {
        assert n >= 0;

        this.n = n;
        this.m = 0;
        this.directed = directed;

        // 初始化数组
        g = new Vector[n];
        for (int i = 0; i < n; i++) {
            // 只要初始化就可以了
            g[i] = new Vector<>();
        }
    }

    @Override
    public int V() {
        return n;
    }

    @Override
    public int E() {
        return m;
    }

    @Override
    public void addEdge(int v, int w) {
        assert v >= 0 && v < n;
        assert w >= 0 && w < n;
        g[v].add(w);
        if (v != w && !directed) { // 无向图
            g[w].add(v);
        }
        m++;
    }

    // 这个方法的复杂度是 O(n)，因为这是一个比较可观的复杂度，
    // 所以我们就暂时允许 addEdge() 中出现平行边，而不作检测。
    @Override
    public boolean hasEdge(int v, int w) {
        assert v >= 0 && v < n;
        assert w >= 0 && w < n;
        Vector<Integer> currentEdge = g[v];
        int length = currentEdge.size();
        for (int i = 0; i < length; i++) {
            int current = currentEdge.get(i);
            if (current == w) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void show() {
        for (int i = 0; i < n; i++) {
            System.out.print("vertex " + i + ":\t");
            for (int j = 0; j < g[i].size(); j++)
                System.out.print(g[i].elementAt(j) + "\t");
            System.out.println();
        }
    }

    /**
     * 返回一个图中指定顶点的所有邻居
     * 在稀疏图中，这个操作是异常简单的
     *
     * @param v
     * @return
     */
    public Iterable<Integer> adj(int v) {
        assert v >= 0 && v < n;
        return g[v];
    }
}