/**
 * 带权图的边
 * W 表示的是边的权值
 * Created by liwei on 17/6/15.
 */
public class Edge<Weight extends Number & Comparable> implements Comparable<Edge> {

    // 边的两个顶点的编号（编号是只是为了区分，没有大小关系，即没有比较的含义）
    private int a;
    private int b;

    /**
     * 边的权值
     */
    private Weight weight;

    /**
     * 根据一条边的两个顶点和边的权值
     * 创建一个边的对象
     * @param a 顶点1
     * @param b 顶点2
     * @param weight 权值
     */
    public Edge(int a, int b, Weight weight) {
        this.a = a;
        this.b = b;
        this.weight = weight;
    }

    /**
     * 根据边的对象复制一个边的对象
     * @param edge
     */
    public Edge(Edge<Weight> edge) {
        this.a = edge.a;
        this.b = edge.b;
        this.weight = edge.weight;
    }

    /**
     * 返回第 1 个顶点
     *
     * @return
     */
    public int v() {
        return a;
    }

    /**
     * 返回第 2 个顶点
     *
     * @return
     */
    public int w() {
        return b;
    }

    /**
     * 返回权值
     *
     * @return
     */
    public Weight weight() {
        return weight;
    }

    /**
     * 给一个顶点，返回另一个顶点
     *
     * @param x
     * @return
     */
    public int other(int x) {
        assert x == a || x == b;
        return x == a ? b : a;
    }

    // 输出边的信息
    @Override
    public String toString() {
        return "" + a + "-" + b + ": " + weight;
    }

    /**
     * 边之间的比较
     *
     * @param that 另一条边
     * @return
     */
    @Override
    public int compareTo(Edge that) {
        if (weight.compareTo(that.weight()) < 0) {
            return -1;
        } else if (weight.compareTo(that.weight()) > 0) {
            return 1;
        } else {
            return 0;
        }
    }
}
