public interface Queue<E> {

    // 入队
    void enqueue(E e);

    // 出队
    E dequeue();

    // 看一看队首的元素
    E getFront();

    // 得到队列中的元素的个数
    int getSize();

    // 队列是否为空
    boolean isEmpty();

}
