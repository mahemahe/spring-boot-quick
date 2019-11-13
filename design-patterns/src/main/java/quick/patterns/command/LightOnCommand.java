package quick.patterns.command;

import quick.patterns.command.entity.Light;

/**
 * 开灯命令
 * @author mahe <mahe@maihaoche.com>
 * @date 2019-11-12
 */
public class LightOnCommand implements Command {
    Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    /**
     * 执行动作
     */
    @Override
    public void execute() {
        light.on();
    }
}
