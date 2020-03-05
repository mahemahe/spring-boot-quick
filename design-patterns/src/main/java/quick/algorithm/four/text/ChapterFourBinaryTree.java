package quick.algorithm.four.text;

import java.util.Comparator;

/**
 * 4.3 二叉树：查找书ADT--二叉查找树
 * 每个节点存储一项数据，且所存储的数据是可比较(可排序)的，
 * 左子树小于当前节点，右子树大于当前节点
 *
 * 4.6 再探树的遍历
 * @author mahe <mahe@maihaoche.com>
 * @date 2020/2/28
 */
public class ChapterFourBinaryTree {
    public static void main(String[] args) {
        int c = 1 != 0 ? 2 : 10;
        System.out.println(c);
    }
}
class BinarySearchTree<T> {
    /**
     * the root node
     */
    private BinaryNode<T> root;
    /**
     * 比较器（对于入参T的比较器）
     * 这个方式为书中的第二种方式，也是TreeMap中的方式
     */
    private Comparator<? super T> comparator;

    public BinarySearchTree() {
        makeEmpty();
    }
    public BinarySearchTree(Comparator<? super T> comparator) {
        makeEmpty();
        this.comparator = comparator;
    }

    /**
     * textbook 4.6
     * infix print the tree's nodes
     */
    public void printTree() {
        if (isEmpty()) {
            System.out.println("This is a empty tree!");
        } else {
            printTree(root);
        }
    }
    /**
     * infix print the current tree's nodes
     * print the nodes's data of the current tree
     */
    private void printTree(BinaryNode<T> current) {
        if (null == current) {
            return;
        }
        printTree(current.left);
        System.out.println(current.data);
        printTree(current.right);
    }

    /**
     * recursively
     * Condition 1, no left or right subtree, remove directly;
     * Condition 2, one left or right subtree,
     *      remove current and set the subtree as the current node;
     * Condition 3, both left and right subtrees,
     *      remove current and find the minimum node in the right subtree,
     *      then set that node as the current node
     */
    public void remove(T data) {
        if (null == data
                || null == root) {
            return;
        }
        root = remove(data, root);
    }

    /**
     * actual remove, recursively
     * First, find the node which element equals to data;
     * Second, find the node which can replace current node (according to that three conditions);
     * Third, remove the replaced node from previous position;
     * Fourth, replace the current node with the replaced node.
     *
     * We can replace only the element in the node, instead of replacing the node.
     * (reference case in the textbook)
     */
    private BinaryNode<T> remove(T data, BinaryNode<T> current) {
        // find the node which element equals to data
        if (null == current) {
            // didn't find
            return null;
        }
        int comparedResult = myCompare(data, current.data);
        if (0 > comparedResult) {
            current.left = remove(data, current.left);
        } else if (0 < comparedResult) {
            current.right = remove(data, current.right);
        } else if (null != current.left && null != current.right) {
            // bingo condition 3
            current.data = findMin(current.right).data;

            // use the method "removeMin" for improving efficiency
            current.right = removeMin(current.right);
//            current.right = remove(current.data, current.right);
        } else {
            // bingo condition 1,2
            current = (null != current.left) ? current.left : current.right;
        }

        return current;
    }
    /**
     * recursive method
     */
    private BinaryNode<T> removeMin(BinaryNode<T> current) {
        if (null != current.left) {
            current.left = removeMin(current.left);
        } else {
            // no one is less than current, remove current
            current = current.right;
        }
        return current;
    }
//    private BinaryNode<T> remove(T data, BinaryNode<T> current) {
//        // find the node which element equals to data
//        if (null == current) {
//            // didn't find
//            return null;
//        }
//        int comparedResult = myCompare(data, current.data);
//        if (0 > comparedResult) {
//            current.left = remove(data, current.left);
//            return current;
//        } else if (0 < comparedResult) {
//            current.right = remove(data, current.right);
//            return current;
//        } else {
//            // bingo
//
//            // the code below can be simplified
//        }
//
//        // find the node which can replace current node (according to that three conditions)
//        if (null == current.left
//                && null == current.right) {
//            // the current node is a leaf node, remove directly
//            return null;
//        } else if (null == current.left
//                || null == current.right) {
//            // have one subtree, set that subtree node which is not null as the current node
//            return null == current.left ? current.right : current.left;
//        } else {
//            // have two subtrees, find the minimum node in the right subtree and remove it
//            BinaryNode<T> replacedOne = findMin(current.right);
//            replacedOne.right = remove(replacedOne.data, current.right);
//            replacedOne.left = current.left;
//            return replacedOne;
//        }
//    }

    /**
     * insert data to tree
     */
    public void insert(T data) {
        if (null == data) {
            throw new NullPointerException();
        }
        insert(data, root);
    }
    /**
     * actually insert (recursively)
     * A node will be created what if the current node is null,
     * otherwise, return the current node
     */
    private BinaryNode<T> insert(T data, BinaryNode<T> current) {
        if (null == current) {
            return new BinaryNode<>(data);
        }
        int comparedResult = myCompare(data, current.data);
        if (comparedResult < 0) {
            current.left = insert(data, current.left);
        } else if (comparedResult > 0) {
            current.right = insert(data, current.right);
        } else {
            // duplicate, do nothing
        }

        return current;
    }

    /**
     * recursive way
     */
    private BinaryNode<T> findMin(BinaryNode<T> current) {
        if (null == current) {
            // this branch is only valid if the current is the root
            return null;
        } else if (null == current.left) {
            return current;
        } else {
            return findMin(current.left);
        }
    }
    /**
     * non-recursive way
     */
    private BinaryNode<T> findMax(BinaryNode<T> current) {
        if (null != current) {
            while (null != current.right) {
                current = current.right;
            }
        }
        return current;
    }

    public boolean contains(T data) {
        return contains(data, root);
    }
    public boolean contains(T data, BinaryNode<T> current) {
        if (null == data) {
            return false;
        }
        if (null == current) {
            return false;
        }

        int compareResult = myCompare(data, current.data);
        if (compareResult < 0) {
            // data less than current, compare to left then
            return contains(data, current.left);
        } else if (compareResult > 0) {
            // data greater than current, compare to right then
            return contains(data, current.right);
        } else {
            // match
            return true;
        }
    }
    /**
     * If there is a comparator, then use it;
     * otherwise, let dataA extends Comparable interface
     */
    private int myCompare(T dataA, T dataB) {
        if (comparator != null) {
            return comparator.compare(dataA, dataB);
        } else {
            return ((Comparable<? super T>)dataA).compareTo(dataB);
        }
    }

    /**
     * 包含的比较（这里采用递归方式，按照书中给出的方式；可以改成while循环的方式，如 current = current.left; continue;）
     * 比较规则按照树的特性，left < current < right 进行比较
     * 因为入参要求 T extends Comparable<? super T>，所以可以直接调用 T 的 compareTo 方法
     */
//    public boolean contains(T data, BinaryNode<T> current) {
//        if (null == data) {
//            return false;
//        }
//        if (null == current) {
//            return false;
//        }
//
//        int compareResult = data.compareTo(current.data);
//        if (compareResult < 0) {
//            // data less than current, compare to left then
//            return contains(data, current.left);
//        } else if (compareResult > 0) {
//            // data greater than current, compare to right then
//            return contains(data, current.right);
//        } else {
//            // match
//            return true;
//        }
//    }

    public void makeEmpty() {
        root = null;
    }
    public boolean isEmpty() {
        return root == null;
    }

    private static class BinaryNode<T> {
        T data;
        BinaryNode<T> left;
        BinaryNode<T> right;

        BinaryNode(T data) {
            this(data, null, null);
        }
        BinaryNode(T data, BinaryNode<T> left, BinaryNode<T> right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }
}