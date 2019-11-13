package quick.patterns.factory;

/**
 * @author mahe <mahe@maihaoche.com>
 * @date 2019-11-03
 */
public enum PizzaNameEnum {
    CHEESE_PIZZA("CheesePizza"),
    CLAM_PIZZA("ClamPizza"),
    PEPPERONI_PIZZA("PepperoniPizza"),
    ;

    PizzaNameEnum(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private String name;
}
