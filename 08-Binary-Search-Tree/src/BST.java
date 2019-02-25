import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 首先，我们先写出二分搜索树的框架。
 * 二分搜索树的元素是一个节点，这个节点我们不希望外界知道，所以定义成一个内部类。
 * Created by liwei on 17/5/29.
 */
public class BST {

    /**
     * 使用内部类来表示节点
     */
    private class Node {
        /**
         * 为了说明算法，我们将 key 和 value 设置成易于比较的 int 类型，设计成实现了 Comparable 接口的对象是更标准的做法
         */
        private int key;
        private int value;
        private Node left;
        private Node right;

        public Node(int key, int value) {
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
    private int count;

    /**
     * 默认构造一棵空的二分搜索树
     */
    public BST() {
        root = null;
        count = 0;
    }


    /**
     * 返回二分搜索树的节点个数
     * @return
     */
    public int size() {
        return count;
    }


    /**
     * 返回二分搜索树是否为空
     * @return
     */
    public boolean isEmpty() {
        return count == 0;
    }


    /**
     * 向一棵二分搜索树中插入一个节点
     * 千万不要忘记把返回值赋给 root ，这样才符合 root 的定义
     *
     * @param key
     * @param value
     */
    public void insert(int key, int value) {
        root = insert(root, key, value);
    }

    /**
     * 在以 node 为节点的二分搜索树中插入节点（key，value），
     * 向一个节点插入 key 和 value，看看放在左边还是放在右边，然后把插入以后形成的树的根节点返回
     * 注意这里的递归调用实现，初学的时候，不是很好理解。可以尝试从最最简单的情况开始分析。
     * （1）count++ 别忘了
     * （2）要把 node 返回回去
     * 牢牢记住：返回的是插入了新的节点的二分搜索树的根
     *
     * @param node
     * @param key
     * @param value
     * @return 插入了 （key，value） 的二分搜索树的根
     */
    private Node insert(Node node, int key, int value) {
        if (node == null) {
            count++;
            return new Node(key, value);
        }
        if (key == node.key) {
            // 如果 key 值重复，表示为更新
            node.value = value;
        } else if (key < node.key) {
            node.left = insert(node.left, key, value);
        } else {
            node.right = insert(node.right, key, value);
        }
        return node;
    }


    public boolean contain(int key) {
        return contain(root, key);
    }


    private boolean contain(Node node, int key) {
        // 首先我们要处理递归到底的情况
        if (node == null) {
            return false;
        }
        if (node.key == key) {
            return true;
        } else if (key < node.key) {
            return contain(node.left, key);
        } else {
            return contain(node.right, key);
        }
    }


    public int search(int key) {
        return search(root, key);
    }


    /**
     * 在以 node 为根的二叉搜索树中查找 key 所对应的 value
     *
     * @param node
     * @param key
     * @return
     */
    private Integer search(Node node, int key) {
        // 先处理递归到底的情况
        if (node == null) {
            return null;
        }
        if (node.key == key) {
            return node.value;
        } else if (key < node.key) {
            return search(node.left, key);
        } else {
            return search(node.right, key);
        }
    }


    /**
     * 二分搜索树的前序遍历
     */
    public void preOrder() {
        preOrder(root);
    }


    private void preOrder(Node node) {
        if (node != null) {
            System.out.printf("%s ", node.value);
            preOrder(node.left);
            preOrder(node.right);
        }
    }


    /**
     * 非递归的前序遍历实现
     */
    public void preOrder1() {
        Stack<Node> stack = new Stack<>();
        Node currentNode = root;

        Node tempNode;
        while (currentNode != null || !stack.isEmpty()) {
            while (currentNode != null) {
                System.out.printf("%s ", currentNode.key);
                stack.push(currentNode);
                currentNode = currentNode.left;
                // 等到左边全部遍历完成以后，stack 里面就存好了刚刚访问的顺序
                // 这些 Node 的左边节点全部访问过了
            }
            if (!stack.isEmpty()) {
                tempNode = stack.pop();
                currentNode = tempNode.right;
            }
        }
    }


    /**
     * 非递归的前序遍历实现2
     */
    public void preOrder2() {
        Stack<Node> stack = new Stack<>();
        stack.add(root);
        Node currentNode = null;
        while (!stack.isEmpty()) {
            currentNode = stack.pop();
            System.out.printf("%s ", currentNode.key);
            // 特别注意：应该先加入右边节点到栈中
            if (currentNode.right != null) {
                stack.add(currentNode.right);
            }
            if (currentNode.left != null) {
                stack.add(currentNode.left);
            }
        }
    }


    /**
     * 二分搜索树的中序遍历
     */
    public void inOrder() {
        inOrder(root);
    }


    private void inOrder(Node node) {
        if (node != null) {
            inOrder(node.left);
            System.out.printf("%s ", node.value);
            inOrder(node.right);
        }
    }


    public void inOrder1() {
        Stack<Node> stack = new Stack<>();
        Node currentNode = root;
        Node tempNode = null;

        while (currentNode != null || !stack.isEmpty()) {
            while (currentNode != null) {
                stack.add(currentNode);
                currentNode = currentNode.left;
            }
            if (!stack.isEmpty()) {
                tempNode = stack.pop();
                System.out.printf("%s ", tempNode.key);
                currentNode = tempNode.right;
            }
        }
    }


    /**
     * 二分搜索树的后序遍历
     */
    public void postOrder() {
        postOrder(root);
    }

    private void postOrder(Node node) {
        if (node != null) {
            postOrder(node.left);
            postOrder(node.right);
            System.out.println(node.value);
        }
    }


    /**
     * 借助队列不难完成广度优先遍历（层序遍历）
     */
    public void levelOrder() {
        Queue<Node> queue = new ArrayDeque<>();
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
     * 二分搜索树的广度优先遍历(层序遍历)
     */
    public void levelOrder1() {
        // 我们这里将 LinkedList 作为队列来使用
        LinkedList<Node> queue = new LinkedList<>();
        while (!queue.isEmpty()) {
            Node node = queue.remove();
            System.out.println(node.key + " " + node.value);
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
    }


    /**
     * 查找二分搜索树 key 的最小值
     * @return
     */
    public int minimum() {
        assert count != 0;
        Node node = minimum(root);
        return node.key;
    }


    private Node minimum(Node node) {
        if (node.left == null) {
            return node;
        }
        return minimum(node.left);
    }


    /**
     * 查找二分搜索树 key 的最大值
     * @return
     */
    public int maximum() {
        assert count != 0;
        Node node = maximum(root);
        return node.key;
    }


    private Node maximum(Node node) {
        if (node.right == null) {
            return node;
        }
        return maximum(node.right);
    }


    /**
     * 从二分搜索树中删除最小 key 所在的节点
     */
    public void removeMin() {
        if (root != null) {
            root = removeMin(root);
        }
    }

    /**
     * 特别注意的一点是：删除了一个节点以后，根元素很可能会发生变化，因此，算法设计的时候，一定要把根节点返回回去
     *
     * @param node
     * @return
     */
    private Node removeMin(Node node) {
        // 仔细体会这个过程
        if (node.left == null) {
            // 就是删除这个节点
            Node rightNode = node.right;
            // 因为左边已经是空了，要把右边释放掉
            node.right = null;
            count--;
            return rightNode;

        }
        node.left = removeMin(node.left);
        return node;
    }


    /**
     * 从二分搜索树中删除最大 key 所在的节点
     */
    public void removeMax() {
        if (root != null) {
            // 删除了最大元素以后的根节点很有可能不是原来的根节点
            // 所以一定要赋值回去
            root = removeMax(root);
        }
    }

    /**
     * 特别注意的一点是：删除了一个节点以后，根元素很可能会发生变化，因此，算法设计的时候，一定要把根节点返回回去
     *
     * @param node
     * @return
     */
    private Node removeMax(Node node) {
        if (node.right == null) {
            Node nodeLeft = node.left;
            node.left = null;
            count--;
            return nodeLeft;
        }
        node.right = removeMax(node.right);
        return node;
    }

    /**
     * 算法并不难理解，但是在编写的过程中有一些情况需要讨论清楚
     * 并且要注意一写细节，多写几遍就清楚了
     *
     * @param key
     */
    public void remove(int key) {
        root = remove(root, key);
    }

    private Node remove(Node node, int key) {
        if (node == null) {
            return null;
        }
        if (key < node.key) {
            // 这里要想清楚一个问题，删除以后的二分搜索树的根节点很可能不是原来的根节点
            node.left = remove(node.left, key);
            return node;
        } else if (key > node.key) {
            node.right = remove(node.right, key);
            return node;
        } else {
            // key == node.key
            if (node.left == null) {
                Node rightNode = node.right;
                node.right = null;
                count--;
                return rightNode;
            }

            if (node.right == null) {
                Node leftNode = node.left;
                node.left = null;
                count--;
                return leftNode;
            }
            // 当前 node 的后继
            Node successor = minimum(node.right);
            count++;// 下面删除了一个节点，所以要先加一下
            successor.right = removeMin(node.right);
            successor.left = node.left;
            node.left = null;
            node.right = null;
            count--;
            return successor;
        }
    }

    /**
     * 返回以 node 为根的二分搜索树中，小于等于 key 的最大值
     * @param node
     * @param key
     * @return
     */
    private Integer floor(Node node, int key) {
        if (node == null) {
            return null;
        }
        if (node.key == key) {
            return node.value;
        }
        if (key < node.key) {
            return floor(node.left, key);
        }
        Integer tempValue = floor(node.right, key);
        if (tempValue != null) {
            return tempValue;
        }
        return node.value;
    }

    /**
     * 返回以 node 为根的二分搜索树中，小于等于 key 的最大值
     * @param node
     * @param key
     * @return
     */
    private Integer ceiling(Node node, int key) {
        if (node == null) {
            return null;
        }
        if (key == node.key) {
            return node.value;
        }
        if (key > node.key) {
            return ceiling(node.right, key);
        }
        Integer tempValue = ceiling(node.left, key);
        if (tempValue != null) {
            return tempValue;
        }
        return node.value;
    }


    // 二分搜索树的前序、中序、后序遍历实现
    // 参考资料：http://www.cnblogs.com/hapjin/p/5679482.html
    // 参考资料：邓俊辉老师的算法课程

    public static void main(String[] args) {
        BST bst = new BST();
        bst.insert(28, 28);
        bst.insert(16, 16);
        bst.insert(30, 30);
        bst.insert(13, 13);
        bst.insert(22, 22);
        bst.insert(29, 29);
        bst.insert(42, 42);


        System.out.println("前序遍历");
        bst.preOrder();
        System.out.printf("\n非递归的前序遍历实现1：");
        bst.preOrder1();
        System.out.printf("\n非递归的前序遍历实现2：");
        bst.preOrder2();


//        System.out.println("中序遍历");
//        bst.inOrder();
//        System.out.println();
//        System.out.println("非递归的中序遍历实现1");
//        bst.inOrder1();

//        System.out.println("后序遍历");
//        bst.postOrder();

        // bst.levelOrder();
    }

}
