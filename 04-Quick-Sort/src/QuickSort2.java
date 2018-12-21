import java.util.Arrays;
import java.util.Random;

/**
 * 指针对撞的双路快速排序：将与标定点相等的元素等概率分散到递归函数的两边
 */
public class QuickSort2 {

    private static Random random = new Random();

    private void sort(int[] arr) {
        int len = arr.length;
        quickSort(arr, 0, len - 1);
    }

    // 3, 4, 6, 2, 1, 8, 5, 7
    // 3, 1, 6, 2, 4, 8, 5, 7
    // 3, 1, 2, 6, 4, 8, 5, 7
    // 2, 1, 3, 6, 4, 8, 5, 7
    public int partition(int[] arr, int left, int right) {
        // [3,4,5]
        int randonIndex = left + random.nextInt(right - left + 1);
        swap(arr, randonIndex, left);
        int p = arr[left];
        int l = left + 1;
        int r = right;
        while (true) {
            while (l <= right && arr[l] < p) {
                l++;
            }
            while (r>=left && arr[r] > p) {
                r--;
            }
            if(l>r){
                break;
            }
            swap(arr, l, r);
            l++;
            r--;
        }
        swap(arr, left, r);
        return r;
    }

    private void quickSort(int[] arr, int left, int right) {
        if (right - left <= 15) {
            insertSort(arr, left, right);
            return;
        }
        int p = partition(arr, left, right);
        quickSort(arr, left, p - 1);
        quickSort(arr, p + 1, right);
    }

    private void insertSort(int[] arr, int left, int right) {
        // 第 1 遍不用插入，所以是总长度减去 1
        for (int i = left + 1; i <= right; i++) {
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
        int[] nums = new int[]{3, 4, 6, 2, 1, 8, 5, 7, 10, 9, 12, 200};
        QuickSort2 quickSort2 = new QuickSort2();
        quickSort2.sort(nums);
        System.out.println(Arrays.toString(nums));
    }
}
