package server.commands;

import common.exceptions.WrongAmountOfArgumentsException;
import common.utility.Outputter;
import server.utility.ResponseOutputter;

import java.io.Serializable;
import java.util.Map;

/**
 * Command that shows usage and description of all available commands.
 */
public class HelpCommand extends AbstractCommand {
    Map<String, ICommand> commands;

    /**
     * Help command constructor.
     * @param commands
     */
    public HelpCommand(Map<String, ICommand> commands) {
        super("help", "Displays help on available commands");
        this.commands = commands;
    }

    /**
     * Show list of usage and description of all available commands.
     * @param argument The argument passed to the command.
     * @return the response of right execution.
     */
    @Override
    public boolean execute(Serializable argument) {
        try {
            String strarg = (String) argument;
            if (!(strarg.isEmpty())) throw new WrongAmountOfArgumentsException();
            for (String name : commands.keySet()) {
                String value = commands.get(name).toString();
                ResponseOutputter.appendLn(value);
            }


        } catch (WrongAmountOfArgumentsException e) {
            ResponseOutputter.appendError(e.getMessage());
            return false;
        }
        return true;

    }
}
