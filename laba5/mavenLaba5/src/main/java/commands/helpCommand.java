package commands;

public class helpCommand implements Commands{
    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String execute(String arguments) {
        if (!arguments.isEmpty()) {
            return "команда help не принимает аргументы";
        }
        return "•\thelp : вывести справку по доступным командам\n" +
                "•\tinfo : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "•\tshow : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "•\tadd {element} : добавить новый элемент в коллекцию\n" +
                "•\tupdate id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
                "•\tremove_by_id id : удалить элемент из коллекции по его id\n" +
                "•\tclear : очистить коллекцию\n" +
                "•\tsave : сохранить коллекцию в файл\n" +
                "•\texecute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                "•\texit : завершить программу (без сохранения в файл)\n" +
                "•\tremove_head : вывести первый элемент коллекции и удалить его\n" +
                "•\tadd_if_max {element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции\n" +
                "•\tremove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный\n" +
                "•\tremove_all_by_group_admin groupAdmin : удалить из коллекции все элементы, значение поля groupAdmin которого эквивалентно заданному\n" +
                "•\tcount_less_than_students_count studentsCount : вывести количество элементов, значение поля studentsCount которых меньше заданного\n" +
                "•\tfilter_less_than_expelled_students expelledStudents : вывести элементы, значение поля expelledStudents которых меньше заданного";
    }
}
