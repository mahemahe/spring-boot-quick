package quick.patterns.decorator;

/**
 * @author mahe <mahe@maihaoche.com>
 * @date 2019-11-02
 */
public enum CoffeeSize {
    SMALL("SMALL"),
    MIDDLE("MIDDLE"),
    BIG("BIG"),
    HUGE("HUGE"),
    ;

    CoffeeSize(String sizeCode){
        this.sizeCode = sizeCode;
    }

    private String sizeCode;
}
