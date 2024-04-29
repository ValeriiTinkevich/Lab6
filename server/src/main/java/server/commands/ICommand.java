package server.commands;

import java.io.Serializable;

/**
 * Interface for all commands.
 */

public interface ICommand {
    String getDescription();

    String getName();

    /**
     * @param argument The argument passed to the command.
     * @return response of right execution.
     */
    boolean execute(Serializable argument);


}
