package server.Commands;

import shared.Structures.StudyGroup;
import server.utils.CollectionManager;

import java.util.Iterator;

/**
 * Команда для удаления элементов, меньших чем заданный
 */
public class RemoveLowerCommand implements Commands {
    private final CollectionManager collectionManager;

    public RemoveLowerCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "remove_lower";
    }

    @Override
    public String execute(String arguments) {
        return "Команда 'remove_lower' требует передачи объекта StudyGroup.";
    }

    public String execute(StudyGroup group) {
        if (group == null) {
            return "Ошибка: объект StudyGroup не передан.";
        }

        int initialSize = collectionManager.getCollection().size();
        Iterator<StudyGroup> iterator = collectionManager.getCollection().iterator();
        while (iterator.hasNext()) {
            StudyGroup current = iterator.next();
            if (current.compareTo(group) < 0) {
                iterator.remove();
            }
        }

        int removed = initialSize - collectionManager.getCollection().size();
        return "Удалено элементов: " + removed + ".";
    }
}