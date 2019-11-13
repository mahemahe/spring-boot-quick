package quick.patterns.command;

import quick.patterns.command.entity.GarageDoor;

/**
 * @author mahe <mahe@maihaoche.com>
 * @date 2019-11-12
 */
public class GarageDoorOpenCommand implements Command {
    private GarageDoor garageDoor;

    public void setGarageDoor(GarageDoor garageDoor) {
        this.garageDoor = garageDoor;
    }

    /**
     * 执行动作
     */
    @Override
    public void execute() {
        garageDoor.up();
    }
}
