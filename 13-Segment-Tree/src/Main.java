/**
 * 线段树的测试用例
 *
 * @author liwei
 */
public class Main {
    public static void main(String[] args) {
        Integer[] nums = {0, -1, 2, 4, 2};
        SegmentTree<Integer> segmentTree = new SegmentTreeUp2Bottom<>(nums, (e1, e2) -> e1 + e2);
        System.out.println(segmentTree);
        int ret = segmentTree.sumRange(1, 4);
        System.out.println(ret);
        segmentTree.update(2, 10);
        // {0, -1, 10, 4, 2};
        ret = segmentTree.sumRange(1, 4);
        // 15
        System.out.println(ret);
    }
}
