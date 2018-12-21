import java.math.BigDecimal;
import java.util.Random;

/**
 * Created by liwei on 17/7/4.
 */
public class UnionFind4 implements IUnionFind {

    private int[] parent;

    private int count;

    // 以索引为 i 的元素为根节点的树的深度（最深的那个深度）
    private int[] rank;

    public UnionFind4(int n) {
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
        return "并查集的第 4 个版本，基于 parent 数组，quick-union，基于 rank";
    }

    // 返回索引为 p 的元素的根节点
    @Override
    public int find(int p) {
        // 跟随链接找到根节点
        while (p != parent[p]) {
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

    public static void main(String[] args) {
        int n = 100000;
        UnionFind4 unionFind4 = new UnionFind4(n);
        Random random = new Random();
        int a;
        int b;
        long begin = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            a = random.nextInt(n);
            b = random.nextInt(n);
            unionFind4.union(a, b);
        }
        for (int i = 0; i < n; i++) {
            a = random.nextInt(n);
            b = random.nextInt(n);
            unionFind4.connected(a, b);
        }
        long end = System.currentTimeMillis();

        long haomiao = end - begin;
        BigDecimal haomiaoBigDecimal = new BigDecimal(String.valueOf(haomiao));
        BigDecimal danwei = new BigDecimal(String.valueOf(1000));
        System.out.println("并查集的版本4 运行了 " + haomiaoBigDecimal.divide(danwei).toString() + " 秒");
    }
}
