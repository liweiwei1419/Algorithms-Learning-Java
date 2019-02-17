/**
 * 较好用的二分查找法模板
 *
 * @author Administrator
 */
public class BinarySearch3 {

    /**
     * @param arr
     * @param target
     * @return
     */
    public static int binarySearch(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;
        while (left < right) {
            int mid = left + (right - left + 1) / 2;

            // [2,3,4,5,6,7] 找 8
            if (arr[mid] <= target) {
                left = mid;
            } else { // arr[mid] > target
                // 右边界取不到，所以可以将右边界设置为 mid
                right = mid - 1;
            }

        }
        // 走到这里是因为二分查找到数组的元素为空的情况下，即不满足 left < right ，都没有找到目标元素，
        // 此时返回 -1

        if (target == arr[left]) {
            return left;
        }
        return -1;
    }

    /**
     * 测试用例
     *
     * @param args
     */
    public static void main(String[] args) {
        // 有序数组的元素个数
        int N = 100000;
        // 测试用例的规模。即我要查找多少次
        int M = 100;

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
