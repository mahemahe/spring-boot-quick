package quick.patterns.adapter;

/**
 * @author mahe <mahe@maihaoche.com>
 * @date 2019-11-18
 */
public class TestAdapter {

    public static void main(String[] args) {
        test();
    }

    public static void test() {
        MallardDuck mallardDuck = new MallardDuck();
        WildTurkey wildTurkey = new WildTurkey();
        TurkeyTransformDuckAdapter duplicate = new TurkeyTransformDuckAdapter(wildTurkey);
        DuckTransformTurkeyAdapter turkeyAdapter = new DuckTransformTurkeyAdapter(mallardDuck);

        System.out.println("The Wild Turkey begin.");
        testTurkey(wildTurkey);

        System.out.println("\nThe mallard duck begin.");
        testDuck(mallardDuck);

        System.out.println("\nThe duck imitator begin.");
        testDuck(duplicate);

        System.out.println("\nThe turkey imitator begin.");
        testTurkey(turkeyAdapter);
    }

    public static void testDuck(Duck duck) {
        duck.quack();
        duck.fly();
    }

    public static void testTurkey(Turkey turkey) {
        turkey.gobble();
        turkey.fly();
    }
}
