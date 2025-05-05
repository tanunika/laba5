package commands;


import StudyGroup.StudyGroup;

import java.time.LocalDate;
import java.util.Collection;

//info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
public class InfoCommand implements Commands{
    private Collection<StudyGroup> collection;

    public InfoCommand(Collection<StudyGroup> collection) {
        this.collection = collection;
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String execute(String arguments) {
        if (!arguments.isEmpty()) {
            return "команда info не принимает аргументы";
        }
        return "Тип коллекции: " + collection.getClass().getSimpleName() + "\n" +
                "Дата инициализации: " + LocalDate.now() + "\n" +
                "Количество элементов: " + collection.size();
    }
}
