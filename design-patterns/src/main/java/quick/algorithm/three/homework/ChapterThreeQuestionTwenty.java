package quick.algorithm.three.homework;
import java.util.ConcurrentModificationException;
import	java.util.LinkedList;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 3.20 不同于我们已经给出的删除方法，另一种是使用懒惰删除（lazy deletion）的删除方法。
 * 要删除一个元素，我们只是标记上该元素被删除（使用一个附加的位--bit域）。
 * 表中被删除和非被删除元素的个数作为数据结构的一部分被保留。如果被删除元素和非被删除元素一样多，
 * 则遍历整个表，对所有被标记的节点执行标准的删除算法。
 *
 * 我的思考：如果使用了懒惰删除方式，整个class要进行相应改造，改造后，可能造成下面的结果
 * 1. 拥有头/尾节点的LinkedList，改造后，get(int x)方法可能就不是很便捷了，原来的逻辑是，
 *      比对x与theSize/2的大小，判断数据在前/后半部分，相应的从头/尾节点开始查找x；
 *      但改造后，这个位置变得不那么准确了，可能造成反效果，但还是可以这样使用
 * @author mahe <mahe@maihaoche.com>
 * @date 2020/2/24
 */
public class ChapterThreeQuestionTwenty {

}
class MyLinkedList<T> implements Iterable<T> {
    private Node<T> startMark;
    private Node<T> endMark;
    private int theSize;
    private int theAllSize;
    private int modCount = 0;

    public int size() {
        return theSize;
    }
    public boolean isEmpty() {
        return theSize == 0;
    }

    public MyLinkedList() {
        doClear();
    }

    /**
     * 初始化链表
     */
    private void doClear() {
        startMark = new Node(null, null, null);
        endMark = new Node(null, startMark, null);
        startMark.next = endMark;
        theSize = 0;
        theAllSize = 0;
        modCount++;
    }

    public boolean addFirst(T data) {
        startMark.next.prev = startMark.next = new Node(data, startMark, startMark.next);
        theSize++;
        theAllSize++;
        modCount++;
        return true;
    }
    public boolean addLast(T data) {
        endMark.prev.next = endMark.prev = new Node(data, endMark.prev, endMark);
        theSize++;
        theAllSize++;
        modCount++;
        return true;
    }

    public boolean add(int idx, T data) {
        Node<T> current = find(idx);
        // 这句没有问题，利用了连等式的特性，在语句初始时会先拿到对应引用的句柄，然后再自右向左执行赋值
        current.prev.next = current.prev = new Node(data, current.prev,current);
        theSize++;
        theAllSize++;
        modCount++;
        return true;
    }

    /**
     * 找到idx位置的Node节点
     * @param idx
     * @return
     */
    private Node<T> find(int idx) {
        if (idx >= size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        Node<T> current;
        int cursor;
        if (idx < size() / 2) {
            // 前半部分从头开始
            cursor = -1;
            current = startMark;
            while (cursor < idx) {
                current = current.next;
                if (current.deleted) {
                    // 被删除了，就不移动光标位置
                    continue;
                }
                cursor++;
            }
        } else {
            // 后半部分从尾开始
            cursor = size();
            current = endMark;
            while (cursor > idx) {
                current = current.prev;
                if (current.deleted) {
                    continue;
                }
                cursor++;
            }
        }
        return current;
    }

    /**
     * 移除第一个元素
     * 要跳过已经标记为删除的元素，再进行删除；
     * 后面要执行一下校验是否执行实际删除动作
     */
    public void removeFirst() {
        remove(0);
    }
    /**
     * 移除最后一个元素
     * 要跳过已经标记为删除的元素，再进行删除；
     * 后面要执行一下校验是否执行实际删除动作
     */
    public void removeLast() {
        remove(size() - 1);
    }

    /**
     * 懒惰删除(Lazy Deletion)的实现过程
     * @param idx 位置
     */
    public void remove(int idx) {
        Node<T> current = find(idx);
        current.deleted = true;
        theSize--;
        modCount++;

        // 校验是否要执行实际删除动作
        if (checkThresholdReached()) {
            doTrueDeletion();
        }
    }

    /**
     * 执行实际的删除数据操作
     */
    private void doTrueDeletion() {
        Node current = startMark.next;
        while (current != endMark) {
            if (current.deleted) {
                current.prev.next = current.next;
                current.next.prev = current.prev;
                theAllSize--;
            }
            current = current.next;
        }
        modCount++;
    }
    /**
     * 检测当前表的有效值数量，是否不足当前真实数量的一半
     * @return
     */
    private boolean checkThresholdReached() {
        return theSize <= theAllSize / 2;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListItr();
    }

    private class LinkedListItr implements Iterator<T> {
        public int expectedModCount = modCount;
        public int cursor = 0;
        public Node<T> current;

        public LinkedListItr() {
            current = startMark;
        }

        /**
         * Returns {@code true} if the iteration has more elements. (In other words, returns {@code true} if {@link
         * #next} would return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return cursor < size();
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public T next() {
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            current = current.next;
            while (current != endMark) {
                if (current.deleted) {
                    current = current.next;
                } else {
                    break;
                }
            }
            cursor++;
            return current.data;
        }
    }

    private class Node<T> {
        public T data;
        public Node<T> next;
        public Node<T> prev;
        public boolean deleted;

        public Node(T data, Node<T> prev, Node<T> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
            deleted = false;
        }
    }
}