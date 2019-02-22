
/**
 * 原地堆排序
 * Created by liwei on 17/5/15.
 */
public class HeapSort3 implements ISortAlgorithm {
    @Override
    public String getName() {
        return "原地堆排序";
    }


    /**
     * 原地堆排序的目标就是，不再借助 MaxHeap3 这个数据结构进行排序，减少了空间复杂度
     * 注意：此时我们的数组索引从 0 开始定义(自己在纸上画一下图，就能清晰地明白算法实现的含义)
     *
     * @param arr 待排序数组
     */
    @Override
    public void sort(int[] arr) {
        int length = arr.length;
        // 首先 heapify：将一个无序的数组组成了一个最大堆，第 1 个元素就是最大值
        for (int i = (length - 1) / 2; i >= 0; i--) {
            shiftDown(arr, length, i);
        }

        for (int i = length - 1; i > 0; i--) {
            swap(arr, 0, i);
            shiftDown(arr, i, 0);
        }
    }

    /**
     * 从索引为 begin 开始，end 为止 [begin,end] 的数组元素进行 shift down 的操作
     * 注意 shiftDown 不能复用我们上面写的，而设计成
     * 对 0 开始，begin 为止，即将 arr 中 [0,begin] 部分的数组元素视为"最大堆"
     * 对索引为 end 的元素进行 shift down 的操作
     *
     * @param arr
     * @param begin
     * @param end
     */
    private void shiftDown(int[] arr, int begin, int end) {
        // 如果有右孩子的节点，并且右孩子节点比左孩子节点的值要大
        // 此时可以忽略左孩子节点的存在，拿右孩子节点的数值和自己比较
        // 只要它有左孩子，就不是叶子节点，就可能 shift down，注意：这里是小于号
        while (2 * end + 1 < begin) {
            int k = 2 * end + 1;
            if (k + 1 < begin && arr[k] < arr[k + 1]) {
                k = k + 1;
            }
            if (arr[end] < arr[k]) {
                swap(arr, end, k);
                // 留意
                end = k;
            } else {
                break;
            }
        }
    }

    private void swap(int[] arr, int index1, int index2) {
        if (index1 == index2) {
            return;
        }
        int temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }
}
