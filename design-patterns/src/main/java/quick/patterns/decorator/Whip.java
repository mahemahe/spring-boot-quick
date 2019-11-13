package quick.patterns.decorator;

/**
 * @author mahe <mahe@maihaoche.com>
 * @date 2019-11-02
 */
public class Whip extends Condiment {
    Beverage beverage;
    public Whip(Beverage beverage) {
        this.beverage = beverage;
    }
    @Override
    public String getDesc() {
        return beverage.getDesc() + ", Whip";
    }

    @Override
    public double cost() {
        return beverage.cost() + 0.6;
    }
}
