package commands;

import StudyGroup.StudyGroup;

import java.util.List;
import java.util.Scanner;

public class RemoveLowerCommand implements Commands {
    private final List<StudyGroup> studyGroups;

    public RemoveLowerCommand(List<StudyGroup> studyGroups) {
        this.studyGroups = studyGroups;
    }

    @Override
    public String getName() {
        return "remove_lower";
    }

    @Override
    public String execute(String arguments) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Введите количество студентов для сравнения: ");
            long studentsCount = Long.parseLong(scanner.nextLine());

            // Удаляем все группы с studentsCount меньше заданного
            int initialSize = studyGroups.size();
            boolean removed = studyGroups.removeIf(
                    group -> group.getStudentsCount() < studentsCount
            );

            if (removed) {
                int removedCount = initialSize - studyGroups.size();
                return "Удалено " + removedCount + " групп(ы) с количеством студентов меньше " + studentsCount;
            } else {
                return "Не найдено групп с количеством студентов меньше " + studentsCount;
            }

        } catch (NumberFormatException e) {
            return "Ошибка: введите корректное число (количество студентов)";
        }
    }
}