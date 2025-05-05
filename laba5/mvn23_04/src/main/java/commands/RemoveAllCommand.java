package commands;

import StudyGroup.StudyGroup;

import java.util.List;

public class RemoveAllCommand implements Commands {
    private final List<StudyGroup> studyGroups;

    public RemoveAllCommand(List<StudyGroup> studyGroups) {
        this.studyGroups = studyGroups;
    }

    @Override
    public String getName() {
        return "remove_all_by_group_admin";
    }

    @Override
    public String execute(String arguments) {
        if (arguments.isEmpty()) {
            return "Ошибка: укажите имя администратора группы (например: remove_all_by_group_admin Иван)";
        }

        String adminName = arguments.trim();
        int initialSize = studyGroups.size();

        // Удаляем все группы с указанным администратором
        studyGroups.removeIf(group ->
                group.getGroupAdmin() != null &&
                        group.getGroupAdmin().getName().equalsIgnoreCase(adminName)
        );

        int removedCount = initialSize - studyGroups.size();

        if (removedCount > 0) {
            return "Удалено: " + removedCount + " групп(ы) с администратором: " + adminName;
        } else {
            return "Группы с администратором '" + adminName + "' не найдены";
        }
    }
}