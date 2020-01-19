package quick.algorithm.one;

/**
 * 编写带有下列声明的例程：
 * public void permute(String str);
 * private void permute(char[] str, int low, int high);
 * 第一个例程是个驱动程序，它调用第二个例程并显示String str中的字符的所有排列。
 * 例如，str是"abc"， 那么输出的串则是abc，acb，bac，bca，cab，cba，第二个例程使用递归。
 * @author mahe <mahe@maihaoche.com>
 * @date 2019-12-13
 */
public class ChapterOneQuestionSix {
    public static void main(String[] args) {
        permute("人过大佛寺");
    }

    public static void permute(String str) {
        permute(str.toCharArray(), 0, str.length() - 1);
    }

    /**
     * 输出排序字符串
     * 思路：从前到后，逐位确认当前序号的位置的字符是什么，在外确认到最后一位前，要进行递归处理，否则输出当前的字符串
     * @param chars 字符串
     * @param low 从哪里开始排序
     * @param high 到哪里结束排序
     */
    private static void permute(char[] chars, int low, int high) {
        // 已确定到最后一位时，进行字符输出
        if (low == high) {
            System.out.println(chars);
        }
        // 如果没有到最后一位，则需要继续确定low位置的字符
        for (int i = low; i <= high; i++) {
            char[] newChars = chars.clone();;
            exchange(newChars, low, i);
            permute(newChars, low + 1, high);
        }
    }

    /**
     * 交换数组中两个位置的字符
     * @param chars
     * @param indexA
     * @param indexB
     */
    private static void exchange(char[] chars, int indexA, int indexB) {
        if (indexA == indexB) {
            return;
        }
        chars[indexA] = (char) (chars[indexA] ^ chars[indexB]);
        chars[indexB] = (char) (chars[indexA] ^ chars[indexB]);
        chars[indexA] = (char) (chars[indexA] ^ chars[indexB]);
    }

}
