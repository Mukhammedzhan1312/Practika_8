import java.util.List;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Scanner;
interface ICommand {
    void execute();
    void undo();
}
class Light {
    public void on() {
        System.out.println("Свет включен");
    }

    public void off() {
        System.out.println("Свет выключен");
    }
}

class AirConditioner {
    public void on() {
        System.out.println("Кондиционер включен");
    }

    public void off() {
        System.out.println("Кондиционер выключен");
    }
}

class Television {
    public void on() {
        System.out.println("Телевизор включен");
    }

    public void off() {
        System.out.println("Телевизор выключен");
    }
}
class LightOnCommand implements ICommand {
    private Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.on();
    }

    @Override
    public void undo() {
        light.off();
    }
}

class LightOffCommand implements ICommand {
    private Light light;

    public LightOffCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.off();
    }

    @Override
    public void undo() {
        light.on();
    }
}
class ACOnCommand implements ICommand {
    private AirConditioner ac;

    public ACOnCommand(AirConditioner ac) {
        this.ac = ac;
    }

    @Override
    public void execute() {
        ac.on();
    }

    @Override
    public void undo() {
        ac.off();
    }
}

class ACOffCommand implements ICommand {
    private AirConditioner ac;

    public ACOffCommand(AirConditioner ac) {
        this.ac = ac;
    }

    @Override
    public void execute() {
        ac.off();
    }

    @Override
    public void undo() {
        ac.on();
    }
}
class TVOnCommand implements ICommand {
    private Television tv;

    public TVOnCommand(Television tv) {
        this.tv = tv;
    }

    @Override
    public void execute() {
        tv.on();
    }

    @Override
    public void undo() {
        tv.off();
    }
}

class TVOffCommand implements ICommand {
    private Television tv;

    public TVOffCommand(Television tv) {
        this.tv = tv;
    }

    @Override
    public void execute() {
        tv.off();
    }

    @Override
    public void undo() {
        tv.on();
    }
}


class RemoteControl {
    private ICommand[] commands;
    private Stack<ICommand> commandHistory;

    public RemoteControl() {
        commands = new ICommand[7]; // Например, 7 кнопок
        commandHistory = new Stack<>();
    }

    public void setCommand(int slot, ICommand command) {
        commands[slot] = command;
    }

    public void pressButton(int slot) {
        if (commands[slot] != null) {
            commands[slot].execute();
            commandHistory.push(commands[slot]);
        } else {
            System.out.println("Команда для этого слота не назначена.");
        }
    }

    public void undo() {
        if (!commandHistory.isEmpty()) {
            ICommand lastCommand = commandHistory.pop();
            lastCommand.undo();
        } else {
            System.out.println("Нет команд для отмены.");
        }
    }
}


class MacroCommand implements ICommand {
    private List<ICommand> commands;

    public MacroCommand() {
        commands = new ArrayList<>();
    }

    public void addCommand(ICommand command) {
        commands.add(command);
    }

    @Override
    public void execute() {
        for (ICommand command : commands) {
            command.execute();
        }
    }

    @Override
    public void undo() {
        for (ICommand command : commands) {
            command.undo();
        }
    }
}


public class Main {
    public static void main(String[] args) {
        RemoteControl remote = new RemoteControl();
        Scanner scanner = new Scanner(System.in);
        Light livingRoomLight = new Light();
        AirConditioner ac = new AirConditioner();
        Television tv = new Television();
        remote.setCommand(0, new LightOnCommand(livingRoomLight));
        remote.setCommand(1, new LightOffCommand(livingRoomLight));
        remote.setCommand(2, new ACOnCommand(ac));
        remote.setCommand(3, new ACOffCommand(ac));
        remote.setCommand(4, new TVOnCommand(tv));
        remote.setCommand(5, new TVOffCommand(tv));

        while (true) {
            System.out.println("\nВыберите команду:");
            System.out.println("0 - Включить свет");
            System.out.println("1 - Выключить свет");
            System.out.println("2 - Включить кондиционер");
            System.out.println("3 - Выключить кондиционер");
            System.out.println("4 - Включить телевизор");
            System.out.println("5 - Выключить телевизор");
            System.out.println("6 - Отмена последней команды");
            System.out.println("7 - Включить макрокоманду (Утренний режим)");
            System.out.println("8 - Выход");
            System.out.print("Ваш выбор: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 0:
                    remote.pressButton(0);
                    break;
                case 1:
                    remote.pressButton(1);
                    break;
                case 2:
                    remote.pressButton(2);
                    break;
                case 3:
                    remote.pressButton(3);
                    break;
                case 4:
                    remote.pressButton(4);
                    break;
                case 5:
                    remote.pressButton(5);
                    break;
                case 6:
                    remote.undo();
                    break;
                case 7:
                    MacroCommand morningRoutine = new MacroCommand();
                    morningRoutine.addCommand(new LightOnCommand(livingRoomLight));
                    morningRoutine.addCommand(new ACOnCommand(ac));
                    remote.setCommand(6, morningRoutine);
                    remote.pressButton(6);
                    break;
                case 8:
                    System.out.println("Выход из программы...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
            }
        }
    }
}
