package commands;

import StudyGroup.StudyGroup;
import java.util.List;

public class CountCommand implements Commands {
    private final List<StudyGroup> studyGroups;

    public CountCommand(List<StudyGroup> studyGroups) {
        this.studyGroups = studyGroups;
    }

    @Override
    public String getName() {
        return "count_less_than_student_count";
    }

    @Override
    public String execute(String arguments) {
        try {
            if (arguments.isEmpty()) {
                return "Ошибка: укажите количество студентов";
            }

            long studentsCount = Long.parseLong(arguments.trim());

            long count = studyGroups.stream()
                    .filter(group -> group.getStudentsCount() < studentsCount)
                    .count();

            return "Количество групп с studentsCount < " + studentsCount + ": " + count;

        } catch (NumberFormatException e) {
            return "Ошибка: аргумент должен быть числом (количество студентов)";
        }
    }
}