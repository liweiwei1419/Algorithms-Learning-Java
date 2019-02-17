import java.math.BigDecimal;
import java.util.Random;

/**
 * Created by liwei on 17/7/4.
 */
public class UnionFind1 implements IUnionFind {
    /**
     * 分量 id
     */
    private int[] id;

    /**
     * 连通分量的数量
     */
    private int count;

    public UnionFind1(int n) {
        this.count = n;
        // 初始化分量 id 数组
        id = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
        }
    }

    @Override
    public String versionName() {
        return "并查集的第 1 个版本，基于 id 数组，quick-find";
    }

    // 以常数时间复杂度，返回分量的标识符，与并查集的规模是无关的，这一步很快
    // 因此我们称这个版本的并查集是 quick-find
    @Override
    public int find(int p) {
        return id[p];
    }

    @Override
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    // 因为需要遍历数组中的全部元素，所以其实这个版本效率并不高
    @Override
    public void union(int p, int q) {
        int pid = find(p);
        int qid = find(q);

        // 如果 p 和 q 已经在相同的分量之中，则什么都不做
        if (pid == qid) {
            return;
        }

        // 将 p 的分量重新命名为 q 的名称
        for (int i = 0; i < id.length; i++) {
            if (find(i) == pid) {
                id[i] = qid;
            }
        }
        // 每次 union 以后，连通分量减 1
        this.count--;
    }

    public static void main(String[] args) {
        int n = 100000;
        UnionFind1 unionFind1 = new UnionFind1(n);
        Random random = new Random();
        int a;
        int b;
        long begin = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            a = random.nextInt(n);
            b = random.nextInt(n);
            unionFind1.union(a, b);
        }
        for (int i = 0; i < n; i++) {
            a = random.nextInt(n);
            b = random.nextInt(n);
            unionFind1.connected(a, b);
        }
        long end = System.currentTimeMillis();

        long haomiao = end - begin;
        BigDecimal haomiaoBigDecimal = new BigDecimal(String.valueOf(haomiao));
        BigDecimal danwei = new BigDecimal(String.valueOf(1000));
        System.out.println("并查集的版本1 运行了 " + haomiaoBigDecimal.divide(danwei).toString() + " 秒");
    }
}
