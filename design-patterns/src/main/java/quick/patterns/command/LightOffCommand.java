package quick.patterns.command;

import quick.patterns.command.entity.Light;

/**
 * 开灯命令
 * @author mahe <mahe@maihaoche.com>
 * @date 2019-11-12
 */
public class LightOffCommand implements Command {
    Light light;

    public LightOffCommand(Light light) {
        this.light = light;
    }

    /**
     * 执行动作
     */
    @Override
    public void execute() {
        light.off();
    }

    /**
     * 撤销动作
     */
    @Override
    public void undo() {
        light.on();
    }
}
