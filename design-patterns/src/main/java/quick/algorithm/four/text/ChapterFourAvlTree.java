package quick.algorithm.four.text;

import java.util.Comparator;

/**
 * 4.4 AVL树 带有平衡条件的二叉查找树
 * 平衡条件：每个节点的左右子树高度最多相差1
 * @author mahe <mahe@maihaoche.com>
 * @date 2020/3/5
 */
public class ChapterFourAvlTree {
    public static void main(String[] args) {

    }
}
class AvlTree<T > {
    /**
     * the maximum height difference allowed
     */
    private static final int ALLOWED_IMBALANCE = 1;
    private AvlNode<T> root;
    private Comparator<? super T> comparator;

    public void remove(T data) {
        if (null == data) {
            return;
        }
        root = remove(data, root);
    }

    /**
     * this method is likely to "quick.algorithm.four.text.BinarySearchTree#remove(java.lang.Object, quick.algorithm.four.text.BinarySearchTree.BinaryNode)"
     * replace only the one line of codes at the end of the method
     * @see BinarySearchTree
     */
    private AvlNode<T> remove(T data, AvlNode<T> current) {
        if (null == current) {
            return null;
        }
        int compareResult = myCompare(data, current.data);
        if (0 > compareResult) {
            current.left = remove(data, current.left);
        } else if (0 < compareResult) {
            current.right = remove(data, current.right);
        } else if (null != current.left && null != current.right) {
            // bingo and has two children nodes
            current.data = findMin(current.right).data;

            current.right = remove(current.data, current.right);
        } else {
            // bingo
            current = (null != current.left) ? current.left : current.right;
        }

        return balance(current);
    }

    public void insert(T data) {
        if (null == data) {
            return;
        }
        root = insert(data, root);
    }
    private AvlNode<T> insert(T data, AvlNode<T> current) {
        if (null == current) {
            return new AvlNode<>(data, null, null);
        }

        int compareResult = myCompare(data, current.data);
        if (0 > compareResult) {
            current.left = insert(data, current.left);
        } else if (0 < compareResult) {
            current.right = insert(data, current.right);
        } else {
            // duplicate; do nothing
        }

        return balance(current);
    }

    /**
     * rebalanced the tree below current node
     * First, calculate the height difference;
     * Second, rotation the subtree which breaking the rule (distinguish single and double rotation situation);
     * Third, calculate the current node's height.
     */
    private AvlNode<T> balance(AvlNode<T> current) {
        if (null == current) {
            return null;
        }

        // distinguish left and right subtree
        if (ALLOWED_IMBALANCE < height(current.left) - height(current.right)) {
            // distinguish single and double rotation
            // pay attention : it is ">=" instead of ">",
            // ">=" reason : in the subtree X, Y, Z,
            //      the removed motion could cause Z less than X or Y 2 height,
            //      X and Y maybe equals. (rotation is also effective in this situation)
            if (height(current.left.left) >= height(current.left.right)) {
                // single rotation situation
                current = rotationWithLeftChild(current);
            } else {
                // double rotation situation
                current = doubleWithLeftChild(current);
            }
        } else if (ALLOWED_IMBALANCE < height(current.right) - height(current.left)) {
            if (height(current.right.right) >= height(current.right.left)) {
                // single rotation situation
                current = rotationWithRightChild(current);
            } else {
                // double rotation situation
                current = doubleWithRightChild(current);
            }
        }

        // calculate the current node's height
        current.height = Math.max(height(current.left), height(current.right)) + 1;
        return current;
    }

    /**
     * a mirror method of the method "doubleWithLeftChild" below
     */
    private AvlNode<T> doubleWithRightChild(AvlNode<T> current) {
        current.right = rotationWithLeftChild(current.right);
        return rotationWithRightChild(current);
    }
    /**
     * double rotation method
     * execute the rotation method twice: first rotation k1 and k2; second rotation k2 and k3
     */
    private AvlNode<T> doubleWithLeftChild(AvlNode<T> current) {
        // current.left as k1; current.left.right as k2; current as k3
        current.left = rotationWithRightChild(current.left);
        return rotationWithLeftChild(current);
    }
    // my code is written below, not like the code in the textbook
//    private AvlNode<T> doubleWithLeftChild(AvlNode<T> current) {
//        // suppose newCurrent as the node k2
//        AvlNode<T> newCurrent = current.left.right;
//        // suppose newLeft as the node k1, and suppose current node as the node k3
//        AvlNode<T> newLeft = current.left;
//        current.left = newCurrent.right;
//        newLeft.right = newCurrent.left;
//        // recalculate the k1, k3 node's height;
//        newLeft.height = Math.max(height(newLeft.left), height(newLeft.right)) + 1;
//        current.height = Math.max(height(current.left), height(current.right)) + 1;
//        // relink k1, k2, k3
//        newCurrent.left = newLeft;
//        newCurrent.right = current;
//        return newCurrent;
//    }


    /**
     * a mirror method of the method "rotationWithLeftChild()" below
     */
    private AvlNode<T> rotationWithRightChild(AvlNode current) {
        AvlNode<T> newCurrent = current.right;
        current.right = newCurrent.left;
        newCurrent.left = current;
        current.height = Math.max(height(current.left), height(current.right)) + 1;
        newCurrent.height = Math.max(current.height, height(newCurrent.right)) + 1;
        return newCurrent;
    }
    /**
     * single rotation left subtree
     * First, use left subtree instead of current;
     * Second, exchange the relation of current and left subtree (which should be parent, another is son);
     * Third, give the left subtree's right subtree to current as a left subtree;
     * Fourth, recalculate the current node's height;
     * Fifth, return the new current node (which one is the left subtree before)
     */
    private AvlNode<T> rotationWithLeftChild(AvlNode<T> current) {
        AvlNode<T> newCurrent = current.left;
        current.left = newCurrent.right;
        newCurrent.right = current;
        current.height = Math.max(height(current.left), height(current.right)) + 1;
        // the codes below couldn't be removed,
        // as although that the newCurrent's height will be calculated at the end of the balance method,
        // but double rotation will execute the rotation method twice,
        // and the height's calculation method only will execute once
        newCurrent.height = Math.max(height(newCurrent.left), current.height) + 1;
        return newCurrent;
    }

    /**
     * empty tree's height is -1
     */
    private int height(AvlNode<T> current) {
        return null == current ? -1 : current.height;
    }

    private int myCompare(T dataA, T dataB) {
        if (null != comparator) {
            return comparator.compare(dataA, dataB);
        } else {
            return ((Comparable<? super T>) dataA).compareTo(dataB);
        }
    }

    /**
     * recursive way
     */
    private AvlNode<T> findMin(AvlNode<T> current) {
        if (null == current) {
            // this branch is only valid if the current is the root
            return null;
        } else if (null == current.left) {
            return current;
        } else {
            return findMin(current.left);
        }
    }

    private static class AvlNode<T> {
        T data;
        AvlNode<T> left;
        AvlNode<T> right;
        /**
         * current node's height (from current to the deepest leaf node)
         * height is 0 by default
         */
        int height;

        AvlNode(T data, AvlNode<T> left, AvlNode<T> right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }
}