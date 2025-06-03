package server.Commands;

import server.utils.CollectionManager;
import shared.Structures.StudyGroup;

/**
 * Команда для вывода и удаления первого элемента коллекции
 */
public class RemoveHeadCommand implements Commands {
    private final CollectionManager collectionManager;

    public RemoveHeadCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "remove_head";
    }

    @Override
    public String execute(String arguments) {
        if (collectionManager.getCollection().isEmpty()) {
            return "Коллекция пуста.";
        }

        StudyGroup first = collectionManager.getCollection().getFirst();
        collectionManager.getCollection().removeFirst();
        return "Первый элемент коллекции:\n" + first + "\nУдален успешно.";
    }
}