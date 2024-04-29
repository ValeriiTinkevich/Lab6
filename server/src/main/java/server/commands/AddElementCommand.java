package server.commands;



import common.data.SpaceMarine;
import common.exceptions.IncorrectInputInScriptException;
import common.exceptions.WrongAmountOfArgumentsException;
import common.utility.Outputter;
import server.utility.CollectionManager;
import server.utility.ResponseOutputter;

import java.io.Serializable;

/**
 * Command for adding an element to collection from user input.
 */
public class AddElementCommand extends AbstractCommand{
    private final CollectionManager collectionManager;

    /**
     * Add command constructor.
     * @param collectionManager Collection manager for add command.
     */
    public AddElementCommand(CollectionManager collectionManager) {
        super("add", "Adds and element to collection");
        this.collectionManager = collectionManager;
    }

    /**
     * Adds element from user input.
     *
     * @param argument The argument passed to the command.
     * @return the response of right execution.
     */
    @Override
    public boolean execute(Serializable argument) {
        try {
            if (!(argument instanceof SpaceMarine)) throw new WrongAmountOfArgumentsException();
            SpaceMarine smArg = (SpaceMarine) argument;
            smArg.setId(collectionManager.generateNewIdForCollection());
            collectionManager.addToCollection((SpaceMarine) argument);
            ResponseOutputter.appendLn("Space marine was added successfully!");
            return true;
        } catch (WrongAmountOfArgumentsException e) {
            ResponseOutputter.appendLn(e.getMessage());
            return false;
        }
    }
}
