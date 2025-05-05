package commands;

import StudyGroup.StudyGroup;
import java.util.List;

public class RemoveByIDCommand implements Commands {
    private final List<StudyGroup> studyGroups;

    public RemoveByIDCommand(List<StudyGroup> studyGroups) {
        this.studyGroups = studyGroups;
    }

    @Override
    public String getName() {
        return "remove_by_id";
    }

    @Override
    public String execute(String arguments) {
        try {
            int id = Integer.parseInt(arguments.trim());

            // Удаление группы по id
            boolean removed = studyGroups.removeIf(group -> group.getId() == id);

            if (removed) {
                return "Группа с id " + id + " успешно удалена.";
            } else {
                return "Группа с id " + id + " не найдена.";
            }
        } catch (NumberFormatException e) {
            return "Неверный формат: введите аргумент для выполнения команды";
        }
    }
}