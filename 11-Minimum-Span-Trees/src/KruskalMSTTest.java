import java.util.List;

public class KruskalMSTTest {

    public static void main(String[] args) {
        String filename = "testG1.txt";
        int v = 8;
        // 无权图
        SpareWeightedGraph<Double> g = new SpareWeightedGraph<>(v, false);
        ReadWeightedGraphUtil readWeightedGraphUtil = new ReadWeightedGraphUtil(g, filename);
        g.show();
        System.out.println("最小生成树的 Kruskal 算法实现：");
        KruskalMST<Double> kruskalMST = new KruskalMST<>(g);
        List<Edge<Double>> mst = kruskalMST.mstEdges();
        mst.forEach(System.out::println);
    }
}
