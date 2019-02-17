// 最小索引堆（由最小堆的代码修改而来）
public class IndexMinHeap<Item extends Comparable> {
    private Item[] data; // 最小索引堆的数据
    private int[] indexes; // 最小索引堆中的索引，data[indexes[0]] ,data[indexes[1]],..., 构成最小堆
    private int[] reverse; // 最小索引堆中的反向查找索引
    private int count;
    private int capacity;

    public IndexMinHeap(int capacity) {
        data = (Item[]) new Comparable[capacity + 1];

        indexes = new int[capacity + 1];
        reverse = new int[capacity + 1];
        // indexes 数组和 reverse 数组在索引为 0 的位置都不存放数据
        for (int i = 0; i <= capacity; i++) {
            reverse[i] = 0;
        }
        this.capacity = capacity;
        this.count = 0;
    }

    public int size() {
        return count;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    // 向最小索引堆中插入一个新的元素, 新元素的索引为 i , 元素为 item
    // 传入的 i 对外部调用者而言的，是从 0 开始的
    public void insert(int i, Item item) {
        assert count < capacity;
        assert i >= 0 && i < capacity;

        // 在插入一个新的元素前，要保证索引 i 这个位置是没有元素
        assert !contain(i);
        i += 1;
        data[i] = item;

        indexes[count + 1] = i; // 有对 indexes 赋值的地方，就一定要同步对 reverse 赋值
        reverse[i] = count + 1;

        count++;
        shiftUp(count);
    }

    // 看索引 i 所在的位置是否存在元素
    public boolean contain(int i) {
        assert i >= 0 && i < capacity;
        // 不为 0 表示外部调用者眼中的 i 位置不存在元素
        return reverse[i + 1] != 0;
    }

    // 获取最小索引堆中索引为i的元素
    public Item getItem(int i) {
        assert contain(i);
        return data[i + 1];
    }


    // 从最小堆中取出堆顶元素, 即堆中所存储的最小数据
    public Item extractMin() {
        assert count > 0;
        Item min = data[indexes[1]];
        swapIndexes(1, count);
        count--;
        shiftDown(1);
        return min;
    }

    // 从最小索引堆中取出堆顶元素的索引
    public int extractMinIndex(){
        assert count > 0;
        int ret = indexes[1] - 1;
        swapIndexes( 1 , count );
        reverse[indexes[count]] = 0;
        count --;
        shiftDown(1);
        return ret;
    }

    // 获取最小堆中的堆顶元素
    public Item getMin() {
        assert (count > 0);
        return data[indexes[1]];
    }

    public int getMinIndex() {
        assert count > 0;
        return indexes[1] - 1;
    }

    public void change(int i, Item newItem) {
        assert contain(i);
        i += 1;
        data[i] = newItem;
        // 有了 reverse 这个反向查找数组 之后,
        // 我们可以非常简单的通过 reverse 直接定位索引 i 在 indexes 中的位置
        // 我们可以非常简单的通过 reverse 直接定位索引 i 在 indexes 中的位置
        // 我们可以非常简单的通过 reverse 直接定位索引 i 在 indexes 中的位置
        shiftUp(reverse[i]);
        shiftDown(reverse[i]);
    }

    // 交换索引堆中的索引 i 和 j
    // 由于有了反向索引 reverse 数组，
    // indexes 数组发生改变以后， 相应的就需要维护 reverse 数组
    private void swapIndexes(int i, int j) {
        int t = indexes[i];
        indexes[i] = indexes[j];
        indexes[j] = t;

        reverse[indexes[i]] = i;
        reverse[indexes[j]] = j;
    }

    private void shiftUp(int k) {
        while (k > 1 && data[indexes[k / 2]].compareTo(data[indexes[k]]) > 0) {
            swapIndexes(k / 2, k);
            k = k / 2;
        }
    }

    private void shiftDown(int k) {
        while (2 * k <= count) {// 只要有左孩子，就应该考虑是否可以 shiftDown
            int t = 2 * k;
            if (t + 1 < count && data[indexes[t]].compareTo(data[indexes[t + 1]]) > 0) {
                t++;
            }
            if (data[indexes[t]].compareTo(data[indexes[k]]) > 0) {
                // 两个孩子中最小的那个元素都比父亲大，就满足最小堆的性质，循环退出
                break;
            }
            swapIndexes(t, k);
            k = t;
        }
    }

    public static void main(String[] args) {
        int N = 1000000;
        IndexMinHeap<Integer> indexMinHeap = new IndexMinHeap<Integer>(N);
        for (int i = 0; i < N; i++) {
            indexMinHeap.insert(i, (int) (Math.random() * N));
        }
    }
}
