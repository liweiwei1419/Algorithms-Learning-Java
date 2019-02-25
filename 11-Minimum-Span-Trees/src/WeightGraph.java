/**
 * 带权图接口
 * Created by liwei on 17/6/15.
 * @param <Weight>
 */
public interface WeightGraph<Weight extends Number & Comparable> {

    /**
     * 返回顶点数
     *
     * @return
     */
    int V();

    /**
     * 返回边的条数
     *
     * @return
     */
    int E();

    /**
     * 添加一条边，权值为 weight
     *
     * @param e
     */
    void addEdge(Edge<Weight> edge);

    /**
     * 验证图中是否有从 v 到 w 的边
     *
     * @param v
     * @param w
     * @return
     */
    boolean hasEdge(int v, int w);

    /**
     * 展示这张图，显示图的信息
     */
    void show();

    /**
     返回图中一个顶点的所有邻边
     * 邻边迭代器
     * @param v
     * @return
     */
    Iterable<Edge<Weight>> adj(int v);
}
