package server.Commands;

import server.utils.CollectionManager;

/**
 * Команда для очистки коллекции
 */
public class ClearCommand implements Commands {
    private final CollectionManager collectionManager;

    public ClearCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String execute(String arguments) {
        collectionManager.getCollection().clear();
        return "Коллекция успешно очищена.";
    }
}