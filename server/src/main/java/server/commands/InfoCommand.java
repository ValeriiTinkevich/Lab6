package server.commands;


import common.exceptions.WrongAmountOfArgumentsException;
import common.utility.Outputter;
import server.utility.CollectionManager;
import server.utility.ResponseOutputter;

import java.io.Serializable;

/**
 * Command that show information about collection.
 */
public class InfoCommand extends AbstractCommand {
    CollectionManager collectionManager;

    /**
     * Info command constructor
     * @param collectionManager Collection manager for info command.
     */
    public InfoCommand(CollectionManager collectionManager) {
        super("info", "Displays information about Collection");
        this.collectionManager = collectionManager;
    }

    /**
     * Shows information about collection.
     * @param argument The argument passed to the command.
     * @return the response of right execution.
     */
    @Override
    public boolean execute(Serializable argument) {
        try {
            String strarg = (String) argument;
            if (!(strarg.isEmpty())) throw new WrongAmountOfArgumentsException();
            ResponseOutputter.appendLn("Collection used is: " + collectionManager.getSpaceMarineCollection()
                    .getClass()
                    .toString()
                    .replace("class java.util.", ""));
            ResponseOutputter.appendLn("Initialization time is: " + collectionManager.getCreationDate().toString());
            ResponseOutputter.appendLn("Current size of collection is: " + collectionManager.getSize() + " elements");
            return true;
        } catch (WrongAmountOfArgumentsException e) {
            ResponseOutputter.appendLn(e.getMessage());
            return false;
        }
    }

}
