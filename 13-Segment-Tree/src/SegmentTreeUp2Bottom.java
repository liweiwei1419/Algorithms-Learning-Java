/**
 * 线段树数组的 Java 实现
 *
 * @param <E> 类型
 * @author liwei
 */
public class SegmentTreeUp2Bottom<E> implements SegmentTree<E> {

    /**
     * 一共要给领导和员工准备的椅子，对应预处理数组
     */
    private E[] tree;

    /**
     * 原始的领导和员工数据，对应原始数组
     */
    private E[] data;

    /**
     * 融合器
     */
    private Merger<E> merger;


    public SegmentTreeUp2Bottom(E[] arr, Merger<E> merger) {
        this.merger = merger;
        // 原始数组初始化、赋值
        data = (E[]) new Object[arr.length];
        for (int i = 0; i < arr.length; i++) {
            data[i] = arr[i];
        }
        // 预处理数组初始化
        tree = (E[]) new Object[4 * arr.length];
        buildSegmentTree(0, 0, arr.length - 1);
    }


    /**
     * 构建线段树中以 treeIndex 为根的子树，统计 data 数组中 [l,r] 区间中的元素
     * 这个方法的实现引入了一个 merger 接口，使得外部可以传入一个方法，方法是的实现根据业务而定
     * 核心代码只有几行，理解的关键在于理解递归方法本身的定义和参数代表的含义
     * 【关键】：l 和 r 是原始数组的索引，即对原始数组的区间构建线段树
     *
     * @param treeIndex 我们要创建的线段树根结点所在的索引，treeIndex 是线段树 tree 的索引
     * @param l         对于 treeIndex 节点所要表示的 data 区间端点是什么，l 是原始数组 data 的索引
     * @param r         对于 treeIndex 节点所要表示的 data 区间端点是什么，r 是原始数组 data 的索引
     */
    private void buildSegmentTree(int treeIndex, int l, int r) {
        if (l == r) {
            // 平衡二叉树叶子节点的赋值就是靠这句话形成的
            // 或者 tree[treeIndex] = data[r]; 此时对应叶子节点的情况
            tree[treeIndex] = data[l];
            // 不要忘记将代码返回
            return;
        }
        int mid = l + (r - l) / 2;
        int leftChild = leftChild(treeIndex);
        int rightChild = rightChild(treeIndex);
        // 假设左边右边都处理完了以后，再处理自己
        // 这一点基于，高层信息的构建依赖底层信息的构建
        // 这个递归的过程我们可以通过画图来理解
        buildSegmentTree(leftChild, l, mid);
        buildSegmentTree(rightChild, mid + 1, r);
        tree[treeIndex] = merger.merge(tree[leftChild], tree[rightChild]);
    }

    /**
     * 在一棵子树里做区间查询
     *
     * @param i
     * @param j
     * @return
     */
    @Override
    public E sumRange(int i, int j) {
        if (i < 0 || i >= data.length || j < 0 || j >= data.length || i > j) {
            throw new IllegalArgumentException("Index is illegal.");
        }
        // data.length - 1 边界不能弄错
        return query(0, 0, data.length - 1, i, j);
    }

    /**
     * 这是一个递归调用的辅助方法，应该定义成私有方法，使用分治思想，归并排序的框架
     *
     * @param treeIndex 线段树的根结点
     * @param l         线段树的区间左端点
     * @param r         线段树的区间右端点
     * @param dataL     原始数组的区间左端点
     * @param dataR     原始数组的区间右端点
     * @return
     */
    private E query(int treeIndex, int l, int r, int dataL, int dataR) {
        // 递归到底的情况
        if (l == dataL && r == dataR) {
            // 这里一定不要犯晕，看图说话
            return tree[treeIndex];
        }
        int mid = l + (r - l) / 2;
        int leftChildIndex = leftChild(treeIndex);
        int rightChildIndex = rightChild(treeIndex);
        // 画个示意图就能清楚自己的逻辑是怎样的
        if (dataR <= mid) {
            return query(leftChildIndex, l, mid, dataL, dataR);
        }
        if (dataL >= mid + 1) {
            return query(rightChildIndex, mid + 1, r, dataL, dataR);
        }
        // 横跨两边的时候，先计算左边，再计算右边，最后合并
        E leftResult = query(leftChildIndex, l, mid, dataL, mid);
        E rightResult = query(rightChildIndex, mid + 1, r, mid + 1, dataR);
        return merger.merge(leftResult, rightResult);
    }

    /**
     * 修改了原始数组 data 在索引 dataIndex 位置的值，递归修改预处理数组与之相关的值
     *
     * @param i   原始数组的指定索引
     * @param val 原始数组的指定索引 dataIndex 欲更改的值
     */
    @Override
    public void update(int i, E val) {
        if (i < 0 || i >= data.length) {
            throw new IllegalArgumentException("Index is illegal.");
        }
        data[i] = val;
        set(0, 0, data.length - 1, i, val);
    }

    /**
     * @param treeIndex 线段树的根结点的索引
     * @param l         线段树的区间左端点
     * @param r         线段树的区间右端点
     * @param dataIndex 原始数组的索引
     * @param val       对于原始数组在 dataIndex 处的值更新为 val
     */
    private void set(int treeIndex, int l, int r, int dataIndex, E val) {
        if (l == r) {
            // 来到平衡二叉树的叶子点，这一步是最底层的更新操作
            tree[treeIndex] = val;
            return;
        }
        // 更新祖辈节点，还是先更新左边孩子和右边孩子，再更新
        int leftTreeIndex = leftChild(treeIndex);
        int rightTreeIndex = rightChild(treeIndex);
        int mid = l + (r - l) / 2;
        if (dataIndex >= mid + 1) {
            // 到右边更新
            set(rightTreeIndex, mid + 1, r, dataIndex, val);
        }
        if (dataIndex <= mid) {
            // 到左边更新
            set(leftTreeIndex, l, mid, dataIndex, val);
        }
        tree[treeIndex] = merger.merge(tree[leftTreeIndex], tree[rightTreeIndex]);
    }

    /**
     * @return 线段树里存储元素的个数
     */
    public int getSize() {
        return data.length;
    }

    /**
     * @param index 原始数组的索引
     * @return 原始数组索引 index 对应的元素
     */
    public E get(int index) {
        if (index < 0 || index >= data.length) {
            throw new IllegalArgumentException("Index is illegal.");
        }
        return data[index];
    }

    /**
     * 返回完全二叉树的数组表示中，索引 index 所表示的元素的左孩子节点的索引
     * 索引从 0 开始计算，画图可以很清晰地发现它们的关系
     *
     * @param index 指定索引
     * @return index 的左结点的索引
     */
    public int leftChild(int index) {
        return 2 * index + 1;
    }

    /**
     * 返回完全二叉树的数组表示中，索引 index 所表示的元素的左孩子节点的索引
     * 索引从 0 开始计算，画图可以很清晰地发现它们的关系
     *
     * @param index 指定索引
     * @return index 的右结点的索引
     */
    public int rightChild(int index) {
        return 2 * index + 2;
    }

    /**
     * 打印出 tree 的数据
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("[");
        for (int i = 0; i < tree.length; i++) {
            if (tree[i] == null) {
                s.append("NULL");
            } else {
                s.append(tree[i]);
            }
            s.append(", ");
        }
        s.append("]");
        return s.toString();
    }
}
