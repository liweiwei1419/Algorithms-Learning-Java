import java.io.*;
import java.util.Scanner;

/**
 * 通过文件读取带权图的信息
 * 读取带权图的工具类
 * Created by liwei on 17/6/15.
 */
public class ReadWeightedGraphUtil {

    private Scanner scanner;

    public ReadWeightedGraphUtil(WeightGraph<Double> graph, String filename) {
        readFile(filename);

        // 为了展示清晰地展示思路，在这里不做异常检测，即 V 和 E 不合法时，抛出异常
        int V = scanner.nextInt(); // 顶点数
        int E = scanner.nextInt();// 边数

        System.out.println(V + " " + E);

        // 从第 2 行开始的文本信息，都表示边
        for (int i = 0; i < E; i++) {
            int v = scanner.nextInt(); // 暂不检测 v 和 w 的合法性
            int w = scanner.nextInt();
            Double weight = scanner.nextDouble();
            // 把一条边的信息读取进来以后，就要添加到图中
            graph.addEdge(new Edge<Double>(v,w,weight));
        }

    }

    private void readFile(String filename) {
        try {
            File file = new File(filename);
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                scanner = new Scanner(new BufferedInputStream(fis));
                // scanner.useLocale(Locale.ENGLISH);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        // 稀疏图
        SpareWeightedGraph<Double> graph1 = new SpareWeightedGraph<>(8, false);
        String filename = "testG1.txt";
        new ReadWeightedGraphUtil(graph1, filename);
        System.out.println("稀疏图表示");
        graph1.show();

        System.out.println();
        // 稠密图
        DenseWeightedGraph<Double> graph2 = new DenseWeightedGraph<>(8, false);
        new ReadWeightedGraphUtil(graph2, filename);
        System.out.println("稠密图表示");
        graph2.show();
    }
}



