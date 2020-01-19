package quick.algorithm.two;

/**
 * @author mahe <mahe@maihaoche.com>
 * @date 2020-01-19
 */
public class ChapterTwoQuestionTwentyTwo {
    public static int count = 0;
    public static void main(String[] args) {
        long n = powNumber(2, 62);
        System.out.println(count);
        System.out.println(n);
    }

    /**
     * 计算power次的x的幂
     * @param x 底数
     * @param power 幂次
     * @return
     */
    public static long powNumber(long x, int power) {
        if (0 == power) {
            return 1L;
        }
        if (1 == power) {
            count++;
            return x;
        }
        // 奇数
        if (1 == power % 2) {
            count += 2;
            return x * powNumber(x * x, power / 2);
        } else {
            count++;
            // 偶数
            return powNumber(x * x, power / 2);
        }
    }
}
