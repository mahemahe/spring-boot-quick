package quick.algorithm.four.homework;

import java.util.Comparator;
import java.util.Iterator;

/**
 * 4.11 program a piece of code that imitate TreeSet
 * The iterator method should use Binary Search Tree.
 * Try to make the Binary Search Tree to be an AVL Tree.(add rotation method)
 * @author mahe <mahe@maihaoche.com>
 * @date 2020/3/11
 */
public class ChapterFourQuestionEleven {

}
class MyTreeSet<T> implements Iterable {
    private BinaryNode<T> root;
    private int size;
    private int modCount;

    private Comparator<? super T> comparator;

    public void remove(T data) {
        root = remove(data, root, root.parent);
    }

    /**
     * to be fixed, this method has defect
     */
    private BinaryNode<T> remove(T data, BinaryNode<T> current, BinaryNode<T> parent) {
        if (null == current) {
            return null;
        }

        int compareResult = myComparator(data, current.data);
        if (compareResult < 0) {
            current.left = remove(data, current.left, current);
        } else if (compareResult > 0) {
            current.right = remove(data, current.right, current);
        } else if (current.left != null && current.right != null) {
            BinaryNode<T> substituteOne = findMin(current.right);
            if (parent.left == current) {
                parent.left.data = substituteOne.data;
            } else {
                parent.right.data = substituteOne.data;
            }
            if ((substituteOne.left = current.left) != null ) {
                substituteOne.left.parent.data = substituteOne.data;
            }
            if ((substituteOne.right = current.right) != null) {
                substituteOne.right.parent.data = substituteOne.data;
            }
            remove(substituteOne.data, substituteOne, substituteOne.parent);
            size--;
            modCount++;
        } else {
            if (current.left != null) {
                current.left.parent = current.parent;
                if (parent.left == current) {
                    parent.left = current.left;
                } else {
                    parent.right = current.left;
                }
                current = current.left;
            } else {
                current.right.parent = current.parent;
                if (parent.left == current) {
                    parent.left = current.right;
                } else {
                    parent.right = current.right;
                }
                current = current.right;
            }
            size--;
            modCount++;
        }

        return current;
    }

    private BinaryNode<T> findMin(BinaryNode<T> current) {
        if (null == current) {
            return null;
        }
        while (current.left != null) {
            current = current.left;
        }

        return current;
    }

    private void removeMin(BinaryNode<T> current) {
        current.parent.left = null;
    }

    public void add(T data) {
        root = add(data, root, null);
    }

    /**
     * have not rotation method
     */
    private BinaryNode<T> add(T data, BinaryNode<T> current, BinaryNode<T> parent) {
        if (null == current) {
            size++;
            modCount++;
            return new BinaryNode<>(data, parent);
        }

        int compareResult = myComparator(data, current.data);
        if (compareResult < 0) {
            current.left = add(data, current.left, current);
        } else if (compareResult > 0) {
            current.right = add(data, current.right, current);
        } else {
            // duplicate element do nothing
        }

        return current;
    }

    public int size() {
        return size;
    }
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator iterator() {
        return new BinaryTreeIterator();
    }

    private int myComparator(T a, T b) {
        if (null != comparator) {
            return comparator.compare(a, b);
        } else {
            return ((Comparable<? super T>)a).compareTo(b);
        }
    }

    public MyTreeSet(Comparator<? super T> comparator) {
        this();
        this.comparator = comparator;
    }

    public MyTreeSet() {
        this.root = null;
    }

    private class BinaryTreeIterator implements Iterator<T> {
        private int expectedModCount;
        private BinaryNode<T> current;
        /**
         * Returns {@code true} if the iteration has more elements. (In other words, returns {@code true} if {@link
         * #next} would return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return current == null;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public T next() {
            T data = current.data;
            return null;
        }

        public BinaryTreeIterator() {
            this.expectedModCount = modCount;
            this.current = findMin(root);
        }
    }

    private class BinaryNode<T> {
        T data;
        BinaryNode<T> parent;
        BinaryNode<T> left;
        BinaryNode<T> right;

        public BinaryNode(T data, BinaryNode<T> parent) {
            this.data = data;
            this.parent = parent;
        }
    }
}