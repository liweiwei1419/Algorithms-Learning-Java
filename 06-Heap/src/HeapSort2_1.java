import java.util.Arrays;

public class HeapSort2_1 {

    private MaxHeap maxHeap;

    public HeapSort2_1(int[] arr) {
        this.maxHeap = new MaxHeap(arr);
    }

    public void sort(int[] nums) {
        while (maxHeap.getSize() > 0) {
            nums[maxHeap.getSize() - 1] = maxHeap.extractMax();
        }
    }

    public static void main(String[] args) {
        int[] nums = {8, 1, 4, 6, 3, 2, 5, 9, 7};
        HeapSort2_1 heapSort2 = new HeapSort2_1(nums);
        heapSort2.sort(nums);
        System.out.println(Arrays.toString(nums));
    }
}
