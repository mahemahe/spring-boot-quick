package quick.algorithm.one;

/**
 * 证明下列公式：
 *
 * 设计一个泛型类Collection，它存储object对象的集合（在数组中），
 * 以及该集合当前的大小。提供public方法isEmtpy，makeEmpty,insert,remove,isPresent.
 * 方法isPresent(x)当且仅当在集合中存在（由equals定义） 等于x的一个object时返回true
 * @author mahe <mahe@maihaoche.com>
 * @date 2019-12-13
 */
public class ChapterOneQuestionThirteen {

    public static void main(String[] args) {

    }
}
class MyCollection<T> {
    T [] arrayTs;
    int count;
}
