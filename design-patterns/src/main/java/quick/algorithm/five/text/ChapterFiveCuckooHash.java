package quick.algorithm.five.text;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import org.apache.commons.math3.primes.Primes;

/**
 * cuckoo hashing（布谷鸟散列）
 * 本例中，并非采用典型的cuckoo hashing方式的多散列表（每个散列表一个hash function），
 * 而是只使用一个散列表，同时使用多个散列函数，计算出的列表位置为空就确定这个位置为当前对象位置与hash code
 * @author mahe <mahe@maihaoche.com>
 * @date 2020/4/9
 */
public class ChapterFiveCuckooHash {

    public static void main(String[] args) {

    }
}
class UniversalHash {
    /**
     * Mersenne Prime's power
     */
    public static final int DIGS = 31;
    /**
     * Mersenne Prime (2^31 - 1)
     */
    public static final int MERSENNE_PRIME = (1 << DIGS) - 1;

    /**
     * Universal Hashing Function
     * h(x) = ((A * x  + B) mod p) mod M
     * calculate
     *      A * x + B = hashVal => val = hashVal mod p = q' + r' = (hashVal >> DIGS) + (h & MERSENNE_PRIME)
     *      {and MERSENNE_PRIME = 2^DIGS - 1}
     *      then finalVal = val mod M
     * @param x input
     * @param A hashing function's coefficient
     * @param B hashing function's constant
     * @param M table size
     * @return hashing value
     */
    public static int universalHash(int x, int A, int B, int M) {
        long hashVal = (long) A * x + B;
        hashVal = (hashVal >> DIGS) + (hashVal & MERSENNE_PRIME);
        if (hashVal >= MERSENNE_PRIME) {
            hashVal -= MERSENNE_PRIME;
        }

        // M is far lower than Mersenne prime, so calculate the module directly
        return (int) hashVal % M;
    }
}

class CuckooHashTable<T> {
    private static final int DEFAULT_TABLE_SIZE = 101;
    private static final int ALLOWED_REHASH = 1;
    private static final double MAX_LOAD = 0.4;

    private int currentSize;
    private T[] array;
    private final HashFamily<? super T> hf;
    private final int numHashFunctions;

    private int rehashes = 0;
    private SecureRandom r = SecureRandom.getInstance("SHA1PRNG");


    public CuckooHashTable(HashFamily<? super T> hashFamily) throws NoSuchAlgorithmException {
        this(hashFamily, DEFAULT_TABLE_SIZE);
    }
    public CuckooHashTable(HashFamily<? super T> hashFamily, int tableSize) throws NoSuchAlgorithmException {
        allocateArray(nextPrime(tableSize));
        doClear();
        this.hf = hashFamily;
        numHashFunctions = hf.getNumberOfFunctions();
    }

    private void allocateArray(int tableSize) {
        array = (T[]) new Object[tableSize];
    }
    public void makeEmpty() {
        doClear();
    }
    private void doClear() {
        currentSize = 0;
        for (int i = 0; i < array.length; i++) {
            array[i] = null;
        }
    }
    private int nextPrime(int x) {
        return Primes.nextPrime(x);
    }

    public boolean contains(T data) {
        return findPos(data) != -1;
    }

    public boolean insert(T data) {
        if (contains(data)) {
            return false;
        }
        if (currentSize >= array.length * MAX_LOAD) {
            expand();
        }

        return insertHelper1(data);
    }

    private boolean insertHelper1(T data) {
        final int COUNT_LIMIT = 100;

        while (true) {
            // last replaced position
            int lastPos = -1;
            // current position
            int pos;

            // replace position with current element until find an empty position in the array
            // can't retry more than COUNT_LIMIT times because prevent loops
            for (int count = 0; count < COUNT_LIMIT; count++) {
                // find an available position without rehash
                for (int i = 0; i < numHashFunctions; i++) {
                    pos = myHash(data, i);
                    if (array[pos] == null) {
                        array[pos] = data;
                        currentSize++;
                        return true;
                    }
                }

                // randomly evict out one element which is in array already
                int i = 0;
                do {
                    pos = myHash(data, r.nextInt(numHashFunctions));
                    // new position can't equal last replaced position, otherwise retry to get new pos and can't retry more than 5 times
                    // to prevent loops
                } while (pos == lastPos && i++ < 5);

                // replace the element
                T temp = array[lastPos = pos];
                array[pos] = data;
                data = temp;
            }


            if (++rehashes > ALLOWED_REHASH) {
                // can't rehash then expend the table
                // a bigger table size
                expand();
                rehashes = 0;
            } else {
                // if the method above doesn't work, do rehash only once
                // the same table size, new hash function
                rehash();
            }
        }
    }

    private void expand() {
        rehash();
    }

    /**
     * change all of hashing functions and re-calculating hash codes
     */
    private void rehash() {
        // change the hashing functions
        hf.generateNewFunctions();
        rehash(array.length);
    }

    private void rehash(int newTableSize) {
        T[] oldArray = array;
        // reset array
        allocateArray(nextPrime(newTableSize));

        for (T temp : oldArray) {
            if (temp != null) {
                insert(temp);
            }
        }
    }

    public boolean remove(T data) {
        int pos = findPos(data);
        if (pos != -1) {
            array[pos] = null;
            currentSize--;
        }

        return pos != -1;
    }
    /**
     * iterate all hash functions to find position of x in the array
     * @param x
     * @return
     */
    private int findPos(T x) {
        for (int i = 0; i < numHashFunctions; i++) {
            int pos = myHash(x, i);
            if (array[pos] != null && array[pos].equals(x)) {
                return pos;
            }
        }
        return -1;
    }
    private int myHash(T x, int which) {
        int hashVal = hf.hash(x, which);
        hashVal %= array.length;
        // keep the hashVal positive
        if (hashVal < 0) {
            hashVal += array.length;
        }
        return hashVal;
    }
}

/**
 * apply a series of hash functions
 * @param <T>
 */
interface HashFamily<T> {
    int hash(T x, int which);
    int getNumberOfFunctions();
    void generateNewFunctions();
}