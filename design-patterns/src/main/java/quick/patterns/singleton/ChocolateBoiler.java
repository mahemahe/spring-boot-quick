package quick.patterns.singleton;

/**
 * @author mahe <mahe@maihaoche.com>
 * @date 2019-11-09
 */
public class ChocolateBoiler {
    private boolean empty;
    private boolean boiled;

    private static ChocolateBoiler chocolateBoiler;

    private ChocolateBoiler() {
        empty = true;
        boiled = false;
    }

    public static ChocolateBoiler getInstance() {
        if (null == chocolateBoiler) {
            chocolateBoiler = new ChocolateBoiler();
        }
        return chocolateBoiler;
    }

    public void fill() {
        if (isEmpty()) {
            empty = false;
            boiled = false;
        }
    }

    public void drain() {
        if (!isEmpty()&& isBoiled()) {
            empty = true;
        }
    }

    public void boil() {
        if (!isEmpty() && !isBoiled()) {
            boiled = true;
        }
    }


    private boolean isBoiled() {
        return boiled;
    }

    private boolean isEmpty() {
        return empty;
    }
}
