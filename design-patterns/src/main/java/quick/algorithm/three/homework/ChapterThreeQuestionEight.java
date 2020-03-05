package quick.algorithm.three.homework;

import java.util.List;

/**
 * 下列例程为 删除作为参数被传递的表的前半部分，例程如下。
 * 问题
 * a.为什么在进入for循环前存储theSize
 * b.如果lst是一个ArrayList，removeFirstHalf的运行时间是多少？
 * c.如果lst是一个LinkedList，removeFirstHalf的运行时间是多少？
 * d.对与这辆种类型的List使用迭代器都能使removeFirstHalf更快吗？
 * @author mahe <mahe@maihaoche.com>
 * @date 2020/2/21
 */
public class ChapterThreeQuestionEight {

    /**
     * 答：
     * a. 因为执行remove后，size的大小会发生变更，所以要先存储最初的大小
     * b. ArrayList执行remove的逻辑是将当前index位置后的所有数据向前移动一个位置，实际进行的是拷贝操作
     *      那么每次如果remove第一个数据，就相当于拷贝操作执行了size - 1次，总共remove需要 size / 2次，
     *      另数组元素个数为N，那么最大执行次数为 (N - 1) * N/2 => O(N的平方)
     * c. LinkedList的remove是直接去掉这个节点Node，执行的操作是unlink(node(index))，
     *      node(index)，在index小于当前数据量一半时，就从start位置开始查找node(index)，node(0)只会执行next一次，
     *      并且后续不会进行数据移动，所以执行时间是 O(N)
     * d. ArrayList不会更快，因为其迭代器中的remove操作调用的就是ArrayList的remove
     *      LinkedList也是不会更快，因为迭代器中的remove操作调用的就是LinkedList实现AbstractList的remove方法
     */
    public static void removeFirstHalf(List<?> lst) {
        int theSize = lst.size() / 2;

        for (int i = 0; i < theSize; i++) {
            lst.remove(0);
        }
    }

}
