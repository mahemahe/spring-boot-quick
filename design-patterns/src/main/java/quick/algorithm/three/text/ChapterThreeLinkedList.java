package quick.algorithm.three.text;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 第三章 集合--LinkedList的实现逻辑
 * @author mahe <mahe@maihaoche.com>
 * @date 2020/2/12
 */
public class ChapterThreeLinkedList {

}
class MyLinkedList<T> implements Iterable<T> {
    private int size;
    private int modCount = 0;
    private Node<T> beginMarker;
    private Node<T> endMarker;

    /**
     * Homework 3.10 实现removeAll
     * 逻辑是，外层循环用items，获取每个data元素，对每个data元素，都对当前list进行一次循环比对
     * 所以运行时间估算是 O(N的平方) ，这个结果的前提是认为items的size N 与 当前list的size M 相近
     * 如果有两个size 相差较大，那 O ( M * N )
     *
     * LinkedList中的removeAll方法，是使用java.util.AbstractCollection#removeAll(java.util.Collection)，
     * 在次方法中，第二层循环是入参的items，外层循环是当前list，
     * @see java.util.LinkedList
     * @param items
     */
    public void removeAll(Iterable<? extends T> items) {
        Iterator<? extends T> itemsItr = items.iterator();
        Iterator<T> currentItr;
        while (itemsItr.hasNext()) {
            T expectedData = itemsItr.next();
            currentItr = this.iterator();
            while (currentItr.hasNext()) {
                if (currentItr.next().equals(expectedData)) {
                    currentItr.remove();
                    // 我的代码错误：这里不能break，如果认为数组中的元素非重才可以这样处理
//                    break;
                }
            }
        }
    }

    /**
     * Homework 3.3 实现MyLinkedList的contains例程
     * @param data
     * @param <T>
     * @return
     */
    public <T> boolean contains(T data) {
        Node<T> node = (Node<T>)beginMarker;
        while (endMarker != (node = node.next)) {
            if (data == node.data) {
                return true;
            }
        }

        return false;
    }

    /**
     * Homework 3.2
     * 通过只调整链（而不是数据）来交换两个相邻的元素，单向链表
     *
     * @param before 只能交换当前节点后面的两个节点
     * @param <T>
     */
    public <T> void swapSingleLinkedList(Node<T> before) {
        if (null == before.next || null == before.next.next) {
            return;
        }
        // 需要一个暂存变量
        Node current = before.next;

        // 进行交换
        before.next = current.next;
        current.next = before.next.next;
        before.next.next = current;

    }

    /**
     * Homework 3.2
     * 通过只调整链（而不是数据）来交换两个相邻的元素，双向链表
     * 与单向链表的区别
     *      1.是肯定有before和after节点，因为有 头节点 和 尾节点
     *      2.每个节点都有prev 和 next
     *      3.单向链表只能交换当前节点后面的两个节点，而双向链表可以交换当前节点与后面一个节点
     *
     * @param current
     * @param <T>
     */
    public <T> void swapDoubleLinkedList(Node<T> current) {
        if (endMarker == current.next) {
            return;
        }
        current.next.prev = current.prev;
        current.prev = current.next;
        current.next = current.next.next;
        current.prev.next = current;
    }

    private static class Node<T> {
        public T data;
        public Node<T> prev;
        public Node<T> next;
        public Node(T data, Node<T> prev, Node<T> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }

    /**
     * 书中示例的迭代器
     */
    private class LinkedListIterator implements Iterator<T> {
        private Node<T> current = beginMarker.next;
        private int expectedModCount = modCount;
        /**
         * 用于识别，当前是否处于可以进行数据移除操作的状态
         *
         * 由于逻辑上认为，数据链表起始于start位置，在调用next前，是不指向任何数据的，
         * 所以只有next执行后，当前位置才指向数据，remove是删除当前数据，这时才能删除
         *
         * 大体逻辑就是：next()方法保证当前位置有数据，remove负责删除当前位置数据
         * 所以remove后，当前是否有数据又是位置状态，所以okToRemove要变更为false
         */
        private boolean okToRemove = false;

        /**
         * Returns {@code true} if the iteration has more elements. (In other words, returns {@code true} if {@link
         * #next} would return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return current != endMarker;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public T next() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            T nextItem = current.data;
            current = current.next;
            okToRemove = true;
            return nextItem;
        }

        /**
         * Removes from the underlying collection the last element returned by this iterator (optional operation).  This
         * method can be called only once per call to {@link #next}.  The behavior of an iterator is unspecified if the
         * underlying collection is modified while the iteration is in progress in any way other than by calling this
         * method.
         *
         * @throws UnsupportedOperationException if the {@code remove} operation is not supported by this iterator
         * @throws IllegalStateException         if the {@code next} method has not yet been called, or the {@code
         *                                       remove} method has already been called after the last call to the
         *                                       {@code next} method
         * @implSpec The default implementation throws an instance of {@link UnsupportedOperationException} and performs
         * no other action.
         */
        @Override
        public void remove() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
            if (!okToRemove) {
                throw new IllegalStateException();
            }

            MyLinkedList.this.remove(current.prev);
            expectedModCount++;
            okToRemove = false;
        }
    }

    /**
     * 我认为的迭代器
     */
//    private class LinkedListIterator implements Iterator<T> {
//        private int expectedModCount;
//        private Node<T> current;
//        private int idx;
//
//        /**
//         * 构造方法
//         */
//        LinkedListIterator() {
//            this.idx = 0;
//            this.expectedModCount = MyLinkedList.this.modCount;
//            current = beginMarker.next;
//        }
//        /**
//         * 校验当前LinkedList的结构，是否与Iterator创建时的结构一致
//         */
//        private void checkModCount() {
//            if (expectedModCount != MyLinkedList.this.modCount) {
//                throw new ConcurrentModificationException();
//            }
//            return ;
//        }
//        /**
//         * Returns {@code true} if the iteration has more elements. (In other words, returns {@code true} if {@link
//         * #next} would return an element rather than throwing an exception.)
//         *
//         * @return {@code true} if the iteration has more elements
//         */
//        @Override
//        public boolean hasNext() {
//            return idx < size();
//        }
//
//        /**
//         * Returns the next element in the iteration.
//         *
//         * @return the next element in the iteration
//         * @throws NoSuchElementException if the iteration has no more elements
//         */
//        @Override
//        public T next() {
//            checkModCount();
//            if (null == current) {
//                throw new NoSuchElementException();
//            } else if (endMarker == current) {
//                throw new NoSuchElementException();
//            }
//            T data = current.data;
//            current = current.next;
//            idx++;
//            return data;
//        }
//
//        /**
//         * 移除的是当前元素的prev元素
//         * 因为本Iterator逻辑中，current元素相当于next后的位置，所以在remove当前元素时，要移除current对象的prev元素
//         * Removes from the underlying collection the last element returned by this iterator (optional operation).  This
//         * method can be called only once per call to {@link #next}.  The behavior of an iterator is unspecified if the
//         * underlying collection is modified while the iteration is in progress in any way other than by calling this
//         * method.
//         *
//         * @throws UnsupportedOperationException if the {@code remove} operation is not supported by this iterator
//         * @throws IllegalStateException         if the {@code next} method has not yet been called, or the {@code
//         *                                       remove} method has already been called after the last call to the
//         *                                       {@code next} method
//         * @implSpec The default implementation throws an instance of {@link UnsupportedOperationException} and performs
//         * no other action.
//         */
//        @Override
//        public void remove() {
//            if (0 == size()) {
//                throw new UnsupportedOperationException();
//            }
//            MyLinkedList.this.remove(current.prev);
//            expectedModCount++;
//
//            return;
//        }
//    }

    /**
     * 初始化方法
     */
    private void doClear() {
        beginMarker = new Node<>(null, null, null);
        endMarker = new Node<>(null, beginMarker, null);
        beginMarker.next = endMarker;

        size = 0;
        modCount++;
    }

    public MyLinkedList() {
        doClear();
    }

    public void clear() {
        doClear();
    }
    public int size() {
        return size;
    }
    public boolean isEmpty() {
        return size() == 0;
    }

    private void addBefore(Node<T> prev, T x) {
        prev.prev.next = prev.prev = new Node<>(x, prev.prev, prev);
        // 与上面一句，在连等式的逻辑中是等价的
        // prev.prev = prev.prev.next = new Node<>(x, prev.prev, prev);
        size++;
        modCount++;
    }
    /**
     * 对外提供的数据，只有Node中的data，Node对象是不会提供外部的
     * @param prev
     * @return
     */
    private T remove(Node<T> prev) {
        prev.prev.next = prev.next;
        prev.next.prev = prev.prev;
        size--;
        modCount++;

        return prev.data;
    }
    private Node<T> getNode(int idx) {
        return getNode(idx, 0, size() - 1);
    }
    /**
     * 看目标位置接近哪里，在前半部分，就从头开始；在后半部分就从尾开始
     * @param idx 目标位置
     * @param lower 最小序号位置
     * @param upper 最大序号位置
     * @return
     */
    private Node<T> getNode(int idx, int lower, int upper) {
        Node<T> result;
        if (idx < lower || idx > upper) {
            throw new IndexOutOfBoundsException();
        }

        if (idx < size() / 2) {
            result = beginMarker.next;
            for (int i = 0; i < idx; i++) {
                result = result.next;
            }
        } else {
            result = endMarker.prev;
            for (int i = size(); i > idx; i--) {
                result = result.prev;
            }
        }

        return result;
    }


    public void add(int idx, T x) {
        addBefore(getNode(idx, 0, size()), x);
    }
    public boolean add(T x) {
        add(size(), x);
        return true;
    }


    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }
}