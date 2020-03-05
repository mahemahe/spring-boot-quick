package quick.algorithm.three.text;
import com.google.gson.Gson;
import	java.util.Iterator;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 第三章 栈ADT（抽象数据类型）
 *
 * 栈有时又叫作LIFO（last in first out--后进先出）表
 *
 * 栈的几个应用示例--
 * 1. 后缀表达式，形如 6 5 2 3 + 8 * + 3 + *，
 *      这个记法叫作 后缀（postfix）或 逆波兰（reverse Polish）记法
 *
 * 书中Text 3.6.3部分的内容
 * 2. 中缀到后缀的转换（中缀表达式--infix，就是标准形式表达式）
 *      这个实现稍微复杂，可以再看看
 *
 * 3. 在每种程序设计语言中，递归方法的实现逻辑是类似的，
 *      当前方法要调用其他方法时，需要报错当前方法的变量、寄存器地址、返回地址等重要信息，
 *      这些信息都会抽象到"一张纸上"并被置于一个堆（pile）的顶部。
 *      "纸上"所存储的信息或称为 活动记录（activation record），或叫作 栈帧（stack frame），
 *      当前环境是由栈顶描述的。
 *      在实际计算机中的栈常常是从内存分区的高端乡下增长，而在许多非Java系统中是不检测溢出的。
 *      由于由太多的同时在运行着的方法，因此栈空间用尽的情况总是可能发生的。
 *      在不进行栈溢出检测的语言和系统中，程序将会崩溃而没有明显的说明；而在Java中则抛出一个异常。
 *      正常情况下，不应该越出栈空间，发生这种情况通常是由失控递归（忽视基准情形）的指向引起。
 *      另一方面，某些完全合法并且表面上无问题的程序也可以越出栈空间。
 *      尾递归，及处理方法看下面示例。
 *
 * @author mahe <mahe@maihaoche.com>
 * @date 2020/2/19
 */
public class ChapterThreeStack {
    public static void main(String[] args) {
        PostFixExpressionCalculator calculator = new PostFixExpressionCalculator();
        // 6 5 2 3 + 8 * + 3 + *      （后缀表达式）
//        String[] postFixExpression = {"6", "5", "2", "3", "+", "8", "*", "+", "3", "+", "*"};
        // 2 * （3 + 4) - 5 * 6 / 2   （中缀表达式）
        String[] infixExpression = {"2", "*", "(", "3", "+", "4", ")", "-", "5", "*", "6", "/", "2"};

        String[] postFixResult = calculator.postFixGenByInfix(infixExpression);
        System.out.println(new Gson().toJson(postFixResult));
        System.out.println(calculator.postFixCalculation(postFixResult));
        calculator.infixGenByPostfix(postFixResult);
    }
}

/**
 * 后缀表达是计算器
 * 参考书中3.6中的例子，编写的后缀表达式计算器
 * 计算逻辑：
 *  依次读入数据，当是数值时，将数值压入栈中；
 *  当是计算符号时，将栈中最上的两个数取出，并按照符号进行计算，将计算结果压入栈中；
 *  直到后缀表达式读取完成，直接返回栈顶元素即可
 */
class PostFixExpressionCalculator {
    private static final int DIVIDE_SCALE = 4;
    /**
     * 数值栈
     */
    private StackByLinkedList<BigDecimal> stack;
    /**
     * 操作符栈
     */
    private StackByLinkedList<Operators> operatorsStack;
    /**
     * 中缀表达式树栈
     */
    private StackByLinkedList<BinaryNode<String>> infixTreeStack;
    /**
     * 结果字符链
     */
    private LinkedList<String> resultList;

    public PostFixExpressionCalculator() {
        stack = new StackByLinkedList();
        operatorsStack = new StackByLinkedList();
        infixTreeStack = new StackByLinkedList();
        resultList = new LinkedList<>();
    }

    /**
     * 根据书中4.2.2 内容编写
     * 通过后缀表达式生成中缀表达式树
     * @param postfix 后缀表达式
     */
    public void infixGenByPostfix(String[] postfix) {
        for (int i = 0; i < postfix.length; i++) {
            readAndDealPostfix(postfix[i]);
        }
        BinaryNode<String> root = infixTreeStack.pop();
        System.out.println(root.data);
    }

    /**
     * 读取，并处理后缀表达式
     * 处理逻辑：
     * 1.如果是数值，就建一个node，放入栈中；
     * 2.如果是操作符，建一个node，从栈中取出两个node，先放right，再放left，再将node放入栈中
     * @param data 表达式中的一个元素
     */
    public void readAndDealPostfix(String data) {
        if (!checkOperator(data)) {
            // 1.如果是数值，就建一个node，放入栈中；
            infixTreeStack.push(new BinaryNode<>(data, null, null));
            return;
        }

        BinaryNode<String> node = new BinaryNode<>(data, null, infixTreeStack.pop());
        node.left = infixTreeStack.pop();
        infixTreeStack.push(node);
    }

    /**
     * 中缀转后缀
     * 使用中缀表达式生成后缀表达式
     * @param infix 中缀表达式
     * @return
     */
    public String[] postFixGenByInfix(String[] infix) {
        // 先读入并处理
        for (int i = 0; i < infix.length; i++) {
            readAndDealInfix(infix[i]);
        }
        /**
         * 在输入处理完成后，这是操作符栈还有值，直接输出即可
         * 注意一点：在中缀表达式的末尾应该是两种字符，
         * a. 右括号（这个符号会在read过程中触发将栈内元素输出的逻辑，所以这时栈中没有元素）
         * b. 数值（这时，栈中会留有元素）
         * 所以其实这里只需要考虑栈中为非括号的加减乘除即可
         */
        while (operatorsStack.size() > 0) {
            resultList.add(operatorsStack.pop().getOperator());
        }

        // 输出结果String[]
        return outputPostFixResult();
    }

    /**
     * 输出后缀表达式
     * LinkedList的iterator中调用的remove，是java.util.AbstractList.Itr#remove()
     */
    private String[] outputPostFixResult() {
        String[] output = new String[resultList.size()];
        Iterator<String> it = resultList.iterator();
        int i = 0;
        // 获取并移除后缀表达式结果list中的值
        while (it.hasNext()) {
            output[i++] = it.next();
            it.remove();
        }
        return output;
    }

    private void readAndDealInfix(String data) {
        Operators operator = Operators.valuesOf(data);
        if (null == operator) {
            // 数值直接到输出表
            resultList.add(data);
            return;
        }

        switch (operator) {
            case LEFT_BRACKET:
                // 做括号直接放入操作符栈即可
                operatorsStack.push(operator);
                break;
            case RIGHT_BRACKET:
                // 右括号时，需要将操作符栈中的操作符弹出直到遇到做括号
                Operators current;
                while (!Operators.LEFT_BRACKET.equals((current = operatorsStack.pop()))) {
                    resultList.add(current.getOperator());
                }
                break;
            default:
                // 加减乘除符号需要按级别处理
                dealOperatorInfix(operator);
                break;
        }
        return;
    }

    /**
     * 处理中缀表达式中的加减乘除符号
     * 1.与top同级符号，采用置换逻辑--将栈顶符号出栈，将当前元素入栈
     * 2.比top低级符号，先将top符号输出，再与最新的top比较（这里存在循环）
     * 3.碰到左括号，直接入栈
     * 4.碰到栈中无数据，直接入栈
     * 5.比top高级符号，直接入栈
     */
    private void dealOperatorInfix(Operators input) {
        // 栈顶的操作符
        Operators top = operatorsStack.top();
        while (true) {
            int compareResult = top == null ? -1 : input.getLevel() - top.getLevel();
            if (null == top
                    || Operators.LEFT_BRACKET.equals(top)
                    || 0 < compareResult ) {
                // 情况3、4和5
                operatorsStack.push(input);
                break;
            } else if (0 == compareResult) {
                // 情况1
                resultList.add(top.getOperator());
                operatorsStack.pop();
                operatorsStack.push(input);
                break;
            } else {
                // 情况2：先将当前栈顶元素输出，更新top元素为最新栈顶
                resultList.add(operatorsStack.pop().getOperator());
                top = operatorsStack.top();
            }
        }
    }

    /**
     * 后缀表达式计算器
     * @param postFixExpression
     * @return
     */
    public String postFixCalculation(String[] postFixExpression) {
        for (int i = 0; i < postFixExpression.length; i++) {
            read(postFixExpression[i]);
        }
        return outputResult();
    }

    /**
     * 执行
     * @param data
     */
    private void read(String data) {
        if (checkOperator(data)) {
            calculate(data);
        } else {
            push(data);
        }
    }

    private void push(String data) {
        stack.push(new BigDecimal(data));
    }

    /**
     * 返回计算的最终结果
     * 就是pop栈顶数据，返回即可
     * @return
     */
    private String outputResult() {
        return stack.pop().toEngineeringString();
    }

    /**
     * 运算：将栈顶的2个数据pop出，按照data运算符进行运算，将结果在push入栈中
     * @param data
     */
    private void calculate(String data) {
        BigDecimal secondData = stack.pop();
        Operators operator = Operators.valuesOf(data);

        switch (operator) {
            case ADDITION:
                stack.push(stack.pop().add(secondData));
                break;
            case SUBTRACTION:
                stack.push(stack.pop().subtract(secondData));
                break;
            case MULTIPLICATION:
                stack.push(stack.pop().multiply(secondData));
                break;
            case DIVISION:
                stack.push(stack.pop().divide(secondData).setScale(DIVIDE_SCALE, BigDecimal.ROUND_HALF_DOWN));
                break;
        }
        return;
    }

    /**
     * 校验数据是否是运算符
     * @param data 传入数据
     * @return 是否是运算符
     */
    private boolean checkOperator(String data) {
        if (StrUtil.isBlank(data)) {
            throw new RuntimeException("非法输入");
        }

        return ReUtil.isMatch("[/+-/*//]", data);
    }

    /**
     * 二叉树的节点(先不写整个树，用节点的连接代表，最后只需要给出根节点即可)
     */
    private class BinaryNode<T> {
        public T data;
        public BinaryNode<T> left;
        public BinaryNode<T> right;

        public BinaryNode(T data, BinaryNode<T> left, BinaryNode<T> right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
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
enum Operators {
    ADDITION("+", 1),
    SUBTRACTION("-", 1),
    MULTIPLICATION("*", 2),
    DIVISION("/", 2),
    LEFT_BRACKET("(", 3),
    RIGHT_BRACKET(")", 3)
    ;

    public String getOperator() {
        return operator;
    }

    public int getLevel() {
        return level;
    }

    Operators(String data, int levelData) {
        operator = data;
        level = levelData;
    }
    private String operator;
    private int level;

    public static Operators valuesOf(String data) {
        Operators[] operators = Operators.values();
        for (Operators temp : operators) {
            if (StrUtil.equals(temp.operator, data)) {
                return temp;
            }
        }

        return null;
    }
}