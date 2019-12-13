package quick.patterns.adapter;

/**
 * @author mahe <mahe@maihaoche.com>
 * @date 2019-11-18
 */
public class DuckTransformTurkeyAdapter implements Turkey {
    private Duck duck;

    public DuckTransformTurkeyAdapter(Duck duck) {
        this.duck = duck;
    }

    /**
     * 咯咯叫
     */
    @Override
    public void gobble() {
        System.out.printf("I'm turkey now.");
        duck.quack();
    }

    /**
     * 飞
     */
    @Override
    public void fly() {
        System.out.printf("I'm flying nearby.");
        duck.fly();
    }
}
