package quick.patterns.command;

import quick.patterns.command.entity.GarageDoor;
import quick.patterns.command.entity.Light;

/**
 * @author mahe <mahe@maihaoche.com>
 * @date 2019-11-12
 */
public class TestCommand {

    public static void main(String[] args) {
        // 简单遥控器
        SimpleRemoteControl control = new SimpleRemoteControl();
        Light light = new Light();
        LightOnCommand lightOnCommand = new LightOnCommand(light);
        control.setCommand(lightOnCommand);
        control.buttonWasPressed();
        LightOffCommand lightOffCommand = new LightOffCommand(light);
        control.setCommand(lightOffCommand);
        control.buttonWasPressed();

        Light doorLight = new Light();
        GarageDoor garageDoor = new GarageDoor();
        garageDoor.setLight(doorLight);
        GarageDoorOpenCommand doorOpenCommand = new GarageDoorOpenCommand();
        doorOpenCommand.setGarageDoor(garageDoor);
        GarageDoorCloseCommand doorCloseCommand = new GarageDoorCloseCommand();
        doorCloseCommand.setGarageDoor(garageDoor);

        // 7个插线孔的遥控器
        RemoteControl remoteControl = new RemoteControl();
        remoteControl.setCommand(2, lightOnCommand, lightOffCommand);
        remoteControl.setCommand(3, doorOpenCommand, doorCloseCommand);
        System.out.println(remoteControl.toString());

        remoteControl.onButtonWasPressed(2);
        remoteControl.onButtonWasPressed(5);
        remoteControl.offButtonWasPressed(3);
        remoteControl.undoButtonWasPressed();
    }
}
