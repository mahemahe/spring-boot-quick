package quick.algorithm.four.text;
import	java.util.TreeMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Find the words that differ in only one character from the given ones
 * @author mahe <mahe@maihaoche.com>
 * @date 2020/3/9
 */
public class ChapterFourSimilarWords {
    public static void main(String[] args) {

    }
}
class CorrespondingWords {

    /**
     * Method 3
     * Computes a map in which the keys are words and values are Lists of words
     * that differ in only one character form the corresponding key.
     * Use an efficient algorithm that is O(N log N) with a TreeMap.
     *
     * improve point :
     *      group theWords by character's number first;
     *      work on each group separately: remove one character in specified position
     *          and compared
     *
     * 89000 words in 1 second
     */
    public static Map<String, List<String>> computeAdjacentWordsThree(List<String> theWords) {
        Map<String, List<String>> adjWords = new TreeMap<>();
        // having same length words
        Map<Integer, List<String>> samelengthWords = new TreeMap<>();
        for (String word : theWords) {
            update(samelengthWords, word.length(), word);
        }

        for (Map.Entry<Integer, List<String>> sameLengthWords : samelengthWords.entrySet()) {
            int length = sameLengthWords.getKey();

            for (int i = 0; i < length; ++i) {
                Map<String, List<String>> removeOneCharMap = new TreeMap<>();

                for (String word : sameLengthWords.getValue()) {
                    String removeOneChar = word.substring(0, i) + word.substring(i + 1);
                    update(removeOneCharMap, removeOneChar, word);
                }

                // similar words map
                for (List<String> slWords : removeOneCharMap.values()) {
                    if (slWords.size() <= 1) {
                        continue;
                    }

                    // similar words mutually
                    for (String word : slWords) {
                        for (String wordInner : slWords) {
                            if (word != wordInner) {
                                update(adjWords, word, wordInner);
                            }
                        }
                    }
                }
            }
        }

        return adjWords;
    }

    /**
     * Method 2
     * Computes a map in which the keys are words and values are Lists of words
     * that differ in only one character form the corresponding key.
     * Use a quadratic algorithm (with appropriate Map)
     *
     * improve point : group theWords by character's number first.
     * 89000 words in 16 seconds
     */
    public static Map<String, List<String>> computeAdjacentWordsTwo(List<String> theWords) {
        Map<String, List<String>> adjWords = new TreeMap<>();
        // having same length words
        Map<Integer, List<String>> slWords = new TreeMap<>();
        for (String word : theWords) {
            update(slWords, word.length(), word);
        }

        for (List<String> temp : slWords.values()) {
            String[] words = new String[temp.size()];
            temp.toArray(words);
            for (int i = 0; i < words.length; i++) {
                for (int j = i + 1; j < words.length; j++) {
                    if (onCharOff(words[i], words[j])) {
                        update(adjWords, words[i], words[j]);
                        update(adjWords, words[j], words[i]);
                    }
                }
            }
        }

        return adjWords;
    }

    /**
     * Method 1
     * Computes a map in which the keys are words and values are Lists of words
     * that differ in only one character form the corresponding key.
     * Use a quadratic algorithm (with appropriate Map)
     *
     * 89000 words in 75 seconds
     */
    public static Map<String, List<String>> computeAdjacentWordsOne(List<String> theWords) {
        Map<String, List<String>> adjWords = new TreeMap<> ();
        String[] words = new String[theWords.size()];
        theWords.toArray(words);
        for (int i = 0; i < words.length; i++) {
            for (int j = i + 1; j < words.length; j++) {
                if (onCharOff(words[i], words[j])) {
                    update(adjWords, words[i], words[j]);
                    update(adjWords, words[j], words[i]);
                }
            }
        }

        return adjWords;
    }

    /**
     * update the word into the list which finds by the keyword from the map
     * note : keyword can be string or integer
     */
    private static <T> void update(Map<T, List<String>> map, T keyword, String word) {
        List<String> words = map.get(keyword);
        if (null == words) {
            words = new ArrayList<>();
            map.put(keyword, words);
        }
        words.add(word);
    }

    /**
     * confirm the two words are different in only one character
     */
    private static boolean onCharOff(String word1, String word2) {
        if (word1.length() != word2.length()) {
            return false;
        }
        int differ = 0;
        for (int i = 0; i < word1.length(); i++) {
            if (word1.charAt(i) != word2.charAt(i)) {
                if (++differ > 1) {
                    return false;
                }
            }
        }
        return differ == 1;
    }

    /**
     * print the map
     */
    public static void printHighChangeable(Map<String, List<String>> adjWords, int minWords) {
        for (Map.Entry<String, List<String>> tempEntry : adjWords.entrySet()) {
            List<String> words = tempEntry.getValue();
            if (words.size() >= minWords) {
                System.out.print("key word :" + tempEntry.getKey() + "(");
                System.out.print(words.size() + ")");
                for (String s : words) {
                    System.out.print(" " + s);
                }
                System.out.println();
            }
        }
    }
}
