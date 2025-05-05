package commands;

import StudyGroup.StudyGroup;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Invoker {
    private final Map<String, Commands> commands = new HashMap<>();
    private final List<StudyGroup> studyGroups;

    public Invoker(List<StudyGroup> studyGroups, File file) {
        this.studyGroups = studyGroups;
        initializeCommands(file);
    }

    private void initializeCommands(File file) {
        commands.put("help", new helpCommand());
        commands.put("info", new InfoCommand(studyGroups));
        commands.put("show", new ShowCommand(studyGroups));
        commands.put("add", new AddCommand(studyGroups));
        commands.put("update", new UpdateCommand(studyGroups));
        commands.put("remove_by_id", new RemoveByIDCommand(studyGroups));
        commands.put("clear", new ClearCommand(studyGroups));
        commands.put("save", new SaveCommand(studyGroups, file)); // <-- изменено
        commands.put("execute_script", new ExecuteCommand(this));
        commands.put("exit", new ExitCommand());
        commands.put("remove_head", new RemoveHeadCommand(studyGroups));
        commands.put("add_if_max", new AddIfMaxCommand(studyGroups));
        commands.put("remove_lower", new RemoveLowerCommand(studyGroups));
        commands.put("remove_all_by_group_admin", new RemoveAllCommand(studyGroups));
        commands.put("count_less_than_student_count", new CountCommand(studyGroups));
        commands.put("filter_less_than_expelled_students", new FilterCommand(studyGroups));
    }

    public String invoke(String line) {
        String[] parts = line.split("\\s+", 2);
        String commandName = parts[0];
        String arguments = parts.length > 1 ? parts[1] : "";

        if (!commands.containsKey(commandName)) {
            System.out.println("Команды " + commandName + " не существует");
            return commandName;
        }

        Commands command = commands.get(commandName);
        System.out.println(command.execute(arguments));
        return commandName;
    }
    public List<StudyGroup> getStudyGroups() {
        return this.studyGroups;
    }
}
