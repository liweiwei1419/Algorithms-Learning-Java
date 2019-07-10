import java.util.ArrayList;
import java.util.List;

/**
 * Kruskal（克鲁斯卡尔）算法
 * @param <Weight>
 */
public class KruskalMST<Weight extends Number & Comparable> {

    private List<Edge<Weight>> mst;
    private Number mstWeight;

    /**
     * 使用 Kruskal 算法计算带权图的最小生成树
     * @param graph
     */
    public KruskalMST(WeightGraph<Weight> graph) {

        mst = new ArrayList<>();

        // 马上我们就会看到，Kruskal 算法会把所有的边都考虑进去
        MinHeap<Edge<Weight>> pq = new MinHeap<>(graph.E());
        // 把所有的边都加入到这个最小堆中
        for (int i = 0; i < graph.V(); i++) {
            for (Edge<Weight> e : graph.adj(i)) {
                // 为了防止重复加入边
                // 为了防止重复加入边
                // 为了防止重复加入边
                if (e.v() < e.w()) {
                    pq.insert(e);
                }
            }
        }

        // 为了判断顶点是否相连接，所以应该开辟顶点数规模的并查集
        UnionFind uf = new UnionFind(graph.V());

        // 如果已经找到了 v - 1 条边，就不必进行下去了
        while (!pq.isEmpty() && mst.size() < graph.V()-1){

            // 从最小堆中拿出当前未考虑到的最小权重的边
            Edge<Weight> e = pq.extractMin();

            // 如果这条边的两个端点是连通的，说明加入这条边会产生环，那么它肯定不是 MST 中的一条边
            if(uf.connected(e.v(),e.w())){
                continue;
            }

            // 如果这条边的两个端点不是连通的，这条边就是 MST 中的一条边，加入之后，不要忘记标记两个节点相连
            mst.add(e);
            uf.union(e.v(),e.w());
        }
        
        this.mstWeight = mst.get(0).weight();
        for (int i = 1; i < mst.size(); i++) {
            this.mstWeight = this.mstWeight.doubleValue() + mst.get(i).weight().doubleValue();
        }
    }

    public List<Edge<Weight>> mstEdges() {
        return mst;
    }

    public Number result() {
        return mstWeight;
    }
}
