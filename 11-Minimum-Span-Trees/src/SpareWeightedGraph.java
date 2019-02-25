import java.util.ArrayList;
import java.util.List;


/**
 * 带权图的稀疏图实现
 * Created by liwei on 17/6/15.
 * @param <Weight>
 */
public class SpareWeightedGraph<Weight extends Number & Comparable> implements WeightGraph {

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
    private List<Edge<Weight>>[] g;

    public SpareWeightedGraph(int n, boolean directed) {
        this.n = n;
        this.m = 0;
        this.directed = directed;
        // 初始化的写法有点怪，要留意，无非就是做强制类型转换
        g = (ArrayList<Edge<Weight>>[]) new ArrayList[n];
        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<Edge<Weight>>();
        }
    }

    /**
     * 返回结点的个数
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
    public void addEdge(Edge edge) {
        assert edge.v() >= 0 && edge.v() < n;
        assert edge.w() >= 0 && edge.w() < n;
        // 在使用邻接表表示带权图的时候，如果检查是否有重复边，要遍历整个链表
        // 为此我们这里允许重复边，即 hasEdge 就不做了
        g[edge.v()].add(new Edge(edge));

        // 我们不允许自环边
        // 但是我们允许平行边
        // 允许平行边的原因：查找平行边的时间复杂度可观 O(n)
        if (!directed) {// edge.v()!=edge.w()
            // 注意：这里我们都让这个数组的所有的边的 w 等于索引到这个数组的 w 值
            // 这就是下面这一行代码为什么要先写 edge.w() 再写 edge.v() 的原因
            // 如果不是很理解，到图的实现算法的时候，就会清楚了
            g[edge.w()].add(new Edge(edge.w(), edge.v(), edge.weight()));
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
        for (int i = 0; i < g[v].size(); i++) {
            if (g[v].get(i).other(v) == w) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void show() {
        for (int i = 0; i < n; i++) {
            System.out.print("vertext " + i + ":\t");
            for (int j = 0; j < g[i].size(); j++) {
                if (g[i].get(j) != null) {
                    Edge e = g[i].get(j);
                    System.out.print("(to: " + e.other(i) + ",wt:" + String.format("%.2f", e.weight()) + ")\t");
                } else {
                    System.out.print("NULL\t");
                }
            }
            System.out.println();
        }
    }

    @Override
    public Iterable<Edge<Weight>> adj(int v) {
        assert v >= 0 && v < n;
        return g[v];
    }
}
