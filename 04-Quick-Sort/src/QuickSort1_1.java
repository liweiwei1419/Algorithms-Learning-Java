import java.util.Arrays;

public class QuickSort1_1 {

    private void sort(int[] arr) {
        int len = arr.length;
        quickSort(arr, 0, len - 1);
    }

    // 从标定点后面一个一个地比较到底，遇到比标定元素大的，就放过，遇到比标定元素小的或者等于的，就依次放在标定元素的后面
    // 从一个具体的例子出发，就能正确地写出代码，例如：
    // 3, 4, 6, 2, 1, 8, 5, 7
    // 3, 2, 6, 4, 1, 8, 5, 7
    // 3, 2, 1, 4, 6, 8, 5, 7
    // 1, 2, 3, 4, 6, 8, 5, 7

    private int partition(int[] arr, int left, int right) {
        // 待优化 1 ：随机选择一个点作为标定点
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
        // 递归终止条件，一定要写，否则就变成死循环了
        // 待优化 2 ：小数组的时候，使用插入排序
        if (left >= right) {
            return;
        }
        int p = partition(arr, left, right);
        quickSort(arr, left, p - 1);
        quickSort(arr, p + 1, right);
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
        QuickSort1_1 quickSort1 = new QuickSort1_1();
        quickSort1.sort(nums);
        System.out.println(Arrays.toString(nums));
    }
}
