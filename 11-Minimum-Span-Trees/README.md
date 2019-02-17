# 带权图的最小生成树算法

## 带权图的数据结构设计、从一个文本文本中读出图的信息到带权图对象

### 首先我们设计一个边的对象，让它保存一条边的两个节点以及边的权重

+ 下面的设计中，当图是有向图的时候，我们总是认为 a 是边的起点， b 是边的终点。

```java
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
```

### 带权图接口

```java
// 带权图接口
public interface WeightGraph<Weight extends Number & Comparable> {

    int V(); // 顶点数

    int E(); // 边数

    // 向图中添加一个边, 权值为 weight
    void addEdge(Edge<Weight> edge);

    // 验证图中是否有从 v 到 w 的边
    boolean hasEdge(int v, int w);

    // 显示图的信息
    void show();

    // 返回图中一个顶点的所有邻边
    Iterable<Edge<Weight>> adj(int v);
}
```

### 实现了带权图接口的稀疏图类和稠密图类、读取一个文本文件的内容到带权图对象

请见：
[带权图数据结构设计、读取一个文本文件的内容到带权图对象](
https://gist.github.com/liweiwei1419/0414f686ffaa91b86b556029eb668cf2)

## MST 算法的理论基础

### 切分以及切分定理

+ 切分定理：在一幅加权图中，给定任意切分，所有横切边中权重最小的边一定属于图的最小生成树。

+ 理解切分定理的关键是关于树的两条性质。
1. 性质1：一棵树任意连接两个顶点，形成环；
2. 性质2：一棵树任意删除一条边，就会分裂成两棵树；


## 最小生成树的 Prim 算法的延时实现

从起点出发（并不一定非得是起点，任意一点都可以），每次访问到一个顶点的时候，都将与这个顶点**直接相连**的、并且**还未加入到最小堆**中的边加入最小堆，这里的最小堆存储的是待查考的 MST 中的边，即从它们之中选出符合 MST 要求的边，规则是：取出当前最小堆中最小的边，如果它是横切边，就一定是 MST 中的边，如果它不是横切边，就不是 MST 中的边，这一步也是 lazy prim 称之为懒惰的地方。

实现的难点在于：

1. 如何判断是不是横切边，与判断是否构成环是否一致？
2. 算法停止的条件是什么，是最小堆中的元素已经为空了，还是我们已经找到了 v-1 条边？



### 最小堆

+ lazy prim 需要借助最小堆，当然，你不用最小堆也可以，那么每一次从待考虑的边中取出最小值，就会比较费时了。

[最小堆实现（Java）](https://gist.github.com/liweiwei1419/ba31af450a83b82db05a35f6f4f14cb1)

### lazy prim 算法实现（Java）


```java
import java.util.ArrayList;
import java.util.List;

// LazyPrim ：懒惰的 Prim 实现，Prim（普里姆）
// MST：Minimum Spanning Tree，最小生成树
public class LazyPrimMST<Weight extends Number & Comparable> {

    private WeightGraph<Weight> G;// 图的引用
    private MinHeap<Edge<Weight>> pq;// 最小堆
    private boolean[] marked; // 标记数组，在算法运行过程中标记节点 i 是否被访问
    private List<Edge<Weight>> mst; // 最小生成树所包含的所有边
    private Number mstWeight;//最小生成树的权值

    // 在构造方法中完成最小生成树的计算
    public LazyPrimMST(WeightGraph<Weight> graph) {

        // 初始化一些成员变量和辅助的数据结构
        this.G = graph;
        pq = new MinHeap(graph.E());// lazy prim 算法每次考虑的边的条数就是图的最多的边数
        marked = new boolean[this.G.V()];
        mst = new ArrayList<>();

        // lazt prim
        visit(0);
        while (!pq.isEmpty()) { // 每次都拿出考虑的边的最小值，看看它是不是横切边，这就是 lazy prim 称之为 lazy 的原因
            // 使用最小堆找出已经访问的边中权值最小的边
            Edge<Weight> e = pq.extractMin();
            // 如果这条边的两端都已经访问过了, 则扔掉这条边
            if (marked[e.v()] == marked[e.w()]) {
                continue;
            }
            // 否则，这条边就是最小生成树中的一条边
            mst.add(e);
            // 将这条边中还没有被访问过的那个顶点，执行和一开始一样的 visit 操作
            if (!marked[e.v()]) {
                visit(e.v());
            } else {
                visit(e.w());
            }
        }

        // 最小堆中的元素都考虑完以后（根据上面的逻辑，要考虑完那些不是横切边的边）
        this.mstWeight = mst.get(0).weight();
        for (int i = 1; i < mst.size(); i++) {
            this.mstWeight = this.mstWeight.doubleValue() + mst.get(i).weight().doubleValue();
        }
    }

    // 访问一个顶点，只做一件事情，将和这个顶点直接相连的还未加入最小堆的边加入最小堆
    private void visit(int v) {
        assert !marked[v];
        marked[v] = true;
        for (Edge<Weight> e : this.G.adj(v)) {
            // 【注意】技巧在这里：只要是另一个端点还没有被标记过，那么就表示这条边还未加入到最小堆中
            if (!marked[e.other(v)]) {
                pq.insert(e);
            }
        }
    }

    // 返回最小生成树的所有边
    public List<Edge<Weight>> mstEdges() {
        return mst;
    }

    // 返回最小生成树的权值
    public Number result() {
        return mstWeight;
    }
}
```

测试方法：

```java
import java.util.List;

public class LazyPrimMSTTest {

    public static void main(String[] args) {
        String filename = "testG1.txt";
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
```

运行结果：
<img src="" width="">


## Prim 算法的即时实现


我们要借助最小索引堆完成 Prim 算法。
[最小索引堆（Java）。](https://gist.github.com/liweiwei1419/4d875955ac991134b0d1f62258407ac1)


```java
import java.util.ArrayList;
import java.util.List;

// Prim 算法的即时实现
public class PrimMST<Weight extends Number & Comparable> {

    private WeightGraph<Weight> G;// 图的引用
    private IndexMinHeap<Weight> ipq;// 最小索引堆
    private boolean[] marked;

    private Edge<Weight>[] edgeTo;// 访问的点所对应的边
    private List<Edge<Weight>> mst; // 最小生成树所包含的所有边
    private Number mstWeight;//最小生成树的权值

    public PrimMST(WeightGraph graph) {
        G = graph;
        ipq = new IndexMinHeap<>(graph.V());

        // 初始化辅助的数据结构和一些成员变量
        marked = new boolean[G.V()];
        edgeTo = new Edge[G.V()];

        for (int i = 0; i < G.V(); i++) {
            marked[i] = false;
            edgeTo[i] = null;
        }

        mst = new ArrayList<>();
        
        // Prim 
        visit(0);
        while (!ipq.isEmpty()){
            int v = ipq.extractMinIndex();
            assert edgeTo[v]!=null;
            mst.add(edgeTo[v]);
            visit(v);
        }
        // 最小堆中的元素都考虑完以后（根据上面的逻辑，要考虑完那些不是横切边的边）
        this.mstWeight = mst.get(0).weight();
        for (int i = 1; i < mst.size(); i++) {
            this.mstWeight = this.mstWeight.doubleValue() + mst.get(i).weight().doubleValue();
        }
    }

    private void visit(int v) {
        assert !marked[v];
        marked[v]=true;

        for(Edge<Weight> e: this.G.adj(v)){
            int w = e.other(v);
            // 如果顶点的另一个端点还没有访问过，那么这条边就可以加入索引堆
            if(!marked[w]){
                 if(edgeTo[w]==null){
                     edgeTo[w]= e;
                     ipq.insert(w,e.weight());
                 }else if(e.weight().compareTo(edgeTo[w].weight())<0){
                     edgeTo[w]= e;
                     ipq.change(w,e.weight());
                 }
            }
        }
    }

    // 返回最小生成树的所有边
    public List<Edge<Weight>> mstEdges() {
        return mst;
    }

    // 返回最小生成树的权值
    public Number result() {
        return mstWeight;
    }
}
```

测试方法：

```java
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
```

运行结果：
<img src="" width="">

## 使用 Kruskal（克鲁斯卡尔）算法计算带权图的最小生成树

需要使用的辅助的数据结构有：

1. [最小堆]()
2. [并查集]()


```java
import java.util.ArrayList;
import java.util.List;

// Kruskal（克鲁斯卡尔）算法
public class KruskalMST<Weight extends Number & Comparable> {

    private List<Edge<Weight>> mst;
    private Number mstWeight;

    // 使用 Kruskal 算法计算带权图的最小生成树
    public KruskalMST(WeightGraph<Weight> graph) {

        mst = new ArrayList<>();

        // 马上我们就会看到，Kruskal 算法会把所有的边都考虑进去
        MinHeap<Edge<Weight>> pq = new MinHeap<>(graph.E());
        // 把所有的边都加入到这个最小堆中
        for (int i = 0; i < graph.V(); i++) {
            for (Edge<Weight> e : graph.adj(i)) {
                // 为了防止重复加入边
                // 为了防止重复加入边
                // 为了防止重复加入边
                if (e.v() < e.w()) {
                    pq.insert(e);
                }
            }
        }

        // 为了判断顶点是否相连接，所以应该开辟顶点数规模的并查集
        UnionFind uf = new UnionFind(graph.V());

        while (!pq.isEmpty() && mst.size() < graph.V()-1){// 凑够数了，就不必进行下去了

            // 从最小堆中拿出当前未考虑到的最小权重的边
            Edge<Weight> e = pq.extractMin();

            // 如果这条边的两个端点是连通的，说明加入这条边会产生环，那么它肯定不是 MST 中的一条边
            if(uf.connected(e.v(),e.w())){
                continue;
            }

            // 如果这条边的两个端点不是连通的，这条边就是 MST 中的一条边，加入之后，不要忘记标记两个节点相连
            mst.add(e);
            uf.union(e.v(),e.w());
        }
        this.mstWeight = mst.get(0).weight();
        for (int i = 1; i < mst.size(); i++) {
            this.mstWeight = this.mstWeight.doubleValue() + mst.get(i).weight().doubleValue();
        }
    }

    public List<Edge<Weight>> mstEdges() {
        return mst;
    }

    public Number result() {
        return mstWeight;
    }

}
```

测试方法：

```java
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
```

运行结果：
<img src="" width="">