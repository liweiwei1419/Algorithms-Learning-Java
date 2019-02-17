// 并查集的第 6 个版本，基于 parent 数组，quick-union，基于 rank，路径压缩递归版本
public class UnionFind  {

    private int[] parent;
    private int count;
    private int[] rank;

    public UnionFind(int n) {
        this.count = n;
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 1;
        }
    }

    public UnionFind(int[] parent) {
        this.count = parent.length;
        this.parent = parent;
        rank = new int[count];
        for (int i = 0; i < count; i++) {
            rank[i] = 1;
        }
    }

    public String versionName() {
        return "";
    }

    public int find(int p) {
        assert p >= 0 && p < count;
        if (p != parent[p]) {
            parent[p] = find(parent[p]);
        }
        return parent[p];
    }

    public boolean connected(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);
        return pRoot == qRoot;
    }

    public void union(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);
        if (pRoot == qRoot) {
            return;
        }
        if (rank[pRoot] > rank[qRoot]) {
            parent[qRoot] = pRoot;
        } else if (rank[pRoot] < rank[qRoot]) {
            parent[pRoot] = qRoot;
        } else {
            parent[qRoot] = pRoot;
            rank[pRoot]++;
        }
        count--;
    }
}
