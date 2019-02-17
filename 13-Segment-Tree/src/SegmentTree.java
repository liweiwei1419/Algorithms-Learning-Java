/**
 * 线段树抽象接口
 *
 * @param <E>
 */
public interface SegmentTree<E> {

    /**
     * 更新索引 i 的值为 val
     *
     * @param i 数组索引，从 0 开始
     * @param val 将要更新的值
     */
    void update(int i, E val);

    /**
     * 统计区间 [i, j] 之间的数据
     * 可以是计算加和、最值等
     *
     * @param i 区间左端点，包含
     * @param j 区间右端点，包含
     * @return
     */
    E sumRange(int i, int j);
}
