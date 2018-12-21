import java.util.Arrays;
import java.util.Random;

public class QuickSort1Optimize_1 {

    public static Random random = new Random(System.currentTimeMillis());

    private static void swap(int[] arr, int index1, int index2) {
        if (index1 == index2) {
            return;
        }
        int temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }

    public void sort(int[] nums) {
        int l = nums.length;
        quickSort(nums, 0, l - 1);
    }

    private void quickSort(int[] arr, int left, int right) {
        // 递归终止条件，一定要写，否则就变成死循环了
        if (left >= right) {
            return;
        }
        int p = partition(arr, left, right);
        quickSort(arr, left, p - 1);
        quickSort(arr, p + 1, right);
    }

    private int partition(int[] arr, int left, int right) {
        int randomIndex = random.nextInt(right - left + 1) + left;
        swap(arr, left, randomIndex);

        int v = arr[left];
        int j = left;
        for (int i = left + 1; i <= right; i++) {
            if (arr[i] <= v) {
                j++;
                swap(arr, j, i);
            }
        }
        swap(arr, j, left);
        return j;
    }

    public static void main(String[] args) {
        int[] nums = {6, 4, 3, 7, 1, 5, 2, 8, 9, 10};
        System.out.println(Arrays.toString(nums));
        QuickSort1Optimize_1 quickSort01 = new QuickSort1Optimize_1();
        quickSort01.sort(nums);
        System.out.println(Arrays.toString(nums));
    }
}
