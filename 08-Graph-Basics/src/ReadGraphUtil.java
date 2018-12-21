import java.io.*;

// 读图的工具类
public class ReadGraphUtil {

    public ReadGraphUtil(Graph graph, String filename) {
        File file = new File(filename);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            String lineone = bufferedReader.readLine(); // 第 1 行
            String[] VandE = lineone.split(",");
            int v = Integer.valueOf(VandE[0]); // 顶点数
            int e = Integer.valueOf(VandE[1]); // 边数
            assert v == graph.V();
            while ((line = bufferedReader.readLine()) != null) {
                String[] vertexsPair = line.split(",");
                // System.out.println("顶点和边的键值对 =>" + vertexsPair[0] + " " + vertexsPair[1]);
                graph.addEdge(Integer.valueOf(vertexsPair[0]), Integer.valueOf(vertexsPair[1]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 测试通过文件读取图的信息
    public static void main(String[] args) {
        // 测试 1 ：将图文件读到一个稀疏图对象中
        // 表示无向图
        boolean isDirected = false;
        SparseGraph sparseGraph1 = new SparseGraph(13, isDirected);
        new ReadGraphUtil(sparseGraph1, "testG1.txt");
        sparseGraph1.show();

        // 测试 2 ：将图文件读到一个图稠密对象中
        DenseGraph denseGraph1 = new DenseGraph(13, isDirected);
        new ReadGraphUtil(denseGraph1, "testG1.txt");
        denseGraph1.show();
    }
}
