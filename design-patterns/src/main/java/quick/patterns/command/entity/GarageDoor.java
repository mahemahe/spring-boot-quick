package quick.patterns.command.entity;

/**
 * @author mahe <mahe@maihaoche.com>
 * @date 2019-11-12
 */
public class GarageDoor {
    private Light light;

    public void setLight(Light light) {
        this.light = light;
    }

    public void up() {
        System.out.println("The garage door is up!");
    }

    public void down() {
        System.out.println("The garage door is down!");
    }

    public void stop() {
        System.out.println("The garage door is stop!");
    }

    public void lightOn() {
        System.out.println("The garage door's light");
        light.on();
    }

    public void lightOff() {
        System.out.println("The garage door's light");
        light.off();
    }
}
