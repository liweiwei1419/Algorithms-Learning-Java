/**
 * 合并排序、归并排序
 * 这一版实现的是自顶向下的归并排序
 * 这其中要使用到递归的算法
 * Created by liwei on 17/5/11.
 */
public class MergeSort implements ISortAlgorithm {

    @Override
    public String getName() {
        return "merge sort（归并排序）";
    }

    @Override
    public void sort(int[] arr) {
        int len = arr.length;
        mergeSort(arr, 0, len - 1);
    }

    private void mergeSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int mid = left + (right - left) / 2;
        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);
        mergeOfTwoArray(arr, left, mid, right);
    }

    // [4,5,6,7] left = 4,mid = 5
    // [0,1,2,3]
    // [left, mid] 有序
    // [mid + 1, right] 有序
    private void mergeOfTwoArray(int[] arr, int left, int mid, int right) {
        // 待优化的地方
        int[] temp = new int[right - left + 1];
        // 编辑器提示可以优化
        for (int i = 0; i < right - left + 1; i++) {
            temp[i] = arr[left + i];
        }
        int i = 0;
        int j = mid - left + 1;
        for (int k = 0; k < right - left + 1; k++) {
            if (i > mid - left) {
                arr[left + k] = temp[j];
                j++;
            } else if (j > right - left) {
                arr[left + k] = temp[i];
                i++;
            } else if (temp[i] < temp[j]) {
                // 取 i
                arr[left + k] = temp[i];
                i++;
            } else {
                arr[left + k] = temp[j];
                j++;
            }
        }
    }
}
