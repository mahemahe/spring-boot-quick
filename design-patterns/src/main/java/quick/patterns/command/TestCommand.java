package quick.patterns.command;

import quick.patterns.command.entity.GarageDoor;
import quick.patterns.command.entity.Light;

/**
 * @author mahe <mahe@maihaoche.com>
 * @date 2019-11-12
 */
public class TestCommand {

    public static void main(String[] args) {
        SimpleRemoteControl control = new SimpleRemoteControl();
        Light light = new Light();
        LightOnCommand command = new LightOnCommand(light);
        control.setCommand(command);
        control.buttonWasPressed();

        Light doorLight = new Light();
        SimpleRemoteControl control1 = new SimpleRemoteControl();
        GarageDoor garageDoor = new GarageDoor();
        garageDoor.setLight(doorLight);
        GarageDoorOpenCommand doorOpenCommand = new GarageDoorOpenCommand();
        doorOpenCommand.setGarageDoor(garageDoor);
        control1.setCommand(doorOpenCommand);
        control1.buttonWasPressed();
    }
}
