/**
 * 快速排序，据说是二十世纪发明的最伟大的排序算法，时间复杂度可以到 O(nlogn)
 * Created by liwei on 17/5/11.
 * 1、先写出框架
 * 2、然后递归调用
 */
public class QuickSort1 implements ISortAlgorithm {
    @Override
    public String getName() {
        return "快速排序";
    }

    @Override
    public void sort(int[] arr) {
        int len = arr.length;
        quickSort(arr, 0, len - 1);
    }

    /**
     * @param arr
     * @param left  可以取到
     * @param right 可以取到
     */
    private void quickSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int p = partition(arr, left, right);
        quickSort(arr, left, p - 1);
        quickSort(arr, p + 1, right);
    }

    /**
     * 将起始的元素作为标定点
     *
     * @param arr
     * @param left
     * @param right
     * @return
     */
    private int partition(int[] arr, int left, int right) {
        // 起始点不能设置为 left + 1，因为很有可能后面的元素都比它大
        // 在这种情况下，这一次 partition 不用交换元素的位置
        int i = left;
        int compared = arr[left];
        // 注意，这里取等号
        for (int j = left + 1; j <= right; j++) {
            if (arr[j] > compared) {
                // 放过什么都不做
            } else {
                // 交换当前元素与 i 的位置
                i++;
                swap(arr, j, i);
            }
        }
        // 最后这一步要记得交换标定点位置
        swap(arr, left, i);
        return i;
    }

    private void swap(int[] arr, int index1, int index2) {
        if (index1 == index2) {
            return;
        }
        int temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }

}
