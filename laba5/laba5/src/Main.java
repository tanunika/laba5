import commands.Invoker;
import StudyGroup.StudyGroup;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<StudyGroup> studyGroups = new ArrayList<>();
        Invoker invoker = new Invoker(studyGroups);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Введите команду: ");
            String input = scanner.nextLine().trim();
            invoker.invoke(input);
        }
    }
}