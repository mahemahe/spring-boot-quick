package quick.algorithm.two;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author mahe <mahe@maihaoche.com>
 * @date 2020/1/30
 */
public class ChapterTwoQuestionThirtyTwo {
    public static int[] arr = {1, 3, 5, 6, 8, 9, 10};
    public static void main(String[] args) {
        testArrayList();
//        testRemove();
//        int result = findInHalf(arr, 6);
//        System.out.println("值的位置index是：" + result);
    }

    public static void testArrayList() {
        int x = 10;
        int y = x >> 1;
        System.out.println("x : " + x + ", y : " + y);

        Integer[] list = {0, 1, 2, 3};
        int current = 1;
        System.out.println("获取的位置是：" + list[--current]);
    }

    public static void testEnforceFor() {
        List<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        for (Integer temp : list) {
            System.out.println(temp);
        }
    }

    public static void testRemove() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        Iterator<Integer> iterator = list.iterator();
        list.remove();
        while (iterator.hasNext()) {
            // 先将当前点位移动值目标值
            if (iterator.next() % 2 == 0) {
                // 再删除目标值
                iterator.remove();
            }
        }
        System.out.println("list的元素个数：" + list.size());
    }

    public static int findInHalf(int[] arr, int x) {
        int low = 0;
        int high = arr.length - 1;
        while (low + 1 < high) {
            int mid = (low + high) / 2;
            if (arr[mid] < x) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        if (arr[low] == x) {
            return low;
        } else if (arr[high] == x) {
            return high;
        } else {
            return -1;
        }
    }
}
