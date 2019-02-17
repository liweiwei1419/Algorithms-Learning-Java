/**
 * 二分查找法的递归实现 Created by liwei on 17/5/29.
 */
public class BinarySearch1 {

    /**
     * 在 arr 这个数组的 [left,right] 区间里查找元素，如果找到了返回下标，如果没有找到，就返回 -1 前提：这个数组必须是排好序的数组
     *
     * @param arr    待查找的已经排好序的数组，要求带查找的数组应该是排好序的
     * @param target 待查找的数
     * @param left
     * @param right  右边界，可以取到
     * @return
     */
    private static int binarySearch(int[] arr, int target, int left, int right) {
        // 先处理递归到底的情况
        if (left > right) {
            // 不能形成区间，返回 -1 表示没有找到
            return -1;
        }
        int mid = left + (right - left) / 2;
        if (target == arr[mid]) {
            // 找到了，就将目标元素的索引返回
            return mid;
        } else if (target < arr[mid]) {
            // 既然是有序数组，目标元素的值，比中间元素还要小，就应该在中间元素的左边去找
            return binarySearch(arr, target, left, mid - 1);
        } else {
            // 既然是有序数组，目标元素的值，比中间元素还要大，就应该在中间元素的右边去找
            return binarySearch(arr, target, mid + 1, right);
        }
    }

    public static void main(String[] args) {
        int[] arr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        int target = 7;
        int index = binarySearch(arr, target, 0, arr.length - 1);
        // 应该返回 7
        System.out.println(index);
    }
}
