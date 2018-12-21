import java.util.Arrays;

/**
 * Created by liwei on 17/7/4.
 */
public class UnionFind6 implements IUnionFind {

    private int[] parent;

    private int count;

    // 以索引为 i 的元素为根节点的树的深度（最深的那个深度）
    private int[] rank;

    public UnionFind6(int n) {
        this.count = n;
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            // 初始化时，所有的元素只包含它自己，只有一个元素，所以 rank[i] = 1
            rank[i] = 1;
        }
    }


    /**
     * 这个构造函数，我们在初始化的时候，就传入一个并查集的状态，看看执行了一次
     * find 操作以后，是不是那种压缩得最最彻底的情况
     *
     * @param parent
     */
    public UnionFind6(int[] parent) {
        this.count = parent.length;
        this.parent = parent;
        rank = new int[count];
        for (int i = 0; i < count; i++) {
            // 路径压缩算法中，rank 数组的值并不准确
            rank[i] = 1;
        }
    }


    @Override
    public String versionName() {
        return "并查集的第 6 个版本，基于 parent 数组，quick-union，基于 rank，路径压缩递归版本";
    }

    /**
     * 返回索引为 p 的元素的根节点
     * 理解这个方法的关键点：我们就是要把这个节点的父节点指向根节点，
     * 既然父亲节点不是根节点，我们就继续拿父亲节点找根节点
     * 一致递归找下去，
     * 最后返回的时候，写 parent[p] 是可以的
     * 写 parent[parent[p]] 也是没有问题的
     *
     * @param p
     * @return
     */
    @Override
    public int find(int p) {
        // 在 find 的时候执行路径压缩
        assert p >= 0 && p < count;
        // 注意：这里是 if 不是 while，否则就变成循环
        if (p != parent[p]) {
            // 这一行代码的逻辑要想想清楚
            // 只要不是根节点
            // 就把父亲节点指向父亲节点的父亲节点
            parent[p] = find(parent[p]);
        }
        return parent[p];
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

    public void printParentArray() {
        System.out.println(Arrays.toString(parent));
    }

    /**
     * 由于这一版的路径压缩并不好理解，我们编写一个测试用例
     *
     * @param args
     */
    public static void main(String[] args) {
        UnionFind6 unionFind6 = new UnionFind6(new int[]{0, 0, 1, 2, 3});
        unionFind6.printParentArray();

        unionFind6.find(4);
        unionFind6.printParentArray();

        // 通过这个测试用例，我们可以看到，我们通过 find 一次叶子节点
        // 就把这个并查集整理成了最彻底的那种情况
    }
}
