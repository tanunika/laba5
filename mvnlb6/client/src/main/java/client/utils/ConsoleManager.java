package client.utils;
import shared.Structures.*;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Optional;
public class ConsoleManager {
    private final Scanner scanner;

    public ConsoleManager(Scanner scanner) {
        this.scanner = scanner;
    }
    public String readCommand() {
        System.out.print("> ");
        return scanner.nextLine().trim();
    }
    public void printMessage(String message) {
        System.out.println(message);
    }

    // Чтение данных учебной группы
    public StudyGroup readStudyGroup() {
        System.out.println("Введите данные учебной группы:");

        String name = readString("Название группы: ", false);
        double x = readDouble("Координата X: ");
        long y = readLong("Координата Y: ");
        long studentsCount = readLong("Количество студентов: ");
        long expelledStudents = readLong("Количество отчисленных студентов: ");
        long shouldBeExpelled = readLong("Количество студентов для отчисления: ");
        Semester semester = readSemester();
        Person admin = readPerson();

        Coordinates coordinates = new Coordinates(x, y);
        return new StudyGroup(name, coordinates, studentsCount,
                expelledStudents, shouldBeExpelled, semester, admin);
    }

    // Чтение данных админа с использованием Stream API для обработки координат
    private Person readPerson() {
        System.out.println("Данные админа группы:");
        String name = readString("Имя: ", false);
        String passport = readString("Паспорт: ", true);
        Color eyeColor = readColor("Цвет глаз (RED, BLACK, BLUE, YELLOW, ORANGE): ");
        ColorHair hairColor = readHairColor("Цвет волос (GREEN, RED, YELLOW, ORANGE, BROWN): ");

        System.out.print("Координаты локации (x y z через пробел): ");
        double[] coords = Arrays.stream(scanner.nextLine().trim().split("\\s+"))
                .limit(3)
                .mapToDouble(Double::parseDouble)
                .toArray();

        Location location = new Location(coords[0], coords[1], coords[2]);
        return new Person(name, passport, eyeColor, hairColor, location);
    }

    // Вспомогательные методы для чтения разных типов данных
    private String readString(String prompt, boolean canBeNull) {
        System.out.print(prompt);
        return Optional.ofNullable(scanner.nextLine().trim())
                .filter(s -> !s.isEmpty() || canBeNull)
                .orElseThrow(() -> new IllegalArgumentException("Значение не может быть пустым"));
    }

    private long readLong(String prompt) {
        System.out.print(prompt);
        return Optional.of(scanner.nextLine())
                .map(String::trim)
                .map(Long::parseLong)
                .orElseThrow(() -> new IllegalArgumentException("Некорректное число"));
    }

    private double readDouble(String prompt) {
        System.out.print(prompt);
        return Optional.of(scanner.nextLine())
                .map(String::trim)
                .map(Double::parseDouble)
                .orElseThrow(() -> new IllegalArgumentException("Некорректное число"));
    }

    private Semester readSemester() {
        System.out.print("Семестр (FIRST, SECOND, THIRD, FOURTH, FIFTH): ");
        return Optional.ofNullable(scanner.nextLine().trim())
                .filter(s -> !s.isEmpty())
                .map(String::toUpperCase)
                .map(Semester::valueOf)
                .orElse(null);
    }

    private Color readColor(String prompt) {
        System.out.print(prompt);
        return Optional.ofNullable(scanner.nextLine().trim())
                .filter(s -> !s.isEmpty())
                .map(String::toUpperCase)
                .map(Color::valueOf)
                .orElse(null);
    }

    private ColorHair readHairColor(String prompt) {
        System.out.print(prompt);
        return Optional.of(scanner.nextLine().trim())
                .filter(s -> !s.isEmpty())
                .map(s -> ColorHair.valueOf(s.toUpperCase()))
                .orElse(null);
    }

}