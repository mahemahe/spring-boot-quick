package quick.algorithm.two;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import quick.algorithm.AlgorithmUtils;

/**
 * 编写一种递归方法，它返回数N的二进制中表示1的个数。利用这样一个事实：N为奇数，其1的个数为N/2的二进制中1的个数加1.
 * @author mahe <mahe@maihaoche.com>
 * @date 2019-11-21
 */
public class ChapterTwoQuestionEight {
    public static void main(String[] args) {
        TestTwoQuestionEight test = new TestTwoQuestionEight();
        test.test();
    }
}

class TestTwoQuestionEight {
    private static ThreadLocalRandom random = ThreadLocalRandom.current();
    public void test() {
        int[] rangesOne = {250, 500, 1000, 2000, 4000, 8000, 16000};
        int[] rangesTwo = {25000, 50000, 100000, 200000, 400000, 800000, 1600000, 3200000, 6400000};
        int[] rangesThree = {100000, 200000, 400000, 800000, 1600000, 3200000, 6400000, 1000000};
        for (int i : rangesOne) {
            long time = algorithmTime(i, this::algorithmOne);
            String output = String.format("算法1：range[%s], time[%s]", i, time);
            System.out.println(output);
        }
        for (int i : rangesTwo) {
            long time = algorithmTime(i, this::algorithmTwo);
            String output = String.format("算法2：range[%s], time[%s]", i, time);
            System.out.println(output);
        }
        for (int i : rangesThree) {
            long time = algorithmTime(i, this::algorithmThree);
            String output = String.format("算法3：range[%s], time[%s]", i, time);
            System.out.println(output);
        }
    }
    /**
     * 获取算法执行时间
     * @param range 取数范围（也是数组大小）
     * @param function
     * @return
     */
    public long algorithmTime(int range, Function<Integer, Long> function) {
        return function.apply(range);
    }

    /**
     * 算法1：从a[0]到a[n-1]的数组a；为了填入a[i]，生成随机数直到它不同与已经生成的a[0],a[1],...,a[i-1]时再将其填入
     * @param range 范围
     * @return
     */
    public long algorithmOne(int range) {
        int[] a = new int[range];
        int i = 0;
        long startTime = System.nanoTime();
        while_mark:
        while (i < range) {
            int x = random.nextInt(range);
            for (int j = 0; j < i; j++) {
                if (x == a[j]) {
                    continue while_mark;
                }
            }
            a[i] = x;
            i++;
        }
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    /**
     * 算法2：同算法1，但要保存一个附加的数组，成为used数组。当一个随机数ran最初被放入数组a时，置used[ran]=true。也就是说，当用一个
     * 随机数填入a[i]时，可以用一步来测试是否该随机数已经被使用，而不像算法1那样（可能）用i步测试
     * @param range 范围
     * @return
     */
    public long algorithmTwo(int range) {
        int[] a = new int[range];
        boolean[] used = new boolean[range];
        int i = 0;
        long startTime = System.nanoTime();
        while_mark:
        while (i < range) {
            int x = random.nextInt(range);
            if (used[x]) {
                continue while_mark;
            }
            a[i] = x;
            used[x] = true;
            i++;
        }
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    /**
     * 算法3：先初始化数组，使a[i]=i+1。然后从1到n-1位，进行与随机位置交换，即swap(a[i], a[randInt(0, i)]);
     * @param range 范围
     * @return
     */
    public long algorithmThree(int range) {
        int[] a = new int[range];
        long startTime = System.nanoTime();
        // 初始化数组
        for (int i = 0; i < range; i++) {
            a[i] = i + 1;
        }
        for (int i = 1; i < range; i++) {
            AlgorithmUtils.exchange(a, i, random.nextInt(i));
        }
        long endTime = System.nanoTime();
        return endTime - startTime;
    }
}
