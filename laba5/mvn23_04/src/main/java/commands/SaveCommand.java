package commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import StudyGroup.StudyGroup;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import util.LocalDateTimeAdapter;
import util.StudyGroupCounter;

public class SaveCommand implements Commands {
    private final List<StudyGroup> studyGroups;

    public SaveCommand(List<StudyGroup> studyGroups) {
        this.studyGroups = studyGroups;
    }

    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String execute(String arguments) {
        String fileName = System.getenv("FILENAME");

        if (fileName == null || fileName.isEmpty()) {
            return "Ошибка: переменная окружения FILENAME не задана.";
        }

        try (FileWriter writer = new FileWriter(fileName)) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .setPrettyPrinting()
                    .create();
            gson.toJson(studyGroups, writer);
            StudyGroupCounter.getInstance().increment();
            return "Коллекция успешно сохранена в файл: " + fileName;
        } catch (IOException e) {
            return "Ошибка при сохранении в файл '" + fileName + "': " + e.getMessage();
        }
    }
}