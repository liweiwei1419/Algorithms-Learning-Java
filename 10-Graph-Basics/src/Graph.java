// 图的接口，统一表示稀疏图与稠密图
public interface Graph {

    // 返回顶点的个数
    int V();

    // 返回边的条数
    int E();

    // 在顶点 v 和 w 之间添加一条表
    void addEdge(int v, int w);

    // 顶点 v 与 顶点 w 是否有边连接
    boolean hasEdge(int v, int w);

    // 显示这个图中的元素
    void show();

    // 得到顶点 v 的所有邻居顶点
    Iterable<Integer> adj(int v);
}
