package quick.algorithm.two;

import com.google.gson.Gson;
import java.util.concurrent.ThreadLocalRandom;
import quick.algorithm.AlgorithmUtils;

/**
 * @author mahe <mahe@maihaoche.com>
 * @date 2020-01-14
 */
public class ChapterTwoSevenTeen {
    public static void main(String[] args) {
        TestSevenTeen test = new TestSevenTeen();
        int[] array = test.randomArray(10);
        System.out.println(new Gson().toJson(array));
        int maxProduct = test.maxSubArrayMultiplicationNLogN(array);
        System.out.println(maxProduct);
    }
}
class TestSevenTeen {
    private static final int ARRAY_LENGTH = 10;
    private static ThreadLocalRandom randomOne = ThreadLocalRandom.current();
    private static ThreadLocalRandom randomTwo = ThreadLocalRandom.current();

    /**
     * 求最大子序列乘积
     * O(N·LogN)
     * 要分3步：
     * 1. 求前缀项，即从0～i位置累乘积，生成新array，位置i为0～i位置的累乘求积值
     * 2. 变成2维数组，对值进行排序
     * 3. 对结果分成两组 正值组 和 负值组，分别求两组序号顺序正确且相距最远的两个数的商，为最大值，两组的最大值的max即为结果
     * (负数相除，为正数)
     * @param arrays
     * @return
     */
    public int maxSubArrayMultiplicationNLogN(int[] arrays) {
        long start = System.nanoTime();
        int[][] subProductArray = new int[ARRAY_LENGTH][2];
        // 进行第一步
        int product = 1;
        int maxSingle = 0;
        for (int i = 0; i < arrays.length; i++) {
            product *= arrays[i];
            subProductArray[i] = new int[]{product, i};
            if (arrays[i] > maxSingle) {
                maxSingle = arrays[i];
            }
        }
        System.out.println(new Gson().toJson(subProductArray));

        // 进行第二步，排序（先用冒泡排序替代，后面可以用优化的排序算法加速排序）
        bubbleSort(subProductArray);
        System.out.println(new Gson().toJson(subProductArray));

        int max_subProductPositiveArray = 0;
        int max_subProductNegativeArray = 0;
        // 第三步，找出符合条件的相邻项做差，求最大累乘结果
        // 先从正数组查起
        int positiveArrayStart = subProductArray[0][1];
        if (positiveArrayStart > 0) {
            for (int i = subProductArray.length - 1; i > 0; i--) {
                if (subProductArray[i][0] <= 0) {
                    continue;
                }
                if (subProductArray[i][1] > positiveArrayStart) {
                    continue;
                }
                max_subProductPositiveArray = subProductArray[0][0] / subProductArray[i][0];
                break;
            }
        }
        int negativeArrayStart = subProductArray[subProductArray.length - 1][0];
        if (negativeArrayStart < 0) {
            for (int i = 0; i < subProductArray.length - 1; i++) {
                if (subProductArray[i][0] >= 0) {
                    continue;
                }
                if (subProductArray[i][1] > negativeArrayStart) {
                    continue;
                }
                max_subProductNegativeArray = negativeArrayStart / subProductArray[i][0];
                break;
            }
        }

        int max = max(max_subProductPositiveArray, max_subProductNegativeArray, maxSingle);
        long end = System.nanoTime();
        System.out.println("算法 NLogN用时：" + (end - start));
        return max;
    }

    private int max(int... arrays) {
        if (arrays.length == 0) {
            return 0;
        }
        int max = 0;
        for (int temp : arrays) {
            if (temp > max) {
                max = temp;
            }
        }
        return max;
    }

    /**
     * 求最小正子序列和
     * O(N·LogN)
     * 要分3步：
     * 1. 求前缀项，即从0～i位置累加求和，生成新array，位置i为0～i位置的累加求和值
     * 2. 变成2维数组，对值进行排序
     * 3. 找出这个2维数组相邻两项差值的最小正数值：条件是，减数与被减数的位置i的差值为正，即减数的序数大，被减数的序数小
     * （sum[i] - sum[j]，即为从第j+1位置到i位置的累加和，从小到大排序后，相邻项的差值，属于最小sum值的可能值；
     * 可以理解为 从0累加到j项的值 与 从0累加到i项的值相近，那么这里的增长量 即为从j+1项到i项的sum值）
     * @param arrays
     * @return
     */
    public int minSubArraySumNLogN(int[] arrays) {
        long start = System.nanoTime();
        int[][] subSumArray = new int[ARRAY_LENGTH][2];
        // 进行第一步
        int sum = 0;
        for (int i = 0; i < arrays.length; i++) {
            sum += arrays[i];
            subSumArray[i] = new int[]{sum, i};
        }
//        System.out.println(new Gson().toJson(subSumArray));

        // 进行第二步，排序（先用冒泡排序替代，后面可以用优化的排序算法加速排序）
        bubbleSort(subSumArray);
//        System.out.println(new Gson().toJson(subSumArray));

        int min_subSum = 0;
        // 第三步，找出符合条件的相邻项做差，求最小正数值
        for (int i = 0; i < subSumArray.length - 1; i++) {
            if (subSumArray[i][1] < subSumArray[i + 1][1]) {
                continue;
            }
            // 序号顺序正确
            int difference = subSumArray[i][0] - subSumArray[i + 1][0];
            if (difference < 0) {
                continue;
            }
            // 差值为正数
            if (min_subSum == 0) {
                // 结果没有被初始化时，先初始化一个差值的正数
                min_subSum = difference;
                continue;
            }
            if (difference < min_subSum) {
                min_subSum = difference;
            }
        }
        if (min_subSum == 0 || min_subSum > subSumArray[0][0]) {
            min_subSum = subSumArray[0][0];
        }

        long end = System.nanoTime();
        System.out.println("算法 NLogN用时：" + (end - start));
        return min_subSum;
    }

    /**
     * 求最小正子序列和
     * @param arrays
     * @return
     */
    public int minSubArraySum(int[] arrays) {
        long start = System.nanoTime();
        // 初始化最小值为负数，用与判断出最小的正数起点
        int min_sum = -1;
        for (int i = 0; i < arrays.length; i++) {
            int sum = 0;
            for (int j = i; j < arrays.length; j++) {
                sum += arrays[j];
                if (min_sum == -1) {
                    // 初始化最小子序列和为正数
                    if (sum > 0) {
                        min_sum = sum;
                    }
                } else {
                    if (sum < min_sum && sum > 0) {
                        min_sum = sum;
                    }
                }
            }
        }

        long end = System.nanoTime();
        System.out.println("算法 N^2用时：" + (end - start));
        return min_sum;
    }

    public int[] randomArray(int length) {
        int[] randomArrays = new int[length];
        for (int i = 0; i < length; i++) {
            int temp = randomTwo.nextInt(ARRAY_LENGTH);
            if (0 == randomOne.nextInt(2)) {
                randomArrays[i] = -temp;
            } else {
                randomArrays[i] = temp;
            }
        }
        return randomArrays;
    }

    /**
     * 对数组进行排序（以第一位值为准）
     * 逆序（从大到小）
     * @param arrays
     */
    private void bubbleSort(int[][] arrays) {
        for (int i = 1; i < arrays.length; i++) {
            for (int j = 0; j < arrays.length - i; j++) {
                if (arrays[j][0] < arrays[j + 1][0]) {
                    AlgorithmUtils.exchange(arrays, j, j + 1);
                }
            }
        }
    }

}
