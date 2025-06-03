package server.Commands;

import server.utils.CollectionManager;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ExecuteScriptCommand implements Commands {
    private final CollectionManager collectionManager;
    private final Invoker invoker;

    public ExecuteScriptCommand(CollectionManager collectionManager, Invoker invoker) {
        this.collectionManager = collectionManager;
        this.invoker = invoker;
    }

    @Override
    public String getName() {
        return "execute_script";
    }

    @Override
    public String execute(String arguments) {
        if (arguments == null || arguments.trim().isEmpty()) {
            return "Не указано имя файла скрипта.";
        }

        StringBuilder result = new StringBuilder();
        String scriptPath = arguments.trim();

        try (BufferedReader reader = new BufferedReader(new FileReader(scriptPath))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();

                if (line.isEmpty() || line.startsWith("//")) {
                    continue;
                }

                try {
                    String[] parts = line.split("\\s+", 2);
                    String commandName = parts[0];
                    String commandArgs = parts.length > 1 ? parts[1] : "";

                    result.append("[Строка ").append(lineNumber).append("] ")
                            .append(commandName).append(" ").append(commandArgs)
                            .append(":\n");

                    String commandResult = invoker.invoke(commandName, commandArgs);
                    result.append(commandResult).append("\n");

                } catch (Exception e) {
                    result.append("Ошибка в строке ").append(lineNumber)
                            .append(": ").append(e.getMessage()).append("\n");
                }
            }
        } catch (IOException e) {
            return "Ошибка чтения файла скрипта: " + e.getMessage();
        }

        return result.toString();
    }

    @Override
    public boolean isInteractive() {
        return false;
    }

    @Override
    public String nextPrompt(String input) {
        return "";
    }

    @Override
    public boolean isReady() {
        return true;
    }
}