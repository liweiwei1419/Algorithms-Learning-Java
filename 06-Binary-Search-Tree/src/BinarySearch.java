/**
 * 我们通过二分查找法体会处理边界问题和索引的指向的大体思路。
 *
 * @author Administrator
 */
public class BinarySearch {

    /**
     * 循环的二分查找法
     * 定义的边界不同，算法也就不同
     *
     * @param arr    注意：该数组一定得是有序数组，这一点是非常关键的
     * @param target 待查找的目标元素
     * @return 如果找到了，返回目标元素在数组中的索引，如果找不到，就返回 -1
     */
    public static int binarySearch(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1; // 我们的定义是在 [0,arr.length-1]，左闭右闭的区间里查找目标元素
        while (left <= right) {// 注意：这里是等于号，当 left == right 的时候，数组还有元素，还可以执行查找操作
            int mid = left + (right - left) / 2;
            if (target == arr[mid]) {
                return mid;
            } else if (target > arr[mid]) {
                left = mid + 1;
            } else { // target < arr[mid]
                right = mid - 1;
            }

        }
        // 走到这里是因为二分查找到数组的元素为空的情况下，即不满足 left <= right ，都没有找到目标元素，
        // 此时返回 -1
        return -1;
    }

    /**
     * 测试用例
     *
     * @param args
     */
    public static void main(String[] args) {
        int N = 100000; // 有序数组的元素个数
        int M = 100; // 测试用例的规模。即我要查找多少次
        int[] sortedArr = new int[N];
        for (int i = 0; i < N; i++) {
            sortedArr[i] = i;
        }
        for (int i = 0; i < M; i++) {
            int target = (int) (Math.random() * N);
            int index = binarySearch(sortedArr, target);
            if (target != index) {
                throw new RuntimeException("运行时异常");
            }
        }
        System.out.println("二分查找算法正确！");
    }
}
