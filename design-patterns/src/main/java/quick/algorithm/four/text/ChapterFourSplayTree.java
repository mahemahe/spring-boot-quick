package quick.algorithm.four.text;

import com.google.gson.Gson;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * 伸展树--Splay Tree
 * @author mahe <mahe@maihaoche.com>
 * @date 2020/3/5
 */
public class ChapterFourSplayTree {

    public static void main(String[] args) {
        testTree();
    }

    public static void testTree() {
        Map<Integer, String> tree = new TreeMap<>();
        tree.put(10, "ten");
        tree.put(5, "five");
        tree.put(8, "eight");
        tree.put(4, "four");
        tree.put(15, "fifteen");
        tree.put(30, "thirty");
        tree.put(12, "twelve");
        System.out.println(new Gson().toJson(tree));
    }

    public static void testNull() {
        Set<String> set = new HashSet<>();
        set.add("abc");
        set.add(null);
        System.out.println(set.size());
    }

    public static void test() {
        Map<String, String> map = new TreeMap<>(new CaseInsensitiveCompare());
        map.put("ABC", "222");
        map.put("abc", "333");
        map.put("BCA", "2323");
        Set<String> setKey = map.keySet();
        Iterator<String> it = setKey.iterator();
        while (it.hasNext()) {
            String temp = it.next();
            System.out.println(temp);
        }
        Set<String> set = new TreeSet<>(new CaseInsensitiveCompare());
        set.add("abc");
        set.add("ABC");
        set.add("AAA");
        set.add("BCA");
        System.out.println(set.size());
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            String temp = iterator.next();
            System.out.println(temp);
        }
    }
}
class CaseInsensitiveCompare implements Comparator<String> {

    /**
     * Compares its two arguments for order.  Returns a negative integer, zero, or a positive integer as the first
     * argument is less than, equal to, or greater than the second.<p>
     * <p>
     * In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.<p>
     * <p>
     * The implementor must ensure that <tt>sgn(compare(x, y)) == -sgn(compare(y, x))</tt> for all <tt>x</tt> and
     * <tt>y</tt>.  (This implies that <tt>compare(x, y)</tt> must throw an exception if and only if <tt>compare(y,
     * x)</tt> throws an exception.)<p>
     * <p>
     * The implementor must also ensure that the relation is transitive:
     * <tt>((compare(x, y)&gt;0) &amp;&amp; (compare(y, z)&gt;0))</tt> implies
     * <tt>compare(x, z)&gt;0</tt>.<p>
     * <p>
     * Finally, the implementor must ensure that <tt>compare(x, y)==0</tt> implies that <tt>sgn(compare(x,
     * z))==sgn(compare(y, z))</tt> for all
     * <tt>z</tt>.<p>
     * <p>
     * It is generally the case, but <i>not</i> strictly required that
     * <tt>(compare(x, y)==0) == (x.equals(y))</tt>.  Generally speaking,
     * any comparator that violates this condition should clearly indicate this fact.  The recommended language is
     * "Note: this comparator imposes orderings that are inconsistent with equals."
     *
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater
     * than the second.
     * @throws NullPointerException if an argument is null and this comparator does not permit null arguments
     * @throws ClassCastException   if the arguments' types prevent them from being compared by this comparator.
     */
    @Override
    public int compare(String o1, String o2) {
        return o1.compareToIgnoreCase(o2);
    }
}