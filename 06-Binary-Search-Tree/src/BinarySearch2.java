/**
 * 二分查找法的非递归实现
 * Created by liwei on 17/5/29.
 */
public class BinarySearch2 {

    private static int binarySearch(int[] arr, int target) {
        int left = 0;  // 左边界
        int right = arr.length - 1; // 右边界
        int mid;
        // 通过 while 循环不断缩减搜索的区间端点，一次可以排除一大半
        while (left <= right) {
            mid = left + (right - left) / 2; // mid = (left + right) / 2;

            if (target == arr[mid]) {
                return mid;
            } else if (target < arr[mid]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1; // 不能形成区间的时候，说明没有找到，返回 -1 就好了
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        int target = 5;
        int ret = binarySearch(arr, target);
        System.out.printf("%d 位于索引 %d 上", target, ret);
    }
}
