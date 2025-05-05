package commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

public class ExecuteCommand implements Commands {
    private final Invoker invoker;
    private static final Deque<String> scriptStack = new ArrayDeque<>();
    private static final int MAX_SCRIPT_DEPTH = 10;

    public ExecuteCommand(Invoker invoker) {
        this.invoker = invoker;
    }

    @Override
    public String getName() {
        return "execute_script";
    }

    @Override
    public String execute(String arguments) {
        if (arguments == null || arguments.trim().isEmpty()) {
            return "Ошибка: укажите путь к файлу скрипта";
        }

        String filePath = arguments.trim().replace("\\", "/");
        File scriptFile = new File(filePath);

        if (!scriptFile.exists()) {
            return "Ошибка: файл '" + filePath + "' не существует";
        }
        if (!scriptFile.canRead()) {
            return "Ошибка: нет прав на чтение файла '" + filePath + "'";
        }

        if (scriptStack.size() >= MAX_SCRIPT_DEPTH) {
            return "Ошибка: превышена максимальная глубина вложенности скриптов (" + MAX_SCRIPT_DEPTH + ")";
        }
        if (scriptStack.contains(filePath)) {
            return "Ошибка: обнаружена рекурсия (файл '" + filePath + "' уже выполняется)";
        }

        scriptStack.push(filePath);
        int lineNumber = 0;
        int commandsExecuted = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(scriptFile))) {
            String line;
            System.out.println("Начало выполнения скрипта: " + filePath);

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();

                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                System.out.printf("[%s:%d] Выполняем: %s%n",
                        scriptFile.getName(), lineNumber, line);

                String result = invoker.invoke(line);
                System.out.println(result);
                commandsExecuted++;
            }

            return String.format(
                    "Скрипт '%s' выполнен. Команд: %d, элементов в коллекции: %d",
                    scriptFile.getName(), commandsExecuted, invoker.getStudyGroups().size());

        } catch (IOException e) {
            return "Ошибка выполнения скрипта '" + filePath + "' (строка " + lineNumber + "): " + e.getMessage();
        } finally {
            scriptStack.pop();
            System.out.println("Завершение выполнения скрипта: " + filePath);
        }
    }
}