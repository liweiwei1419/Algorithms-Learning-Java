
import java.util.Arrays;

public class InsertSort2 {

    /**
     * 赋值后移
     * @param args
     */
    public static void main(String[] args) {
        int[] arr = SortTestHelper.generateRandomArray(15, 10, 100);
        System.out.println("原始数组：" + Arrays.toString(arr));
        // 插入排序算法的一个小优化，把逐个向前移动变成了多次赋值
        int len = arr.length;
        for (int i = 1; i < len; i++) { // 第 1 轮的时候，第 1 个数可以认为是排好序的
            int temp = arr[i];
            int j = i;
            for (; j > 0; j--) {
                if (arr[j - 1] > temp) {
                    // 后移一位
                    arr[j] = arr[j - 1];
                } else {
                    break;
                }
            }
            arr[j] = temp;
        }
        SortTestHelper.testSorted(arr);
        System.out.println("排序数组：" + Arrays.toString(arr));
    }
}
