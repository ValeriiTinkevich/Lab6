package server.commands;


import common.data.SpaceMarine;
import common.exceptions.WrongAmountOfArgumentsException;
import common.utility.Outputter;
import server.utility.CollectionManager;
import server.utility.ResponseOutputter;

import java.io.Serializable;

/**
 * Command that displays collection elements for user.
 */
public class ShowCommand extends AbstractCommand {
    CollectionManager collectionManager;

    /**
     * Show command constructor.
     * @param collectionManager Collection manager for show command.
     */
    public ShowCommand(CollectionManager collectionManager) {
        super("show", "Displays collection elements as Strings");
        this.collectionManager = collectionManager;
    }
    /**
     * Displays collection.
     * @param argument The argument passed to the command.
     * @return the response of right execution.
     */
    @Override
    public boolean execute(Serializable argument) {
        try {
            String strarg = (String) argument;
            if (!(strarg.isEmpty())) throw new WrongAmountOfArgumentsException();
            if (collectionManager.getSpaceMarineCollection().isEmpty()) {
                ResponseOutputter.appendLn("Collection is empty.");
            } else {
                for (SpaceMarine spaceMarine : collectionManager.getSpaceMarineCollection()) {
                    ResponseOutputter.appendLn(spaceMarine.toString() + "\n===============");

                }
            }
            return true;
        } catch (WrongAmountOfArgumentsException e) {
            ResponseOutputter.appendLn(e.getMessage());
        }
        return false;
    }

}
