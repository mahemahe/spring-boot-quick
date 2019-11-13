package quick.patterns.decorator;

/**
 * @author mahe <mahe@maihaoche.com>
 * @date 2019-11-02
 */
public class Espresso extends Beverage {

    public Espresso () {
        this.setDesc("Espresso");
    }

    @Override
    public double cost() {
        switch (getSize()) {
            case SMALL:
                return 0.5;
            case MIDDLE:
                return 1.0;
            case BIG:
                return 1.5;
            case HUGE:
                return 2.0;
            default:
                return 0.5;
        }
    }
}
