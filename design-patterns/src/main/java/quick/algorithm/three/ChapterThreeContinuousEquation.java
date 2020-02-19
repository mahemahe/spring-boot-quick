package quick.algorithm.three;

/**
 * 连等式
 * 其执行过程为从右向左依次执行赋值过程
 * @author mahe <mahe@maihaoche.com>
 * @date 2020/2/12
 */
public class ChapterThreeContinuousEquation {
    public static void main(String[] args) {
        /***连等式测试1****/
        MainTest testA = new MainTest(1);
        MainTest testB = new MainTest(2);
        MainTest testC = testA;
        // 这里的testA.node，在连等开始时，
        // 就已经指定了是testA对象的node，
        // 就算执行testA=testB后，testA的引用指向了testB的对象，
        // 但testA.node的句柄，还是原testA对象中node的引用位置，
        // 所以在连等赋值完成后，最新的testA的node相当于是testB的node为null，
        // 而原testA对象的node为新testA指向的testB对象，
        // 此时，testC指向了原testA对象，所以testC.node指向了新testA即testB
        // 所以，下面的连等，并不等同于testA=testB; testA.node=testA;
        testA.node = testA = testB;

        /***连等式测试2****/
        Node one = new Node(1, null, null);
        Node three = new Node(3, one, null);
        one.next = three;
        Node two = null;
        // 这里并没有问题，因为没有变更three所引用的对象，所以并不反常规逻辑
        three.prev.next = three.prev = two = new Node(2, three.prev, three);
        System.out.println(three + "    " + two + "    " + one);
    }
}
class MainTest{
    public MainTest node;
    public int n;
    public MainTest(int n) {
        this.n = n;
    }

    public void print() {
        System.out.println("n = " + n);
    }
}
class Node {
    int now;
    protected Node prev;
    protected Node next;
    Node(int now, Node prev, Node next) {
        this.now = now;
        this.prev = prev;
        this.next = next;
    }
}