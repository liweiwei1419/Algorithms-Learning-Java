import java.util.Arrays;

/**
 * 插入排序的一个特点是：循环有终止的时候
 * Created by liwei on 17/5/10.
 */
public class InsertSort implements ISortAlgorithm {
    @Override
    public String getName() {
        return "insert sort";
    }

    /**
     * [3,6,5]
     *
     * @param arr 待排序数组
     */
    @Override
    public void sort(int[] arr) {
        int len = arr.length;
        // 外层循环应该到最后一个元素
        for (int i = 1; i < len; i++) {
            // 内层循环从外层循环的标定点开始，直到索引为 1 的那个元素，
            // 因为索引为 1 的那个元素前面没有元素了
            for (int j = i; j > 0; j--) {
                // 只要后面元素比前面元素小，就交换，否则退出循环
                if (arr[j - 1] > arr[j]) {
                    swap(arr, j, j - 1);
                } else {
                    break;
                }
            }
            // 观察每一轮外层循环以后的数组
            // System.out.println(Arrays.toString(arr));
        }
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
        int[] randomArray = SortTestHelper.generateRandomArray(5000, 100, 500);
        SortTestHelper.testSortEfficiency(new InsertSort(), randomArray);
        SortTestHelper.testSorted(randomArray);
    }

}
