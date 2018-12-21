/**
 * 我们通过二分查找法体会处理边界问题和索引的指向的大体思路。
 *
 * @author Administrator
 */
public class BinarySearch3 {

    /**
     * 定义的边界不同，算法也就不同
     * 在 [left,right) 中实现二分查找，后续的代码只要稍作改动就可以实现
     *
     * @param arr
     * @param target
     * @return
     */
    public static int binarySearch(int[] arr, int target) {
        int left = 0;
        int right = arr.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (target == arr[mid]) {
                return mid;
            } else if (target > arr[mid]) {
                left = mid + 1;
            } else { // target < arr[mid]
                right = mid; // 右边界取不到，所以可以将右边界设置为 mid
            }

        }
        // 走到这里是因为二分查找到数组的元素为空的情况下，即不满足 left < right ，都没有找到目标元素，
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
        System.out.println("测试用例执行完毕！");
    }
}
