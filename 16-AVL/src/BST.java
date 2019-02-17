import java.util.LinkedList;

/**
 * 标准的 BST 模板（Java 实现）
 * Created by liwei on 18/6/13.
 */
public class BST<K extends Comparable<K>, V> {

    /**
     * 使用内部类来表示节点
     */
    private class Node {
        private K key;
        private V value;
        private Node left;
        private Node right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }

        public Node(Node node) {
            this.key = node.key;
            this.value = node.value;
            this.left = node.left;
            this.right = node.right;
        }
    }

    /**
     * 根结点
     */
    private Node root;

    /**
     * 二分搜索树中的结点个数
     */
    private int size;

    /**
     * 默认构造一棵空的二分搜索树
     */
    public BST() {
        root = null;
        size = 0;
    }

    /**
     * 返回二分搜索树的节点个数
     *
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * 返回二分搜索树是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }


    /**
     * 向一棵二分搜索树中插入一个节点，千万不要忘记把返回值赋给 root ，这样才符合 root 的定义
     * 注意：体会这里使用辅助函数的作用，体会它是如何将新插入的结点挂接到原来的结点的（左）右子树中
     *
     * @param key
     * @param value
     */
    public void insert(K key, V value) {
        root = insert(root, key, value);
    }

    /**
     * 辅助函数：在以 node 为节点的二分搜索树中插入节点（key，value），
     * 向一个节点插入 key 和 value，看看放在左边还是放在右边，然后把插入以后形成的树的根节点返回
     * 注意这里的递归调用实现，初学的时候，不是很好理解。可以尝试从最最简单的情况开始分析。
     * （1）size++ 别忘了
     * （2）要把 node 返回回去
     * 牢牢记住：返回的是插入了新的节点的二分搜索树的根
     *
     * @param node
     * @param key
     * @param value
     * @return 插入了 （key，value） 的二分搜索树的根
     */
    private Node insert(Node node, K key, V value) {
        // 首先编写递归到底的情况，以避免在后续的逻辑中，做左右孩子结点非空的判断
        if (node == null) {
            size++;
            return new Node(key, value);
        }
        if (key.compareTo(node.key) == 0) {
            // 如果 key 值重复，表示为更新
            node.value = value;
        } else if (key.compareTo(node.key) < 0) {
            node.left = insert(node.left, key, value);
        } else {
            assert key.compareTo(node.key) > 0;
            node.right = insert(node.right, key, value);
        }
        // 【重要】最终要把根结点返回回去
        return node;
    }


    public boolean contains(K key) {
        return contains(root, key);
    }

    /**
     * contains 函数的辅助函数
     *
     * @param node
     * @param key
     * @return
     */
    private boolean contains(Node node, K key) {
        // 首先我们要处理递归到底的情况
        if (node == null) {
            return false;
        }
        if (node.key.compareTo(key) == 0) {
            return true;
        } else if (key.compareTo(node.key) < 0) {
            return contains(node.left, key);
        } else {
            assert key.compareTo(node.key) > 0;
            return contains(node.right, key);
        }
    }

    /**
     * getNode 就是一般意义上的 get，把 key 和 value 一起返回回去
     *
     * @param key
     * @return
     */
    public Node getNode(K key) {
        return getNode(root, key);
    }

    /**
     * getNode 函数的辅助函数
     * 在以 node 为根的二叉搜索树中查找 key 所对应的 value
     *
     * @param node
     * @param key
     * @return
     */
    private Node getNode(Node node, K key) {
        // 先处理递归到底的情况
        if (node == null) {
            return null;
        }
        if (node.key.compareTo(key) == 0) {
            return node;
        } else if (key.compareTo(node.key) < 0) {
            return getNode(node.left, key);
        } else {
            assert key.compareTo(node.key) > 0;
            return getNode(node.right, key);
        }
    }

    public V get(K key){
        Node node = getNode(root, key);
        return node == null ? null : node.value;
    }

    public void set(K key, V newValue){
        Node node = getNode(root, key);
        if(node == null){
            throw new IllegalArgumentException(key + " doesn't exist!");
        }
        node.value = newValue;
    }

    /**
     * 二分搜索树的前序遍历
     */
    public void preOrder() {
        preOrder(root);
    }

    /**
     * 二分搜索树的前序遍历的辅助函数
     *
     * @param node
     */
    private void preOrder(Node node) {
        if (node == null) {
            return;
        }
        System.out.printf("%s ", node.key);
        preOrder(node.left);
        preOrder(node.right);
    }


    /**
     * 二分搜索树的中序遍历
     */
    public void inOrder() {
        inOrder(root);
    }

    /**
     * 二分搜索树的中序遍历的辅助函数
     */
    private void inOrder(Node node) {
        if (node == null) {
            return;
        }
        inOrder(node.left);
        System.out.printf("%s ", node.key);
        inOrder(node.right);
    }


    /**
     * 二分搜索树的后序遍历
     */
    public void postOrder() {
        postOrder(root);
    }

    /**
     * 二分搜索树的后序遍历的辅助函数
     *
     * @param node
     */
    private void postOrder(Node node) {
        if (node == null) {
            return;
        }
        postOrder(node.left);
        postOrder(node.right);
        System.out.println(node.key);
    }

    /**
     * 借助队列不难完成广度优先遍历（层序遍历）
     */
    public void levelOrder() {
        // 我们这里将 LinkedList 作为队列来使用
        LinkedList<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            System.out.println(node.key);
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
    }


    /**
     * 查找二分搜索树 key 的最小值所在的结点
     *
     * @return
     */
    public Node minimum() {
        assert size != 0;
        Node node = minimum(root);
        return node;
    }

    /**
     * 返回以 node 为根的二分搜索树的 key 的最小值所在的结点
     *
     * @return
     */
    private Node minimum(Node node) {
        if (node.left == null) {
            return node;
        }
        return minimum(node.left);
    }


    /**
     * 查找二分搜索树 key 的最大值所在的结点
     *
     * @return
     */
    public Node maximum() {
        assert size != 0;
        Node node = maximum(root);
        return node;
    }

    /**
     * 返回以 node 为根的二分搜索树的 key 的最大值所在的结点
     *
     * @param node
     * @return
     */
    private Node maximum(Node node) {
        if (node.right == null) {
            return node;
        }
        return maximum(node.right);
    }


    /**
     * 从二分搜索树中删除最小 key 所在的结点
     */
    public void removeMin() {
        if (root != null) {
            root = removeMin(root);
        }
    }

    /**
     * 特别注意的一点是：删除了一个结点以后，
     * 根元素很可能会发生变化，
     * 因此，算法设计的时候，一定要把根节点返回回去
     *
     * @param node
     * @return
     */
    private Node removeMin(Node node) {
        // 仔细体会这个过程
        if (node.left == null) {
            // 如果这个结点的左孩子为空，要删除的就是这个结点
            Node rightNode = node.right;
            // 因为左边已经是空了，再把右边释放掉
            node.right = null;
            size--;
            // 让原来的右孩子作为根结点返回回去即可
            return rightNode;
        }
        // 在左结点不为空的情况下，继续递归删除以左孩子为根的子树的 key 的最小值所在结点
        node.left = removeMin(node.left);
        return node;
    }


    /**
     * 从二分搜索树中删除 key 的最大值所在的结点
     */
    public void removeMax() {
        if (root != null) {
            // 删除了最大元素以后的根节点很有可能不是原来的根节点
            // 所以一定要赋值回去
            root = removeMax(root);
        }
    }

    /**
     * 特别注意的一点是：删除了一个结点以后，根元素很可能会发生变化，
     * 因此，算法设计的时候，一定要把根结点返回回去
     *
     * @param node
     * @return
     */
    private Node removeMax(Node node) {
        // 如果右边孩子为 null，要删除的就是这个结点
        if (node.right == null) {
            Node nodeLeft = node.left;
            node.left = null;
            size--;
            return nodeLeft;
        }
        node.right = removeMax(node.right);
        return node;
    }

    /**
     * LeetCode 上就有关于 BST 删除结点的问题，所以一定要掌握，不是什么很难的问题
     * 算法并不难理解，但是在编写的过程中有一些情况需要讨论清楚
     * 并且要注意一写细节，多写几遍就清楚了
     *
     * @param key
     */
    public void remove(K key) {
        root = remove(root, key);
    }

    /**
     * remove 的辅助函数
     *
     * @param node
     * @param key
     * @return
     */
    private Node remove(Node node, K key) {
        if (node == null) {
            return null;
        }
        // 从简单到复杂逐个讨论下去
        if (key.compareTo(node.key) < 0) {
            // 这里要想清楚一个问题，删除以后的二分搜索树的根结点很可能不是原来的根结点
            // 所以，要通过这种方式把根结点挂接回去
            node.left = remove(node.left, key);
            return node;
        } else if (key.compareTo(node.key) > 0) {
            node.right = remove(node.right, key);
            return node;
        } else {
            // 当我们找到了要删除的结点的时候
            assert key.compareTo(node.key) == 0;
            // 画图
            if (node.left == null) {
                Node rightNode = node.right;
                node.right = null;
                size--;
                return rightNode;
            }
            // 注意：上面的 if 分支在函数体中 return 回去了
            // 所以，下面可以直接新开一个 if 语句，它们之间不不冲突，逻辑不会重复执行
            if (node.right == null) {
                Node leftNode = node.left;
                node.left = null;
                size--;
                return leftNode;
            }
            // 当前 node 的后继（也可以使用后继来代替它）
            Node successor = minimum(node.right);
            size++;// 下面删除了一个节点，所以要先加一下
            successor.right = removeMin(node.right);
            successor.left = node.left;
            node.left = null;
            node.right = null;
            size--;
            return successor;
        }
    }
}
