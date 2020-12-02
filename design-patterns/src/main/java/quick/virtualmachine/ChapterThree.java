package quick.virtualmachine;

/**
 * @author mahe <mahe@maihaoche.com>
 * @date 9:39 上午
 */
public class ChapterThree {
    public static FinalizeEscapeGC SAVE_GC = null;

    /**
     *
     */
    public static void main(String[] args) throws InterruptedException {
        ChapterThree.SAVE_GC = new FinalizeEscapeGC();
        SAVE_GC = null;

        // gc action make FinalizeEscapeGC's object method finalize() be executed.
        System.gc();
        // wait for the thread which execute finalize() method, it has a low priority level.
        Thread.sleep(500);

        if (SAVE_GC != null) {
            SAVE_GC.isAlive();
        } else {
            System.out.println("It's dead!");
        }
        // one more gc
        SAVE_GC = null;
        System.gc();
        Thread.sleep(500);
        if (SAVE_GC != null) {
            SAVE_GC.isAlive();
        } else {
            System.out.println("It's dead!");
        }
    }

    /**
     * test gc, collect object
     */
//    public static void main(String[] args) {
//        ChapterThree three1 = new ChapterThree();
//        ChapterThree three2 = new ChapterThree();
//        three1.instance = three2;
//        three2.instance = three1;
//
//        three1 = null;
//        three2 = null;
//
//        System.gc();
//    }
}

class FinalizeEscapeGC {
    public void isAlive() {
        System.out.println("I'm still alive!");
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("Method finalize() executed!");
        super.finalize();
        ChapterThree.SAVE_GC = this;
    }
}