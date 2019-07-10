import java.util.ArrayList;
import java.util.List;

/**
 * Prim 算法的即时实现
 *
 * @param <Weight>
 */
public class PrimMST<Weight extends Number & Comparable> {
    /**
     * 图的引用
     */
    private WeightGraph<Weight> G;
    /**
     * 最小索引堆
     */
    private IndexMinHeap<Weight> ipq;
    private boolean[] marked;
    /**
     * 访问的点所对应的边
     */
    private Edge<Weight>[] edgeTo;
    /**
     * 最小生成树所包含的所有边
     */
    private List<Edge<Weight>> mst;
    /**
     * 最小生成树的权值
     */
    private Number mstWeight;

    public PrimMST(WeightGraph graph) {
        G = graph;
        ipq = new IndexMinHeap<>(graph.V());

        // 初始化辅助的数据结构和一些成员变量
        marked = new boolean[G.V()];
        edgeTo = new Edge[G.V()];

        for (int i = 0; i < G.V(); i++) {
            marked[i] = false;
            edgeTo[i] = null;
        }

        mst = new ArrayList<>();

        // Prim 
        visit(0);
        while (!ipq.isEmpty()) {
            // 我们其实不需要知道这个索引
            int v = ipq.extractMinIndex();
            // 我们要知道的是和顶点 v 相连的横切边是谁

            // 【注意】我们这里要确认一下这个横切边是存在的
            assert edgeTo[v] != null;

            mst.add(edgeTo[v]);
            visit(v);
        }
        // 最小堆中的元素都考虑完以后（根据上面的逻辑，要考虑完那些不是横切边的边）
        this.mstWeight = mst.get(0).weight();
        for (int i = 1; i < mst.size(); i++) {
            this.mstWeight = this.mstWeight.doubleValue() + mst.get(i).weight().doubleValue();
        }
    }

    private void visit(int v) {
        // 这个顶点一定要是未访问过的，因此这里要做一个判断
        assert !marked[v];
        marked[v] = true;

        // 遍历这个顶点的所有邻边
        for (Edge<Weight> e : this.G.adj(v)) {
            int w = e.other(v);
            // 如果顶点的另一个端点还没有访问过，那么这条边就可以加入索引堆
            if (!marked[w]) {

                // 【注意】另一个顶点还是没有被访问过，就添加进去
                // 如果被访问过，那么一定要比当前的权值还小，才更新

                if (edgeTo[w] == null) {
                    edgeTo[w] = e;
                    ipq.insert(w, e.weight());
                } else if (e.weight().compareTo(edgeTo[w].weight()) < 0) {
                    // 新找到的横切边一定要比以前找到的横切边要小
                    // 才进行更新
                    edgeTo[w] = e;
                    ipq.change(w, e.weight());
                }
            }
        }
    }


    /**
     * 返回最小生成树的所有边
     * @return
     */
    public List<Edge<Weight>> mstEdges() {
        return mst;
    }


    /**
     * 返回最小生成树的权值
     * @return
     */
    public Number result() {
        return mstWeight;
    }
}
