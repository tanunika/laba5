package server.Commands;

import server.utils.CollectionManager;

/**
 * Команда для сохранения коллекции в файл
 */
public class SaveCommand implements Commands {
    private final CollectionManager collectionManager;

    public SaveCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String execute(String arguments) {
        collectionManager.saveToFile();
        return "Коллекция успешно сохранена в файл.";
    }
}