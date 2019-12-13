package quick.patterns.command;

/**
 * 遥控器
 * @author mahe <mahe@maihaoche.com>
 * @date 2019-11-16
 */
public class RemoteControl {
    Command[] onCommands;
    Command[] offCommands;
    Command undoCommand;

    public RemoteControl() {
        onCommands = new Command[7];
        offCommands = new Command[7];
        for (int i = 0; i < 7; i++) {
            onCommands[i] = new NoCommand();
            offCommands[i] = new NoCommand();
        }
        undoCommand = new NoCommand();
    }

    public void setCommand(int slot, Command on, Command off) {
        onCommands[slot] = on;
        offCommands[slot] = off;
    }

    public void onButtonWasPressed(int slot) {
        undoCommand = onCommands[slot];
        onCommands[slot].execute();
    }

    public void offButtonWasPressed(int slot) {
        undoCommand = offCommands[slot];
        offCommands[slot].execute();
    }

    public void undoButtonWasPressed() {
        undoCommand.undo();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("RemoteControl\n");
        for (int i = 0; i < 7; i++) {
            sb.append("[slot:").append(i).append("] ").append(onCommands[i].getClass().getName());
            sb.append("\t").append(offCommands[i].getClass().getName()).append("\n");
        }
        return sb.toString();
    }
}
