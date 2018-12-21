
import java.util.Arrays;

/**
 * Created by liwei on 17/6/11.
 */
public class BinarySearchTest {

    /**
     * 二分查找算法的递归实现
     */
    @Test
    public void test01() {
        /*int[] randomArray = SortTestHelper.generateRandomArray(100, 1, 100);
        SortTestHelper.testSortEfficiency(new BubbleSort(), randomArray);
        int searchNum = 76;
        System.out.println(Arrays.toString(randomArray));

        int index = binarySearch(searchNum, randomArray, 0, randomArray.length - 1);
        System.out.println("索引是 => " + index);
        System.out.println("查找的数是 => " + randomArray[index]);
        assert searchNum == randomArray[index];*/
    }

    /**
     * 递归实现
     * @param searchNum
     * @param arr
     * @param left
     * @param right
     * @return
     */
    private int binarySearch(int searchNum, int[] arr, int left, int right) {
        if (left >= right) {
            return -1;
        }
        int mid = left + (right - left) / 2;
        if (searchNum == arr[mid]) {
            return mid;
        }
        if (searchNum > arr[mid]) {
            return binarySearch(searchNum, arr, mid + 1, right);
        } else {
            return binarySearch(searchNum, arr, left, mid - 1);
        }
    }

    /**
     * 二分查找算法的循环实现
     */
    @Test
    public void test02() {
        /*int[] randomArray = SortTestHelper.generateRandomArray(100, 1, 100);
        SortTestHelper.testSortEfficiency(new BubbleSort(), randomArray);
        int searchNum = 76;
        System.out.println(Arrays.toString(randomArray));

        int index = binarySearch2(searchNum, randomArray, 0, randomArray.length - 1);

        System.out.println("索引是 => " + index);
        System.out.println("查找的数是 => " + randomArray[index]);
        assert searchNum == randomArray[index];*/
    }

    /**
     * @param searchNum
     * @param randomArray
     * @param left
     * @param right
     * @return
     */
    private int binarySearch2(int searchNum, int[] randomArray, int left, int right) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (searchNum == randomArray[mid]) {
                return mid;
            } else if (searchNum > randomArray[mid]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }
}
