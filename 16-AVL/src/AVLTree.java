import javax.swing.tree.TreeNode;
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
public class AVLTree<K extends Comparable<K>, V> {

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

    public AVLTree() {
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
     * 判断该二叉树是否是一棵平衡二叉树
     *
     * @return
     */
    public boolean isBalanced() {
        return isBalanced(root);
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

    public void set(K key, V newValue) {
        Node node = getNode(root, key);
        if (node == null) {
            throw new IllegalArgumentException(key + " 所在的结点没有找到。");
        }
        // 找到了就直接更新
        node.value = newValue;
    }


    public Node minimum() {
        assert size != 0;
        Node node = minimum(root);
        return node;
    }

    private Node minimum(Node node) {
        if (node.left == null) {
            return node;
        }
        return minimum(node.left);
    }

    public Node maximum() {
        assert size != 0;
        Node node = maximum(root);
        return node;
    }

    private Node maximum(Node node) {
        if (node.right == null) {
            return node;
        }
        return maximum(node.right);
    }

    public void removeMin() {
        if (root != null) {
            root = removeMin(root);
        }
    }

    private Node removeMin(Node node) {
        if (node.left == null) {
            Node rightNode = node.right;
            node.right = null;
            size--;
            return rightNode;
        }
        node.left = removeMin(node.left);
        return node;
    }


    public void removeMax() {
        if (root != null) {
            root = removeMax(root);
        }
    }

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

    public void remove(K key) {
        root = remove(root, key);
    }

    private Node remove(Node node, K key) {
        if (node == null) {
            return null;
        }

        // 代码修改之处1：声明一个 retNode 结点，后序统一进行结点的平衡因子判定和维护
        Node retNode;
        if (key.compareTo(node.key) < 0) {
            node.left = remove(node.left, key);
            // 代码修改之处2：不马上返回，使用 retNode
            // 否则要在这里做结点的平衡因子判定和维护，后序一样，代码会非常冗长
            // return node
            retNode = node;
        } else if (key.compareTo(node.key) > 0) {
            node.right = remove(node.right, key);
            retNode = node;
        } else {
            assert key.compareTo(node.key) == 0;
            if (node.left == null) {
                Node rightNode = node.right;
                node.right = null;
                size--;
                retNode = rightNode;
                // 代码修改之处3：这几个分支之间是互斥的关系，所以要写成 if else 的形式
            } else if (node.right == null) {
                Node leftNode = node.left;
                node.left = null;
                size--;
                retNode = leftNode;
            } else {
                // 当前 node 的后继（也可以使用后继来代替它）
                Node successor = minimum(node.right);
                size++;
                // 代码修改之处4：removeMin 方法中没有结点的平衡因子判定和维护的逻辑，为此有两种方案，我们采用方案2
                // 方案1：在 removeMin 方法中添加结点的平衡因子判定和维护的逻辑
                // 方法2：递归调用自己，因为我们就是要在"自己"这个函数中，编写结点的平衡因子判定和维护的逻辑
                // successor.right = removeMin(node.right);
                successor.right = remove(node.right, successor.key);
                successor.left = node.left;
                node.left = null;
                node.right = null;
                size--;
                retNode = successor;
            }
        }

        // 代码修改之处5：我们代码的语义是删除结点，当删除的结点是叶子结点的时候，我们返回的是空结点
        // 那么在当前递归中，就不会有结点的平衡因子判定和维护的逻辑
        // 从后序的结点的平衡因子判定和维护的逻辑中，我们也可以看出，它会使用 retNode.left 和 retNode.right 进行左旋和右旋
        // 而对空节点进行左旋和右旋是没有意义的
        if (retNode == null) {
            return null;
        }
        // 代码修改之处6：添加结点的平衡因子判定和维护的逻辑，
        // 这里与 add 方法中的代码是一模一样的，只是针对的是结点 retNode（返回的子树的新的根结点）

        retNode.height = 1 + Math.max(getHeight(retNode.left), getHeight(retNode.right));

        // 计算一下平衡因子
        int balanceFactor = getBalanceFactor(retNode);
// 在失衡的时候，做平衡维护

        // LL
        if (balanceFactor > 1 && getBalanceFactor(retNode.left) >= 0) {
            return rightRotate(retNode);
        }

        // RR
        if (balanceFactor < -1 && getBalanceFactor(retNode.right) <= 0) {
            return leftRotate(retNode);
        }

        // LR
        if (balanceFactor > 1 && getBalanceFactor(retNode.left) < 0) {
            retNode.left = leftRotate(retNode.left);
            return rightRotate(retNode);
        }

        // RL
        if (balanceFactor < -1 && getBalanceFactor(retNode.right) > 0) {
            retNode.right = rightRotate(retNode.right);
            return leftRotate(retNode);
        }
        return retNode;
    }
}
