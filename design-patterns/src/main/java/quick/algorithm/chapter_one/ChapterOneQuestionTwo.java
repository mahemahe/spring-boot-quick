package quick.algorithm.chapter_one;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 注意：在char[]数组转为string时，会按照char数组长度完全转换为string，就算后面或部分数组成员为空。
 *      这时需要将数组中的空对象刨除，余下的char组成string
 * @author mahe <mahe@maihaoche.com>
 * @date 2019-11-12
 */
public class ChapterOneQuestionTwo {
    public static void main(String[] args) {
        try {
            TestTwo two = new TestTwo(new String[]{"sport", "all", "game", "hah", "word", "hello", "as", "to", "go", "high"});
            char[][] chars= two.createCharsBySideLength(2000);
            // 打印待匹配字符
            System.out.println(AlgorithmUtils.convertArrayArrayToString(chars));
            long start = System.currentTimeMillis();
            List<String> matchedWords = two.matchWords(chars);
            long end = System.currentTimeMillis();
            System.out.println("计时：" + new BigDecimal(end - start).divide(new BigDecimal(1000)).toPlainString());
            System.out.println("匹配的words：");
            matchedWords.stream().distinct().forEach(temp -> System.out.println(temp));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
class TestTwo {
    private String[] words;
    private int minLength;
    private int maxLength;

    /**
     * 设置要匹配的单词字典
     * @param words
     */
    public TestTwo(String[] words) throws Exception {
        if (null == words || 0 == words.length) {
            throw new Exception("There're no words");
        }
        this.words = words;
        for (int i = 0; i < words.length; i++) {
            if (0 == i) {
                maxLength = minLength = words[i].length();
                continue;
            }

            if (minLength > words[i].length()) {
                minLength = words[i].length();
            }
            if (maxLength < words[i].length()) {
                maxLength = words[i].length();
            }
        }
    }

    /**
     * 匹配随机生成的字符数组，与单词字典，并输出正确匹配的值
     * @return
     */
    public List<String> matchWords(char[][] chars) {
        char[][] toBeMatchChars = chars;
        // 匹配的结果
        List<String> matchedWords = new ArrayList<>(toBeMatchChars.length);
        // 向3个方向获取字符
        for (int i = 0; i < toBeMatchChars.length - (minLength - 1); i++) {
            for (int j = 0; j < toBeMatchChars[i].length - (minLength - 1); j++) {
                // 向三个方向提取字符串
                List<String> pickWords = pickWords(toBeMatchChars, i, j);
                // 当前位置复合条件的单词s
                List<String> partialMatchedWords = matchWordsWithDictionary(pickWords);
                if (partialMatchedWords.size() > 0) {
                    matchedWords.addAll(partialMatchedWords);
                }
            }
        }

        return matchedWords;
    }

    /**
     * 与字典比较，获取匹配的words
     * @param toBeMatchedWords 带匹配words
     * @return 匹配words
     */
    public List<String> matchWordsWithDictionary(List<String> toBeMatchedWords) {
        List<String> matchedWords = new ArrayList<>();
        String temp;
        for (int j = 0; j < toBeMatchedWords.size(); j++) {
            for (int i = 0; i < words.length; i++) {
                temp = toBeMatchedWords.get(j);
                if (temp.equals(String.valueOf(words[i]))) {
                    matchedWords.add(temp);
                    break;
                }
            }
        }

        return matchedWords;
    }

    /**
     * 向3个方向获取字符
     * 字符长度要在minLength与maxLength之间
     * @param chars
     * @param verticalIndex 垂直方向的序号
     * @param horizontalIndex 水平方向序号
     * @return
     */
    public List<String> pickWords(char[][] chars, int verticalIndex, int horizontalIndex) {
        List<String> result = new ArrayList<>(chars.length);
        // 垂直方向字符串
        char[] verticalChars = new char[maxLength];
        // 水平方向字符串
        char[] horizontalChars = new char[maxLength];
        // 斜向字符串
        char[] obliqueChars = new char[maxLength];

        // 按照字符长度循环
        for (int i = 0; i < maxLength; i++) {
            // 垂直方向是否未超界
            boolean verticalNotOverSuperBound = verticalIndex + i < chars.length;
            // 水平方向是否未超界
            boolean horizontalNotOverSuperBound = horizontalIndex + i < chars[verticalIndex].length;
            // 斜向是否未超界
            boolean obliqueNotOverSuperBound = verticalNotOverSuperBound && horizontalNotOverSuperBound;
            // 是否有未超界方向
            boolean notOverSuperBound = verticalNotOverSuperBound || horizontalNotOverSuperBound;
            // 字符长度是否为有效长度
            boolean validWord = minLength <= i + 1;

            // 垂直方向
            if (verticalNotOverSuperBound) {
                verticalChars[i] = chars[verticalIndex + i][horizontalIndex];
                if (validWord) {
                    result.add(charsToString(verticalChars));
                }
            }
            if (horizontalNotOverSuperBound) {
                // 水平方向
                horizontalChars[i] = chars[verticalIndex][horizontalIndex + i];
                if (validWord) {
                    result.add(charsToString(horizontalChars));
                }
            }
            if (obliqueNotOverSuperBound) {
                // 斜向
                obliqueChars[i] = chars[verticalIndex + i][horizontalIndex + i];
                if (validWord) {
                    result.add(charsToString(obliqueChars));
                }
            }

            if (!notOverSuperBound) {
                break;
            }
        }

        return result;
    }

    /**
     * 将char数组中的非空字符连起来组成String
     * @param chars 字符数组
     * @return 字符串
     */
    public String charsToString(char[] chars) {
        if (null == chars || 0 == chars.length) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            if (0 == c) {
                break;
            }
            sb.append(c);
        }
        return sb.toString();
    }

    public char[][] createCharsBySideLength(int n) {
        char[][] result = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = (char) (97 + AlgorithmUtils.getRandomIntByBound(26));
            }
        }

        return result;
    }
}
