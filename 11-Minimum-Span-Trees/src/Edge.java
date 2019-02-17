// 带权图的边
public class Edge<Weight extends Number & Comparable> implements Comparable<Edge> {

    // 边的两个端点的编号（编号是只是为了区分，没有大小关系，即没有比较的含义）
    private int a;
    private int b;
    private Weight weight;

    public Edge(int a, int b, Weight weight) {
        this.a = a;
        this.b = b;
        this.weight = weight;
    }

    public Edge(Edge<Weight> edge) {
        this.a = edge.a;
        this.b = edge.b;
        this.weight = edge.weight;
    }

    public int v() {
        return a;
    }

    public int w() {
        return b;
    }

    public Weight weight() {
        return weight;
    }

    public int other(int x) {
        assert x == a || x == b;
        return x == a ? b : a;
    }

    // 输出边的信息
    public String toString() {
        return "" + a + "-" + b + ": " + weight;
    }

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
