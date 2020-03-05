package quick.algorithm.three.homework;
import	java.util.ArrayList;

import java.util.List;

/**
 * 3.7 求程序的运行时间，程序如下
 * @author mahe <mahe@maihaoche.com>
 * @date 2020/2/21
 */
public class ChapterThreeQuestionSeven {

    /**
     * 一个for循环，如果list的size要是大于N，
     * lst.add(i) 此句执行的次数也就是 N，但由于 lst.trimToSize() 此句--将数组容量限制在当前的元素个数，
     * 所以每次list的size都等于当前元素个数，每次执行lst.add(i)时，都要对数组进行扩容，
     * 扩容的大小为 newCapacity = oldCapacity + (oldCapacity >> 1)，基本是扩大原大小的1.5倍
     * 然后进行数据拷贝到扩容后的数组中，所以执行数组拷贝次数为 N - 1 次，
     * 每次数组拷贝信息赋值操作的次数为当前数组元素大小，次数要小于N，所以总的来说赋值操作执行测试最大估计为 O(N的平方)
     */
    public static List<Integer> makeList(int N) {
        ArrayList<Integer> lst = new ArrayList<> ();
        for (int i = 0; i < N; i++) {
            lst.add(i);
            lst.trimToSize();
        }
        return lst;
    }
}
