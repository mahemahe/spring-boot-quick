package quick.patterns.factory;

/**
 * @author mahe <mahe@maihaoche.com>
 * @date 2019-11-03
 */
public abstract class Pizza {
    private String name;

    Pizza(String name) {
        this.name = name;
    }

    public void prepare() {
        System.out.println("prepared");
    }

    public void bake() {
        System.out.println("baked");
    }

    public void cut() {
        System.out.println("cutting");
    }

    public void box() {
        System.out.println("boxed");
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
