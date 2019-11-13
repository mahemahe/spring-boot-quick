package quick.patterns.command;

/**
 * 自定义按键遥控器的一个按钮
 * @author mahe <mahe@maihaoche.com>
 * @date 2019-11-12
 */
public class SimpleRemoteControl {
    Command slot;

    /**
     * 设定按钮为某个功能的命令
     * @param command
     */
    public void setCommand(Command command) {
        this.slot = command;
    }

    /**
     * 按下按钮的事件
     */
    public void buttonWasPressed() {
        slot.execute();
    }
}
