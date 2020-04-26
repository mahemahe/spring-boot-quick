package quick.virtualmachine;
import	java.util.ArrayList;

import java.util.List;

/**
 * @author mahe <mahe@maihaoche.com>
 * @date 2020/4/24
 */
public class ChapterTwoOom {

    public static void main(String[] args) {
        String a2 = "a";
        String a3 = "a";
        System.out.println("a3 = a2 ? " + (a3 == a2));

        long l1 = 1;
        long l2 = 1;
        Long l3 = new Long(1);
        Long l4 = Long.valueOf(1);
        Long l5 = Long.valueOf("1");
        Long l6 = 1L;

        System.out.println("l1 = l2 ? " + (l1 == l2));
        System.out.println("l3 = l2 ? " + (l3 == l2));
        System.out.println("l3 = l4 ? " + (l3 == l4));
        System.out.println("l5 = l4 ? " + (l5 == l4));
        System.out.println("l5 = l6 ? " + (l5 == l6));
    }

    /**
     * -Xss256k
     * stack depth: 99
     */
//    public static void main(String[] args) {
//        try {
//            JavaVMStackSOF2.test();
//        } catch (Error e) {
//            System.out.println("stack depth : " + JavaVMStackSOF2.stackDepth);
//            throw e;
//        }
//    }

    /**
     * -Xss 256k
     * stack depth: 1890
     */
//    public static void main(String[] args) {
//
//        JavaVMStackSOF of = new JavaVMStackSOF();
//        try {
//            of.stackLeak();
//        } catch (StackOverflowError e) {
//            System.out.println("stack depth : " + of.stackLength);
//            throw e;
//        }
//    }

//    public static void main(String[] args) {
//        List<Oom> list = new ArrayList<> (100000);
//        int count = 0;
//        while(true) {
//            list.add(new Oom(count++));
//        }
//    }

    static class Oom {
        private int count;

        public Oom(int count) {
            this.count = count;
        }
    }

    static class JavaVMStackSOF {
        private int stackLength = 1;

        public void stackLeak() {
            stackLength++;
            stackLeak();
        }
    }

    static class JavaVMStackSOF2 {
        private static int stackDepth = 0;

        public static void test() {
            long unused1, unused2, unused3, unused4, unused5,
                    unused6, unused7, unused8, unused9, unused10,
                    unused11, unused12, unused13, unused14, unused15,
                    unused16, unused17, unused18, unused19, unused20,
                    unused21, unused22, unused23, unused24, unused25,
                    unused26, unused27, unused28, unused29, unused30,
                    unused31, unused32, unused33, unused34, unused35,
                    unused36, unused37, unused38, unused39, unused40,
                    unused41, unused42, unused43, unused44, unused45,
                    unused46, unused47, unused48, unused49, unused50,
                    unused51, unused52, unused53, unused54, unused55,
                    unused56, unused57, unused58, unused59, unused60,
                    unused61, unused62, unused63, unused64, unused65,
                    unused66, unused67, unused68, unused69, unused70,
                    unused71, unused72, unused73, unused74, unused75,
                    unused76, unused77, unused78, unused79, unused80,
                    unused81, unused82, unused83, unused84, unused85,
                    unused86, unused87, unused88, unused89, unused90,
                    unused91, unused92, unused93, unused94, unused95,
                    unused96, unused97, unused98, unused99, unused100;
            stackDepth++;
            test();

            unused1 = unused2 = unused3 = unused4 = unused5 =
                    unused6 = unused7 = unused8 = unused9 = unused10 =
                    unused11 = unused12 = unused13 = unused14 = unused15 =
                    unused16 = unused17 = unused18 = unused19 = unused20 =
                    unused21 = unused22 = unused23 = unused24 = unused25 =
                    unused26 = unused27 = unused28 = unused29 = unused30 =
                    unused31 = unused32 = unused33 = unused34 = unused35 =
                    unused36 = unused37 = unused38 = unused39 = unused40 =
                    unused41 = unused42 = unused43 = unused44 = unused45 =
                    unused46 = unused47 = unused48 = unused49 = unused50 =
                    unused51 = unused52 = unused53 = unused54 = unused55 =
                    unused56 = unused57 = unused58 = unused59 = unused60 =
                    unused61 = unused62 = unused63 = unused64 = unused65 =
                    unused66 = unused67 = unused68 = unused69 = unused70 =
                    unused71 = unused72 = unused73 = unused74 = unused75 =
                    unused76 = unused77 = unused78 = unused79 = unused80 =
                    unused81 = unused82 = unused83 = unused84 = unused85 =
                    unused86 = unused87 = unused88 = unused89 = unused90 =
                    unused91 = unused92 = unused93 = unused94 = unused95 =
                    unused96 = unused97 = unused98 = unused99 = unused100 = 0;
        }
    }
}
