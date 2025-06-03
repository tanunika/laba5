package server.Commands;

import server.utils.CollectionManager;

/**
 * Команда для вывода всех элементов коллекции
 */
public class ShowCommand implements Commands {
    private final CollectionManager collectionManager;

    public ShowCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String execute(String arguments) {
        if (collectionManager.getCollection().isEmpty()) {
            return "Коллекция пуста.";
        }

        StringBuilder result = new StringBuilder();
        collectionManager.getCollection().forEach(group ->
                result.append(group).append("\n"));
        return result.toString();
    }
}