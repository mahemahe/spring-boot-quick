package quick.patterns.singleton;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author mahe <mahe@maihaoche.com>
 * @date 2019-11-09
 */
public class SingletonModel {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        testParallel();
    }

    private static void testParallel() throws ExecutionException, InterruptedException {
        for (int k = 0; k < 3; k++) {
            Singleton.singleton = null;
            final int x = k;
            for (int i = 0; i < 10; i++) {
                CompletableFuture.runAsync(() -> getSingleton(x));
            }
        }
    }

    static void getSingleton(int i) {
        System.out.println(i + "---" +Singleton.getInstance());
    }

    static class SingletonLook implements Runnable {

        /**
         * When an object implementing interface <code>Runnable</code> is used to create a thread, starting the thread
         * causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method <code>run</code> is that it may take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            System.out.println(Singleton.getInstance());
        }
    }
}
