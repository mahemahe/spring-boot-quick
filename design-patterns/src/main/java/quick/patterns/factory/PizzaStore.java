package quick.patterns.factory;

/**
 * @author mahe <mahe@maihaoche.com>
 * @date 2019-11-03
 */
public abstract class PizzaStore {
    public final Pizza orderPizza(String name) {
        Pizza pizza;
        pizza = createPizza(name);

        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();

        return pizza;
    }

    public abstract Pizza createPizza(String name);
}
