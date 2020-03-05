package quick.algorithm.three.homework;

/**
 * 3.25 提出一种数据结构支持栈push和pop操作以及第三种操作findMin，
 *      它返回该数据结构中的最小元素。所有操作均以O(1)最坏情形时间运行
 *
 * 我的思考逻辑：
 * 1.如果findMin要以O(1)时间运行，那么就必须记录下当前的min；
 * 2.push和pop要以O(1)时间运行，还是链表好些
 *
 * 我的思考逻辑中，有缺陷的点：
 * 1.因为stack还有pop的功能，即移除数据功能，所以只记录当前最小值的话，执行pop后，
 *      就要遍历当前数据找到最新的最小值；
 *      所以从这里看，需要有两个链表，
 *      一个stack1正常存数据，
 *      一个stack2在每次stack1进行push时，比对stack2的栈顶数据，如果小于等于此值，就执行stack2的pop
 *
 * @author mahe <mahe@maihaoche.com>
 * @date 2020/2/25
 */
public class ChapterThreeQuestionTwentyFive {

}
class MyStack<T extends Comparable<? super T>> {
    // TODO: 2020/2/25 待完成
}
