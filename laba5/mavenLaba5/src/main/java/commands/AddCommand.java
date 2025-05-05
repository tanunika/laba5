package commands;

import StudyGroup.*;
import StudyGroup.eyeColor.Color;
import StudyGroup.hairColor.ColorHair;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class AddCommand implements Commands {
    private final List<StudyGroup> studyGroups;

    public AddCommand(List<StudyGroup> studyGroups) {
        this.studyGroups = studyGroups;
    }

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String execute(String arguments) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Введите название группы: ");
            String name = scanner.nextLine();

            System.out.print("Введите координату X: ");
            double x = getValidDouble(scanner);
            System.out.print("Введите координату Y: ");
            long y = getValidLong(scanner);
            Coordinates coordinates = new Coordinates(x, y);

            long studentsCount = getValidLong(scanner, "Введите количество студентов: ");
            long expelledStudents = getValidLong(scanner, "Введите количество отчисленных студентов: ");
            long shouldBeExpelled = getValidLong(scanner, "Введите количество студентов, которые должны быть отчислены: ");

            System.out.print("Введите семестр (FIRST, SECOND, THIRD, FOURTH, FIFTH): ");
            Semester semesterEnum = getValidSemester(scanner);

            System.out.print("Введите имя админа группы: ");
            String nameAdmin = scanner.next();

            System.out.print("Введите паспорт админа группы: ");
            String namePassport = scanner.next();

            System.out.print("Введите цвет глаз админа (RED, BLACK, BLUE, YELLOW, ORANGE): ");
            Color adminColorEye = getValidColor(scanner);

            System.out.print("Введите цвет волос админа (GREEN, RED, YELLOW, ORANGE, BROWN): ");
            ColorHair adminColorHair = getValidColorHair(scanner);

            System.out.print("Введите локацию админа (x y z): ");
            float xcoord = getValidFloat(scanner);
            float ycoord = getValidFloat(scanner);
            float zcoord = getValidFloat(scanner);
            Location loc = new Location(xcoord, ycoord, zcoord);

            Person person = new Person(nameAdmin, namePassport, adminColorEye, adminColorHair, loc);

            StudyGroup studyGroup = new StudyGroup(
                    name, coordinates, LocalDateTime.now(), studentsCount, expelledStudents, shouldBeExpelled, semesterEnum, person
            );
            studyGroups.add(studyGroup);
            return "Группа " + studyGroup + " успешно добавлена.";
        } catch (Exception e) {
            return "Ошибка: " + e.getMessage();
        }
    }

    private long getValidLong(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            long value = getValidLong(scanner);
            if (value > 0) {
                return value;
            }
            System.out.println("Ошибка: Значение должно быть больше 0");
        }
    }
    private double getValidDouble(Scanner scanner) {
        while (!scanner.hasNextDouble()) {
            System.out.print("Неверный ввод. Введите число: ");
            scanner.next();
        }
        return scanner.nextDouble();
    }

    private long getValidLong(Scanner scanner) {
        while (!scanner.hasNextLong()) {
            System.out.print("Неверный ввод. Введите целое число: ");
            scanner.next();
        }
        return scanner.nextLong();
    }

    private float getValidFloat(Scanner scanner) {
        while (!scanner.hasNextFloat()) {
            System.out.print("Неверный ввод. Введите число: ");
            scanner.next();
        }
        return scanner.nextFloat();
    }


    private Semester getValidSemester(Scanner scanner) {
        while (true) {
            try {
                return Semester.valueOf(scanner.next().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.print("Неверный семестр. Введите корректное значение: ");
            }
        }
    }

    private Color getValidColor(Scanner scanner) {
        while (true) {
            try {
                return Color.valueOf(scanner.next().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.print("Неверный цвет глаз. Попробуйте еще раз: ");
            }
        }
    }

    private ColorHair getValidColorHair(Scanner scanner) {
        while (true) {
            try {
                return ColorHair.valueOf(scanner.next().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.print("Неверный цвет волос. Попробуйте еще раз: ");
            }
        }
    }
}