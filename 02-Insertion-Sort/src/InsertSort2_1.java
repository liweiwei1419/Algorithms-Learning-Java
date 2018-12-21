import java.util.Arrays;

public class InsertSort2_1 {

    public void sort(int[] arr) {
        int len = arr.length;
        // 外层循环应该到最后一个元素
        for (int i = 1; i < len; i++) {
            int temp = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > temp) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = temp;
            // 观察每一轮外层循环以后的数组
            System.out.println(Arrays.toString(arr));
        }
    }

    public static void main(String[] args) {
        int[] nums = {8, 4, 3, 6, 5, 1};
        InsertSort2_1 insertionSort2 = new InsertSort2_1();
        insertionSort2.sort(nums);
        System.out.println(Arrays.toString(nums));
    }
}
