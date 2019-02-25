import java.util.ArrayList;
import java.util.List;

/**
 * LazyPrim ：懒惰的 Prim 实现，Prim（普里姆）
 * MST：Minimum Spanning Tree，最小生成树
 * 使用 lazy 的 Prim 算法求得最小生成树
 * Created by liwei on 17/6/16.
 */
public class LazyPrimMST<Weight extends Number & Comparable> {
    /**
     * 带权图的引用
     */
    private WeightGraph<Weight> G;
    /**
     * pq 是优先（priority）队列（queue）的意思，最小堆
     */
    private MinHeap<Edge<Weight>> pq;
    /**
     * 标记数组，在算法运行过程中标记节点 i 是否被访问
     */
    private boolean[] marked;
    /**
     * 最小生成树包含的所有的边
     */
    private List<Edge<Weight>> mst;
    /**
     * 最小生成树的权值
     */
    private Number mstWeight;

    // 在构造方法中完成最小生成树的计算
    public LazyPrimMST(WeightGraph<Weight> graph) {

        // 初始化一些成员变量和辅助的数据结构
        this.G = graph;
        pq = new MinHeap(graph.E());// lazy prim 算法每次考虑的边的条数就是图的最多的边数
        marked = new boolean[this.G.V()];
        mst = new ArrayList<>();

        // lazt prim
        visit(0);
        while (!pq.isEmpty()) { // 每次都拿出考虑的边的最小值，看看它是不是横切边，这就是 lazy prim 称之为 lazy 的原因
            // 使用最小堆找出已经访问的边中权值最小的边
            Edge<Weight> e = pq.extractMin();
            // 如果这条边的两端都已经访问过了, 则扔掉这条边
            if (marked[e.v()] == marked[e.w()]) {
                continue;
            }
            // 否则，这条边就是最小生成树中的一条边
            mst.add(e);
            // 将这条边中还没有被访问过的那个顶点，执行和一开始一样的 visit 操作
            if (!marked[e.v()]) {
                visit(e.v());
            } else {
                visit(e.w());
            }
        }

        // 最小堆中的元素都考虑完以后（根据上面的逻辑，要考虑完那些不是横切边的边）
        this.mstWeight = mst.get(0).weight();
        for (int i = 1; i < mst.size(); i++) {
            this.mstWeight = this.mstWeight.doubleValue() + mst.get(i).weight().doubleValue();
        }
    }

    // 访问一个顶点，只做一件事情，将和这个顶点直接相连的还未加入最小堆的边加入最小堆
    private void visit(int v) {
        assert !marked[v];
        marked[v] = true;
        for (Edge<Weight> e : this.G.adj(v)) {
            // 【注意】技巧在这里：只要是另一个端点还没有被标记过，那么就表示这条边还未加入到最小堆中
            if (!marked[e.other(v)]) {
                pq.insert(e);
            }
        }
    }

    // 返回最小生成树的所有边
    public List<Edge<Weight>> mstEdges() {
        return mst;
    }

    // 返回最小生成树的权值
    public Number result() {
        return mstWeight;
    }
}

