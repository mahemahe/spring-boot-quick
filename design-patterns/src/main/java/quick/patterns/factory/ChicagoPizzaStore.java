package quick.patterns.factory;

/**
 * @author mahe <mahe@maihaoche.com>
 * @date 2019-11-03
 */
public class ChicagoPizzaStore extends PizzaStore {

    @Override
    public Pizza createPizza(String name) {
        Pizza pizza;
        if (PizzaNameEnum.CHEESE_PIZZA.getName().equals(name)) {
            pizza = new CheesePizza();
        } else if (PizzaNameEnum.CLAM_PIZZA.getName().equals(name)) {
            pizza = new ClamPizza();
        } else {
            pizza = null;
        }
        System.out.println("ChicagoPizzaStore create");

        return pizza;
    }
}
