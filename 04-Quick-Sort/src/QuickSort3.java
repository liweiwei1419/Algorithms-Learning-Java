import java.util.Arrays;
import java.util.Random;

public class QuickSort3 {

    private static Random random = new Random();

    private void sort(int[] arr) {
        int len = arr.length;
        quickSort(arr, 0, len - 1);
    }

    // 3, 4, 3, 6, 3, 2, 1, 8, 5, 7
    // [left+1,l] 是小于 p 的部分
    // [l+1, i) 是等于 p 的部分
    // [r,right] 是大于 p 的部分

    private void quickSort(int[] arr, int left, int right) {
        if (right - left <= 15) {
            insertSort(arr, left, right);
            return;
        }
        // [3,4,5]
        int randonIndex = left + random.nextInt(right - left + 1);
        swap(arr, randonIndex, left);
        int p = arr[left];
        int l = left;
        int r = right + 1;
        for (int i = left; i < r;) {
            if(arr[i]<p){
                l++;
                swap(arr,i,l);
                i++;
            }else if(arr[i]==p){
                i++;
            }else {
                r--;
                swap(arr,i,r);
            }
        }
        swap(arr,left,l);
        quickSort(arr, left, l - 1);
        quickSort(arr, r, right);
    }


    private void insertSort(int[] arr, int left, int right) {
        // 第 1 遍不用插入，所以是总长度减去 1
        for (int i = left + 1; i <= right; i++) {
            int temp = arr[i];
            int j;
            for (j = i - 1; j >= left; j--) {
                // 后移一位
                if (arr[j] > temp) {
                    arr[j + 1] = arr[j];
                } else {
                    break;
                }
            }
            arr[j + 1] = temp;
        }
    }


    private void swap(int[] data, int index1, int index2) {
        if (index1 == index2) {
            return;
        }
        int temp = data[index1];
        data[index1] = data[index2];
        data[index2] = temp;
    }


    public static void main(String[] args) {
        int[] nums = new int[]{3, 4, 6, 2, 1, 8, 5, 7, 10, 9, 12, 200};
        QuickSort3 quickSort3 = new QuickSort3();
        quickSort3.sort(nums);
        System.out.println(Arrays.toString(nums));
    }
}
