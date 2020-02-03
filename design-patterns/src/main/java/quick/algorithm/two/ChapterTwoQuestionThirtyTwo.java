package quick.algorithm.two;

/**
 * @author mahe <mahe@maihaoche.com>
 * @date 2020/1/30
 */
public class ChapterTwoQuestionThirtyTwo {
    public static int[] arr = {1, 3, 5, 6, 8, 9, 10};
    public static void main(String[] args) {
        int result = findInHalf(arr, 6);
        System.out.println("值的位置index是：" + result);
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
