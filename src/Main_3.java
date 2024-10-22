import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
interface IMediator {
    void sendMessage(String message, User user);
    void addUser(User user);
    void removeUser(User user);
}


class ChatMediator implements IMediator {
    private List<User> users;

    public ChatMediator() {
        users = new ArrayList<>();
    }

    @Override
    public void sendMessage(String message, User user) {
        for (User u : users) {
            if (u != user) {
                u.receiveMessage(message);
            }
        }
    }

    @Override
    public void addUser(User user) {
        users.add(user);
        notifyUsers(user.getName() + " присоединился к чату.");
    }

    @Override
    public void removeUser(User user) {
        users.remove(user);
        notifyUsers(user.getName() + " покинул чат.");
    }

    private void notifyUsers(String message) {
        for (User user : users) {
            user.receiveMessage(message);
        }
    }
}

interface IUser {
    void sendMessage(String message);
    void receiveMessage(String message);
    String getName();
}

class User implements IUser {
    private String name;
    private IMediator mediator;

    public User(String name, IMediator mediator) {
        this.name = name;
        this.mediator = mediator;
        this.mediator.addUser(this);
    }

    @Override
    public void sendMessage(String message) {
        System.out.println(this.name + ": " + message);
        mediator.sendMessage(message, this);
    }

    @Override
    public void receiveMessage(String message) {
        System.out.println(this.name + " получил сообщение: " + message);
    }

    @Override
    public String getName() {
        return name;
    }

    public void leaveChat() {
        mediator.removeUser(this);
    }
}

class ChannelMediator {
    private Map<String, ChatMediator> channels;

    public ChannelMediator() {
        channels = new HashMap<>();
    }

    public void createChannel(String channelName) {
        if (!channels.containsKey(channelName)) {
            channels.put(channelName, new ChatMediator());
        }
    }

    public ChatMediator getChannel(String channelName) {
        return channels.get(channelName);
    }

    public void sendMessage(String channelName, String message, User user) {
        ChatMediator channel = channels.get(channelName);
        if (channel != null) {
            channel.sendMessage(message, user);
        } else {
            System.out.println("Канал не существует.");
        }
    }
}


public class Main_3 {
    public static void main(String[] args) {
        ChannelMediator channelMediator = new ChannelMediator();
        channelMediator.createChannel("General");
        ChatMediator generalChannel = channelMediator.getChannel("General");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Добро пожаловать в чат! Введите свое имя:");
        String userName = scanner.nextLine();
        User user = new User(userName, generalChannel);

        boolean running = true;
        while (running) {
            System.out.println("\nВыберите команду:");
            System.out.println("1. Отправить сообщение");
            System.out.println("2. Покинуть чат");
            System.out.println("3. Выход из приложения");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Введите ваше сообщение:");
                    String message = scanner.nextLine();
                    user.sendMessage(message);
                    break;
                case 2:
                    user.leaveChat();
                    System.out.println(user.getName() + " покинул чат.");
                    running = false;
                    break;
                case 3:
                    System.out.println("Выход из приложения...");
                    running = false;
                    break;
                default:
                    System.out.println("Некорректный выбор. Пожалуйста, попробуйте снова.");
                    break;
            }
        }

        scanner.close();
    }
}
