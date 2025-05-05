package commands;

import StudyGroup.StudyGroup;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SaveCommand implements Commands {
    private final List<StudyGroup> studyGroups;
    private final File file;

    public SaveCommand(List<StudyGroup> studyGroups, File file) {
        this.studyGroups = studyGroups;
        this.file = file;
    }

    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String execute(String arguments) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(studyGroups, writer);
            return "Коллекция успешно сохранена в JSON-файл: " + file.getPath();
        } catch (IOException e) {
            return "Ошибка при сохранении в JSON: " + e.getMessage();
        }
    }
}
