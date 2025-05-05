package commands;

import StudyGroup.StudyGroup;

import java.util.List;
import java.util.stream.Collectors;

public class FilterCommand implements Commands {
    private final List<StudyGroup> studyGroups;

    public FilterCommand(List<StudyGroup> studyGroups) {
        this.studyGroups = studyGroups;
    }

    @Override
    public String getName() {
        return "filter_less_than_expelled_students";
    }

    @Override
    public String execute(String arguments) {
        try {
            if (arguments.isEmpty()) {
                return "Ошибка: укажите количество отчисленных студентов";
            }

            long expelledStudents = Long.parseLong(arguments.trim());

            List<StudyGroup> filteredGroups = studyGroups.stream()
                    .filter(group -> group.getExpelledStudents() < expelledStudents)
                    .collect(Collectors.toList());

            if (filteredGroups.isEmpty()) {
                return "Нет групп с количеством отчисленных студентов меньше " + expelledStudents;
            }

            StringBuilder result = new StringBuilder();
            result.append("Группы с expelledStudents < ").append(expelledStudents).append(":\n");
            filteredGroups.forEach(group ->
                    result.append(group.toString()).append("\n")
            );

            return result.toString().trim();

        } catch (NumberFormatException e) {
            return "Ошибка: аргумент должен быть числом";
        }


    }
}