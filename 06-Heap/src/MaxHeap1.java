import java.util.Arrays;

/**
 * 这个最大堆的索引从 1 开始，从 1 开始得到的最大堆的性质比较好记
 * Created by liwei on 17/6/9.
 */
public class MaxHeap1 {
    private int[] data;
    /**
     * 最大堆中元素的个数
     */
    private int count;
    /**
     * 最大堆的容量
     */
    private int capacity;


    public int[] getData() {
        return data;
    }

    public MaxHeap1() {
    }

    public MaxHeap1(int capacity) {
        this.capacity = capacity;
        this.count = 0;
        // 索引从 1 开始，所以 capacity + 1
        data = new int[capacity + 1];
    }

    public boolean isEmpty() {
        return count <= 0;
    }

    public int size() {
        return count;
    }

    /**
     * 在堆的尾部增加一个元素，将这个元素执行 shift up 操作，保持最大堆的性质
     *
     * @param element
     * @return
     */
    public void insertElement(int element) {
        assert count + 1 <= capacity;
        this.count = this.count + 1;
        data[count] = element;
        shiftUp(count);
    }

    /**
     * //TODO:17/6/9 还可以优化，把多次的交换工作编程多次的赋值
     * 对索引是 h 的元素执行 shiftUp 操作
     *
     * @param h
     */
    private void shiftUp(int h) {
        int temp = data[h];
        while (h > 1) {
            if (data[h / 2] < temp) {
                data[h] = data[h / 2];
                h /= 2;
            } else {
                break;
            }
        }
        data[h] = temp;
    }

    private void swap(int[] data, int index1, int index2) {
        if (index1 == index2) return;
        int temp = data[index1];
        data[index1] = data[index2];
        data[index2] = temp;
    }


    // 从最大堆中拿掉一个元素

    public int extractElement() {
        int num = data[1];
        assert this.count >= 0;
        swap(data, 1, count);
        this.count--;
        shiftDown(1);
        return num;
    }

    /**
     * 只要有左右孩子，左右孩子只要比自己大，就交换
     *
     * @param h
     */
    private void shiftDown(int h) {
        while (2 * h <= count) {
            // 如果这个元素有左边的孩子
            int k = 2 * h;
            if (k + 1 <= count && data[k + 1] > data[k]) {
                // 如果有右边的孩子，大于左边的孩子，就好像左边的孩子不存在一样样
                k = k + 1;
            }

            if (data[h] < data[k]) {
                swap(data, h, k);
            }
            h *= 2;
        }
    }

    /**
     * 打印出这个最大堆
     */
    public void showMaxHeap() {
        for (int i = 1; i <= count; i++) {
            System.out.printf("%d ", data[i]);
        }
        System.out.println();
    }


    /**
     * 判断自己写的数据结构是否符合最大堆的定义
     */
    public void judgeMaxHeapDefinition() {

        boolean flag = false;
        for (int k = 1; 2 * k <= count || 2 * k + 1 <= count; k++) {
            if (2 * k <= count) {
                if (data[2 * k] >= data[k]) {
                    flag = true;
                    break;
                }
            }

            if (2 * k + 1 <= count) {
                if (data[2 * k + 1] >= data[k]) {
                    flag = true;
                    break;
                }
            }

        }
        if (flag) {
            throw new RuntimeException("不符合最大堆的定义");
        }
        System.out.println("符合最大堆的定义");
    }


    /**
     * 将一个数组构建成最大堆
     *
     * @param arr
     */
    public MaxHeap1(int[] arr) {
        int len = arr.length;
        data = new int[len + 1];
        for (int i = 0; i < len; i++) {
            data[i + 1] = arr[i];

        }
        // 这一行不要忘记掉
        // 这一行不要忘记掉
        // 这一行不要忘记掉
        count = len;
        for (int i = count / 2; i >= 1; i--) {
            shiftDown(i);
        }
    }

    public static void main(String[] args) {
        MaxHeap1 maxHeap = new MaxHeap1(10);
        maxHeap.insertElement(3);
        maxHeap.showMaxHeap();
        maxHeap.insertElement(12);
        maxHeap.showMaxHeap();
        maxHeap.insertElement(45);
        maxHeap.showMaxHeap();
        maxHeap.insertElement(8);
        maxHeap.showMaxHeap();
        maxHeap.insertElement(78);
        maxHeap.showMaxHeap();
        maxHeap.insertElement(19);
        maxHeap.showMaxHeap();
        maxHeap.insertElement(20);
        maxHeap.showMaxHeap();

        maxHeap.judgeMaxHeapDefinition();
        System.out.println("开始取出元素");

        while (!maxHeap.isEmpty()) {
            System.out.printf("最大堆的容量 %d", maxHeap.size());
            int extractElement1 = maxHeap.extractElement();
            System.out.printf(" %d \n", extractElement1);

        }

        int[] arr = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        MaxHeap1 maxHeap1 = new MaxHeap1(arr);
        maxHeap1.judgeMaxHeapDefinition();
    }

}
