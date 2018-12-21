// 关于无权图算法的测试
public class Main {

    public static void main(String[] args) {
        // 稠密图
        // DenseGraph graph = new DenseGraph(13, false);
        // new ReadGraphUtil(graph, "testG1.txt");
        // graph.show();

        // 稀疏图
        SparseGraph graph = new SparseGraph(13, false);
        new ReadGraphUtil(graph, "testG1.txt");
        // graph.show();

        Component component = new Component(graph);
        System.out.println("图的连通分量：" + component.getCcount());

        // 通过深度优先遍历，得到一个路径
        Path path = new Path(graph, 0);
        // 此时的路径不是最短路径
        path.showPath(6);

        // 广度优先遍历
        ShortestPath shortestPath = new ShortestPath(graph, 0);
        shortestPath.showPath(3);
    }
}
