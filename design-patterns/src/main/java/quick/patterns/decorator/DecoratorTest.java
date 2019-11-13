package quick.patterns.decorator;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mahe <mahe@maihaoche.com>
 * @date 2019-11-02
 */
public class DecoratorTest {
    private static Fruit fruit = new Fruit();
    private static Apple apple = new Apple();

    public static void main(String args[]) {
        testArray();
    }

    /**
     * 类型擦除会导致，String s = arr1[0].getT();报错
     */
    private static void testArray() {
        Banana<String>[] arr1 = (Banana<String>[])new Banana[3];
        Banana<Double> cell2 = new Banana<>(0.5);
        Object[] arr2 = arr1;
        arr2[0] = cell2;
        // 这里报错
        String s = arr1[0].getT();
        System.out.println(s);
        System.out.println(arr2);
    }

    private static void testFruit() {
        List<Fruit> arr = new ArrayList<>();
        arr.add(fruit);
        arr.add(fruit);

        List<Apple> apples = new ArrayList<>();
        apples.add(apple);
        apples.add(apple);

        findMax(arr);
        findMax(apples);
    }

    private static <AnyType extends Comparable<? super AnyType>> AnyType findMax(List<AnyType> arr) {
        int maxIndex = 0;
        for (int i = 1; i< arr.size(); i++) {
            if (arr.get(i).compareTo(arr.get(maxIndex)) > 0) {
                maxIndex = i;
            }
        }
        return arr.get(maxIndex);
    }

    private static void testCovariation() {
        Beverage[] abc = {};
        abc = new Espresso[]{};
        System.out.println(abc);

    }

    private static void testPakage() {
        Integer i = 1;
        int a = i;
        System.out.println(i);
    }

    private static void testCoffee() {
        Beverage.test();
        Beverage espresso = new Espresso();
        espresso.setSize(CoffeeSize.HUGE);
        espresso = new Soy(espresso);
        System.out.println(espresso.getDesc() + ", $ " + espresso.cost());

        Beverage houseBlend = new HouseBlend();
        System.out.println(houseBlend.getDesc() + ", $ " + houseBlend.cost());

        houseBlend = new Mocha(houseBlend);
        houseBlend = new Mocha(houseBlend);
        houseBlend = new Whip(houseBlend);
        houseBlend = new Whip(houseBlend);
        System.out.println(houseBlend.getDesc() + ", $ " + houseBlend.cost());
    }
    private static void testInput() {
        int c;
        try {
            InputStream in = new LowerCaseInputStream(
                    new BufferedInputStream(
                            new FileInputStream("/Users/oudesine/test.txt")
                    )
            );

            while ((c = in.read()) >= 0) {
                System.out.print((char) c);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
class Fruit implements Comparable<Fruit> {
    Fruit() {
        this.type = "1";
    }
    String type;
    /**
     * Compares this object with the specified object for order.  Returns a negative integer, zero, or a positive
     * integer as this object is less than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This implies that <tt>x.compareTo(y)</tt> must
     * throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     *
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for all <tt>z</tt>.
     *
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates this condition should clearly indicate
     * this fact.  The recommended language is "Note: this class has a natural ordering that is inconsistent with
     * equals."
     *
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater
     * than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException if the specified object's type prevents it from being compared to this object.
     */
    @Override
    public int compareTo(Fruit o) {
        return 0;
    }

}
class Apple extends Fruit {

}

class Banana<T> {

    public T getT() {
        return t;
    }

    T t;
    public Banana(T t) {
        this.t = t;
    }
    public Banana(){}
}

