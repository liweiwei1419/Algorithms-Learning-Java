/**
 * Created by liwei on 17/6/15.
 */
public class Main {

    public static void main(String[] args) {
        // 稀疏图
        System.out.println("带权值的稀疏图");
        SparseWeightedGraph<Double> sparseWeightedGraph
                = new SparseWeightedGraph<>(8,false);
        String fileName = "/Users/liwei/codes-java/algorithm/src/main/java/com/weighted/graph/testG1.txt";
        new ReadWeightedGraph(sparseWeightedGraph,fileName);
        sparseWeightedGraph.show();

        // 稠密图
        System.out.println("带权值的稠密图");
        DenseWeightedGraph<Double> denseWeightedGraph
                = new DenseWeightedGraph<>(8,false);

        new ReadWeightedGraph(denseWeightedGraph,fileName);
        denseWeightedGraph.show();


    }
}
