package quick.algorithm.one;
import java.io.FileNotFoundException;
import	java.io.FileReader;
import	java.io.BufferedReader;
import com.google.common.base.Strings;
import java.io.IOException;

/**
 * C允许拥有形如
 * #include filename
 * 的语句，它将filename读入并将其插入到include语句处。include语句可以嵌套：换句话说，文件
 * filename本身还可以包含include语句，但是显然一个文件在任何链接中都不能包含它自己。编写一个
 * 程序，使它读入被一些include语句修饰的文件并且输出这个文件。
 * @author mahe <mahe@maihaoche.com>
 * @date 2019-11-21
 */
public class ChapterOneQuestionFour {
    private static final String INCLUDE = "#include";
    private static String classPath = null;

    public static void main(String[] args) {
        classPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();

        try {
            String out = getFileText("a.txt");
            System.out.println(out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String getFileText(String filePath) throws FileNotFoundException {
        if (Strings.isNullOrEmpty(filePath)) {
            throw new IllegalArgumentException("filePath is empty!");
        }
        filePath = filePath.trim();

        FileReader fileReader = new FileReader(classPath + filePath);
        BufferedReader reader = new BufferedReader(fileReader);

        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while (null != (line = reader.readLine())) {
                int fileIndex = line.indexOf(INCLUDE);
                if (0 <= fileIndex) {
                    line = getFileText(line.substring(fileIndex + INCLUDE.length()));
                }
                sb.append(line);
                if (0 > fileIndex) {
                    sb.append("\n");
                }
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
}
