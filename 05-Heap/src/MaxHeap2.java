import java.util.Random;

/**
 * Created by liwei on 17/5/29.
 */
public class MaxHeap2 {


    private int[] data;
    private int count;

    /**
     * 构造函数
     *
     * @param capacity
     */
    public MaxHeap2(int capacity) {
        data = new int[capacity + 1];
        count = 0;
    }

    public boolean isEmpty() {
        return count > 0;
    }

    public int size() {
        return count;
    }

    /**
     * 向最大堆中插入一个元素
     * 思路：在数组的最后位置添加一个元素，然后把这个元素 shift up 放在合适的位置
     *
     * @param num
     */
    public void insert(int num) {
        count++;
        data[count] = num;
        shiftUp(count);
    }

    /**
     * 把指定索引（记住：这里的索引从 1 开始）的元素与父节点进行比较，如果父节点大，就要进行交换
     *
     * @param count
     */
    private void shiftUp(int count) {
        // 父节点的索引
        for (int k = count; k >= 1 && data[k] > data[k / 2]; k /= 2) {
            swap(data, k, k / 2);
        }
    }

    private void swap(int[] data, int index1, int index2) {
        int temp = data[index1];
        data[index1] = data[index2];
        data[index2] = temp;
    }

    /**
     * 从最大堆中取出一个元素
     */
    public int extractElement() {
        assert count > 0;
        int ret = data[1];
        // 把最后一个元素和第 1 个元素交换
        swap(data, 1, count);
        count--;
        shiftDown(1);
        return ret;
    }

    /**
     * 从指定的索引出执行 shift down 的操作
     *
     * @param k
     */
    private void shiftDown(int k) {
        // 有孩子，就可能有 shiftDown 这个过程
        while (2 * k <= count) {
            int j = 2 * k;
            // 看看有没有右边的孩子
            if (j + 1 <= count && data[j] < data[j + 1]) {
                // 如果有右边的孩子，并且右边的孩子比左边的孩子大,就好像左孩子不存在一样
                j = j + 1;
            }
            // 否则，只看左边节点
            if (data[k] >= data[j]) {
                break;
            }
            swap(data, k, j);
            k = j;
        }
    }

    /**
     * 通过一个数组构建一个最大堆
     * 这个过程叫做 Heapify
     * 从 index/2 这个元素开始，到 root 即 index = 1 的节点，依次进行 shift down 就可以构建成最大堆了
     *
     * @param arr
     */
    public MaxHeap2(int[] arr) {
        int count = arr.length;
        data = new int[count + 1];
        for (int i = 1; i <= count; i++) {
            data[i] = arr[i - 1];
        }
        // 这一步很关键，并且位置也很关键，因为 shift down 依赖这个值，用 debug 就可以测试出来
        this.count = count;

        for (int k = count / 2; k >= 1; k--) {
            shiftDown(k);
        }
    }


    /**
     * 测试方法
     *
     * @param args
     */
    public static void main(String[] args) {
        method2();
    }

    private static void method2() {
        int capacity = 500;
        int[] array = new int[capacity];
        int currentNum;
        Random random = new Random();
        for (int i = 0; i < capacity; i++) {
            currentNum = random.nextInt(1000000000);
            array[i] = currentNum;
        }
        // System.out.println(Arrays.toString(array));
        MaxHeap2 myMaxHeap = new MaxHeap2(array);
        for (int i = capacity - 1; i >= 0; i--) {
            array[i] = myMaxHeap.extractElement();
        }
        // System.out.println(Arrays.toString(array));
        // System.out.println(ArrayUtil.judgeArraySorted(array));
    }

    private static void method1() {
        int capacity = 500000;
        MaxHeap2 myMaxHeap = new MaxHeap2(capacity);
        // 元素个数

        int currentNum;
        Random random = new Random();
        for (int i = 0; i < capacity; i++) {
            currentNum = random.nextInt(1000000000);
            myMaxHeap.insert(currentNum);
        }


        int[] array = new int[capacity];
        for (int i = capacity - 1; i >= 0; i--) {
            array[i] = myMaxHeap.extractElement();
        }
        // System.out.println(Arrays.toString(array));
        // System.out.println(ArrayUtil.judgeArraySorted(array));
    }
}
