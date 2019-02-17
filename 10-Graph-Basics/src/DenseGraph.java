import java.util.Vector;

// 稠密图的邻接矩阵表示
public class DenseGraph implements Graph {

    private int n; // 有多少个顶点
    private int m; // 有多少条边
    private boolean directed;    // 是否为有向图
    private boolean[][] g;

    public boolean[][] getG() {
        return g;
    }

    public DenseGraph(int n, boolean directed) {
        this.n = n;
        this.m = 0;
        this.directed = directed;

        // 初始化的时候 n*n 的矩阵里所有的元素都是 false
        g = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                g[i][j] = false;
            }
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

    /**
     * 顶点 v 和 顶点 w 之间建立一条边
     *
     * @param v
     * @param w
     */
    @Override
    public void addEdge(int v, int w) {
        assert v >= 0 && v < n;
        assert w >= 0 && w < n;
        // 不允许添加平行点
        if (hasEdge(v, w)) {
            return;
        }
        g[v][w] = true;
        if (!directed) { // 如果是无向图，保证无向图的对称性
            g[w][v] = true;
        }
        m++;
    }

    // 验证图中是否有从v到w的边
    @Override
    public boolean hasEdge(int v, int w) {
        assert v >= 0 && v < n;
        assert w >= 0 && w < n;
        return g[v][w];
    }

    /**
     * 显示图的时候，简单把它打印出来
     */
    @Override
    public void show() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                System.out.print(g[i][j] + "\t");
            System.out.println();
        }
    }

    public Iterable<Integer> adj(int v) {
        assert v >= 0 && v < n;
        Vector<Integer> adjV = new Vector<>();
        for (int i = 0; i < n; i++) {
            if (g[v][i]) {
                adjV.add(i);
            }
        }
        return adjV;
    }
}
