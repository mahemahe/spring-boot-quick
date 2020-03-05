package quick.algorithm.three.text;

import java.util.Iterator;

/**
 * 尾递归
 *      尾递归是在最后一行进行递归调用。
 *
 * 尾递归与循环的转换：
 *      尾递归可以通过将代码放到一个while循环中，并用每个方法参数的一次赋值代替递归调用而被手工消除。
 *
 * 尾递归的去除，是很简单的，以至于某些编译器能自动完成（意味着就是写成了尾递归，也会按照循环执行，不涉及栈溢出）。
 *
 * 递归总能够被彻底去除（编译器是在转变成汇编语言时完成递归去除的），但这么作是相当冗长乏味的。
 * 普通递归的转换逻辑是，要求使用一个栈，而且仅当你能够把最低限度的最小值放到栈上时，这个方法才值得用。
 *
 * 一般情况， 非递归程序 会比等价的 递归程序 快，但速度的优势的代价是清晰性受到了影响
 *
 * @author mahe <mahe@maihaoche.com>
 * @date 2020/2/19
 */
public class ChapterThreeTailRecursion {

    /**
     * 尾递归示例1，改while循环
     * 这种改法，估计属于编译器的通用改法
     */
    public static <T> void printListFix(Iterator<T> itr) {
        while (true) {
            if (!itr.hasNext()) {
                return;
            }

            System.out.println(itr.next());
        }
    }
    /**
     * 尾递归示例1，打印迭代器内容
     */
    public static <T> void printList(Iterator<T> itr) {
        if (!itr.hasNext()) {
            return;
        }

        System.out.println(itr.next());
        printList(itr);
    }
}


