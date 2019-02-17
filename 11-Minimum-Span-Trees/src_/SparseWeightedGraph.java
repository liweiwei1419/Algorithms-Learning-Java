import java.util.Vector;

/**
 * 带权图的稀疏图表示（邻接表表示）
 * <p>
 * Created by liwei on 17/6/15.
 */
public class SparseWeightedGraph<W extends Number & Comparable>
        implements WeightedGraph {
    /**
     * 顶点数
     */
    private int n;
    /**
     * 邻边数
     */
    private int m;
    /**
     * 是否是有向图
     */
    private boolean directed;
    /**
     * 图的具体数据
     */
    private Vector<Edge<W>>[] g;


    public SparseWeightedGraph(int n, boolean directed) {
        assert n >= 0;
        this.n = n;
        this.m = 0;
        this.directed = directed;
        g = (Vector<Edge<W>>[]) new Vector[n];
        for (int i = 0; i < n; i++) {
            g[i] = new Vector<>();
        }
    }

    /**
     * 返回节点的个数
     *
     * @return
     */
    @Override
    public int V() {
        return n;
    }

    /**
     * 返回边的条数
     *
     * @return
     */
    @Override
    public int E() {
        return m;
    }

    /**
     * 这个方法要留意
     * 这个方法要留意
     * 这个方法要留意
     * 向图中添加一条边的对象
     *
     * @param e
     */
    @Override
    public void addEdge(Edge e) {
        assert e.v() >= 0 && e.v() < n;
        assert e.w() >= 0 && e.w() < n;

        // 这里要 new 一个新的对象添加进去，
        // 下面也一样
        g[e.v()].add(new Edge<W>(e));

        // 我们不允许自环边
        // 但是我们允许平行边
        // 允许平行边的原因：查找平行边的时间复杂度可观 O(n)
        if (!directed && e.v() != e.w()) {
            g[e.w()].add(new Edge(e.w(), e.v(), e.wt()));
        }
        m++;
    }

    /**
     * 这里应用了 other 这个函数帮助我们判断 v 和 w 之间是否有连线
     *
     * @param v
     * @param w
     * @return
     */
    @Override
    public boolean hasEdge(int v, int w) {
        assert v >= 0 && v < n;
        assert w >= 0 && w < n;
        Vector<Edge<W>> current = g[v];
        for (int i = 0; i < current.size(); i++) {
            if (current.get(i).other(v) == w) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void show() {
        for (int i = 0; i < n; i++) {
            System.out.printf("节点[%d]：", i);
            for (int j = 0; j < g[i].size(); j++) {
                Edge current = g[i].get(j);
                System.out.printf("{to:" + current.other(i) + ",weight:" + current.wt() + "}\t");
            }
            System.out.println();
        }
    }

    /**
     * 返回一个图中
     *
     * @param v
     * @return
     */
    @Override
    public Iterable<Edge<W>> iterator(int v) {
        assert v >= 0 && v < n;
        return g[v];
    }

}
