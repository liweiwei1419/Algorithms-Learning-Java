import java.util.Vector;

/**
 * 使用 lazy 的 Prim 算法求得最小生成树
 * Created by liwei on 17/6/16.
 */
public class LazyPrimMST <W extends Number & Comparable>{

    /**
     * 带权图的引用
     */
    private WeightedGraph<W> wWeightedGraph;

    /**
     * pq 是优先（priority）队列（queue）的意思
     */
    private MinHeap<Edge<W>> pq;

    /**
     * 标记数组，在算法过程中，标记节点 i 是否被引用过
     */
    private boolean[] marked;

    /**
     * 最小生成树包含的所有的边
     */
    private Vector<Edge<W>> mst;
    /**
     * 最小生成树的权值
     */
    private Number mstWeight;
}
