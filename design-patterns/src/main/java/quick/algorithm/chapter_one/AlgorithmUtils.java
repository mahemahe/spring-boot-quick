package quick.algorithm.chapter_one;

import java.util.Random;

/**
 * @author mahe <mahe@maihaoche.com>
 * @date 2019-11-12
 */
public class AlgorithmUtils {
    private static final Random RANDOM = new Random();

    /**
     * 根据bound，获取随机数
     * @param bound
     * @return
     */
    public static Integer getRandomIntByBound(int bound) {
        return RANDOM.nextInt(bound);
    }

    public static <T>  String convertArrayToString(T[] objects) {
        if (null == objects || 0 == objects.length) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < objects.length; i++) {
            if (i != 0) {
                sb.append(", ");
            }
            sb.append(objects[i]);
        }

        return sb.toString();
    }

    public static String convertArrayArrayToString(char[][] objects) {
        if (null == objects || 0 == objects.length) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < objects.length; j++) {
            if (j != 0) {
                sb.append("\n");
            }
            for (int i = 0; i < objects[j].length; i++) {
                sb.append(objects[j][i]);
            }
        }

        return sb.toString();
    }
}
