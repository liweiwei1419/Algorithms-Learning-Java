import java.util.Arrays;

/**
 * 三路快速排序
 * Created by liwei on 17/6/8.
 */
public class QuickSort3_1 {

    /**
     * @param arr
     * @param left
     * @param right
     */
    private void quickSort3Ways(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int v = arr[left];
        int lt = left;
        int gt = right + 1;
        int i = left + 1;
        while (i < gt) {
            if (arr[i] < v) {
                swap(arr, lt + 1, i);
                i++;
                lt++;
            } else if (arr[i] > v) {
                swap(arr, gt - 1, i);
                gt--;
            } else { // arr[i]==v
                i++;
            }
        }
        swap(arr, left, lt);
        quickSort3Ways(arr, left, lt - 1);
        quickSort3Ways(arr, gt, right);
    }

    private void swap(int[] arr, int index1, int index2) {
        if (index1 == index2) {
            return;
        }
        int temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }


    public void sort(int[] nums) {
        quickSort3Ways(nums, 0, nums.length - 1);
    }


    public static void main(String[] args) {
        int[] nums = {6, 4, 3, 7, 1, 5, 2, 8, 9, 10};
        System.out.println(Arrays.toString(nums));
        QuickSort3_1 quickSort03 = new QuickSort3_1();

        quickSort03.sort(nums);
        System.out.println(Arrays.toString(nums));
    }
}
