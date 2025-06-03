package server.network;

import server.Commands.*;
import server.utils.CollectionManager;
import shared.Structures.StudyGroup;
import shared.network.Request;
import shared.network.Response;
import shared.utils.Serializer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

public class ServerHandler {
    private static final int BUFFER_SIZE = 65536;
    private final int port;
    private final CollectionManager collectionManager;
    private final Invoker invoker;
    private final Logger logger = Logger.getLogger(ServerHandler.class.getName());
    private final AtomicBoolean isRunning = new AtomicBoolean(true);

    public ServerHandler(int port, CollectionManager collectionManager) {
        this.port = port;
        this.collectionManager = collectionManager;
        this.invoker = new Invoker(collectionManager);
    }

    public void run() {
        try (DatagramChannel channel = DatagramChannel.open()) {
            channel.bind(new InetSocketAddress(port));
            channel.configureBlocking(false);
            logger.info("UDP-сервер запущен на порту " + port);

            Thread consoleThread = new Thread(() -> {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                try {
                    while (isRunning.get()) {
                        if (reader.ready()) {
                            String command = reader.readLine().trim();
                            if (!command.isEmpty()) {
                                processConsoleCommand(command);
                            }
                        }
                        Thread.sleep(100);
                    }
                } catch (Exception e) {
                    logger.warning("Ошибка в потоке ввода: " + e.getMessage());
                }
            });
            consoleThread.setDaemon(true);
            consoleThread.start();

            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

            while (isRunning.get()) {
                buffer.clear();
                SocketAddress clientAddress = channel.receive(buffer);
                if (clientAddress != null) {
                    buffer.flip();
                    byte[] requestData = new byte[buffer.remaining()];
                    buffer.get(requestData);

                    Request request = (Request) Serializer.deserialize(requestData);
                    Response response = processRequest(request);

                    byte[] responseData = Serializer.serialize(response);
                    buffer.clear();
                    buffer.put(responseData);
                    buffer.flip();
                    channel.send(buffer, clientAddress);
                }

                Thread.sleep(100);
            }

        } catch (Exception e) {
            logger.severe("Ошибка сервера: " + e.getMessage());
        }
    }

    private void shutdownServer() {
        logger.info("Сохранение коллекции...");
        collectionManager.saveToFile();
        System.out.println("Сервер завершает работу.");
        System.exit(0);
    }

    private void processConsoleCommand(String command) {
        try {
            String[] parts = command.split(" ", 2);
            String commandName = parts[0].toLowerCase();
            String arguments = parts.length > 1 ? parts[1] : "";

            switch (commandName) {
                case "save":
                    collectionManager.saveToFile();
                    logger.info("Коллекция сохранена");
                    break;
                case "exit":
                    shutdownServer();
                    break;
                case "info":
                    logger.info("Информация о сервере:\nПорт: " + port + "\nЭлементов в коллекции: " + collectionManager.getCollection().size());
                    break;
                case "help":
                    logger.info("Доступные команды:\nsave\nexit\ninfo\nhelp");
                    break;
                default:
                    String result = invoker.invoke(commandName, new Object[]{arguments});
                    logger.info("Результат команды: " + result);
            }
        } catch (Exception e) {
            logger.warning("Ошибка обработки команды: " + e.getMessage());
        }
    }

    private Response processRequest(Request request) {
        if ("exit".equalsIgnoreCase(request.getCommandName())) {
            shutdownServer();
            return new Response("Сервер завершает работу", false);
        }

        try {
            String commandName = request.getCommandName();
            Object[] args = request.getArguments();
            StudyGroup group = request.getStudyGroup();

            if (group != null) {
                switch (commandName) {
                    case "add":
                        return new Response(((AddCommand) invoker.getCommand("add")).execute(group), false);
                    case "update":
                        int id = Integer.parseInt(args[0].toString());
                        return new Response(invoker.invoke(commandName, id, group), false);
                    case "add_if_max":
                    case "remove_lower":
                        return new Response(invoker.invoke(commandName, group), false);
                    case "remove_all_by_group_admin":
                        return new Response(invoker.invoke(commandName, group.getGroupAdmin()), false);
                }
            }

            String result = invoker.invoke(commandName, args);
            return new Response(result, false);

        } catch (Exception e) {
            return new Response("Ошибка выполнения команды: " + e.getMessage(), false);
        }
    }
}
