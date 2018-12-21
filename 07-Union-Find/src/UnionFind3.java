import java.math.BigDecimal;
import java.util.Random;

// union-find 算法的实现（加权 quick-union 算法）
public class UnionFind3 implements IUnionFind {

    private int[] parent; // 第 i 个元素存放它的父元素的索引

    private int count; // 联通分量的数量

    private int[] size; // 以当前索引为根的树所包含的元素的总数

    public UnionFind3(int n) {
        this.count = n;
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            // 初始化时，所有的元素只包含它自己，只有一个元素，所以 size[i] = 1
            size[i] = 1;
        }
    }

    @Override
    public String versionName() {
        return "并查集的第 3 个版本，基于 parent 数组，quick-union，基于 size";
    }

    // 返回索引为 p 的元素的根节点的索引
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
        // 这一步是与第 2 版不同的地方，我们不是没有根据地把一个节点的根节点的父节点指向另一个节点的根节点
        // 而是将小树的根节点连接到大树的根节点
        if (size[pRoot] > size[qRoot]) {
            parent[qRoot] = pRoot;
            size[pRoot] += size[qRoot];
        } else {
            parent[pRoot] = qRoot;
            size[qRoot] += size[pRoot];
        }
        // 每次 union 以后，连通分量减 1
        count--;
    }

    public static void main(String[] args) {
        int n = 100000;
        UnionFind3 unionFind3 = new UnionFind3(n);
        Random random = new Random();
        int a;
        int b;
        long begin = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            a = random.nextInt(n);
            b = random.nextInt(n);
            unionFind3.union(a, b);
        }
        for (int i = 0; i < n; i++) {
            a = random.nextInt(n);
            b = random.nextInt(n);
            unionFind3.connected(a, b);
        }
        long end = System.currentTimeMillis();

        long haomiao = end - begin;
        BigDecimal haomiaoBigDecimal = new BigDecimal(String.valueOf(haomiao));
        BigDecimal danwei = new BigDecimal(String.valueOf(1000));
        System.out.println("并查集的版本3 运行了 " + haomiaoBigDecimal.divide(danwei).toString() + " 秒");
    }
}
