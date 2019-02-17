import java.math.BigDecimal;
import java.util.Random;

/**
 * Created by liwei on 17/7/4.
 */
public class UnionFind2 implements IUnionFind {

    private int[] parent; // 第 i 个元素存放它的父元素的索引

    private int count; // 联通分量的数量

    public UnionFind2(int n) {
        this.count = n;
        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }

    @Override
    public String versionName() {
        return "并查集的第 2 个版本，基于 parent 数组，quick-union";
    }

    @Override
    public int find(int p) {
        // 跟随链接找到根节点
        while (parent[p] != p) { // 只要不是根节点
            p = parent[p];
        }
        return p;
    }

    @Override
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    @Override
    public void union(int p, int q) {
        int pRoot = find(p); // 将 p 归并与之相同的分量中
        int qRoot = find(q); // 将 q 归并与之相同的分量中

        // 如果 p 和 q 已经在相同的分量之中，则什么都不做
        if (pRoot == qRoot) {
            return;
        }
        // 如果 parent[qRoot] = pRoot; 也是可以的，即将其中一个节点指向另一个节点
        parent[pRoot] = qRoot;
        // 每次 union 以后，连通分量减 1
        count--;
    }

    public static void main(String[] args) {
        int n = 100000;
        UnionFind2 unionFind2 = new UnionFind2(n);
        Random random = new Random();
        int a;
        int b;
        long begin = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            a = random.nextInt(n);
            b = random.nextInt(n);
            unionFind2.union(a, b);
        }
        for (int i = 0; i < n; i++) {
            a = random.nextInt(n);
            b = random.nextInt(n);
            unionFind2.union(a, b);
        }
        long end = System.currentTimeMillis();

        long haomiao = end - begin;
        BigDecimal haomiaoBigDecimal = new BigDecimal(String.valueOf(haomiao));
        BigDecimal danwei = new BigDecimal(String.valueOf(1000));
        System.out.println("并查集的版本2 运行了 " + haomiaoBigDecimal.divide(danwei).toString() + " 秒");
    }
}
