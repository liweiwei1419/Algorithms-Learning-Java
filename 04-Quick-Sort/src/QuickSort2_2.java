
import java.util.Arrays;

/**
 * 双路快速排序
 * 快速排序，据说是二十世纪发明的最伟大的排序算法，时间复杂度可以到 O(nlogn)
 * Created by liwei on 17/5/11.
 */
public class QuickSort2_2 implements ISortAlgorithm {
    @Override
    public String getName() {
        return "快速排序的实现2";
    }

    @Override
    public void sort(int[] arr) {
        int len = arr.length;
        quickSort(arr, 0, len - 1);
    }

    private void quickSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int p = partition(arr, left, right);
        quickSort(arr, left, p - 1);
        quickSort(arr, p + 1, right);
    }

    /**
     * 这个定义非常重要，直接影响我们的算法初始值定义
     * [left+1,i) 全部小于标定点
     * (j,right] 全部大于标定点
     * 在指针对撞的过程中，和标定点相同的元素就被挤到了中间
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
            while (j > left && arr[j] > v) {
                j--;
            }
            if (i > j) {
                break;
            }
            swap(arr, i, j);
            i++;
            j--;
        }
        swap(arr, left, j);
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


    public static void main(String[] args) {
        QuickSort2_2 o = new QuickSort2_2();
        int[] arr = {4, 6, 2, 3, 1, 5, 7, 8};
        int p = o.partition(arr, 0, arr.length - 1);
        System.out.println(p);
        System.out.println(Arrays.toString(arr));
    }
}
