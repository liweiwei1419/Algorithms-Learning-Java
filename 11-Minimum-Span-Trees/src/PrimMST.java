import java.util.ArrayList;
import java.util.List;

// Prim 算法的即时实现
public class PrimMST<Weight extends Number & Comparable> {

    private WeightGraph<Weight> G;// 图的引用
    private IndexMinHeap<Weight> ipq;// 最小索引堆
    private boolean[] marked;

    private Edge<Weight>[] edgeTo;// 访问的点所对应的边
    private List<Edge<Weight>> mst; // 最小生成树所包含的所有边
    private Number mstWeight;//最小生成树的权值

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
        while (!ipq.isEmpty()){
            int v = ipq.extractMinIndex();
            assert edgeTo[v]!=null;
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
        assert !marked[v];
        marked[v]=true;

        for(Edge<Weight> e: this.G.adj(v)){
            int w = e.other(v);
            // 如果顶点的另一个端点还没有访问过，那么这条边就可以加入索引堆
            if(!marked[w]){
                 if(edgeTo[w]==null){
                     edgeTo[w]= e;
                     ipq.insert(w,e.weight());
                 }else if(e.weight().compareTo(edgeTo[w].weight())<0){
                     edgeTo[w]= e;
                     ipq.change(w,e.weight());
                 }
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
