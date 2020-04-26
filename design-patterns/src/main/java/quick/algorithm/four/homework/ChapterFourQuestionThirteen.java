package quick.algorithm.four.homework;

/**
 * Homework 4.13
 * @author mahe <mahe@maihaoche.com>
 * @date 2020/3/17
 */
public class ChapterFourQuestionThirteen {
    public static void main(String[] args) {
        MyTreeSet2<Integer> tree = new MyTreeSet2<>();
        tree.insert(10);
        tree.insert(5);
        tree.insert(15);
        tree.insert(3);
        tree.insert(4);
        tree.insert(8);
        tree.insert(7);
        tree.insert(9);
        System.out.println("end");
    }
}
class UnderflowException extends Exception { }

/**
 * The answer book's result (not mine)
 * @param <T>
 */
class MyTreeSet2<T extends Comparable<? super T>> {
    /**
     * set 0 default
     */
    int modCount = 0;
    private BinaryNode<T> root;

    /**
     * constructor
     */
    public MyTreeSet2() {
        root = null;
    }

    public java.util.Iterator<T> iterator() {
        return new MyTreeSet2Iterator();
    }

    public void makeEmpty() {
        modCount++;
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public boolean contains(T x) {
        return contains(x, root);
    }

    public T findMin() throws UnderflowException {
        if (isEmpty()) {
            throw new UnderflowException();
        } else {
            return findMin(root).element;
        }
    }

    public T findMax() throws UnderflowException {
        if (isEmpty()) {
            throw new UnderflowException();
        } else {
            return findMax(root).element;
        }
    }

    public void insert(T x) {
        root = insert(x, root, null, null);
    }

    public void remove(T x) {
        root = remove(x, root);
    }

    public void printTree() {
        if (isEmpty()) {
            System.out.println("Empty tree");
        } else {
            printTree(root);
        }
    }

    private void printTree(BinaryNode<T> t) {
        if (t != null) {
            printTree(t.left);
            System.out.println(t.element);
            printTree(t.right);
        }
    }

    private boolean contains(T x, BinaryNode<T> t) {
        if (t == null) {
            return false;
        }
        int compareResult = x.compareTo(t.element);
        if (compareResult < 0) {
            return contains(x, t.left);
        } else if (compareResult > 0) {
            return contains(x, t.right);
        } else {
            // match
            return true;
        }
    }

    private BinaryNode<T> findMin(BinaryNode<T> t) {
        if (t == null) {
            return null;
        } else if (t.left == null) {
            return t;
        }
        return findMin(t.left);
    }

    private BinaryNode<T> findMax(BinaryNode<T> t) {
        if (t == null) {
            return null;
        } else if (t.right == null) {
            return t;
        }
        return findMax(t.right);
    }

    private BinaryNode<T> insert(T x, BinaryNode<T> t,
            BinaryNode<T> nt, BinaryNode<T> pv) {
        if (t == null) {
            modCount++;
            BinaryNode<T> newNode = new BinaryNode<T>(x, null, null, nt, pv);
            if (nt != null) {
                nt.prev = newNode;
            }
            if (pv != null) {
                pv.next = newNode;
            }
            return newNode;
        }
        int compareResult = x.compareTo(t.element);
        if (compareResult < 0) {
            t.left = insert(x, t.left, t, pv);
        } else if (compareResult > 0) {
            t.right = insert(x, t.right, nt, t);
        } else {
            ; // duplicate
        }
        return t;
    }

    private BinaryNode<T> remove(T x, BinaryNode<T> t) {
        if (t == null) {
            // not found
            return null;
        }
        int compareResult = x.compareTo(t.element);
        if (compareResult < 0) {
            t.left = remove(x, t.left);
        } else if (compareResult > 0) {
            t.right = remove(x, t.right);
        } else if (t.left != null && t.right != null) {
            // two children
            t.element = findMin(t.right).element;
            t.right = remove(t.element, t.right);
        } else {
            modCount++;
            // update next and prev links t.next.prev = t.prev;
            t.prev.next = t.next;
            t = (t.left != null) ? t.left : t.right;
        }
        return t;
    }

    private static class BinaryNode<AnyType> {

        AnyType element;
        BinaryNode<AnyType> left;
        BinaryNode<AnyType> right;
        BinaryNode<AnyType> next;
        BinaryNode<AnyType> prev;
        BinaryNode(AnyType theElement) {
            this(theElement, null, null, null, null);
        }
        BinaryNode(AnyType theElement,
                BinaryNode<AnyType> lt, BinaryNode<AnyType> rt,
                BinaryNode<AnyType> nt, BinaryNode<AnyType> pv) {
            element = theElement;
            left = lt;
            right = rt;
            next = nt;
            prev = pv;
        }
    }

    /**
     * custom iterator
     */
    private class MyTreeSet2Iterator implements java.util.Iterator<T> {

        private BinaryNode<T> current = findMin(root);
        private BinaryNode<T> previous;
        private int expectedModCount = modCount;
        private boolean okToRemove = false;
        private boolean atEnd = false;

        @Override
        public boolean hasNext() {
            return !atEnd;
        }

        @Override
        public T next() {
            if (modCount != expectedModCount) {
                throw new java.util.ConcurrentModificationException();
            }
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            T nextItem = current.element;
            previous = current;
            current = current.next;
            if (current == null) {
                atEnd = true;
            }
            okToRemove = true;
            return nextItem;
        }

        @Override
        public void remove() {
            if (modCount != expectedModCount) {
                throw new java.util.ConcurrentModificationException();
            }
            if (!okToRemove) {
                throw new IllegalStateException();
            }
            MyTreeSet2.this.remove(previous.element);
            okToRemove = false;
        }
    }
}