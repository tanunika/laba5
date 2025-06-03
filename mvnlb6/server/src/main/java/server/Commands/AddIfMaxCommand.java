package server.Commands;

import shared.Structures.StudyGroup;
import server.utils.CollectionManager;

import java.util.Collections;

/**
 * Команда для добавления элемента, если он больше максимального в коллекции
 */
public class AddIfMaxCommand implements Commands {
    private final CollectionManager collectionManager;

    public AddIfMaxCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "add_if_max";
    }

    @Override
    public String execute(String arguments) {
        return "Команда 'add_if_max' требует передачи объекта StudyGroup.";
    }

    public String execute(StudyGroup group) {
        if (group == null) {
            return "Ошибка: объект StudyGroup не передан.";
        }

        if (collectionManager.getCollection().isEmpty()) {
            collectionManager.getCollection().add(group);
            return "Коллекция была пуста. Элемент добавлен.";
        }

        StudyGroup max = Collections.max(collectionManager.getCollection());
        if (group.compareTo(max) > 0) {
            collectionManager.getCollection().add(group);
            Collections.sort(collectionManager.getCollection());
            return "Элемент больше максимального. Добавлен успешно.";
        } else {
            return "Элемент не больше максимального. Не добавлен.";
        }
    }
}