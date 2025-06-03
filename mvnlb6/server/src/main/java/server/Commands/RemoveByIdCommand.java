package server.Commands;

import server.utils.CollectionManager;
import shared.Structures.StudyGroup;

/**
 * Команда для удаления элемента по ID
 */
public class RemoveByIdCommand implements Commands {
    private final CollectionManager collectionManager;

    public RemoveByIdCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "remove_by_id";
    }

    @Override
    public String execute(String arguments) {
        try {
            int id = Integer.parseInt(arguments.trim());
            return execute(id);
        } catch (NumberFormatException e) {
            return "Неверный формат ID. Должно быть целое число.";
        }
    }

    public String execute(int id) {
        for (StudyGroup group : collectionManager.getCollection()) {
            if (group.getId() == id) {
                collectionManager.getCollection().remove(group);
                return "Элемент с ID " + id + " успешно удален.";
            }
        }
        return "Элемент с ID " + id + " не найден.";
    }
}