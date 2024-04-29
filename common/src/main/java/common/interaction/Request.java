package common.interaction;

import java.io.Serializable;

public class Request implements Serializable {
    String commandName;
    Serializable commandArgument;

    public Request() {
        this("","");
    }

    public Request(String commandName, Serializable commandArgument) {
        this.commandName = commandName;
        this.commandArgument = commandArgument;
    }

    public String getCommandName() {
        return commandName;
    }

    public Serializable getCommandArgument() {
        return commandArgument;
    }

    public boolean isEmpty() {
        return commandName.isEmpty() && (commandArgument == null);
    }

    @Override
    public String toString() {
        return "Request[" + commandName + ", " + commandArgument.toString() + "]";
    }

}
