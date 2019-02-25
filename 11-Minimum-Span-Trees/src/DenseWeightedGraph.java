import java.util.ArrayList;
import java.util.List;

/**
 * 使用邻接矩阵
 * 稠密图的带权图表示，
 * Created by liwei on 17/6/16.
 */
public class DenseWeightedGraph<Weight extends Number & Comparable> implements WeightGraph {

    /**
     * 顶点个数
     */
    private int n;
    /**
     * 边的条数
     */
    private int m;
    /**
     * 是否是有向
     */
    private boolean directed;
    // 图的具体数据，有边就有了结点信息，就能表示图
    private Edge<Weight>[][] g;

    public DenseWeightedGraph(int n, boolean directed) {
        assert n > 0;
        this.n = n; // 顶点数
        this.m = 0;
        this.directed = directed; // 是否有向
        g = new Edge[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                g[i][j] = null;
            }
        }
    }

    // 返回节点的个数
    @Override
    public int V() {
        return n;
    }

    // 返回边的条数
    @Override
    public int E() {
        return m;
    }

    // 添加一条边
    @Override
    public void addEdge(Edge edge) {
        assert edge.v() >= 0 && edge.v() < n;
        assert edge.w() >= 0 && edge.w() < n;
        if (hasEdge(edge.v(), edge.w())) {
            // 如果已经有了这条边就什么都不做
            return;
        }
        g[edge.v()][edge.w()] = new Edge(edge);
        if (edge.v()!=edge.w() && !directed) { //
            // 如果是无向图，反着还要添加一下
            g[edge.w()][edge.v()] = new Edge(edge);
        }
        m++;
    }

    @Override
    public boolean hasEdge(int v, int w) {
        assert v >= 0 && v < n;
        assert w >= 0 && w < n;
        return g[v][w] != null;
    }

    @Override
    public void show() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (g[i][j] != null) {
                    System.out.print(String.format("%.2f", g[i][j].weight()) + "\t");
                } else {
                    System.out.print("NULL\t");
                }
            }
            System.out.println();
        }
    }

    // 返回图中一个顶点的所有邻边，由于 Java 使用引用机制，返回一个 Vector 不会带来额外开销,
    @Override
    public Iterable<Edge<Weight>> adj(int v) {
        assert v >= 0 && v < n;
        List<Edge<Weight>> adjV = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (g[v][i] != null) {
                adjV.add(new Edge<W>(g[v][i]));
            }
        }
        return adjV;
    }
}
