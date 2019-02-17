/**
 * W 表示的是边的权值
 * Created by liwei on 17/6/15.
 */
public class Edge<W extends Number & Comparable> implements Comparable<Edge> {
    // 边的两个端点
    private int a;
    private int b;
    // 边的权值
    private W w;

    /**
     * 根据一条边的两个顶点和边的权值
     * 创建一个边的对象
     * @param a 顶点1
     * @param b 顶点2
     * @param w 权值
     */
    public Edge(int a, int b, W w) {
        this.a = a;
        this.b = b;
        this.w = w;
    }

    /**
     * 根据边的对象复制一个边的对象
     * @param e
     */
    public Edge(Edge<W> e) {
        this.a = e.a;
        this.b = e.b;
        this.w = e.w;
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
    public W wt() {
        return w;
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


    @Override
    public String toString() {
        return "Edge{" +
                "a=" + a +
                ", b=" + b +
                ", w=" + w +
                '}';
    }

    /**
     * 边之间的比较
     *
     * @param that 另一条边
     * @return
     */
    @Override
    public int compareTo(Edge that) {
        if (this.w.compareTo(that.wt()) < 0) {
            return -1;
        } else if (this.w.compareTo(that.wt()) > 0) {
            return 1;
        } else {
            return 0;
        }
    }
}
