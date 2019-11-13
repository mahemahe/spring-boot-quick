package quick.patterns.decorator;

/**
 * @author mahe <mahe@maihaoche.com>
 * @date 2019-11-02
 */
public abstract class Beverage {
    private String desc = "Unknown";

    private CoffeeSize size = CoffeeSize.SMALL;

    public CoffeeSize getSize() {
        return size;
    }

    public void setSize(CoffeeSize size) {
        this.size = size;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public abstract double cost();

    public static void test(){
        System.out.println("test");
    }
}
