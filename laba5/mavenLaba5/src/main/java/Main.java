import StudyGroup.StudyGroup;
import commands.Invoker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<StudyGroup> studyGroups = new ArrayList<>();
        String path = System.getenv("STUDY_GROUP_FILE");
        if (path == null || path.isEmpty()) {
            System.out.println("Ошибка: переменная окружения STUDY_GROUP_FILE не установлена.");
            return;
        }
        File file = new File(path);
        Invoker invoker = new Invoker(studyGroups, file);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Введите команду: ");
            String input = scanner.nextLine().trim();
            invoker.invoke(input);
        }
    }
}