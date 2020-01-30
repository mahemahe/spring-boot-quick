package quick.algorithm.two;

import quick.algorithm.AlgorithmUtils;

/**
 * @author mahe <mahe@maihaoche.com>
 * @date 2020-01-20
 */
public class ChapterTwoQuestionTwentyEight {
    public static void main(String[] args) {
        TestTwentyEight test = new TestTwentyEight();
//        test.initArray(10);
//        System.out.println("数组为：" + new Gson().toJson(TestTwentyEight.array));
//        int maxSumTwo = test.maxSumValue();
//        System.out.println("两个数的最大和为：" + maxSumTwo);
//        int maxDifference = test.maxDifference();
//        System.out.println("两个数的最大差为：" + maxDifference);
        double maxQuotient = test.maxQuotient();
        System.out.println("两个数的最大商为：" + maxQuotient);
    }
}
class TestTwentyEight {
    public static Integer[] array = {4,7,5,4,8,2,7,3,5,8};

    /**
     * a.找正数数组中和值的最大值
     * c.找正数数组中乘积的最大值（与a相同，一样是找俩个大数，只不过返回乘积）
     * 逻辑就是找到数组中最大的两个数
     * @return
     */
    public int maxSumValue() {
        int[] maxTwo = {-1, -1};
        for (Integer temp : array) {
            // 不把同一个temp元素同时放入maxTwo数组中两个位置
            for (int i = 0; i < maxTwo.length; i++) {
                // 初始化时，每一个maxTwo放入一个数组中的数组
                if (-1 == maxTwo[i]) {
                    maxTwo[i] = temp;
                    break;
                }
                if (temp > maxTwo[i]) {
                    maxTwo[i] = temp;
                    break;
                }
            }
        }

        return maxTwo[0] + maxTwo[1];
    }

    /**
     * b.找正数数组中差值最大的值
     * 但有个要求：a[j] - a[i]的最大值，且 j > i
     * 1.简单算法就是先找到最大值（且不能是第一个值，隐含的意思就是 N个数中，a[1]~a[n - 1]位置的最大值，要大于a[0]）；然后在最大值前找一个最小值
     * 2.可以同时找最大最小值，将a[0]设置为最小值，然后从a[1]~a[n - 1]位置找最大值，
     * 并同时找最小值的候选值，候选最小值要比最小值小，在最大值进行更新时，候选最小值赋值给最小值
     *
     * 当前方法中，采用第二中方法
     * @return
     */
    public int maxDifference() {
        // 最大值
        int max = array[1];
        // 最小值
        int min = array[0];
        // 候选最小值
        int preMin = -1;
        for (int i = 2; i < array.length; i++) {
            // 当前值小于最小值，则为候选最小值
            if (array[i] < min) {
                // 注意：这时，preMin可能已经设置了值，只要preMin设置了值，那就要换成preMin去和当前值比对
                if (-1 == preMin) {
                    preMin = array[i];
                    continue;
                }
                if (array[i] < preMin) {
                    preMin = array[i];
                    continue;
                }
            }
            // 当前值大于最大值，则为最大值，并且将preMin赋值给最小值
            // （注意：这里得是 >=，因为在数组{4,7,5,4,8,2,7,3,5,8}中，max是8，但min应该是2，如果不加 = 号，那么max就是第一个8，min是4）
            if (array[i] >= max) {
                max = array[i];
                if (-1 != preMin) {
                    // preMin更新过，就进行赋值
                    min = preMin;
                    preMin = -1;
                }
            }
        }

        // 如果最后选出的最大值小于最小值，即最大值在a[0]位置，那么就throw
        if (max < min) {
            throw new RuntimeException("数组内没有符合规则的两个数");
        }

        return max - min;
    }

    /**
     * d.找正数数组中商值最大的值
     * 但有个要求：a[j] - a[i]的最大值，且 j > i
     * 这个逻辑与找差值最大的逻辑基本一致，只是最小值不能为0，将这个逻辑放入找差值的逻辑中，即可
     *
     * @return
     */
    public double maxQuotient() {
        // 最小值
        int min = 0;
        // 第一个非0数的index
        int index = 0;
        // 先找最小值
        while (index < array.length) {
            if (0 != array[index]) {
                min = array[index];
                break;
            }
        }
        // 如果没找到非0的值，或非0值出现在最后一位（意味着没有a[j]了），都认为没有最大商值
        if (0 == min || index == array.length - 1) {
            throw new RuntimeException("没有复合条件的最大商值");
        }

        // 最大值
        int max = array[index + 1];
        // 候选最小值
        int preMin = -1;
        for (int i = index + 2; i < array.length; i++) {
            // 当前值小于最小值，则为候选最小值
            if (array[i] < min) {
                // 注意：这时，preMin可能已经设置了值，只要preMin设置了值，那就要换成preMin去和当前值比对
                if (-1 == preMin) {
                    preMin = array[i];
                    continue;
                }
                if (array[i] < preMin) {
                    preMin = array[i];
                    continue;
                }
            }
            // 当前值大于最大值，则为最大值，并且将preMin赋值给最小值
            // （注意：这里得是 >=，因为在数组{4,7,5,4,8,2,7,3,5,8}中，max是8，但min应该是2，如果不加 = 号，那么max就是第一个8，min是4）
            if (array[i] >= max) {
                max = array[i];
                if (-1 != preMin) {
                    // preMin更新过，就进行赋值
                    min = preMin;
                    preMin = -1;
                }
            }
        }

        // 注意：这里无需要求max > min（即不用要求max值大于min）
        return Double.valueOf(max) / min;
    }

    /**
     * 初始化随机数组
     * @param range
     */
    public void initArray(int range) {
        array = AlgorithmUtils.randomArray(range);
    }
}

