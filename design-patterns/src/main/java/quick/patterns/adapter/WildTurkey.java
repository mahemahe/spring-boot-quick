package quick.patterns.adapter;

/**
 * @author mahe <mahe@maihaoche.com>
 * @date 2019-11-18
 */
public class WildTurkey implements Turkey {

    /**
     * 咯咯叫
     */
    @Override
    public void gobble() {
        System.out.println("I'm Gobbling, I think so.");
    }

    /**
     * 飞
     */
    @Override
    public void fly() {
        System.out.println("I'm flying? I can't believe that.");
    }
}
