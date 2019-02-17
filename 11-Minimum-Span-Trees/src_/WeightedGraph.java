/**
 * Created by liwei on 17/6/15.
 */
public interface WeightedGraph<W extends Number & Comparable> {

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
     * 添加一条边
     *
     * @param e
     */
    void addEdge(Edge<W> e);

    /**
     * 判断节点之间是否有边
     *
     * @param v
     * @param w
     * @return
     */
    boolean hasEdge(int v, int w);

    /**
     * 展示这张图
     */
    void show();

    /**
     * 邻边迭代器
     * @param v
     * @return
     */
    Iterable<Edge<W>> iterator(int v);

}
