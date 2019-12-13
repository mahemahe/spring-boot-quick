package quick.patterns.adapter;

import java.util.Enumeration;
import java.util.Iterator;

/**
 * 将Enumeration 转为 Iterator 适配器
 * @author mahe <mahe@maihaoche.com>
 * @date 2019-11-18
 */
public class EnumerationIteratorAdapter implements Iterator {
    private Enumeration enumeration;

    public EnumerationIteratorAdapter(Enumeration enumeration) {
        this.enumeration = enumeration;
    }

    /**
     * Returns {@code true} if the iteration has more elements. (In other words, returns {@code true} if {@link #next}
     * would return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    @Override
    public boolean hasNext() {
        return enumeration.hasMoreElements();
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws java.util.NoSuchElementException if the iteration has no more elements
     */
    @Override
    public Object next() {
        return enumeration.nextElement();
    }
}
