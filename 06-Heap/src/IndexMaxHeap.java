import java.util.Arrays;

/**
 * 最大索引堆
 * 最大索引堆的特性：
 * 1、最大索引堆的内部维护了一个索引数组，这个索引数组是一个最大堆
 * 2、使用最大索引堆的时候，可以很好地支持 change 这个操作，即我们想改改数组中的第几个元素的值，
 * 此时，根据最大索引堆的定义，内部的索引数组就会发生重组，来保证这个索引数组是一个最大堆
 * 3、最大索引堆中的 data 数组是由用户定义的，用户的 insert、extract、和 change 操作只会很单纯地
 * 插入、取出和修改 data 中的元素，程序员来维护内部的索引数据，使之构成最大堆
 * 4、最大索引堆的一个重要应用就是动态地维护了最大堆的定义
 * <p>
 * Created by liwei on 17/5/16.
 */
public class IndexMaxHeap {
    /**
     * 最大索引堆中的数据
     */
    private int[] data;
    /**
     * 最大索引堆中的元素个数
     */
    private int count;
    /**
     * 最大索引堆的容量，一经确定，就不能更改
     */
    private int capacity;

    /**
     * 最大索引堆中的内置索引，外部用户并不感知
     */
    public int[] indexes;


    public int[] getData() {
        return data;
    }

    public IndexMaxHeap(int capacity) {
        data = new int[capacity + 1];
        indexes = new int[capacity + 1];
        count = 0;
        this.capacity = capacity;
    }

    public int size() {
        return count;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    /**
     * 插入，元素的索引是 i ，对于外部用户来说，i 是从 0 开始计算的
     *
     * @param i
     * @param item
     */
    public void insert(int i, int item) {
        if (count + 1 <= capacity) {
            // 索引不会产生越界的问题
            assert i + 1 >= 1 && i + 1 <= capacity;
            i += 1;
            data[i] = item;
            // 这一步很关键，在内部索引数组的最后设置索引数组的索引
            indexes[count + 1] = i;
            count++;
            shiftUp(count);
        } else {
            throw new RuntimeException("数组容量不够了");
        }
    }

    private void shiftUp(int k) {
        // 有索引就要考虑索引越界的情况
        while (k > 1 && data[indexes[k / 2]] < data[indexes[k]]) {
            swap(indexes, k / 2, k);
            k /= 2;
        }
    }

    private void swap(int[] data, int index1, int index2) {
        if (index1 == index2) {
            return;
        }
        int temp = data[index1];
        data[index1] = data[index2];
        data[index2] = temp;
    }

    /**
     * 取出最大堆中的根节点
     * 1、把最后一个元素和索引是 1 的元素进行交换
     * 2、从根节点开始逐层下移：下移的算法是，跟左右孩子节点进行比较，把最大的那个跟自己交换
     *
     * @return 根节点的元素
     */
    public int extractMax() {
        assert count > 0;
        int ret = data[indexes[1]];
        swap(indexes, 1, count);
        count--;
        shiftDown(1);
        return ret;
    }

    private void shiftDown(int k) {
        // 只要它有孩子，注意，这里的等于号是十分关键的
        while (2 * k <= count) {
            int j = 2 * k;
            // 如果它有右边的孩子，并且右边的孩子大于左边的孩子
            if (j + 1 <= count && data[indexes[j + 1]] > data[indexes[j]]) {
                // 右边的孩子胜出，此时可以认为没有左孩子，
                j = j + 1;
            }

            // 如果当前的元素的值，比右边的孩子节点要大，则逐渐下落的过程到此结束
            if (data[indexes[k]] >= data[indexes[j]]) {
                break;
            }
            // 否则，交换位置，继续循环
            swap(indexes, k, j);
            k = j;
        }
    }


    /**
     * 支持的特殊操作，返回索引堆中优先级最高的那个元素的索引
     * 由用户再调用 getItem() 方法去取得这个数组
     *
     * @return
     */
    public int extractMapIndex() {
        assert count > 0;
        // 为了满足用户看起来的索引的定义，所以要减 1
        int ret = indexes[1] - 1;
        swap(indexes, 1, count);
        count--;
        shiftDown(1);
        return ret;
    }

    public int getItem(int i) {
        return data[i + 1];
    }

    public void change(int i, int newItem) {
        i += 1;
        data[i] = newItem;
        // 找到 index[j] = i，j 表示 data[i] 在堆中的位置
        // 之后 shiftUp(j)，在 shiftDown(j)
        for (int j = 1; j <= count; j++) {
            if (indexes[j] == i) {
                shiftDown(j);
                shiftUp(j);
                return;
            }
        }
    }


    /**
     * 下面是我自己写的最大索引堆的测试方法
     *
     * @param args
     */
    public static void main(String[] args) {
        int N = 10000;
        IndexMaxHeap indexMapHeap = new IndexMaxHeap(N);
//        for (int i = 0; i < N; i++) {
//            int randomNum = (int) (Math.random() * N);
//            indexMapHeap.insert(i, randomNum);
//        }
//        int[] newArray = new int[N];
//        while (!indexMapHeap.isEmpty()) {
//            newArray[--N] = indexMapHeap.getItem(indexMapHeap.extractMapIndex());
//        }
//        SortTestHelper.testSorted(newArray);

        int[] data = new int[]{15, 17, 19, 13, 22, 16, 28, 30, 41, 62};
        for (int i = 0; i < data.length; i++) {
            indexMapHeap.insert(i, data[i]);
        }
        System.out.println(Arrays.toString(indexMapHeap.indexes));

    }
}
