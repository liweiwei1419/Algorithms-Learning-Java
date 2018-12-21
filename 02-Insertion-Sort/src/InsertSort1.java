
import java.util.Arrays;

public class InsertSort1 {

    // 与 InsertSort 等价的写法，没有 break 语句

    public static void main(String[] args) {
        int[] arr = SortTestHelper.generateRandomArray(15, 10, 100);
        System.out.println("原始数组" + Arrays.toString(arr));
        // 插入排序(内层循环可以提前终止)
        int len = arr.length;
        // 第 1 轮的时候，第 1 个数可以认为是排好序的
        for (int i = 1; i < len; i++) {
            for (int j = i ; j > 0 && arr[j - 1] > arr[j] ; j--) {
                SortTestHelper.swap(arr, j, j - 1);
            }
        }
        SortTestHelper.testSorted(arr);
        System.out.println("排好序的数组" + Arrays.toString(arr));
    }
}
