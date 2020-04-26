package quick.algorithm.five.text;
import java.util.ArrayList;
import java.util.HashMap;
import	java.util.LinkedList;

import java.util.List;
import java.util.Map;

/**
 * hash 分离链接法
 * @author mahe <mahe@maihaoche.com>
 * @date 2020/4/2
 */
public class ChapterFiveSeparateChainHash {
    public static void main(String[] args) {
        List<String>[] list = (ArrayList<String>[])new ArrayList[2];
    }
}

class SeparateChainingHashTable<T> {
    private static final int DEFAULT_TABLE_SIZE = 101;
    private List<T>[] theList;
    private int currentSize;

    public SeparateChainingHashTable() {
        this(DEFAULT_TABLE_SIZE);
    }
    /**
     * Construct the hash table
     * @param size approximate table size
     */
    public SeparateChainingHashTable(int size) {
        theList = new LinkedList[nextPrime(size)];
        for (int i = 0; i < theList.length; i++) {
            theList[i] = new LinkedList<T> ();
        }
    }

    public void insert(T data) {
        List<T> whichList = theList[myHash(data)];
        if (!whichList.contains(data)) {
            whichList.add(data);

            // rehash when we have exceeded theList's length
            if (++currentSize > theList.length) {
                rehash();
            }
        }
    }
    public void remove(T data) {
        List<T> whichList = theList[myHash(data)];
        if (whichList.contains(data)) {
            whichList.remove(data);
            currentSize--;
        }
    }
    public boolean contains(T data) {
        List<T> whichList = theList[myHash(data)];
        // cause each one in the array is initial
        return whichList.contains(data);
    }
    public void makeEmpty() {
        for (int i = 0; i < theList.length; i++) {
            theList[i].clear();
        }
        currentSize = 0;
    }

    private void rehash() {
        List<T>[] oldList = theList;

        // create new double-sized, empty table
        theList = new List[nextPrime(oldList.length * 2)];
        // initial new list
        for (int j = 0; j < theList.length; j++) {
            theList[j] = new LinkedList<>();
        }
    }
    private int myHash(T x) {
        int hashVal = x == null ? 0 : x.hashCode();
        hashVal %= theList.length;
        if (hashVal < 0) {
            hashVal += theList.length;
        }
        return hashVal;
    }

    /**
     * prime number : 素数
     * return the biggest prime number which is less than n
     */
    private static int nextPrime(int n) {
        /*see online code*/
        return 0;
    }
    private static boolean isPrime(int n) {
        /*see online code*/
        return false;
    }
}

class Employee {
    private String name;
    private double salary;
    private int seniority;

    @Override
    public boolean equals(Object o) {
        return o instanceof Employee && name.equals(((Employee)o).name);
    }
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}