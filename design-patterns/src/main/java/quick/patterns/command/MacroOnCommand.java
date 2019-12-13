package quick.patterns.command;

/**
 * 宏命令
 * @author mahe <mahe@maihaoche.com>
 * @date 2019-11-16
 */
public class MacroOnCommand implements Command {
    Command[] macroCommand;
    public MacroOnCommand(Command[] macroCommand) {
        this.macroCommand = macroCommand;
    }
    /**
     * 执行动作
     */
    @Override
    public void execute() {
        for (int i = 0; i < macroCommand.length; i++) {
            macroCommand[i].execute();
        }
    }

    /**
     * 撤销动作
     */
    @Override
    public void undo() {
        for (int i = 0; i < macroCommand.length; i++) {
            macroCommand[i].undo();
        }
    }
}
