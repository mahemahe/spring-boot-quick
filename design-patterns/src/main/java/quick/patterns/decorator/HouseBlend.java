package quick.patterns.decorator;

/**
 * @author mahe <mahe@maihaoche.com>
 * @date 2019-11-02
 */
public class HouseBlend extends Beverage {

    public HouseBlend() {
        this.setDesc("HouseBlend");
    }

    @Override
    public double cost() {
        switch (getSize()) {
            case SMALL:
                return 1.2;
            case MIDDLE:
                return 2.1;
            case BIG:
                return 3.0;
            case HUGE:
                return 3.9;
            default:
                return 1.2;
        }
    }
}
