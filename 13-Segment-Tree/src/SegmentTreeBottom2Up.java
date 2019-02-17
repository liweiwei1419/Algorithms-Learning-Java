/**
 * @param <E>
 * @author liwei
 */
public class SegmentTreeBottom2Up<E> implements SegmentTree<E> {

    private E[] tree;
    private int len;
    private Merger<E> merger;

    public SegmentTreeBottom2Up(E[] arr, Merger<E> merger) {

        this.merger = merger;
        len = arr.length;
        tree = (E[]) new Object[len * 2];
        for (int i = len; i < 2 * len; i++) {
            tree[i] = arr[i - len];
        }
        for (int i = len - 1; i > 0; i--) {
            tree[i] = merger.merge(tree[2 * i], tree[2 * i + 1]);
        }
    }

    @Override
    public void update(int i, E val) {
        i += len;
        tree[i] = val;
        while (i > 0) {
            int left = i;
            int right = i;
            // i 是左边结点
            if (i % 2 == 0) {
                right = i + 1;
            } else {
                left = i - 1;
            }
            if (left == 0) {
                tree[i / 2] = tree[right];
            } else {
                tree[i / 2] = merger.merge(tree[left], tree[right]);

            }
            i /= 2;
        }
    }

    @Override
    public E sumRange(int i, int j) {
        i += len;
        j += len;
        E res = null;
        while (i <= j) {
            if (i % 2 == 1) {
                if (res == null) {
                    res = tree[i];
                } else {
                    res = merger.merge(res, tree[i]);
                }
                i++;
            }
            if (j % 2 == 0) {
                if (res == null) {
                    res = tree[j];
                } else {
                    res = merger.merge(res, tree[j]);
                }
                j--;
            }
            i /= 2;
            j /= 2;
        }
        return res;
    }
}
