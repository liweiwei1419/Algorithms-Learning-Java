import java.util.List;

public class LazyPrimMSTTest {

    public static void main(String[] args) {
        String filename = "11-Minimum-Span-Trees/testG1.txt";
        int v = 8;
        // 无权图
        SpareWeightedGraph<Double> g = new SpareWeightedGraph<>(v, false);
        ReadWeightedGraphUtil readWeightedGraphUtil = new ReadWeightedGraphUtil(g, filename);
        g.show();
        System.out.println("最小生成树的 Prim 算法的延时实现：");
        LazyPrimMST<Double> lazyPrimMST = new LazyPrimMST<>(g);
        List<Edge<Double>> mst = lazyPrimMST.mstEdges();
        mst.forEach(System.out::println);
    }
}
