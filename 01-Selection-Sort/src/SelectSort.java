import java.util.Arrays;


/**
 * 选择排序
 * Created by liwei on 17/5/10.
 */
public class SelectSort {

    public static void main(String[] args) {
        int[] arr = {17, 15, 7, 9, 4};
        // 起始位置选择到最后一个元素的前 1 个就可以
        // 因为最后一个元素无后继元素，内层循环就没有意义了
        for (int i = 0; i < arr.length - 1; i++) {
            // 假设外层循环最小的那个元素的下标就是这一轮循环的第 1 个元素
            int minIndex = i;
            // 注意边界值 j < length，从 i 后面的元素开始，直到最后一个元素，都要参与比较
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[minIndex] > arr[j]) {
                    minIndex = j;
                }
            }
            swap(arr, i, minIndex);
        }
        System.out.println(Arrays.toString(arr));
    }

    private static void swap(int[] arr, int i, int minIndex) {
        // 如果最小的那个元素的索引是自己，就没有必要再使用一个额外的空间进行元素的交换
        if (i == minIndex) {
            return;
        }
        int temp = arr[i];
        arr[i] = arr[minIndex];
        arr[minIndex] = temp;
    }


}
