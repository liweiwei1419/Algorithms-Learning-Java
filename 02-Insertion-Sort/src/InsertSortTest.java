import java.util.Arrays;


public class InsertSortTest {

    /**
     * 逐个交换到指定位置
     *
     * @param args
     */
    public static void main(String[] args) {
        // 插入排序(内层循环可以提前终止)
        int[] arr = SortTestHelper.generateRandomArray(15, 10, 100);
        System.out.println("原始数组：" + Arrays.toString(arr));
        int len = arr.length;
        for (int i = 1; i < len ; i++) {
            // 第 1 轮的时候，第 1 个数可以认为是排好序的
            for (int j = i ; j > 0; j--) {
                if (arr[j - 1] > arr[j]) {
                    SortTestHelper.swap(arr, j, j - 1);
                } else {
                    break;
                }
            }
        }
        SortTestHelper.testSorted(arr);
        System.out.println("排序数组：" + Arrays.toString(arr));
    }
}
