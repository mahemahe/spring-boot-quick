package quick.patterns.decorator;

/**
 * @author mahe <mahe@maihaoche.com>
 * @date 2019-11-02
 */
public class Mocha extends Condiment {
    Beverage beverage;

    public Mocha(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDesc() {
        return beverage.getDesc() + ", Mocha";
    }

    @Override
    public double cost() {
        return 0.15 + beverage.cost();
    }
}
