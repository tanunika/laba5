package server.Commands;

import shared.Structures.StudyGroup;
import server.utils.CollectionManager;

import java.util.Collections;

/**
 * Команда для обновления элемента коллекции по ID
 */
public class UpdateCommand implements Commands {
    private final CollectionManager collectionManager;

    public UpdateCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String execute(String arguments) {
        return "Команда 'update' требует передачи ID и объекта StudyGroup.";
    }

    public String execute(int id, StudyGroup group) {
        if (group == null) {
            return "Ошибка: объект StudyGroup не передан.";
        }

        for (int i = 0; i < collectionManager.getCollection().size(); i++) {
            if (collectionManager.getCollection().get(i).getId() == id) {
                group.setId(id); // Сохраняем старый ID
                collectionManager.getCollection().set(i, group);
                Collections.sort(collectionManager.getCollection());
                return "Элемент с ID " + id + " успешно обновлен.";
            }
        }
        return "Элемент с ID " + id + " не найден.";
    }
}