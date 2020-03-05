package quick.algorithm.three.homework;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 11.假设单链表使用一个头节点实现，但无尾节点，并假设它只保留对该头节点的引用。
 * 编写一个类，包含如下方法：
 * a.返回链表大小的方法
 * b.打印链表的方法
 * c.测试值x是否含于链表的方法
 * d.如果值x尚未含于链表，添加值x到该链表的方法
 * e.如果值x含于链表，将x从该链表中删除的方法
 *
 * 12.保持其他条件不便，以排序的顺序重做11题的内容
 * @author mahe <mahe@maihaoche.com>
 * @date 2020/2/22
 */
public class ChapterThreeQuestionElevenAndTwelve {
    public static void main(String[] args) {
        MySingleLinkedList<Integer> list = new MySingleLinkedList<>();
        list.add(1);
        list.add(2);
        System.out.println(list);
    }
}
class MySingleSortLinkedList<T extends Comparable<? super T>> implements Iterable<T> {
    private Node<T> startMark;
    private int theSize;

    /**
     * 这个没变化
     * a.返回链表大小的方法
     */
    public int size() {
        return theSize;
    }
    public boolean isEmpty() {
        return 0 == theSize;
    }

    /**
     * 这个没变化
     * b.打印链表的方法
     */
    public void printList() {
        Node<T> current = startMark.next;
        while (current != null) {
            System.out.println(current.data.toString());
        }
    }

    /**
     * 这个也没什么变化
     * c.测试值x是否含于链表的方法
     */
    public boolean contains(T data) {
        Node<T> current = startMark.next;
        while (current != null) {
            if (current.data.equals(data)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     * 逻辑上，变化也不大，只不过这里的add是要将数据插入到相应的排序位置
     * 不是直接在头部/尾部加就行，这部的时间有所增加
     * d.如果值x尚未含于链表，添加值x到该链表的方法
     */
    public boolean addDifferent(T data) {
        if (contains(data)) {
            return false;
        }

        return add(data);
    }

    /**
     * 这个稍有不同，可以先compare一下，如果当前的数值小于current，那么就可以提前终止循环了，
     * 因为是按照从小到大的数据进行排序的
     * e.如果值x含于链表，将x从该链表中删除的方法
     * @param data
     */
    public void removeSame(T data) {
        Node<T> prev = startMark;
        Node<T> current = prev.next;
        while (current != null) {
            int compareResult = current.data.compareTo(data);
            if (compareResult < 0) {
                return;
            } else if (compareResult == 0) {
                prev.next = current.next;
                theSize--;
                return;
            }
            prev = current;
            current = current.next;
        }
        return;
    }

    /**
     * 添加方法：不添加同样的data，并按顺序排序(升序)
     * @param data
     * @return
     */
    public boolean add(T data) {
        Node<T> current = startMark.next;
        Node<T> prev = startMark;
        while (current != null) {
            // 如果当前值大于data，那么data要放到当前值之前
            if (current.data.compareTo(data) >= 0) {
                prev.next = new Node(data, current);
                theSize++;
                return true;
            }
            prev = current;
            current = current.next;
        }
        // 已经比对到最后的节点了，那么将data插入尾部即可
        prev.next = new Node(data, null);
        theSize++;
        return true;
    }


    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<T> iterator() {
        return null;
    }

    /**
     * 数据存储节点
     * @param <T>
     */
    private class Node<T> {
        public T data;
        public Node<T> next;

        public Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }
    }
}

class MySingleLinkedList<T> implements Iterable<T> {
    private Node<T> start;
    private int theSize = 0;
    /**
     * 表的结构状态值
     */
    private int modCount = 0;

    public MySingleLinkedList() {
        start = new Node<>(null, null);
    }

    /**
     * a.返回链表大小的方法
     */
    public int size() {
        return theSize;
    }
    public boolean isEmpty() {
        return 0 == theSize;
    }

    /**
     * b.打印链表的方法
     */
    public void printList() {
        Iterator<T> iterator = this.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    /**
     * c.测试值x是否含于链表的方法
     */
    public boolean contains(T data) {
        Node<T> current = start.next;
        while (current != null) {
            if (current.data.equals(data)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     * 我的答案（我对问题的理解有个差异，这里只说添加到链表，但未说添加到尾部还是头部，所以这个方法可以简写）
     * d.如果值x尚未含于链表，添加值x到该链表的方法
     */
    public void addDifferent(T data) {
        Node<T> current = start;
        while (current.next != null) {
            if (current.next.data.equals(data)) {
                return;
            }
            current = current.next;
        }
    }
    /**
     * 我参照书上答案的简写
     * d.如果值x尚未含于链表，添加值x到该链表的方法
     */
    public void addDifferent2(T data) {
        if (contains(data)) {
            return;
        } else {
            start.next = new Node<T>(data, start.next);
            theSize++;
            modCount++;
        }
    }

    /**
     * 书中答案的方法，相当于用两次循环，第一次用contains方法判断是否有，有的话再循环一次移除目标值
     * e.如果值x含于链表，将x从该链表中删除的方法
     */
    public void removeSame(T data) {
        Node<T> current = start;
        while (current.next != null) {
            if (current.next.data.equals(data)) {
                current.next = current.next.next;
                theSize--;
                modCount++;
                return;
            }
            current = current.next;
        }
    }

    /**
     * 由于只有start（头部）节点，所以add到第一个元素之前
     * @param data
     */
    public void add(T data) {
        start.next = new Node<T>(data, start.next);
    }
    /**
     * 由于只有start（头部）节点，所以remove只能移除第一个元素
     * @return
     */
    public T remove() {
        if (null == start.next) {
            throw new NoSuchElementException();
        }

        modCount++;
        theSize--;
        T data = start.next.data;
        start.next = start.next.next;
        return data;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<T> iterator() {
        return new SingleLinkedListIterator();
    }

    /**
     * 这里当前不记录cursor（光标）的位置
     */
    private class SingleLinkedListIterator implements Iterator<T> {
        // 表的iterator的结构状态值
        private int expectedModCount = modCount;
        private Node<T> current = start.next;

        public int size() {
            return MySingleLinkedList.this.size();
        }

        /**
         * Returns {@code true} if the iteration has more elements. (In other words, returns {@code true} if {@link
         * #next} would return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return null != current;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public T next() {
            if (null == current) {
                throw new NoSuchElementException();
            }
            T data = current.data;
            current = current.next;
            return data;
        }

        /**
         * 因为这里的remove是移除当前节点，而外部类移除的是第一个元素，所以不能用
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
            expectedModCount++;
            modCount++;
            theSize--;
            current.next = current.next.next;
        }
    }

    /**
     * 数据存储节点
     * @param <T>
     */
    private class Node<T> {
        public T data;
        public Node<T> next;

        public Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }
    }
}
