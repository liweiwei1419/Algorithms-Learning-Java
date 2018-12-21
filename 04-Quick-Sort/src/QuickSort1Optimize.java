import java.util.Arrays;
import java.util.Random;

public class QuickSort1Optimize {

    private static Random random = new Random(System.currentTimeMillis());

    private void sort(int[] arr) {
        int len = arr.length;
        quickSort(arr, 0, len - 1);
    }

    private int partition(int[] arr, int left, int right) {
        // 优化 1 ：随机选择一个点作为标定点
        // [3,4,5]
        int randonIndex = left + random.nextInt(right - left + 1);
        swap(arr, randonIndex, left);
        int p = arr[left];
        int k = left;
        for (int i = left + 1; i <= right; i++) {
            if (arr[i] < p) {
                k++;
                swap(arr, i, k);
            }
        }
        swap(arr, left, k);
        return k;
    }

    private void quickSort(int[] arr, int left, int right) {
        // 优化 2 ：小数组的时候，使用插入排序
        if (right - left <= 15) {
            insertSort(arr, left, right);
            return;
        }
        int p = partition(arr, left, right);
        quickSort(arr, left, p - 1);
        quickSort(arr, p + 1, right);
    }

    private void insertSort(int[] arr, int left, int right) {
        for (int i = left + 1; i <= right; i++) { // 第 1 遍不用插入，所以是总长度减去 1
            int temp = arr[i];
            int j;
            for (j = i - 1; j >= left; j--) {
                // 后移一位
                if (arr[j] > temp) {
                    arr[j + 1] = arr[j];
                } else {
                    break;
                }
            }
            arr[j + 1] = temp;
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

    public static void main(String[] args) {
        int[] nums = new int[]{3, 4, 6, 2, 1, 8, 5, 7};
        QuickSort1Optimize optimize = new QuickSort1Optimize();
        optimize.sort(nums);
        System.out.println(Arrays.toString(nums));
    }
}
