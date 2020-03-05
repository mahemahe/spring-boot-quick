package quick.algorithm.three.homework;

import com.google.gson.Gson;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 给定两个已排序的表L1和L2，只使用基本的表操作编写计算L1 并 L2的过程
 *
 * 计算逻辑：
 *      由于两个表都是排过序的，所逻辑过程就是两个表谁的当前值小，谁的表进行next操作，然后比较next后的值与当前值是否相等
 *      出现next后值比当前值大时，就变更进行next操作的表
 * @author mahe <mahe@maihaoche.com>
 * @date 2020/2/20
 */
public class ChapterThreeQuestionFive {

    public static void main(String[] args) {
        List<Integer> l1 = new LinkedList<> ();
        List<Integer> l2 = new LinkedList<> ();
        l1.add(1);l1.add(3);l1.add(4);l1.add(5);l1.add(7);l1.add(8);l1.add(9);
        l2.add(2);l2.add(3);l2.add(4);l2.add(6);l2.add(7);l2.add(8);l2.add(10);

        List<Integer> result = union(l1, l2);
        System.out.println(new Gson().toJson(result.toArray()));
        List<Integer> result2 = new LinkedList<> ();
        union2(l1, l2, result2);
        System.out.println(new Gson().toJson(result2.toArray()));
    }

    /**
     * 假设是按照由小到大的顺序进行排序的表（我的答案）
     * @param l1 排好序的表1
     * @param l2 排好序的表2
     * @param <T> 表中的元素类型，需要实现过Comparable接口
     * @return 两表的交集结果
     */
    public static <T extends Comparable<? super T>> List<T> union(List<T> l1, List<T> l2) {
        List<T> result = new LinkedList<> ();
        // current代表当前进行循环对迭代器
        Iterator<T> current = l1.iterator();
        // another代表作为比对者的迭代器
        Iterator<T> another = l2.iterator();
        // 当前待比对的值
        T expectedData;

        while (current.hasNext() && another.hasNext()) {
            // 获取待比对的当前值
            expectedData = another.next();

            while (current.hasNext()) {
                T currentData = current.next();
                if (expectedData.compareTo(currentData) > 0) {
                    // 预期值大于当前值，继续循环
                    result.add(currentData);
                    continue;
                } else if (expectedData.equals(currentData)) {
                    // 预期值等于当前值，获取到一个交集结果
                    result.add(currentData);
                    // 让待对比值进行变更
                    break;
                }

                // 预期值小于当前值，将当前值变为待对比值，并变更循环的迭代器
                result.add(expectedData);
                expectedData = currentData;
                Iterator<T> temp = current;
                current = another;
                another = temp;
            }
        }

        return result;
    }

    /**
     * 参考答案书中的3.4的答案，改写3.5
     * @param l1
     * @param l2
     * @param result
     * @param <T>
     */
    public static <T extends Comparable<? super T>> void union2(List<T> l1, List<T> l2, List<T> result) {
        Iterator<T> i1 = l1.iterator();
        Iterator<T> i2 = l2.iterator();

        T item1 = null, item2 = null;
        // get first item in each list
        if (i1.hasNext() && i2.hasNext()) {
            item1 = i1.next();
            item2 = i2.next();
        }

        // 求并集是，就要保存item1和item2中将要发生变化的那个
        while (item1 != null && item2 != null) {
            int compareResult = item1.compareTo(item2);

            if (compareResult == 0) {
                result.add(item1);
                item1 = i1.hasNext() ? i1.next() : null;
                item2 = i2.hasNext() ? i2.next() : null;
            } else if (compareResult < 0) {
                result.add(item1);
                item1 = i1.hasNext() ? i1.next() : null;
            } else {
                result.add(item2);
                item2 = i2.hasNext() ? i2.next() : null;
            }
        }
    }

}

