package commands;

import StudyGroup.StudyGroup;
import StudyGroup.Coordinates;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class UpdateCommand implements Commands {
    private List<StudyGroup> studyGroups;

    public UpdateCommand(List<StudyGroup> studyGroups) {
        this.studyGroups = studyGroups;
    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String execute(String arguments) {
        Scanner scanner = new Scanner(System.in);

        try {
            int id = Integer.parseInt(arguments.trim());

            StudyGroup groupToUpdate = studyGroups.stream()
                    .filter(group -> group.getId() == id)
                    .findFirst()
                    .orElse(null);

            if (groupToUpdate == null) {
                return "Группа с id " + id + " не найдена.";
            }

            System.out.println("Обновление группы (оставьте поле пустым, чтобы не изменять значение):");

            // Название группы
            System.out.print("Новое название группы [" + groupToUpdate.getName() + "]: ");
            String name = scanner.nextLine();
            if (!name.isEmpty()) {
                groupToUpdate.setName(name);
            }

            // Координаты
            System.out.print("Новая координата X [" + groupToUpdate.getCoordinates().getX() + "]: ");
            String xInput = scanner.nextLine();
            System.out.print("Новая координата Y [" + groupToUpdate.getCoordinates().getY() + "]: ");
            String yInput = scanner.nextLine();
            if (!xInput.isEmpty() || !yInput.isEmpty()) {
                double x = xInput.isEmpty() ? groupToUpdate.getCoordinates().getX() : Double.parseDouble(xInput);
                long y = yInput.isEmpty() ? groupToUpdate.getCoordinates().getY() : Long.parseLong(yInput);
                groupToUpdate.setCoordinates(new Coordinates(x, y));
            }

            // Количество студентов
            System.out.print("Новое количество студентов [" + groupToUpdate.getStudentsCount() + "]: ");
            String studentsCountInput = scanner.nextLine();
            if (!studentsCountInput.isEmpty()) {
                groupToUpdate.setStudentsCount((int) Long.parseLong(studentsCountInput));
            }

            // Остальные поля аналогично...
            // (добавьте обработку всех остальных полей StudyGroup)

            groupToUpdate.setCreationDate(LocalDateTime.now()); // Обновляем дату изменения

            return "Группа с id " + id + " успешно обновлена.";
        } catch (NumberFormatException e) {
            return "Неверный формат ввода: введите аргумент для выполнения команды";
        }
    }
}