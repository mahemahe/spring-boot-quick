package quick.algorithm.three.text;

/**
 * 第三章 队列ADT（抽象数据类型）
 *
 * 像栈一样，队列（Queue）也是表；插入在一端进行，而删除在另一端进行
 * 队列的基本操作：
 *      enqueue（入队），是在表的末端（叫作队尾--rear）插入一个元素
 *      dequeue（出队），是删除（并返回）在表的开头（叫作队头--front）的元素
 *
 * 与栈的实现一样，采用 数组 和 LinkedList 分别实现
 * 数组方式实现：
 *      稍微困难，具体实现逻辑中，要加入 循环数组（circular array） 实现；
 *      整个queue的大小是有限的，数组实现时，要记 front 和 rear 的位置，
 *      在 rear/front 到达数组的末尾时，将其指向数组的起始位置重新移动，这就是循环数组的逻辑
 *      即 如果 front 或 rear 增1导致超越了数组，那么其值就要重置到数组的第一个位置
 *
 * @author mahe <mahe@maihaoche.com>
 * @date 2020/2/19
 */
public class ChapterThreeQueue {

}
