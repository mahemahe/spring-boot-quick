package quick.algorithm.four.homework;

import cn.hutool.core.util.StrUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * code a program, list all of files and their size in a dictionary
 * @author mahe <mahe@maihaoche.com>
 * @date 2020/3/10
 */
public class ChapterFourQuestionTen {
    public static void main(String[] args) {
        MyDictionary myDictionary = new MyDictionary();
        myDictionary.addFile("file1.jps", 100, "/usr/mahe/test/");
        myDictionary.addFile("picture.png", 1200, "/usr/mahe/test/");
        myDictionary.addFile("didi.xxx", 200, "/usr/mahe/application/");
        myDictionary.addFile("test.java", 92200, "/usr/mahe/application/");
        myDictionary.addFile("first.jar", 11112200, "/usr/mahe/application/");
        myDictionary.addFile("video.jar", 111312237, "/usr/mahe/video/");
        myDictionary.listAll();
    }
}
class MyDictionary {
    private Folder root;
    private Stack<String> outputStack;

    public void listAll() {
        listAll(root);
        while (!outputStack.isEmpty()) {
            System.out.println(outputStack.pop());
        }
    }

    /**
     * print all elements of the folder, and return the folder's total size
     * @param current
     * @return
     */
    private int listAll(Folder current) {
        int totalSize = current.size;

        // print all elements of the folder , then print current folder's information
        for (FolderOrFile temp : current.folderOrFiles) {
            if (isFolder(temp)) {
                totalSize += listAll((Folder) temp);
            } else {
                totalSize += temp.size;
                println(temp.depth, temp.name, temp.size);
            }
        }

        println(current.depth, current.name, totalSize);
        return totalSize;
    }

    private void println(int depth, String name, int size) {
        StringBuilder output = new StringBuilder("");
        while (depth-- > 0) {
            output.append("\t");
        }
        output.append(name)
                .append("    size:")
                .append(size);
        outputStack.push(output.toString());
    }

    public void addFile(String name, int size, String path) {
        if (StrUtil.isBlank(name)) {
            throw new NullPointerException();
        }

        Folder folder = getOrAddFolder(path);
        File file = new File(name, size, folder.depth + 1);
        folder.folderOrFiles.add(file);
    }

    private void addFolder(String path) {
        getOrAddFolder(path);
    }

    private Folder getOrAddFolder(String path) {
        if (StrUtil.isBlank(path)) {
            throw new NullPointerException();
        }

        // change the path str to node list
        StringTokenizer st = new StringTokenizer(path, "//");
        Folder current = root;
        int i = 0;
        lableWhile: while (st.hasMoreElements()) {
            i++;
            String currentPathName = (String) st.nextElement();
            for (FolderOrFile temp : current.folderOrFiles) {
                if (currentPathName.equals(temp.name)) {
                    if (isFolder(temp)) {
                        current = (Folder) temp;
                        continue lableWhile;
                    }
                }
            }

            Folder folder = new Folder(currentPathName, i);
            current.folderOrFiles.add(folder);
            current = folder;
        }

        return current;
    }

    MyDictionary() {
        this.root = new Folder("root", 0);
        outputStack = new Stack<>();
    }

    private boolean isFolder(FolderOrFile object) {
        if (object == null) {
            return false;
        }
        if (object instanceof Folder) {
            return true;
        }
        return false;
    }

    private class File extends FolderOrFile {
        public File(String name, int size, int depth) {
            this.name = name;
            this.size = size;
            this.depth = depth;
        }
    }

    private class Folder extends FolderOrFile {
        List<FolderOrFile> folderOrFiles;

        public Folder(String name, int depth) {
            this(name, new ArrayList<>());
            this.depth = depth;
        }
        public Folder(String name, List<FolderOrFile> folderOrFiles) {
            size = 1;
            depth = 1;
            this.name = name;
            this.folderOrFiles = folderOrFiles;
        }
    }

    abstract class FolderOrFile {
        String name;
        int size;
        int depth;
    }
}
