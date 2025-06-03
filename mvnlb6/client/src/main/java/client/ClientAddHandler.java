package client;

import shared.Structures.*;

import java.time.LocalDateTime;
import java.util.Scanner;
//создание объекта studyGroup (ввод данных от пользователя)
public class ClientAddHandler {

    private final Scanner scanner;

    public ClientAddHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    public StudyGroup createStudyGroup() {
        System.out.print("Введите название группы: ");
        String name = scanner.nextLine();

        System.out.print("Введите координату X (double): ");
        double x = getValidDouble();

        System.out.print("Введите координату Y (long): ");
        long y = getValidLong();
        Coordinates coordinates = new Coordinates(x, y);

        System.out.print("Введите количество студентов: ");
        long studentsCount = getPositiveLong();

        System.out.print("Введите количество отчисленных студентов: ");
        long expelledStudents = getPositiveLong();

        System.out.print("Введите количество студентов, которых следует отчислить: ");
        long shouldBeExpelled = getPositiveLong();

        System.out.print("Введите семестр (FIRST, SECOND, THIRD, FOURTH, FIFTH): ");
        Semester semesterEnum = getValidSemester();

        // Очищаем буфер перед вводом строки
        scanner.nextLine();  // Это очистит символ новой строки из буфера, оставшийся после числовых значений

        System.out.print("Введите имя админа: ");
        String nameAdmin = scanner.nextLine();

        System.out.print("Введите паспорт админа: ");
        String passport = scanner.nextLine();

        System.out.print("Введите цвет глаз админа (RED, BLACK, BLUE, YELLOW, ORANGE): ");
        Color eyeColor = getValidEyeColor();

        System.out.print("Введите цвет волос админа (GREEN, RED, YELLOW, ORANGE, BROWN): ");
        ColorHair hairColor = getValidHairColor();

        System.out.print("Введите координаты локации админа (x y z через пробел): ");
        float locX = getValidFloat();
        float locY = getValidFloat();
        float locZ = getValidFloat();
        Location location = new Location(locX, locY, locZ);

        Person admin = new Person(nameAdmin, passport, eyeColor, hairColor, location);

        return new StudyGroup(name, coordinates, studentsCount, expelledStudents, shouldBeExpelled, semesterEnum, admin);
    }


    private long getPositiveLong() {
        while (true) {
            long value = getValidLong();
            if (value > 0) return value;
            System.out.print("Значение должно быть положительным. Повторите ввод: ");
        }
    }

    private double getValidDouble() {
        while (!scanner.hasNextDouble()) {
            System.out.print("Введите корректное число (double): ");
            scanner.next();  // Считываем некорректный ввод
        }
        double result = scanner.nextDouble();
        scanner.nextLine(); // Очищаем буфер от лишнего символа новой строки
        return result;
    }

    private long getValidLong() {
        while (!scanner.hasNextLong()) {
            System.out.print("Введите корректное целое число (long): ");
            scanner.next();  // Считываем некорректный ввод
        }
        long result = scanner.nextLong();
        scanner.nextLine(); // Очищаем буфер от лишнего символа новой строки
        return result;
    }


    private float getValidFloat() {
        while (!scanner.hasNextFloat()) {
            System.out.print("Введите корректное число (float): ");
            scanner.next();  // Считываем некорректный ввод
        }
        float result = scanner.nextFloat();
        scanner.nextLine(); // Очищаем буфер от лишнего символа новой строки
        return result;
    }

    private Semester getValidSemester() {
        while (true) {
            try {
                return Semester.valueOf(scanner.next().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.print("Неверный семестр. Повторите ввод: ");
            }
        }
    }

    private Color getValidEyeColor() {
        while (true) {
            try {
                return Color.valueOf(scanner.next().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.print("Неверный цвет глаз. Повторите ввод: ");
            }
        }
    }

    private ColorHair getValidHairColor() {
        while (true) {
            try {
                return ColorHair.valueOf(scanner.next().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.print("Неверный цвет волос. Повторите ввод: ");
            }
        }
    }
}
