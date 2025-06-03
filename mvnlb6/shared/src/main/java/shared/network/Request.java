package shared.network;

import shared.Structures.StudyGroup;
import java.io.Serializable;

public class Request implements Serializable {
    private final String commandName;
    private final Object[] arguments;
    private final StudyGroup studyGroup;

    public Request(String commandName, Object[] arguments, StudyGroup studyGroup) {
        this.commandName = commandName;
        this.arguments = arguments;
        this.studyGroup = studyGroup;
    }

    public String getCommandName() {
        return commandName;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public StudyGroup getStudyGroup() {
        return studyGroup;
    }
}