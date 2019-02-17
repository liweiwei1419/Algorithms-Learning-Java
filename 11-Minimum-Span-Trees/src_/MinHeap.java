import java.util.Arrays;

/**
 * Created by liwei on 17/6/16.
 */
public class MinHeap<T extends Comparable> {

    private T[] data;
    private int count;
    private int capacity;

    public MinHeap(int capacity) {
        // 因为这个最小索引堆是自己使用，所以我们将最小堆的内部的数组的记录从索引 1 开始
        data = (T[]) new Comparable[capacity + 1];
        count = 0;
        this.capacity = capacity;
    }

    /**
     * 通过给定一个数组构建最小堆
     *
     * @param arr
     */
    public MinHeap(T[] arr) {
        int len = arr.length;
        data = (T[]) new Comparable[len + 1];
        capacity = len;
        for (int i = 0; i < len; i++) {
            data[i + 1] = arr[i];
        }
        count = len;

        for (int i = count / 2; i >= 1; i--) {
            shiftDown(i);
        }
    }

    /**
     * 取左右孩子中最小的那个孩子进行交换
     * @param i
     */
    private void shiftDown(int i) {
        T temp = data[i];
        while (2 * i <= count) { // 有左边的孩子
            int k = 2 * i;
            if (k + 1 <= count && data[k + 1].compareTo(data[k]) < 0) {
                // 有右边的孩子，并且右边的孩子还要比左边的孩子小
                // 我们才取右边的孩子
                k = k + 1;
            }
            if (temp.compareTo(data[k]) > 0) { // 如果这个临时存起来的值比左右孩子中小的一个还要大，就把这个左右孩子中最小的元素上移
                data[i] = data[k] ;
                i = k;
            } else {

                break;

            }
        }
        data[i] = temp;

    }

    /**
     * 返回元素的个数
     *
     * @return
     */

    public int size() {
        return count;
    }

    /**
     * @return
     */
    public boolean isEmpty() {
        return count == 0;
    }

    /**
     *
     */
    public void insert(T t) {
        assert count + 1 <= capacity;
        data[count + 1] = t;
        count++;
        shiftUp(count);
    }


    private void shiftUp(int i) {
        T temp = data[i];
        while (i > 1 && data[i / 2].compareTo(temp) > 0) {
            data[i] = data[i / 2];
            i /= 2;
        }
        data[i] = temp;
    }


    public T extractMin() {
        assert count>0;
        T ret = data[1];
        data[1] = data[count];
        count--;
        shiftDown(1);
        return ret;
    }

    public static void main(String[] args) {
        int N = 10;
        MinHeap<Integer> minHeap = new MinHeap<>(N);
        for (int i = 0; i < N; i++) {
            minHeap.insert((int)(Math.random()*N));
        }
        System.out.println(Arrays.toString(minHeap.data));
        int[] sortedArray = new int[N];
        for (int i = 0; i < N; i++) {
            sortedArray[i] =minHeap.extractMin();
        }
        System.out.println(Arrays.toString(sortedArray));


        for (int i = 0; i < N-1; i++) {
            if(sortedArray[i]>sortedArray[i+1]){
                throw new RuntimeException("数组没有正确排序");
            }
        }

        /*int N = 5;
        MinHeap<Integer> minHeap = new MinHeap<>(N);
        minHeap.insert(9);
        System.out.println(Arrays.toString(minHeap.data));
        minHeap.insert(8);
        System.out.println(Arrays.toString(minHeap.data));
        minHeap.insert(7);
        System.out.println(Arrays.toString(minHeap.data));
        minHeap.insert(6);
        System.out.println(Arrays.toString(minHeap.data));
        minHeap.insert(5);
        System.out.println(Arrays.toString(minHeap.data));


        while (!minHeap.isEmpty()){
            Integer extractMin1 = minHeap.extractMin();
            System.out.println(extractMin1);
            System.out.println(Arrays.toString(minHeap.data));
        }*/


    }
}
