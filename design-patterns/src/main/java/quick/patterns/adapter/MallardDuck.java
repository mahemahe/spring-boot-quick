package quick.patterns.adapter;

/**
 * 绿头鸭
 * @author mahe <mahe@maihaoche.com>
 * @date 2019-11-18
 */
public class MallardDuck implements Duck{

    /**
     * 鸭子叫
     */
    @Override
    public void quack() {
        System.out.println("I'm Quacking, I'm mallard duck.");
    }

    /**
     * 飞
     */
    @Override
    public void fly() {
        System.out.println("I'm flying, I believe it.");
    }
}
