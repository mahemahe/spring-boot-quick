package quick.algorithm.one;

/**
 * 1.3 只使用处理IO的printDigit函数，编写一个过程以输出任意实数可以为负
 * 一位字符一位字符的打印
 * @author mahe <mahe@maihaoche.com>
 * @date 2019-11-13
 */
public class ChapterOneQuestionThree {

    public static void main(String[] args) {
        printDigit(123.0948934170689732);
        printDigit(-376089.39);
        printDigit(0.23489760893452000);
    }

    /**
     * 打印输入的double数组
     * @param num 输入的double数值
     */
    private static void printDigit(double num) {
        if (0d == num) {
            System.out.print(0);
        }

        // 打印符号
        if (0 > num) {
            num = -num;
            System.out.print("-");
        }

        // 打印整数部分
        int integerPart = (int) num;
        printInteger(integerPart);

        // 打印小数部分
        double decimalPart = num - integerPart;
        if (0d == decimalPart) {
            return;
        }
        System.out.printf(".");
        printDecimal(decimalPart);
        System.out.print("\n");
    }

    private static void printInteger(int integer) {
        if (0 == integer) {
            System.out.print(0);
        }
        while (0 < integer) {
            System.out.print(integer % 10);
            integer /= 10;
        }
    }

    /**
     * 保留12位精度
     * @param decimal
     */
    private static void printDecimal(double decimal) {
        int count = 0;
        while (0d < decimal && count++ < 12) {
            decimal *= 10;
            int integerPart = (int)(decimal);
            System.out.print(integerPart);
            decimal -= integerPart;
        }
    }

}
