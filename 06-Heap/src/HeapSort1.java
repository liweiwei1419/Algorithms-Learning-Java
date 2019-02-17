/**
 * 第 1 个版本的堆排序算法
 * Created by liwei on 17/5/15.
 */
public class HeapSort1 implements ISortAlgorithm {
    @Override
    public String getName() {
        return "第 1 个版本的堆排序算法，逐个全部入队，再逐个出队";
    }

    @Override
    public void sort(int[] arr) {
        int length = arr.length;
        MaxHeap maxHeap = new MaxHeap(length);
        for (int i = 0; i < length; i++) {
            maxHeap.insert(arr[i]);
        }
        for (int i = length - 1; i >= 0; i--) {
            arr[i] = maxHeap.extractMax();
        }
    }
}
