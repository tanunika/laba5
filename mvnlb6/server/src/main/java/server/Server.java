package server;

import server.network.ServerHandler;
import server.utils.CollectionManager;

/**
 * Главный класс сервера
 */
public class Server {
    public static void main(String[] args) {
        String fileName = System.getenv("COLLECTION_FILE");
        if (fileName == null || fileName.isEmpty()) {
            System.err.println("Не задана переменная окружения COLLECTION_FILE");
            System.exit(1);
        }

        CollectionManager collectionManager = new CollectionManager(fileName); //управление данными коллекций
        ServerHandler serverHandler = new ServerHandler(65437, collectionManager); //запуск обработчика соединений
        serverHandler.run();
    }
}
