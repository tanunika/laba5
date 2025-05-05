import StudyGroup.StudyGroup;
import commands.Invoker;
import util.StudyGroupParser;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class MainTest {
    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        // Получаем путь к файлу из переменной окружения
        String path = System.getenv("STUDYGROUPFILE");
        System.out.println("Путь из переменной окружения: " + path);
        if (path == null || path.isEmpty()) {
            System.out.println("Ошибка: переменная окружения STUDYGROUPFILE не установлена.");
            return;
        }

        File file = new File(path);

        // Проверяем, существует ли файл
        if (!file.exists()) {
            System.out.println("Ошибка: файл по пути '" + path + "' не существует.");
            return;
        }

        List<StudyGroup> studyGroups = StudyGroupParser.loadFromFile(file);

        if (studyGroups == null || studyGroups.isEmpty()) {
            System.out.println("Ошибка: не удалось загрузить данные из файла.");
            return;
        }

        Invoker invoker = new Invoker(studyGroups, file);

        while (true) {
            System.out.print("Введите команду: ");
            String command = in.nextLine();
            invoker.invoke(command);
        }
    }
}
