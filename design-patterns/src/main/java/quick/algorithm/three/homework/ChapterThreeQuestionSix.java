package quick.algorithm.three.homework;
import	java.util.Iterator;
import	java.util.LinkedList;
import quick.algorithm.AlgorithmUtils;

/**
 * Josephus Problem
 * 约瑟夫斯问题，直接百度即可
 *
 * a.编写一个程序解决M和N在一般值下的Josephus问题，尽可能提高程序效率，清除各个单元
 * b.你的程序的运行时间是多少
 * @author mahe <mahe@maihaoche.com>
 * @date 2020/2/20
 */
public class ChapterThreeQuestionSix {

    public static void main(String[] args) {
        int personNum = resolveJosephusProblemSimple(1, 5);
        System.out.println(personNum);
        LinkedList<String> lst = new LinkedList<String> ();
        lst.iterator();
    }

    /**
     * 一种解法，容易想到的
     * 有两个可以简化的点：
     * 1.(我自己想到了)防止M大于表的size，导致表进行多次循环，先进行取模操作，M % 当前的数组大小n = X，用X进行迭代
     * 2.(我未想到的)当 M > 当前数据长度 / 2 时，向后进行 M 次循环，不如向前进行 当前数据长度 - M 次循环 快
     *      因为是循环数组，向后查 M 次 = 向前查 N - M 次
     * 下面的方法中可以针对第2点进行改进
     *
     * 运行时间估算，第二层while的执行次数，
     * 1.当M < size时循环次数是M，等于M*(size -M) = M*(N-M)=N*M - M平方
     * 2.当M > size时循环次数是M模size的余数 肯定小于size，这里用最大值size估算，size的值从M 到 1 = (M + 1)M/2 约为 O(M平方除以2)
     * 总时间估计为  a.O(M*N) 当 N大于M    b. O(M平方) 当 N小于M
     * @param m 间隔数
     * @param n 参与问题的总人数
     * @return
     */
    public static int resolveJosephusProblemSimple(int m, int n) {
        LinkedList<Integer> persons = AlgorithmUtils.generateLinkedList(n);
        int current;
        int tempM;

        Iterator<Integer> iterator = persons.iterator();

        while (persons.size() > 1) {

            // 防止M大于表的size，导致表进行多次循环，先进行取模操作
            tempM = m % persons.size();
            if (!iterator.hasNext()) {
                iterator = persons.iterator();
            }

            // 先移动到第一个元素位置
            iterator.next();
            current = 0;

            // 开始传递
            while (current++ < tempM) {
                // 当前迭代器到尾部后，重新生成迭代器
                if (!iterator.hasNext()) {
                    iterator = persons.iterator();
                }
                iterator.next();
            }

            iterator.remove();
        }

        return persons.element();
    }
}
