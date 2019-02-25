/**
 * 插入排序的一个特点是：循环有终止的时候
 * Created by liwei on 17/5/10.
 */
public class InsertSortOptimize2 implements ISortAlgorithm {

    @Override
    public String getName() {
        return "insert sort";
    }

    @Override
    public void sort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int temp = arr[i];
            int j;
            for (j = i; j > 0; j--) {
                if (arr[j - 1] > temp) {
                    arr[j] = arr[j - 1];
                } else {
                    break;
                }
            }
            arr[j] = temp;
        }
    }
}
