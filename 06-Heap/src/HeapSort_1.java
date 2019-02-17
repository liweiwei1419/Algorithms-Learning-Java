import java.util.Arrays;

public class HeapSort_1 {

    private MaxHeap maxHeap;

    public HeapSort_1() {
        this.maxHeap = new MaxHeap(20);
    }

    public HeapSort_1(int capacity) {
        this.maxHeap = new MaxHeap(capacity);
    }

    /**
     * 把数组中的元素先全部挨个 insert 到最大堆中
     * 然后在依次取出，因为每次取出的都是剩下的元素中的最大者
     * 因此应该倒着覆盖到原待排序数组
     *
     * @param nums
     */
    public void sort(int[] nums) {
        int[] temp = nums.clone();
        for (Integer item : temp) {
            maxHeap.insert(item);
        }
        while (maxHeap.getSize() > 0) {
            nums[maxHeap.getSize() - 1] = maxHeap.extractMax();
        }
    }

    public static void main(String[] args) {
        int[] nums = {8, 1, 4, 6, 3, 2, 5, 9, 7};
        HeapSort_1 heapSort = new HeapSort_1(nums.length);
        heapSort.sort(nums);
        System.out.println(Arrays.toString(nums));
    }
}
