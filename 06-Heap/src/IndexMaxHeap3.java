

import java.util.Arrays;

/**
 * 这一版与上一版的区别在于交换元素
 */
public class IndexMaxHeap3 {

    private int[] data;
    private int count;
    private int capacity;
    private int[] indexes;
    private int[] reverse;

    public IndexMaxHeap3(int capacity) {
        data = new int[capacity + 1];
        indexes = new int[capacity + 1];
        reverse = new int[capacity + 1];
        count = 0;
        this.capacity = capacity;
    }

    public int getSize() {
        return count;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public void insert(int i, int item) {
        assert count + 1 <= capacity;
        assert i + 1 >= 1 && i + 1 <= capacity;
        i += 1;
        data[i] = item;
        indexes[count + 1] = i;
        reverse[i] = indexes[count + 1];
        count++;
        shiftUp(count);
    }


    private void shiftUp(int k) {
        while (k > 1 && data[indexes[k / 2]] < data[indexes[k]]) {
            swapIndexes(k / 2, k);
            k /= 2;
        }
    }

    private void swapIndexes(int index1, int index2) {
        if (index1 == index2) {
            return;
        }
        int temp = indexes[index1];
        indexes[index1] = indexes[index2];
        indexes[index2] = temp;

        reverse[indexes[index1]] = index2;
        reverse[indexes[index2]] = index1;
    }


    /**
     * @return
     */
    public int extractMax() {
        // 将此时二叉堆中的最大的那个数据删除（出队），返回的是数据，不是返回索引
        assert count > 0;
        int ret = data[indexes[1]];
        // 只要设计交换的操作，就一定是索引数组交换
        // 每一次交换了 indexes 索引以后，还要把 reverse 索引也交换
        swapIndexes(1, count);
        count--;
        shiftDown(1);
        return ret;
    }


    private void shiftDown(int k) {
        while (2 * k <= count) {
            int j = 2 * k;
            if (j + 1 <= count && data[indexes[j + 1]] > data[indexes[j]]) {
                j = j + 1;
            }
            if (data[indexes[k]] >= data[indexes[j]]) {
                break;
            }
            // 每一次交换了 indexes 索引以后，还要把 reverse 索引也交换
            swapIndexes(k, j);
            k = j;
        }
    }


    public int extractMaxIndex() {
        assert count > 0;
        int ret = indexes[1] - 1;
        // 每一次交换了 indexes 索引以后，还要把 reverse 索引也交换
        swapIndexes(1, count);
        count--;
        shiftDown(1);
        return ret;
    }

    public int getItem(int i) {
        return data[i + 1];
    }


    public void change(int i, int item) {
        i = i + 1;
        data[i] = item;
        // 原先遍历的操作，现在就变成了这一步，是不是很酷
        int j = reverse[i];
        shiftDown(j);
        shiftUp(j);
        return;

    }

    // 编写测试用例
    public static void main(String[] args) {
        // 测试用例2：
        int n = 10;

        int[] nums = SortTestHelper.generateRandomArray(n, 10, 100);
        System.out.println("原始数组：" + Arrays.toString(nums));

        IndexMaxHeap3 indexMaxHeap3 = new IndexMaxHeap3(nums.length);

        for (int i = 0; i < n; i++) {
            indexMaxHeap3.insert(i, nums[i]);
        }
        int[] ret = new int[n];
        for (int i = 0; i < n; i++) {
            int extractMax = indexMaxHeap3.extractMax();
            // System.out.println("还剩多少元素：" + indexMaxHeap.count);
            ret[n - 1 - i] = extractMax;
        }

        System.out.println("我的排序：" + Arrays.toString(ret));
        int[] copy = nums.clone();
        Arrays.sort(copy);
        System.out.println("系统排序：" + Arrays.toString(copy));
        System.out.println("原始数组：" + Arrays.toString(nums));
    }
}
