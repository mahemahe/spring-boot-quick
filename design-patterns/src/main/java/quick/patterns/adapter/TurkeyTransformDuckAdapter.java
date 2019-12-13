package quick.patterns.adapter;

/**
 * @author mahe <mahe@maihaoche.com>
 * @date 2019-11-18
 */
public class TurkeyTransformDuckAdapter implements Duck {
    private Turkey turkey;

    public TurkeyTransformDuckAdapter(Turkey turkey) {
        this.turkey = turkey;
    }

    /**
     * 鸭子叫
     */
    @Override
    public void quack() {
        System.out.printf("I'm trying to imitate the Duck. ");
        turkey.gobble();
    }

    /**
     * 飞
     */
    @Override
    public void fly() {
        System.out.println("I'm trying to fly like a Duck.");
        for (int i = 0; i < 3; i++) {
            turkey.fly();
        }
    }
}
