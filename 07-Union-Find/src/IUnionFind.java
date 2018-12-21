/**
 * Created by liwei on 17/6/13.
 */
public interface IUnionFind {

    // 并查集的版本名称，由开发者指定
    String versionName();

    // p (0 到 N-1)所在的分量的标识符
    int find(int p);

    // 如果 p 和 q 存在于同一分量中则返回 true
    boolean connected(int p, int q);

    // 在 p 与 q 之间添加一条连接
    void union(int p, int q);

}
