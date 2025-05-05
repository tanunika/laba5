package commands;

import StudyGroup.StudyGroup;

import java.util.List;

public class RemoveHeadCommand implements Commands {
    private final List<StudyGroup> studyGroups;

    public RemoveHeadCommand(List<StudyGroup> studyGroups) {

        this.studyGroups = studyGroups;
    }

    @Override
    public String getName() {
        return "remove_head";
    }

    @Override
    public String execute(String arguments) {
        if (studyGroups.isEmpty()) {
            return "Коллекция пуста.";
        }

        if (!arguments.isEmpty()) {
            return "данная команда не принимает аргументы";
        }

        //получение первого элемента
        StudyGroup firstElement = studyGroups.get(0);
        studyGroups.remove(0);

        return "Первый элемент коллекции: " + firstElement + "\nЭлемент удален";
    }
}