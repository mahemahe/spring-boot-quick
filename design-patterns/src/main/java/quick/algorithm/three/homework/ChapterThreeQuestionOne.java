package quick.algorithm.three.homework;

import java.util.Iterator;
import java.util.List;

/**
 * 给定一个表L和另一个表P，他们包含以升序排列的整数。
 * 操作printLots(L, P)将打印L中那些由P所指定的位置上的元素。
 * 写出过程printLots(L, P)。
 * 只可使用public型的Collections API容器操作。（就是只能用java.util.Collection的public成员方法）
 * 该过程的运行时间是多少？
 *
 * @author mahe <mahe@maihaoche.com>
 * @date 2020/2/19
 */
public class ChapterThreeQuestionOne {

    public static void main(String[] args) {

    }

    /**
     * java.util.Collection接口继承了java.lang.Iterable，所以可以用迭代器的相关接口
     * 这里的位置，按照从0开始的数组位置计算
     */
    public static <T> void printLost(List<T> listL, List<Integer> listP) {
        Iterator<T> iteratorL = listL.iterator();
        Iterator<Integer> iteratorP = listP.iterator();
        // 标记L表的当前位置
        int indexL = -1;
        T dataL = null;

        // 因为表 L 和 P 都是升序排列好的，所以可以写成这样的逻辑
        while (iteratorL.hasNext() && iteratorP.hasNext()) {
            Integer targetIndex = iteratorP.next();

            while (indexL < targetIndex && iteratorL.hasNext()) {
                indexL++;
                dataL = iteratorL.next();
            }

            System.out.println(dataL);
        }
    }
}
