package server.Commands;

import server.utils.CollectionManager;

/**
 * Команда для вывода информации о коллекции
 */
public class InfoCommand implements Commands {
    private final CollectionManager collectionManager;

    public InfoCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String execute(String arguments) {
        return collectionManager.getInfo();
    }
}