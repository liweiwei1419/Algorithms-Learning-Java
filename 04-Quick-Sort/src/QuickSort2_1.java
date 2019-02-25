import java.util.Arrays;

/**
 * Created by liwei on 17/6/8.
 */
public class QuickSort2_1 {

    private void quickSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }

        int p = partition(arr, left, right);
        quickSort(arr, left, p - 1);
        quickSort(arr, p + 1, right);
    }

    /**
     * 两路快速排序，能解决有很多重复值的问题
     *
     * @param arr
     * @param left
     * @param right
     * @return
     */
    private int partition(int[] arr, int left, int right) {
        int v = arr[left];
        int i = left + 1;
        int j = right;
        while (true) {
            while (i <= right && arr[i] < v) {
                i++;
            }
            while (j >= left + 1 && arr[j] > v) {
                j--;
            }
            if (i > j) {
                break;
            }
            swap(arr, i, j);
            i++;
            j--;
        }
        swap(arr, j, left);
        return j;
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
        quickSort(nums, 0, nums.length - 1);
    }

    public static void main(String[] args) {
        int[] nums = {6, 4, 3, 7, 1, 5, 2, 8, 9, 10};
        System.out.println(Arrays.toString(nums));
        QuickSort2_1 quickSort02 = new QuickSort2_1();

        quickSort02.sort(nums);
        System.out.println(Arrays.toString(nums));
    }
}
