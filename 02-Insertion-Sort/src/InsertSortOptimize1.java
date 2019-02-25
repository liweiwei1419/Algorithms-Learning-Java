/**
 * 插入排序的一个特点是：循环有终止的时候
 * Created by liwei on 17/5/10.
 */
public class InsertSortOptimize1 implements ISortAlgorithm {

    @Override
    public String getName() {
        return "insert sort 的优化";
    }

    @Override
    public void sort(int[] arr) {
        int len = arr.length;
        for (int i = 1; i < len; i++) {
            // 先把这个元素存一下
            int temp = arr[i];
            int j = i - 1;
            for (; j >= 0; j--) {
                // 只要它前面的元素比自己大，就后移
                if (arr[j] > temp) {
                    arr[j + 1] = arr[j];
                } else {
                    break;
                }
            }
            arr[j + 1] = temp;
        }
    }
}
