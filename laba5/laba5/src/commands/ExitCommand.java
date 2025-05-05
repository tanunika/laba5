package commands;

//завершить программу (без сохранения в файл)
public class ExitCommand implements Commands{
    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public String execute(String arguments) {
        if (!arguments.isEmpty()) {
            return "команда exit не принимает аргументы";
        }
        System.exit(0);
        return "";

    }
}
