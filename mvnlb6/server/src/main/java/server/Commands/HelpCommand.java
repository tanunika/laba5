package server.Commands;

public class HelpCommand implements Commands {
    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String execute(String arguments) {
        if (!arguments.isEmpty()) {
            return "команда help не принимает аргументы";
        }
        return
                "	help : вывести справку по доступным командам" + "\n" +
                        "	info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)" + "\n" +
                        "	show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении" + "\n" +
                        "	add {element} : добавить новый элемент в коллекцию" + "\n" +
                        "	update_id {element} : обновить значение элемента коллекции, id которого равен заданному" + "\n" +
                        "	remove_by_id id : удалить элемент из коллекции по его id" + "\n" +
                        "	clear : очистить коллекцию" + "\n" +
                        "	save : сохранить коллекцию в файл" + "\n" +
                        "	execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме." + "\n" +
                        "	exit : завершить программу (без сохранения в файл)" + "\n" +
                        "	remove_head : вывести первый элемент коллекции и удалить его" + "\n" +
                        "	add_if_max {element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции" + "\n" +
                        "	remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный" + "\n" +
                        "	remove_all_by_group_admin groupAdmin : удалить из коллекции все элементы, значение поля groupAdmin которого эквивалентно заданному" + "\n" +
                        "	count_less_than_students_count studentsCount : вывести количество элементов, значение поля studentsCount которых меньше заданного" + "\n" +
                        "	filter_less_than_expelled_students expelledStudents : вывести элементы, значение поля expelledStudents которых меньше заданного" + "\n"
                ;
    }
}