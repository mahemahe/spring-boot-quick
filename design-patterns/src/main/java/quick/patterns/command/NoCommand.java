package quick.patterns.command;

/**
 * @author mahe <mahe@maihaoche.com>
 * @date 2019-11-16
 */
public class NoCommand implements Command {

    /**
     * 执行动作
     */
    @Override
    public void execute() {
        System.out.println("Nothing.");
    }

    /**
     * 撤销动作
     */
    @Override
    public void undo() {
        System.out.println("Nothing undo.");
    }
}
