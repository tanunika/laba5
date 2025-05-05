package commands;

import StudyGroup.StudyGroup;
import StudyGroup.Coordinates;
import StudyGroup.Semester;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class AddIfMaxCommand implements Commands {
    private final List<StudyGroup> studyGroups;

    public AddIfMaxCommand(List<StudyGroup> studyGroups) {
        this.studyGroups = studyGroups;
    }

    @Override
    public String getName() {
        return "add_if_max";
    }

    @Override
    public String execute(String arguments) {
        Scanner scanner = new Scanner(System.in);

        try {
            if (arguments.isEmpty()) {
                return "Введите количество студентов для сравнения (например: add_if_max 50)";
            }

            // Проверка ввода количества студентов
            long inputStudentsCount;
            try {
                inputStudentsCount = Long.parseLong(arguments.trim());
            } catch (NumberFormatException e) {
                return "Ошибка: количество студентов должно быть числом";
            }

            // Проверка максимального значения
            long maxStudentsCount = studyGroups.stream()
                    .mapToLong(StudyGroup::getStudentsCount)
                    .max()
                    .orElse(0);

            if (inputStudentsCount <= maxStudentsCount) {
                return "Элемент не добавлен: " + inputStudentsCount +
                        " ≤ текущему максимуму (" + maxStudentsCount + ")";
            }

            // Интерактивный ввод данных с проверками
            System.out.println("Создание новой группы (значение " + inputStudentsCount +
                    " > текущего максимума " + maxStudentsCount + ")");

            System.out.print("Введите название группы: ");
            String name = scanner.nextLine();
            if (name.trim().isEmpty()) {
                return "Ошибка: название группы не может быть пустым";
            }

            // Ввод координат
            double x;
            long y;
            try {
                System.out.print("Введите координату X: ");
                x = Double.parseDouble(scanner.nextLine());
                System.out.print("Введите координату Y: ");
                y = Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException e) {
                return "Ошибка: координаты должны быть числами";
            }
            Coordinates coordinates = new Coordinates(x, y);

            // Ввод числовых значений
            long expelledStudents;
            long shouldBeExpelled;
            try {
                System.out.print("Введите количество отчисленных студентов: ");
                expelledStudents = Long.parseLong(scanner.nextLine());
                System.out.print("Введите количество студентов для отчисления: ");
                shouldBeExpelled = Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException e) {
                return "Ошибка: количество студентов должно быть числом";
            }

            // Ввод семестра с обработкой неверного ввода
            Semester semester;
            while (true) {
                try {
                    System.out.print("Введите семестр (FIRST, SECOND, THIRD, FOURTH, FIFTH): ");
                    String semesterInput = scanner.nextLine().trim();
                    if (semesterInput.isEmpty()) {
                        semester = null; // или можно установить значение по умолчанию
                        break;
                    }
                    semester = Semester.valueOf(semesterInput.toUpperCase());
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Ошибка: введите корректный семестр или оставьте пустым для null");
                }
            }

            // Создаем и добавляем группу
            StudyGroup newGroup = new StudyGroup(
                    name,
                    coordinates,
                    LocalDateTime.now(),
                    inputStudentsCount,
                    expelledStudents,
                    shouldBeExpelled,
                    semester,
                    null // groupAdmin можно установить как null или добавить ввод
            );

            studyGroups.add(newGroup);
            return "Группа успешно добавлена!";

        } catch (Exception e) {
            return "Неожиданная ошибка: " + e.getMessage();
        }
    }
}