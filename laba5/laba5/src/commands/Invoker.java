package commands;

import StudyGroup.StudyGroup;

import java.util.*;

public class Invoker {

    private final Map<String, Commands> commands = new HashMap<>();
    private final List<StudyGroup> studyGroups;


    public Invoker(List<StudyGroup> studyGroups) {
        this.studyGroups = studyGroups;

        initializeCommands();
    }

    private void initializeCommands() {
            commands.put("help", new helpCommand()); //вывести справку по доступным командам
        commands.put("info", new InfoCommand(studyGroups)); //вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
        commands.put("show", new ShowCommand(studyGroups)); //вывести в стандартный поток вывода все элементы коллекции в строковом представлении
        commands.put("add", new AddCommand(studyGroups)); //добавить новый элемент в коллекцию
        commands.put("update_id", new UpdateCommand(studyGroups)); //обновить значение элемента коллекции, id которого равен заданному
        commands.put("remove_by_id", new RemoveByIDCommand(studyGroups)); //удалить элемент из коллекции по его id
        commands.put("clear", new ClearCommand(studyGroups)); //очистить коллекцию
        commands.put("save", new SaveCommand(studyGroups)); // сохранить коллекцию в файл
        commands.put("execute_script", new ExecuteCommand(this)); // Передаем текущий Invoker //считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.
        commands.put("exit", new ExitCommand()); //завершить программу (без сохранения в файл)
        commands.put("remove_head", new RemoveHeadCommand(studyGroups)); //вывести первый элемент коллекции и удалить его
        commands.put("add_if_max", new AddIfMaxCommand(studyGroups)); //добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции
        commands.put("remove_lower", new RemoveLowerCommand(studyGroups)); //удалить из коллекции все элементы, меньшие, чем заданный
        commands.put("remove_all_by_group_admin", new RemoveAllCommand(studyGroups)); //удалить из коллекции все элементы, значение поля groupAdmin которого эквивалентно заданному
        commands.put("count_less_than_student_count", new CountCommand(studyGroups)); //вывести количество элементов, значение поля studentsCount которых меньше заданного
        commands.put("filter_less_than_expelled_students", new FilterCommand(studyGroups)); //вывести элементы, значение поля expelledStudents которых меньше заданного
    }

    public List<StudyGroup> getStudyGroups() {
        return this.studyGroups;
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
}