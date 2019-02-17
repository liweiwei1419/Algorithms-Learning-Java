import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * 读取带权图的工具类。
 * Created by liwei on 17/6/15.
 */
public class ReadWeightedGraph {

    private Scanner scanner;

    public ReadWeightedGraph(WeightedGraph<Double> graph, String fileName) {
        readFile(fileName);
        int V = scanner.nextInt();
        System.out.printf("顶点数：" + V);
        int E = scanner.nextInt();
        System.out.println(" 边数：" + E);

        for (int i = 0; i < E; i++) {
            int v = scanner.nextInt();
            int w = scanner.nextInt();
            Double weight = scanner.nextDouble();
            System.out.printf("第 %d 条边：\t",(i+1));
            System.out.printf("%d \t",v);
            System.out.printf("%d \t",w);
            System.out.println(weight);
            graph.addEdge(new Edge<Double>(v,w,weight));
        }
    }

    private void readFile(String fileName) {
        assert fileName != null;
        File file = new File(fileName);
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        scanner = new Scanner(fileReader);
    }

}
