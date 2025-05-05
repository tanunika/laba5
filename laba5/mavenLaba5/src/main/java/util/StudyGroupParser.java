package util;

import StudyGroup.StudyGroup;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class StudyGroupParser {
    public static List<StudyGroup> loadFromFile(File file) {
        List<StudyGroup> groups = new ArrayList<>();
        if (!file.exists()) return groups;

        try (FileReader reader = new FileReader(file)) {
            Gson gson = new Gson();
            Type collectionType = new TypeToken<List<StudyGroup>>() {}.getType();
            groups = gson.fromJson(reader, collectionType);
        } catch (Exception e) {
            System.out.println("Ошибка при чтении JSON: " + e.getMessage());
        }

        return groups;
    }
}
