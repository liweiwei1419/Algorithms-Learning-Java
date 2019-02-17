public class MaxHeap3 {
    /**
     * 我们这个版本的实现中，0 号索引是不存数据的，这一点一定要注意
     */
    private int[] data;

    /**
     * 当前堆中存储的元素的个数
     */
    private int count;

    /**
     * 堆中能够存储的元素的最大数量（为简化问题，不考虑动态扩展）
     */
    private int capacity;

    public MaxHeap3(int capacity) {
        // 初始化最大堆
        // 初始化底层数组元素（ 0 号索引位置不存数据，这是为了使得通过父节点获得左右孩子有更好的表达式）
        data = new int[capacity + 1];
        count = 0;
        this.capacity = capacity;
    }

    /**
     * 传递一个数组，形成一个最大堆，这一步操作叫 heapify
     *
     * @param arr 待排序的数组元素
     */
    public MaxHeap3(int[] arr) {
        int length = arr.length;
        data = new int[length + 1];
        for (int i = 0; i < length; i++) {
            data[i + 1] = arr[i];
        }
        count = length;
        // 理解这一步是关键 heapify
        for (int i = length / 2; i >= 1; i--) {
            shiftDown(i);
        }
    }

    // 返回堆中的元素个数
    public int getSize() {
        return count;
    }

    // 返回一个布尔值，返回堆中是否为空
    public boolean isEmpty() {
        return count == 0;
    }

    public void insert(int item) {
        assert count + 1 <= capacity;
        // 把新添加的元素放在数组的最后一位，对应于最大堆最后一个叶子节点
        data[count + 1] = item;
        count++;
        // 考虑将它上移
        shiftUp(count);
    }

    /**
     * 将索引是 k 这个位置（第 k 位，注意我们最大堆的根节点从数组索引为 1 的地方开始定义）的元素
     * 逐渐上移，直到满足最大堆的定义
     *
     * @param k
     */
    private void shiftUp(int k) {
        int temp = data[k];
        // 有索引就要考虑索引越界的情况，已经在索引 1 的位置，就没有必要上移了
        while (k > 1 && data[k / 2] < temp) {
            data[k] = data[k / 2];
            k /= 2;
        }
        data[k] = temp;
    }

    /**
     * shiftUp 的简单实现：逐层交换上移
     *
     * @param k
     */
    private void shiftUp_(int k) {
        // 有索引就要考虑索引越界的情况，已经在索引 1 的位置，就没有必要上移了
        while (k > 1 && data[k / 2] < data[k]) {
            swap(data, k / 2, k);
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
     * 2、从根节点开始逐层下移：下移的过程中将与左右孩子节点进行比较，把最大的那个跟自己交换
     *
     * @return 根节点的元素
     */
    public int extractMax() {
        assert count > 0;
        int ret = data[1];
        swap(data, 1, count);
        count--;
        shiftDown(1);
        return ret;
    }

    /**
     * 把在索引 k 这个位置元素逐渐下移
     *
     * @param k
     */
    private void shiftDown(int k) {
        int temp = data[k];
        // 只要它有孩子，注意，这里的等于号是十分关键的
        while (2 * k <= count) {
            int j = 2 * k;
            // 如果它有右边的孩子，并且右边的孩子大于左边的孩子
            if (j + 1 <= count && data[j + 1] > data[j]) {
                // 右边的孩子胜出，此时可以认为没有左孩子，
                j = j + 1;
            }
            // 如果当前的元素的值，比右边的孩子节点要大，则逐渐下落的过程到此结束
            if (temp >= data[j]) {
                break;
            }
            // 否则，交换位置，继续循环
            data[k] = data[j];
            k = j;
        }
        data[k] = temp;
    }

    /**
     * 把在索引 k 这个位置元素逐渐下落
     *
     * @param k
     */
    private void shiftDown_(int k) {
        // 只要它有孩子，注意，这里的等于号是十分关键的
        while (2 * k <= count) {
            int j = 2 * k;
            // 如果它有右边的孩子，并且右边的孩子大于左边的孩子
            if (j + 1 <= count && data[j + 1] > data[j]) {
                // 右边的孩子胜出，此时可以认为没有左孩子，
                j = j + 1;
            }
            // 如果当前的元素的值，比右边的孩子节点要大，则逐渐下落的过程到此结束
            if (data[k] >= data[j]) {
                break;
            }
            // 否则，交换位置，继续循环
            swap(data, k, j);
            k = j;
        }
    }


    /**
     * 显示最大堆中的数据
     */
    public void show() {
        System.out.printf("[");
        for (int i = 1; i <= count; i++) {
            if (i == count) {
                System.out.printf("%d]\n", data[i]);
            } else {
                System.out.printf("%d,", data[i]);
            }
        }
    }
}
