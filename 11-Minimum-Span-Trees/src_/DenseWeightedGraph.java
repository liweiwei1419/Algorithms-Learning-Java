import java.util.Vector;

/**
 * 使用邻接矩阵
 * 稠密图的带权图表示，
 * Created by liwei on 17/6/16.
 */
public class DenseWeightedGraph<W extends Number & Comparable> implements WeightedGraph {
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
    /**
     * 图的具体数据
     */
    private Edge<W>[][] g;


    public DenseWeightedGraph(int n, boolean directed) {
        assert n >= 0;
        this.n = n;
        this.m = 0;
        this.directed = directed;
        g = new Edge[n][n];
        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < g[i].length; j++) {
                g[i][j] = null;
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

    @Override
    public void addEdge(Edge e) {
        assert e.v() >= 0 && e.v() < n;
        assert e.w() >= 0 && e.w() < n;
        if (hasEdge(e.v(), e.w())) {
            return;
        }
        g[e.v()][e.w()] = new Edge<W>(e);
        if (e.v() != e.w() && !directed) {
            g[e.w()][e.v()] = new Edge(e.v(), e.w(), e.wt());
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
        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < g[i].length; j++) {
                if (g[i][j] != null) {
                    System.out.printf("%.2f\t", g[i][j].wt());
                } else {
                    System.out.printf("%s\t", "NULL");
                }
            }
            System.out.println();
        }

    }

    @Override
    public Iterable<Edge<W>> iterator(int v) {
        assert v>=0 && v<n;
        Vector<Edge<W>> adjV = new Vector<>();
        for (int i = 0; i < g[v].length; i++) {
            if(g[v][i]!=null){
                adjV.add(new Edge<W>(g[v][i]));
            }

        }
        return adjV;
    }
}
