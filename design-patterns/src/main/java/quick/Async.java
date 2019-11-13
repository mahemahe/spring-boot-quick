package quick;

/**
 * 语句重排：jvm的语句执行优化，优化后，代码未必为coding时的顺序，但相互依赖的语句，一定会保证顺序
 *
 * jvm的有序性，是依靠happens-before原则
 * 下面就来具体介绍下happens-before原则（先行发生原则）：
 *
 * 程序次序规则：一个线程内，按照代码顺序，书写在前面的操作先行发生于书写在后面的操作
 * 锁定规则：一个unLock操作先行发生于后面对同一个锁额lock操作
 * volatile变量规则：对一个变量的写操作先行发生于后面对这个变量的读操作
 * 传递规则：如果操作A先行发生于操作B，而操作B又先行发生于操作C，则可以得出操作A先行发生于操作C
 * 线程启动规则：Thread对象的start()方法先行发生于此线程的每个一个动作
 * 线程中断规则：对线程interrupt()方法的调用先行发生于被中断线程的代码检测到中断事件的发生
 * 线程终结规则：线程中所有的操作都先行发生于线程的终止检测，我们可以通过Thread.join()方法结束、Thread.isAlive()的返回值手段检测到线程已经终止执行
 * 对象终结规则：一个对象的初始化完成先行发生于他的finalize()方法的开始
 * @author mahe <mahe@maihaoche.com>
 * @date 2019-11-10
 */
public class Async {
    String context;

    /**
     * 上面代码中，由于语句1和语句2没有数据依赖性，因此可能会被重排序。
     * 假如发生了重排序，在线程1执行过程中先执行语句2，而此是线程2会以为初始化工作已经完成，
     * 那么就会跳出while循环，去执行doSomethingwithconfig(context)方法，而此时context并没有被初始化，就会导致程序出错。
     * @throws InterruptedException
     */
    public void test() throws InterruptedException {
        //线程1:
        //语句1
        context = loadContext();
        //语句2
        boolean inited = true;

        //线程2:
        while(!inited ){
            Thread.sleep(1);
        }
        doSomethingWithConfig(context);

        // 因为l25依赖inited的数据，所以这样一定排在l22后，同理l20与l28也是一组有顺序的代码，但l20与l22是相互无关的
    }

    private void doSomethingWithConfig(String context) {
        System.out.println(context);
    }

    private String loadContext() {
        return "abc";
    }
}
