package commands;

import StudyGroup.StudyGroup;

import java.util.Collection;
import java.util.List;

/**
 * Команда show: выводит все элементы коллекции в строковом представлении.
 */
public class ShowCommand implements Commands {
    private final Collection<StudyGroup> collection;

    public ShowCommand(List<StudyGroup> studyGroups) {
        this.collection = studyGroups;
    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String execute(String arguments) {
        // Проверка на пустую коллекцию
        if (collection.isEmpty()) {
            return "Коллекция пуста.";
        }

        // Формируем строковое представление всех элементов коллекции
        StringBuilder result = new StringBuilder();
        for (StudyGroup element : collection) {
            result.append(element).append("\n"); // Используем toString() элемента
        }

        return result.toString();
    }
}