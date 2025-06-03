package server.Commands;

import shared.Structures.Person;
import shared.Structures.StudyGroup;
import server.utils.CollectionManager;

import java.util.Iterator;

/**
 * Команда для удаления элементов по значению groupAdmin
 */
public class RemoveAllByGroupAdminCommand implements Commands {
    private final CollectionManager collectionManager;

    public RemoveAllByGroupAdminCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "remove_all_by_group_admin";
    }

    @Override
    public String execute(String arguments) {
        return "Команда 'remove_all_by_group_admin' требует передачи объекта Person.";
    }

    public String execute(Person groupAdmin) {
        if (groupAdmin == null) {
            return "Ошибка: объект Person не передан.";
        }

        int initialSize = collectionManager.getCollection().size();
        Iterator<StudyGroup> iterator = collectionManager.getCollection().iterator();
        while (iterator.hasNext()) {
            StudyGroup current = iterator.next();
            if (current.getGroupAdmin() != null &&
                    current.getGroupAdmin().equals(groupAdmin)) {
                iterator.remove();
            }
        }

        int removed = initialSize - collectionManager.getCollection().size();
        return "Удалено элементов: " + removed + ".";
    }
}