package server.commands;


import common.exceptions.WrongAmountOfArgumentsException;
import common.utility.Outputter;
import server.utility.ResponseOutputter;

import java.io.Serializable;

public class ExitCommand extends AbstractCommand {
    public ExitCommand() {
        super("exit", "Exits console");
    }

    /**
     * Exit the program
     *
     * @param argument The argument passed to the command.
     * @return the response of right execution.
     */
    @Override
    public boolean execute(Serializable argument) {
        try {
            if (!(argument == null)) throw new WrongAmountOfArgumentsException();
            ResponseOutputter.appendLn("Exiting the program...");
            System.exit(0);
        } catch (WrongAmountOfArgumentsException e) {
            ResponseOutputter.appendLn(e);
            return false;
        }
        return true;
    }

}
