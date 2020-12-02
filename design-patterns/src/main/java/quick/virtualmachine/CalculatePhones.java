package quick.virtualmachine;
import com.google.gson.Gson;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import	java.util.StringTokenizer;

/**
 * @author mahe <mahe@maihaoche.com>
 * @date 4:21 下午
 */
public class CalculatePhones {

    /**
     *
     */
    public static void main(String[] args) {
        String[] ccc = {};
        String[] aaa = {"a", "b", ""};
        ccc = aaa;
        System.out.println(new Gson().toJson(ccc));
        String abc = "a,b,c";
        StringTokenizer prePhones = new StringTokenizer(abc, ",");
        while (prePhones.hasMoreTokens()) {
            System.out.println(prePhones.nextToken());
        }
    }
//    /**
//     *
//     */
//    public static void main(String[] args) {
//        StringTokenizer prePhones = new StringTokenizer(PhonesPre.prePhones, "\n");
//        StringTokenizer postPhones = new StringTokenizer(PhonesPre.postPhones, "\n");
//        Set<String> prePhonesSet = new HashSet<>(prePhones.countTokens()*2);
//        while (prePhones.hasMoreTokens()) {
//            prePhonesSet.add(prePhones.nextToken());
//        }
//
//        while (postPhones.hasMoreTokens()) {
//            prePhonesSet.remove(postPhones.nextToken());
//        }
//
//        Iterator<String> itr = prePhonesSet.iterator();
//        while (itr.hasNext()) {
//            System.out.println(itr.next());
//        }
//    }
}
