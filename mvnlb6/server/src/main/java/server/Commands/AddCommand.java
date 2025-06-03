package server.Commands;

import shared.Structures.StudyGroup;
import server.utils.CollectionManager;

import java.util.Collections;
import java.util.List;

/**
 * Команда для добавления нового элемента в коллекцию
 */
public class AddCommand implements Commands {
    private final CollectionManager collectionManager;

    public AddCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String execute(String arguments) {
        // This method should either be removed or modified to handle both cases
        return "Команда 'add' требует передачи объекта StudyGroup.";
    }

    // This should be the primary execute method for adding a group
    public String execute(StudyGroup group) {
        if (group == null) {
            return "Ошибка: объект StudyGroup не передан.";
        }
        collectionManager.getCollection().add(group);
        Collections.sort(collectionManager.getCollection());
        return "Группа \"" + group.getName() + "\" успешно добавлена.";
    }

    @Override
    public boolean isInteractive() {
        return false;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public String nextPrompt(String input) {
        return "";
    }
}