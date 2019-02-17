import java.util.List;

public class PrimMSTTest {

    public static void main(String[] args) {
        String filename = "testG1.txt";
        int v = 8;
        // 无权图
        SpareWeightedGraph<Double> g = new SpareWeightedGraph<>(v, false);
        ReadWeightedGraphUtil readWeightedGraphUtil = new ReadWeightedGraphUtil(g, filename);
        g.show();
        System.out.println("最小生成树的 Prim 算法的即时实现：");
        PrimMST<Double> primMST = new PrimMST<>(g);
        List<Edge<Double>> mst = primMST.mstEdges();
        mst.forEach(System.out::println);
    }
}
