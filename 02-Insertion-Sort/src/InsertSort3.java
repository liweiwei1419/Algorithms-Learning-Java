import java.util.Arrays;

public class InsertSort3 {

    public static void main(String[] args) {
        int[] arr = SortTestHelper.generateRandomArray(15, 10, 100);
        System.out.println("原始数组" + Arrays.toString(arr));
        int len = arr.length;
        for (int i = 1; i < len; i++) { // 第 1 轮的时候，第 1 个数可以认为是排好序的
            int temp = arr[i];
            int j = i;
            for (; j > 0 && arr[j - 1] > temp; j--) {
                // 后移一位
                arr[j] = arr[j - 1];
            }
            arr[j] = temp;
        }
        SortTestHelper.testSorted(arr);
        System.out.println("排好序的数组" + Arrays.toString(arr));
    }
}
