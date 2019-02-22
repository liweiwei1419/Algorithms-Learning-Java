
import java.util.Arrays;

/**
 * 跟着老师的代码实现的最大索引堆，这是第 1 版的最大索引堆，在 change() 这个方法的时候效率并不高
 * 使用了泛型
 * Created by liwei on 17/6/9.
 */
public class IndexMaxHeap1<T extends Comparable> {
    /**
     * 最大索引堆中的数据
     */
    private T[] data;
    /**
     * 最大索引堆中的索引
     * 【特别注意】：索引构成的数据结构，依次排列出来是一个堆
     * indexes[1] = 10 表示 data[10] 在索引堆中的位置是 1
     * indexes[x] = i ，表示索引 i 的元素在堆中的位置是 x
     */
    private int[] indexes;
    /**
     * 最大索引堆的反向索引
     * 找 indexes[] 中原来索引是 i 的元素的位置
     * reverse[i] = j 表示 data[i] 在索引堆中的位置是 j
     * <p>
     * reverse[i] = j 表示 indexes[] 中值为 i 的元素在原数组中的索引是 j
     */
    private int[] reverse;
    private int count;
    private int capacity;

    /**
     * 我们还是采取了索引从 1 开始的索引最大堆
     *
     * @param capacity
     */
    public IndexMaxHeap1(int capacity) {
        data = (T[]) new Comparable[capacity + 1];
        indexes = new int[capacity + 1];
        reverse = new int[capacity + 1];
        // 为 reverse[] 数组赋初始值
        // 为 reverse[] 数组赋初始值
        // 为 reverse[] 数组赋初始值，0 有特殊的含义：reverse[i]=0 表示 indexes 中值为 i 的元素不存在了
        for (int i = 0; i <= capacity; i++) {
            reverse[i] = 0;
        }
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
     * 向最大索引堆中插入一个元素
     * 新元素的索引为 i，元素为 t
     * 【特别注意】：这里的 insert 虽然指定了索引，但是一定是在 data 数组的最后添加数据
     *
     * @param i
     * @param t
     */
    public void insert(int i, T t) {
        assert count + 1 <= capacity;
        assert i >= 1 && i <= capacity - 1;
        i++;
        data[i] = t;
        // 这一步很关键
        // 这一步很关键
        // 这一步很关键
        indexes[count + 1] = i;
        reverse[i] = count + 1;

        count++;
        shiftUp(count);
    }

    private void shiftUp(int h) {
        while (h > 1) {
            if (data[indexes[h / 2]].compareTo(data[indexes[h]]) < 0) {
                swapIndexes(indexes, h / 2, h);
                h /= 2;
            } else {
                break;
            }
        }
    }

    private void swapIndexes(int[] indexes, int i, int j) {
        if (i == j) {
            return;
        }
        int temp = indexes[i];
        indexes[i] = indexes[j];
        indexes[j] = temp;

        reverse[indexes[i]] = i;
        reverse[indexes[j]] = j;
    }


    public int extractMaxIndex() {
        assert count > 0;
        int ret = indexes[1] - 1;
        swapIndexes(indexes, 1, count);
        reverse[indexes[count]] = 0;
        count--;
        shiftDown(1);
        return ret;

    }

    /**
     * @param h
     */
    private void shiftDown(int h) {
        while (2 * h <= count) {
            int k = 2 * h;
            if (k + 1 <= count && data[indexes[k + 1]].compareTo(data[indexes[k]]) > 0) {
                k = k + 1;
            }
            if (data[indexes[h]].compareTo(data[indexes[k]]) < 0) {
                swapIndexes(indexes, h, k);
                h = k;
            } else {
                break;
            }
        }
    }

    /**
     * 获取最大索引堆中索引为 i 的元素
     *
     * @param i
     * @return
     */
    public T getItem(int i) {
        assert i + 1 >= 1 && i + 1 <= capacity;
        return data[i + 1];
    }

    /**
     * 将最大索引堆中索引为 i 的元素修改为 t
     *
     * @param i
     * @param t
     */
    public void change(int i, T t) {

        assert contain(i);

        i++;
        data[i] = t;
        // 找到 index[j] = i 中的 j，尝试将 j shiftUp 和 shiftDown
        /*for(int j=1;i<=count;j++){
            if(indexes[j] == i){
                shiftDown(j);
                shiftUp(j);
                return;
            }
        }*/

        // 使用 reverse[] 数组以后，我们可以很方便地索引
        // 原来的数组索引为 i 的元素在 indexes 数组中的位置
        shiftDown(reverse[i]);
        shiftUp(reverse[i]);
    }


    /**
     * 在用户看来的索引 i 还在不在最大堆中
     * 看索引i所在的位置是否存在元素
     *
     * @param i
     * @return
     */
    private boolean contain(int i) {
        assert i >= 0 && i + 1 <= capacity;
        return reverse[i + 1] == 0;
    }

    /**
     * 测试最大索引堆的方法
     */
    public boolean testIndex() {
        int[] copyIndex = new int[count + 1];
        int[] copyReverse = new int[count + 1];

        for (int i = 0; i <= count; i++) {
            copyIndex[i] = indexes[i];
        }

        for (int i = 0; i <= count; i++) {
            copyReverse[i] = reverse[i];
        }


        System.out.println("数据堆" + Arrays.toString(data));
        System.out.println("索引堆" + Arrays.toString(copyIndex));
        System.out.println("反向索引堆" + Arrays.toString(copyIndex));
        Arrays.sort(copyIndex);

        System.out.println("排序以后的索引堆" + Arrays.toString(copyIndex));


        boolean res = true;
        for (int i = 1; i <= count; i++) {
            if (copyIndex[i - 1] + 1 != copyIndex[i]) {
                res = false;
                break;
            }
        }

        if (!res) {
            System.out.println("error");
            return false;
        }

        return true;
    }


    public void showIndexes() {

        System.out.printf("最大索引堆中的数据结构");
        System.out.println(Arrays.toString(data));

        System.out.printf("最大索引堆内部的索引堆的数据结构");
        System.out.println(Arrays.toString(indexes));


        System.out.printf("最大索引堆中反向索引堆中的数据结构");
        System.out.println(Arrays.toString(reverse));
    }

    // 测试 IndexMaxHeap4
    public static void main(String[] args) {
        /*int N = 10;
        IndexMaxHeap1<Integer> indexMaxHeap = new IndexMaxHeap1<>(N);
        for (int i = 0; i < N; i++) {
            indexMaxHeap.insert(i, (int) (Math.random() * N));
        }
        indexMaxHeap.showIndexes();
        indexMaxHeap.testIndex();
        int[] arr = new int[N];
        while (!indexMaxHeap.isEmpty()) {
            int num = indexMaxHeap.getItem(indexMaxHeap.extractMaxIndex());
            //System.out.println(num);
            arr[--N] = num;
        }
        SortTestHelper.testSorted(arr);*/

        IndexMaxHeap1<Integer> indexMaxHeap = new IndexMaxHeap1<>(10);
        indexMaxHeap.insert(0, 15);
        indexMaxHeap.insert(1, 17);
        indexMaxHeap.insert(2, 19);
        indexMaxHeap.insert(3, 13);
        indexMaxHeap.insert(4, 22);
        indexMaxHeap.insert(5, 16);
        indexMaxHeap.insert(6, 28);
        indexMaxHeap.insert(7, 30);
        indexMaxHeap.insert(8, 41);
        indexMaxHeap.insert(9, 62);
        indexMaxHeap.showIndexes();
        while (!indexMaxHeap.isEmpty()) {
            int maxIndex = indexMaxHeap.extractMaxIndex();
            int element = indexMaxHeap.getItem(maxIndex);
            System.out.printf("%d,", element);
        }




    }
}
