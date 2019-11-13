package quick.algorithm.chapter_one;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * 1.1编写一个程序解决选择问题。令k=N/2。画出表格显示你的程序对于N为不同值的运行时间。
 *
 * @author mahe <mahe@maihaoche.com>
 * @date 2019-11-08
 */
public class ChapterOneQuestionOne {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        TestOne one = new TestOne();
        try {
            one.test1_1(40000);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("计时：" + new BigDecimal(end - start).divide(new BigDecimal(1000)).toPlainString());
    }

}

class TestOne {
    /**
     * 1.1编写一个程序解决选择问题。令k=N/2。画出表格显示你的程序对于N为不同值的运行时间。
     */
    void test1_1(int n) throws NoSuchAlgorithmException {
        Integer[] intArray = initIntArray(n, 1000);
        // 二分数组
        int kIndex = (int) Math.ceil(n / 2);
        Integer[] intArrayOne = Arrays.copyOfRange(intArray, 0, kIndex);
        Integer[] intArrayTwo = Arrays.copyOfRange(intArray, kIndex + 1, intArray.length);

        // 排序后的两个数组
        bubbleSort(intArrayOne);
        bubbleSort(intArrayTwo);

        Integer k = getMiddleNum(intArrayOne, intArrayTwo);

        System.out.println("N=" + n + "个数：" + AlgorithmUtils.convertArrayToString(intArray) + "中，\n第N/2个数，是：" + k);
        System.out.println(AlgorithmUtils.convertArrayToString(intArrayOne));
    }

    /**
     * 取两个数组的中间值
     * @param intArrayOne 排序后的
     * @param intArrayTwo 排序后的
     * @return
     */
    private Integer getMiddleNum(Integer[] intArrayOne, Integer[] intArrayTwo) {
        // 以arrayOne为主，去判断arrayTwo在其的位置
        /**
         * 判断条件：
         * 1.two中的数值如果比one中的最大值大／等，则停止比对
         * 2.two中的数值只要比one中的任意值小，就插入到那个位置
         */
        for (int two = 0; two < intArrayTwo.length; two++) {
            for (int one = 0; one < intArrayOne.length; one++) {
                if (intArrayTwo[two] < intArrayOne[one]) {
                    // two小于one的情况，直接插入
                    insertArrayAtIndex(intArrayOne, intArrayTwo[two], one);
                    break;
                }
            }
        }

        return intArrayOne[intArrayOne.length - 1];
    }

    /**
     * 向array的index位置插入input数值
     * @param array 数组
     * @param input 输入数值
     * @param index 位置
     */
    private void insertArrayAtIndex(Integer[] array, Integer input, int index) {
        if (index == array.length - 1) {
            array[index] = input;
            return;
        }
        // 先所有数值向后移动一位，然后将index位置设置为input
        for (int i = array.length - 1; i >= index; i--) {
            if (i == index) {
                array[i] = input;
            } else {
                array[i] = array[i - 1];
            }
        }
        return;
    }

    /**
     * 冒泡排序
     * @param unSortList
     * @return
     */
    private void bubbleSort(Integer[] unSortList) {
        if (1 >= unSortList.length) {
            return;
        }
        for (int i = 0; i < unSortList.length - 1; i++) {
            for (int j = 1; j < unSortList.length - i; j++) {
                if (unSortList[j - 1] > unSortList[j]) {
                    exchange(unSortList, j - 1, j);
                }
            }
        }
    }

    /**
     * 交换list中两个index的值
     * @param list
     * @param indexOne
     * @param indexTwo
     */
    private void exchange(Integer[] list, int indexOne, int indexTwo) {
        list[indexOne] = list[indexOne] ^ list[indexTwo];
        list[indexTwo] = list[indexOne] ^ list[indexTwo];
        list[indexOne] = list[indexOne] ^ list[indexTwo];
    }

    /**
     * 初始化一个num个数的数组
     * @param num
     * @return
     */
    private Integer[] initIntArray(int num, int bound) throws NoSuchAlgorithmException {
        if (1 > num) {
            return new Integer[]{};
        }
        if (1 > bound) {
            return new Integer[]{0};
        }
        Integer[] result = new Integer[num];
        for (int i = 0; i < num; i++) {
            result[i] = AlgorithmUtils.getRandomIntByBound(bound);
        }

        return result;
    }
}