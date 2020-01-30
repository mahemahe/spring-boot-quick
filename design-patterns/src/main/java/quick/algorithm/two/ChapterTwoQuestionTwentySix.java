package quick.algorithm.two;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 求一个数组中的主元素（主值），满足特征为数组元素个数为N，主值出现次数 > N/2
 * 查询逻辑：
 * 1.因为主值元素数量大于总数量的一半，所以每相邻的两个元素中，至少有一组全是主值元素组成
 * 所以按顺序将每两个元素看成一组，组内元素相同，则将此数放入另一数组B中；否则不用放
 * 2.然后对数组B递归重复这个过程，直到数组中剩余元素 <=2，这时这个数组中元素为候选主值
 * 3.再用候选主值去数组中验证，得到结果
 * 注：在1中，如果数组中元素个数为奇数时，可将最后一个元素去掉，剩余偶数个元素的数组进行找主值的过程，如果没找到，则说明去掉的这个是主值
 * @author mahe <mahe@maihaoche.com>
 * @date 2020-01-19
 */
public class ChapterTwoQuestionTwentySix {
    public static void main(String[] args) {
        TestTwentySix test = new TestTwentySix();
        Integer mainNumber = test.findMajor(TestTwentySix.arrayA);
        System.out.println("序列：" + new Gson().toJson(TestTwentySix.arrayA) + "，长度为：" + TestTwentySix.arrayA.length);
        System.out.println("主值为：" + mainNumber);
    }
}
class TestTwentySix {
    static Integer[] arrayA = {3,4,3,4,3,4,4,3,4,4,2,2,2,4,4};

    public Integer findMajor(Integer[] arr) {
        Integer[] candidateArr = findMajorFuzzy(arr);
        if (0 == candidateArr.length) {
            System.out.println("没有主值");
            return null;
        }
        for (int temp : candidateArr) {
            Integer candidate = checkMainNumber(arr, temp);
            if (null == candidate) {
                continue;
            }
            return temp;
        }
        return null;
    }

    /**
     * 查询array主值的候选项
     * @param arr
     * @return
     */
    public Integer[] findMajorFuzzy(Integer[] arr) {
        if (2 >= arr.length) {
            return arr;
        }
        // 候选值
        // 数组为奇数时
        if (1 == arr.length % 2) {
            Integer candidate = null;
            candidate = arr[arr.length - 1];
            Integer[] result = findMajorFuzzy(Arrays.copyOfRange(arr, 0, arr.length - 1));
            if (0 == result.length) {
                result = new Integer[1];
                result[0] = candidate;
            }
            return result;
        }
        // 数组为偶数时
        arr = findCouple(arr);
        return findMajorFuzzy(arr);
    }

    /**
     * 两两分组数值，将组内值相同的数值放入新数组，并返回新数组
     * @param arr
     * @return
     */
    public Integer[] findCouple(Integer[] arr) {
        List<Integer> newArr = new ArrayList<>(arr.length / 2);
        for (int i = 0; i < arr.length; i = i + 2) {
            if (Objects.equals(arr[i], arr[i + 1])) {
                newArr.add(arr[i]);
            }
        }
        if (0 == newArr.size()) {
            return null;
        }
        return newArr.toArray(new Integer[newArr.size()]);
    }

    /**
     * 校验候选值是否是arr中的主值
     * @param arr
     * @param candidate
     * @return
     */
    public Integer checkMainNumber(Integer[] arr, int candidate) {
        int count = 0;
        for (int temp : arr) {
            if (candidate == temp) {
                count++;
            }
        }
        if (count * 2 > arr.length) {
            return candidate;
        }
        return null;
    }
}