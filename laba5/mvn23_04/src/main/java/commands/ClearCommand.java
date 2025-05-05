package commands;

import StudyGroup.StudyGroup;
import java.util.List;

public class ClearCommand implements Commands {
    private final List<StudyGroup> studyGroups;

    // Конструктор теперь принимает коллекцию
    public ClearCommand(List<StudyGroup> studyGroups) {
        this.studyGroups = studyGroups;
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String execute(String arguments) {
        if (studyGroups.isEmpty()) {
            return "Коллекция уже пуста.";
        }

        studyGroups.clear();
        StudyGroup.resetLastId(); // Сбрасываем счетчик ID для новых элементов
        return "Коллекция успешно очищена.";
    }
}