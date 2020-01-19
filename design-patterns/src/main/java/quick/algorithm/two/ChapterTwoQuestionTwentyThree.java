package quick.algorithm.two;

/**
 * 第24题，用N表示乘法的运算次数，应该是小于等于 2LogN
 * @author mahe <mahe@maihaoche.com>
 * @date 2020-01-19
 */
public class ChapterTwoQuestionTwentyThree {
    public static void main(String[] args) {
        int pow = 64;
        long n = powNumber(2, pow);
        double log = Math.log(pow) / Math.log(2);
        System.out.println("pow的对数：" + log);
        System.out.println("2倍对数值：" + 2 * Math.floor(log));
    }

    /**
     * 计算power次的x的幂（用非递归方式）
     * @param x 底数
     * @param power 幂次
     * @return
     */
    public static long powNumber(long x, int power) {
        if (0 == power) {
            return 1L;
        }
        if (1 == power) {
            return x;
        }
        int count = 0;
        // 乘数
        long multiplier = 1;
        do {
            // 奇数
            if (1 == power % 2) {
                multiplier *= x;
                count++;

            }
            x *= x;
            count++;
        } while (1 != (power /= 2));
        System.out.println("乘法次数：" + count);
        return multiplier * x;
    }
}
