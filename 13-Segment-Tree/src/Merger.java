/**
 * 融合器接口
 *
 * @param <E>
 */
public interface Merger<E> {
    /**
     * 定义了两个对象的一个函数，返回一个对象，可以是加、减、乘、除、求最值等
     *
     * @param e1 对象 1
     * @param e2 对象 2
     * @return 对象 1 和对象 2 操作的结果
     */
    E merge(E e1, E e2);
}
