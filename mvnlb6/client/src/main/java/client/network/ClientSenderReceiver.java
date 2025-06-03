package client.network;

import client.utils.ConsoleManager;
import shared.Structures.StudyGroup;
import shared.network.Request;
import shared.network.Response;
import shared.utils.Serializer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

public class ClientSenderReceiver {
    private final String host;
    private final int port;
    private DatagramChannel clientChannel;
    private final ConsoleManager consoleManager;
    private InetSocketAddress serverAddress;

    public ClientSenderReceiver(String host, int port) {
        this.host = host;
        this.port = port;
        this.consoleManager = new ConsoleManager(new Scanner(System.in));
    }

    public void run() {
        try {
            serverAddress = new InetSocketAddress(host, port);
            clientChannel = DatagramChannel.open();
            clientChannel.configureBlocking(true);
            consoleManager.printMessage("UDP-клиент подключён к серверу " + host + ":" + port);

            while (true) {
                String input = consoleManager.readCommand();
                if (input.equalsIgnoreCase("exit")) {
                    break;
                }

                try {
                    Request request = createRequest(input);
                    if (request != null) {
                        Response response = sendRequest(request);
                        consoleManager.printMessage(response.getMessage());
                    }
                } catch (Exception e) {
                    consoleManager.printMessage("Ошибка выполнения команды: " + e.getMessage());
                }
            }

        } catch (IOException e) {
            consoleManager.printMessage("Ошибка подключения: " + e.getMessage());
        } finally {
            try {
                if (clientChannel != null && clientChannel.isOpen()) {
                    clientChannel.close();
                }
            } catch (IOException e) {
                consoleManager.printMessage("Ошибка закрытия канала: " + e.getMessage());
            }
        }
    }

    private Request createRequest(String input) {
        String[] parts = input.trim().split("\\s+", 2);
        String commandName = parts[0];
        String arguments = parts.length > 1 ? parts[1] : "";

        try {
            switch (commandName.toLowerCase()) {
                case "add":
                    StudyGroup group = consoleManager.readStudyGroup();
                    return new Request("add", null, group);
                case "update":
                    int id = Integer.parseInt(arguments);
                    StudyGroup updateGroup = consoleManager.readStudyGroup();
                    return new Request("update", new Object[]{id}, updateGroup);
                case "add_if_max":
                    StudyGroup maxGroup = consoleManager.readStudyGroup();
                    return new Request("add_if_max", null, maxGroup);
                case "remove_lower":
                    StudyGroup lowerGroup = consoleManager.readStudyGroup();
                    return new Request("remove_lower", null, lowerGroup);
                case "save":
                    consoleManager.printMessage("Клиент не может сохранять коллекции ");
                    return null;
                default:
                    return new Request(commandName, new Object[]{arguments}, null);
            }
        } catch (Exception e) {
            consoleManager.printMessage("Ошибка создания запроса: " + e.getMessage());
            return null;
        }
    }

    private Response sendRequest(Request request) throws IOException, ClassNotFoundException {
        byte[] requestData = Serializer.serialize(request);
        ByteBuffer buffer = ByteBuffer.wrap(requestData);

        // Отправка запроса серверу
        clientChannel.send(buffer, serverAddress);

        // Получение ответа
        ByteBuffer responseBuffer = ByteBuffer.allocate(65536);
        clientChannel.receive(responseBuffer);

        responseBuffer.flip();
        byte[] responseData = new byte[responseBuffer.remaining()];
        responseBuffer.get(responseData);

        return (Response) Serializer.deserialize(responseData);
    }
}
