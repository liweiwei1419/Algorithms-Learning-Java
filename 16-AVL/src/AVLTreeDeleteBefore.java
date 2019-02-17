import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 基于 BST 增加了维护平衡的操作，于是得到了 AVL 树
 *
 * @param <K>
 * @param <V>
 * @author liwei 2018-06-13
 */
public class AVLTreeDeleteBefore<K extends Comparable<K>, V> {

    private class Node {
        private K key;
        private V value;
        private Node left;
        private Node right;
        /**
         * 添加了属性：高度
         */
        private int height;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
            // 默认起始的高度值为 1 ，很容易理解，单结点的高度就是 1
            this.height = 1;
        }

        public Node(Node node) {
            this.key = node.key;
            this.value = node.value;
            this.left = node.left;
            this.right = node.right;
        }
    }

    private Node root;

    private int size;

    public AVLTreeDeleteBefore() {
        root = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 获得结点 node 的高度
     *
     * @param node
     * @return
     */
    private int getHeight(Node node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    /**
     * 平衡因子，是根据高度即时计算出来的，不作为结点的属性
     *
     * @param node
     * @return
     */
    private int getBalanceFactor(Node node) {
        if (root == null) {
            return 0;
        }
        return getHeight(node.left) - getHeight(node.right);
    }

    public void add(K key, V value) {
        root = add(root, key, value);
    }

    private Node add(Node node, K key, V value) {
        if (node == null) {
            size++;
            return new Node(key, value);
        }
        if (key.compareTo(node.key) == 0) {
            node.value = value;
        } else if (key.compareTo(node.key) < 0) {
            node.left = add(node.left, key, value);
        } else {
            assert key.compareTo(node.key) > 0;
            node.right = add(node.right, key, value);
        }

        // 体会这一行代码：在递归函数中，从下至上（自底向上）地更新了影响到的结点的高度
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));

        // 计算一下平衡因子
        int balanceFactor = getBalanceFactor(node);
//        if (Math.abs(balanceFactor) > 1) {
//            System.out.println("该结点不平衡，平衡因子为：" + balanceFactor);
//        }

        // 在失衡的时候，做平衡维护

        // LL
        if (balanceFactor > 1 && getBalanceFactor(node.left) >= 0) {
            return rightRotate(node);
        }

        // RR
        if (balanceFactor < -1 && getBalanceFactor(node.right) <= 0) {
            return leftRotate(node);
        }

        // LR
        if (balanceFactor > 1 && getBalanceFactor(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // RL
        if (balanceFactor < -1 && getBalanceFactor(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;

    }


    /**
     * 检查是否满足 BST 的性质：利用 BST 中序遍历的有序性
     *
     * @return
     */
    public boolean isBST() {
        List<K> keys = new ArrayList<>();
        inOrderCheckBST(root, keys);
        for (int i = 1; i < keys.size(); i++) {
            if (keys.get(i - 1).compareTo(keys.get(i)) > 0) {
                return false;
            }
        }
        return true;
    }

    private void inOrderCheckBST(Node root, List<K> keys) {
        if (root == null) {
            return;
        }
        inOrderCheckBST(root.left, keys);
        keys.add(root.key);
        inOrderCheckBST(root.right, keys);
    }


    /**
     * 检查是否满足 AVL 的性质：利用前序遍历，逐个检查以当前 node 为根的子树是否满足 AVL 的性质
     *
     * @param node
     * @return
     */
    public boolean isBalanced(Node node) {
        if (node == null) {
            return true;
        }
        if (Math.abs(getBalanceFactor(node)) > 1) {
            return false;
        }
        // 注意：还要继续判断左右子树的平衡因子
        return isBalanced(node.left) && isBalanced(node.right);
    }


    /**
     * 辅助函数：右旋转，针对 LL 失衡（作为全过程）和 RL 失衡（作为子过程）
     * 右旋转示意图：
     *
     * @param node
     * @return
     */
    private Node rightRotate(Node node) {
        // 这一句完全可以不要，只是针对画出来的图，方便比对
        Node x = node;
        Node y = node.left;

        Node w = y.right;

        y.right = x;
        x.left = w;

        // 不要忘记重新计算高度差
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;

        return y;
    }

    /**
     * 辅助函数：左旋转，针对 RR 失衡（作为全过程）和 LR 失衡（作为子过程）
     * 右旋转示意图：
     *
     * @param node
     * @return
     */
    private Node leftRotate(Node node) {
        // 这一句完全可以不要，只是针对画出来的图，方便比对
        Node x = node;

        Node y = x.right;
        Node w = y.left;

        y.left = x;
        x.right = w;

        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;

        return y;
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

    public V get(K key) {
        Node node = getNode(root, key);
        return node == null ? null : node.value;
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
        // key.equals(node.key)
        if (node.key.compareTo(key) == 0) {
            return node;
        } else if (key.compareTo(node.key) < 0) {
            return getNode(node.left, key);
        } else {
            assert key.compareTo(node.key) > 0;
            return getNode(node.right, key);
        }
    }


    public void  set(K key,V newValue){
        Node node = getNode(root,key);
        if(node ==null){
            throw new IllegalArgumentException( key +  " 所在的结点没有找到。");
        }
        // 找到了就直接更新
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
