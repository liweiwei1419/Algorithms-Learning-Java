/**
 * Created by liwei on 17/7/4.
 */
public class UnionFind5 implements IUnionFind{

    private int[] parent;

    private int count;

    // 以索引为 i 的元素为根节点的树的深度（最深的那个深度）
    private int[] rank;

    public UnionFind5(int n) {
        this.count = n;
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            // 初始化时，所有的元素只包含它自己，只有一个元素，所以 rank[i] = 1
            rank[i] = 1;
        }
    }

    @Override
    public String versionName() {
        return "并查集的第 5 个版本，基于 parent 数组，quick-union，基于 rank ，路径压缩循环版本";
    }

    /**
     * 返回索引为 p 的元素的根节点
     *
     * @param p
     * @return
     */
    @Override
    public int find(int p) {
        // 在 find 的时候执行路径压缩
        while (p != parent[p]) {
            // 编写这句代码的时候可能会觉得有点绕，
            // 技巧是画一个示意图，就能很直观地写出正确的逻辑
            // 两步一跳完成路径压缩
            parent[p] = parent[parent[p]];
            p = parent[p];
        }
        return p;
    }

    @Override
    public boolean connected(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);
        return pRoot == qRoot;
    }


    @Override
    public void union(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);
        if (pRoot == qRoot) {
            return;
        }
        // 这一步是与第 3 版不同的地方
        if (rank[pRoot] > rank[qRoot]) {
            parent[qRoot] = pRoot;
        } else if (rank[pRoot] < rank[qRoot]) {
            parent[pRoot] = qRoot;
        } else {
            parent[qRoot] = pRoot;
            rank[pRoot]++;
        }
        // 每次 union 以后，连通分量减 1
        count--;
    }
}
