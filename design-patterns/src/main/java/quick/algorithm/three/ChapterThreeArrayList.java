package quick.algorithm.three;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * 第三章 集合--ArrayList的实现逻辑
 * @author mahe <mahe@maihaoche.com>
 * @date 2020/2/11
 */
public class ChapterThreeArrayList {

    public static void main(String[] args) {
        testMyArray();
    }

    public static void testCreateArray() {
        String[] abc = new String[3];
        abc[0] = "a";
        abc[1] = "b";
        abc[2] = "c";
        // 下面这行会报错，ClassCastException: [Ljava.lang.Object; cannot be cast to [Ljava.lang.String;
        String[] newAbc = ArrayTest.simpleCopyOf(abc, 10, abc.getClass());
        System.out.println(newAbc);
    }

    public static void testMyArray() {
        MyArrayList<String> my = new MyArrayList<>();
        boolean c = my.add("c");
        Iterator<String> iterator = my.iterator();
        System.out.println(iterator.hasNext());
        System.out.println(c);
    }
}
/**
 * 该类，是按照书中第三章的 图3-15的 MyArrayList来写的
 *
 * 这个类有个问题就是，在类中，存储数据的数组 items 的类型  始终是 Object[] 类型
 * 在实际的ArrayList中，先对传入类型T[] 与 Object[]进行比较，如果相当，那就直接new Object[]；
 * 否则会调用 Array的 newArray方法，是一个native方法，传入数组中元素的类别 T 和数组长度
 * @param <T>
 */
class MyArrayList<T> implements Iterable<T> {

    private static final int DEFAULT_CAPACITY = 10;
    private int theSize;
    private T[] items;
    public MyArrayList() {
        doClear();
    }
    private void doClear() {
        theSize = 0;
        ensureCapacity(DEFAULT_CAPACITY);
    }

    public void ensureCapacity(int newCapacity) {
        if (newCapacity < theSize) {
            return;
        }
        T[] old = items;
        items = (T[]) new Object[newCapacity];
        for (int i = 0; i < size(); i++) {
            items[i] = old[i];
        }
    }

    public int size() {
        return theSize;
    }

    public T set(int idx, T newVal) {
        if (idx < 0 || idx >= size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        T old = items[idx];
        items[idx] = newVal;
        return old;

    }

    public boolean add(T x) {
        add(size(), x);
        return true;
    }

    public void add(int idx, T x) {
        if (items.length == size()) {
            ensureCapacity(size() * 2 + 1);
        }
        for (int i = theSize; i > idx; i--) {
            items[i] = items[i - 1];
        }
        items[idx] = x;
        theSize++;
    }

    private class ArrayListIterator implements Iterator<T> {
        private int current;
        /**
         * Returns {@code true} if the iteration has more elements. (In other words, returns {@code true} if {@link
         * #next} would return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            int a = this.current;
            int b = MyArrayList.this.size();
            System.out.println("a :" + a + ", b :" + b);
            return current < size();
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public T next() {
            if (current >= size()) {
                throw new NoSuchElementException();
            }
            return items[current++];
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

        }
    }
    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<T> iterator() {
        return new ArrayListIterator();
    }

    /**
     * Performs the given action for each element of the {@code Iterable} until all elements have been processed or the
     * action throws an exception.  Unless otherwise specified by the implementing class, actions are performed in the
     * order of iteration (if an iteration order is specified).  Exceptions thrown by the action are relayed to the
     * caller.
     *
     * @param action The action to be performed for each element
     * @throws NullPointerException if the specified action is null
     * @implSpec <p>The default implementation behaves as if:
     * <pre>{@code
     *     for (T t : this)
     *         action.accept(t);
     * }</pre>
     * @since 1.8
     */
    @Override
    public void forEach(Consumer<? super T> action) {

    }

    /**
     * Creates a {@link Spliterator} over the elements described by this {@code Iterable}.
     *
     * @return a {@code Spliterator} over the elements described by this {@code Iterable}.
     * @implSpec The default implementation creates an
     * <em><a href="Spliterator.html#binding">early-binding</a></em>
     * spliterator from the iterable's {@code Iterator}.  The spliterator inherits the <em>fail-fast</em> properties of
     * the iterable's iterator.
     * @implNote The default implementation should usually be overridden.  The spliterator returned by the default
     * implementation has poor splitting capabilities, is unsized, and does not report any spliterator characteristics.
     * Implementing classes can nearly always provide a better implementation.
     * @since 1.8
     */
    @Override
    public Spliterator<T> spliterator() {
        return null;
    }

}
class ArrayTest {

    /**
     * Array.copyOf 方法
     * Class的getComponentType，返回的是array中的元素Class，如果不是array，返回null
     * @param original
     * @param newLength
     * @param newType
     * @param <T>
     * @param <U>
     * @return
     */
    public static <T,U> T[] copyOf(U[] original, int newLength, Class<? extends T[]> newType) {
        T[] copy = ((Object)newType == (Object)Object[].class)
                ? (T[]) new Object[newLength]
                : (T[]) Array.newInstance(newType.getComponentType(), newLength);
        System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
        return copy;
    }

    public static <T,U> T[] simpleCopyOf(U[] original, int newLength, Class<? extends T[]> newType) {
        T[] copy = (T[]) new Object[newLength];
        System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
        return copy;
    }
}
