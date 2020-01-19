package quick.algorithm.one;

/**
 * 编写一种递归方法，它返回数N的二进制中表示1的个数。利用这样一个事实：N为奇数，其1的个数为N/2的二进制中1的个数加1.
 * @author mahe <mahe@maihaoche.com>
 * @date 2019-11-21
 */
public class ChapterOneQuestionFive {

    public static void main(String[] args) {
        int n = 15;

        int count = countOnes(n);
        System.out.println(count);
    }

    /**
     * 判断数N的二进制数中1的个数
     * @param n
     * @return
     */
    public static int countOnes(int n) {
        if (0 == n) {
            return 0;
        }

        if (isOdd(n)) {
            return 1 + countOnes(n / 2);
        } else {
            return countOnes(n / 2);
        }
    }

    /**
     * 判断当前数字是奇数
     * @param i
     * @return
     */
    public static boolean isOdd(int i) {
        if (i % 2 == 0) {
            return false;
        }

        return true;
    }
}
