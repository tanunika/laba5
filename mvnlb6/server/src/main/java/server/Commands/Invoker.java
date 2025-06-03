package server.Commands;

import server.utils.CollectionManager;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayDeque;
import java.util.Deque;

public class Invoker {
    private final Map<String, Commands> commands = new HashMap<>();
    private Commands currentInteractiveCommand = null;
    private final Deque<String> scriptCallStack = new ArrayDeque<>();
    private static final int MAX_RECURSION_DEPTH = 10;
    private final CollectionManager collectionManager;

    public Invoker(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        initializeCommands();
    }

    private void initializeCommands() {
        registerCommand("help", new HelpCommand());
        registerCommand("info", new InfoCommand(collectionManager));
        registerCommand("show", new ShowCommand(collectionManager));
        registerCommand("add", new AddCommand(collectionManager));
        registerCommand("update", new UpdateCommand(collectionManager));
        registerCommand("remove_by_id", new RemoveByIdCommand(collectionManager));
        registerCommand("clear", new ClearCommand(collectionManager));
        registerCommand("save", new SaveCommand(collectionManager));
        registerCommand("execute_script", new ExecuteScriptCommand(collectionManager, this));
        registerCommand("remove_head", new RemoveHeadCommand(collectionManager));
        registerCommand("add_if_max", new AddIfMaxCommand(collectionManager));
        registerCommand("remove_lower", new RemoveLowerCommand(collectionManager));
        registerCommand("remove_all_by_group_admin", new RemoveAllByGroupAdminCommand(collectionManager));
        registerCommand("count_less_than_students_count", new CountLessThanStudentsCountCommand(collectionManager));
        registerCommand("filter_less_than_expelled_students", new FilterLessThanExpelledStudentsCommand(collectionManager));
    }

    public void registerCommand(String name, Commands command) {
        commands.put(name, command);
    }

    public Commands getCommand(String name) {
        return commands.get(name);
    }

    public String invoke(String commandName, Object... arguments) {
        // Проверка на рекурсивные вызовы execute_script
        if ("execute_script".equals(commandName) && arguments.length > 0) {
            String scriptPath = arguments[0].toString();
            if (scriptCallStack.contains(scriptPath)) {
                return "Ошибка: обнаружена рекурсия в скрипте " + scriptPath;
            }
            if (scriptCallStack.size() >= MAX_RECURSION_DEPTH) {
                return "Ошибка: превышена максимальная глубина рекурсии (" + MAX_RECURSION_DEPTH + ")";
            }
            scriptCallStack.push(scriptPath);
        }

        try {
            Commands command = commands.get(commandName);
            if (command == null) {
                return "Команда не найдена: " + commandName;
            }

            if (command.isInteractive()) {
                return handleInteractiveCommand(command, arguments);
            } else {
                return command.execute(arguments != null && arguments.length > 0 ?
                        arguments[0].toString() : "");
            }
        } finally {
            if ("execute_script".equals(commandName) && arguments.length > 0) {
                scriptCallStack.pop();
            }
        }
    }

    private String handleInteractiveCommand(Commands command, Object[] arguments) {
        if (currentInteractiveCommand == null) {
            currentInteractiveCommand = command;
            return command.nextPrompt("");
        } else {
            String result = command.nextPrompt(arguments[0].toString());
            if (command.isReady()) {
                currentInteractiveCommand = null;
                return command.execute("");
            }
            return result;
        }
    }

    public boolean isInteractiveActive() {
        return currentInteractiveCommand != null && !currentInteractiveCommand.isReady();
    }

    public boolean isExecutingScript() {
        return !scriptCallStack.isEmpty();
    }
}