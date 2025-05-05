import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import StudyGroup.StudyGroup;
import commands.Invoker;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import util.LocalDateTimeAdapter;
import util.StudyGroupCounter;

public class MainTest {

    public static void main(String[] args) {
        List<StudyGroup> studyGroups = new ArrayList<>();
        String fileName = System.getenv("FILENAME");

        if (fileName == null || fileName.isEmpty()) {
            System.out.println("Ошибка: переменная окружения FILENAME не задана.");
        } else {
            studyGroups = loadCollectionFromFile(fileName);
        }
        StudyGroupCounter.getInstance().setCount(studyGroups.size());
        Invoker invoker = new Invoker(studyGroups);

        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.print("Введите команду: ");
            String command = in.nextLine();
            invoker.invoke(command);
        }
    }

    private static List<StudyGroup> loadCollectionFromFile(String fileName) {
        File file = new File(fileName);

        // Проверка на пустой файл
        if (file.length() == 0) {
            System.out.println("Файл пуст.");
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(fileName)) {
            BufferedReader br = new BufferedReader(reader);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line.trim());  // Убираем лишние пробелы или пустые строки
            }

            // Выводим содержимое файла для проверки
            System.out.println("Содержимое файла: \n" + sb.toString());

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .create();

            Type listType = new TypeToken<List<StudyGroup>>() {}.getType();
            return gson.fromJson(sb.toString(), listType);
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке: " + e.getMessage());
            return new ArrayList<>();
        } catch (JsonSyntaxException e) {
            System.out.println("Ошибка синтаксиса JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Адаптер для работы с LocalDateTime
    public static class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
        private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        @Override
        public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.format(formatter));
        }

        @Override
        public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            try {
                return LocalDateTime.parse(json.getAsString(), formatter);
            } catch (Exception e) {
                System.out.println("Ошибка десериализации LocalDateTime: " + e.getMessage());
                return null;  // Возвращаем null в случае ошибки
            }
        }
    }

}
