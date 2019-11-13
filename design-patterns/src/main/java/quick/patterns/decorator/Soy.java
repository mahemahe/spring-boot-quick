package quick.patterns.decorator;

/**
 * @author mahe <mahe@maihaoche.com>
 * @date 2019-11-02
 */
public class Soy extends Beverage {
    Beverage beverage;

    public Soy(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDesc() {
        return beverage.getDesc() + ", Soy";
    }

    @Override
    public CoffeeSize getSize() {
        return beverage.getSize();
    }

    @Override
    public double cost() {
        double cost = beverage.cost();
        switch (getSize()) {
            case SMALL:
                cost += 0.5;
            case MIDDLE:
                cost += 1.0;
            case BIG:
                cost += 1.5;
            case HUGE:
                cost += 2.0;
            default:
                cost += 0.5;
        }
        return cost;
    }
}
