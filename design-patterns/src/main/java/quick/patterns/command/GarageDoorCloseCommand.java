package quick.patterns.command;

import quick.patterns.command.entity.GarageDoor;

/**
 * @author mahe <mahe@maihaoche.com>
 * @date 2019-11-12
 */
public class GarageDoorCloseCommand implements Command {
    private GarageDoor garageDoor;

    public void setGarageDoor(GarageDoor garageDoor) {
        this.garageDoor = garageDoor;
    }

    /**
     * 执行动作
     */
    @Override
    public void execute() {
        garageDoor.down();
    }

    /**
     * 撤销动作
     */
    @Override
    public void undo() {
        garageDoor.up();
    }
}
