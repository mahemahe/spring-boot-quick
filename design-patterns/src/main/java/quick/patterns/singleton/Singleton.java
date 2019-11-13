package quick.patterns.singleton;

/**
 * 2.volatile保证原子性吗？
 *
 * 　　从上面知道volatile关键字保证了操作的可见性，但是volatile能保证对变量的操作是原子性吗？
 *
 * 　　下面看一个例子：
 * public class Test {
 *     public volatile int inc = 0;
 *
 *     public void increase() {
 *         inc++;
 *     }
 *
 *     public static void main(String[] args) {
 *         final Test test = new Test();
 *         for(int i=0;i<10;i++){
 *             new Thread(){
 *                 public void run() {
 *                     for(int j=0;j<1000;j++)
 *                         test.increase();
 *                 };
 *             }.start();
 *         }
 *
 *         while(Thread.activeCount()>1)  //保证前面的线程都执行完
 *             Thread.yield();
 *         System.out.println(test.inc);
 *     }
 * }
 *  　　大家想一下这段程序的输出结果是多少？也许有些朋友认为是10000。但是事实上运行它会发现每次运行结果都不一致，都是一个小于10000的数字。
 *
 * 　　可能有的朋友就会有疑问，不对啊，上面是对变量inc进行自增操作，由于volatile保证了可见性，那么在每个线程中对inc自增完之后，在其他线程中都能看到修改后的值啊，所以有10个线程分别进行了1000次操作，那么最终inc的值应该是1000*10=10000。
 *
 * 　　这里面就有一个误区了，volatile关键字能保证可见性没有错，但是上面的程序错在没能保证原子性。可见性只能保证每次读取的是最新的值，但是volatile没办法保证对变量的操作的原子性。
 *
 * 　　在前面已经提到过，自增操作是不具备原子性的，它包括读取变量的原始值、进行加1操作、写入工作内存。那么就是说自增操作的三个子操作可能会分割开执行，就有可能导致下面这种情况出现：
 *
 * 　　假如某个时刻变量inc的值为10，
 *
 * 　　线程1对变量进行自增操作，线程1先读取了变量inc的原始值，然后线程1被阻塞了；
 *
 * 　　然后线程2对变量进行自增操作，线程2也去读取变量inc的原始值，由于线程1只是对变量inc进行读取操作，而没有对变量进行修改操作，所以不会导致线程2的工作内存中缓存变量inc的缓存行无效，所以线程2会直接去主存读取inc的值，发现inc的值时10，然后进行加1操作，并把11写入工作内存，最后写入主存。
 *
 * 　　然后线程1接着进行加1操作，由于已经读取了inc的值，注意此时在线程1的工作内存中inc的值仍然为10，所以线程1对inc进行加1操作后inc的值为11，然后将11写入工作内存，最后写入主存。
 *
 * 　　那么两个线程分别进行了一次自增操作后，inc只增加了1。
 *
 * 　　解释到这里，可能有朋友会有疑问，不对啊，前面不是保证一个变量在修改volatile变量时，会让缓存行无效吗？然后其他线程去读就会读到新的值，对，这个没错。这个就是上面的happens-before规则中的volatile变量规则，但是要注意，线程1对变量进行读取操作之后，被阻塞了的话，并没有对inc值进行修改。然后虽然volatile能保证线程2对变量inc的值读取是从内存中读取的，但是线程1没有进行修改，所以线程2根本就不会看到修改的值。
 *
 * 　　根源就在这里，自增操作不是原子性操作，而且volatile也无法保证对变量的任何操作都是原子性的。
 *
 *
 * 3.volatile能保证有序性吗？
 *
 * 　　在前面提到volatile关键字能禁止指令重排序，所以volatile能在一定程度上保证有序性。
 *
 * 　　volatile关键字禁止指令重排序有两层意思：
 *
 * 　　1）当程序执行到volatile变量的读操作或者写操作时，在其前面的操作的更改肯定全部已经进行，且结果已经对后面的操作可见；在其后面的操作肯定还没有进行；
 *
 * 　　2）在进行指令优化时，不能将在对volatile变量访问的语句放在其后面执行，也不能把volatile变量后面的语句放到其前面执行。
 *
 * 　　可能上面说的比较绕，举个简单的例子：
 *
 * //x、y为非volatile变量
 * //flag为volatile变量
 *
 * x = 2;        //语句1
 * y = 0;        //语句2
 * flag = true;  //语句3
 * x = 4;         //语句4
 * y = -1;       //语句5
 *  　　由于flag变量为volatile变量，那么在进行指令重排序的过程的时候，不会将语句3放到语句1、语句2前面，也不会讲语句3放到语句4、语句5后面。但是要注意语句1和语句2的顺序、语句4和语句5的顺序是不作任何保证的。
 *
 * 　　并且volatile关键字能保证，执行到语句3时，语句1和语句2必定是执行完毕了的，且语句1和语句2的执行结果对语句3、语句4、语句5是可见的。
 *
 * 　　那么我们回到前面举的一个例子：
 *
 * //线程1:
 * context = loadContext();   //语句1
 * inited = true;             //语句2
 *
 * //线程2:
 * while(!inited ){
 *   sleep()
 * }
 * doSomethingwithconfig(context);
 *  　　前面举这个例子的时候，提到有可能语句2会在语句1之前执行，那么久可能导致context还没被初始化，而线程2中就使用未初始化的context去进行操作，导致程序出错。
 *
 * 　　这里如果用volatile关键字对inited变量进行修饰，就不会出现这种问题了，因为当执行到语句2时，必定能保证context已经初始化完毕。
 * @author mahe <mahe@maihaoche.com>
 * @date 2019-11-09
 */
public class Singleton {

    public static volatile Singleton singleton;

    private Singleton() {}

    public static Singleton getInstance() {
        if (null == singleton) {
            synchronized (Singleton.class) {
                if (null == singleton) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}
