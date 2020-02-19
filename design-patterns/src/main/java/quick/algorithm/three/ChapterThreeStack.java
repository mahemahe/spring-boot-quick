package quick.algorithm.three;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 第三章 栈ADT（抽象数据类型）
 *
 * 栈有时又叫作LIFO（last in first out--后进先出）表
 * @author mahe <mahe@maihaoche.com>
 * @date 2020/2/19
 */
public class ChapterThreeStack {
    public static void main(String[] args) {

    }
}

class StackByArrayList<T> {
    private ArrayList<T> stack;
    /**
     * 栈顶位置
     */
    private int top;

    public StackByArrayList() {
        stack = new ArrayList<>();
        top = -1;
    }

    public void push(T data) {
        stack.add(++top, data);
    }

    public T pop() {
        return stack.remove(top--);
    }

    public T top() {
        return stack.get(top);
    }

    public int size() {
        return stack.size();
    }
}

/**
 * 用LinkedList实现
 * @param <T>
 */
class StackByLinkedList<T> {
    private LinkedList<T> stack;

    public StackByLinkedList() {
        stack = new LinkedList<>();
    }

    public void push(T data) {
        stack.addFirst(data);
    }

    public T pop() {
        return stack.poll();
    }

    public T top() {
        return stack.peek();
    }

    public int size() {
        return stack.size();
    }
}