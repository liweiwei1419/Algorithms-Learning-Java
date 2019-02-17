// 带权图接口
public interface WeightGraph<Weight extends Number & Comparable> {

    int V(); // 顶点数

    int E(); // 边数

    // 向图中添加一个边, 权值为 weight
    void addEdge(Edge<Weight> edge);

    // 验证图中是否有从 v 到 w 的边
    boolean hasEdge(int v, int w);

    // 显示图的信息
    void show();

    // 返回图中一个顶点的所有邻边
    Iterable<Edge<Weight>> adj(int v);
}
