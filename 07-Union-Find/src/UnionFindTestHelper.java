/**
 * 并查集的第 1 个版本：实现了快速查询（Quick Find）
 * 接下来要实现的版本是快速合并（Quick Union）
 * Created by liwei on 17/6/13.
 */
public class UnionFindTestHelper {


    public static void main(String[] args) {
        int N = 100000;
        UnionFindTestHelper.testUF(new UnionFind1(N), N);
        UnionFindTestHelper.testUF(new UnionFind2(N), N);
        UnionFindTestHelper.testUF(new UnionFind3(N), N);
        UnionFindTestHelper.testUF(new UnionFind4(N), N);
        UnionFindTestHelper.testUF(new UnionFind5(N), N);
        UnionFindTestHelper.testUF(new UnionFind6(N), N);
    }

    /**
     * 虽然 isConnected 操作的时间复杂度是 O(1)
     * 但是 unionElements 的时间复杂度是 O(n)
     * 综上所述，testUF1 的时间复杂度是 O(n^2)
     *
     * @param n
     */
    public static void testUF(IUnionFind unionFind, int n) {

        long startTime = System.currentTimeMillis();
        // 进行 n 次操作，每个随机选取两个元素进行合并
        for (int i = 0; i < n; i++) {
            int a = (int) (Math.random() * n);
            int b = (int) (Math.random() * n);
            unionFind.union(a, b);
        }

        // 再进行 n 次操作，每次随机选取两个元素进行查询是否是同一组的操作
        for (int i = 0; i < n; i++) {
            int a = (int) (Math.random() * n);
            int b = (int) (Math.random() * n);
            unionFind.union(a, b);
        }
        long endTime = System.currentTimeMillis();
        // 打印这两个操作的耗时
        System.out.printf("并查集的实现 %s 执行 %d 个操作耗时 %d 毫秒。\n", unionFind.versionName(), 2 * n, (endTime - startTime));
    }
}
