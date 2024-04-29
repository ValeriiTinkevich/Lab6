package server.commands;



import common.data.SpaceMarine;
import common.exceptions.IncorrectInputInScriptException;
import common.exceptions.WrongAmountOfArgumentsException;
import common.utility.Outputter;
import server.utility.CollectionManager;
import server.utility.ResponseOutputter;

import java.io.Serializable;
import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * Command for adding element to collection if its value is more than max in the collection.
 */
public class AddIfMaxCommand extends AbstractCommand{
    CollectionManager collectionManager;

    /**
     * Add if max command constructor
     * @param collectionManager Collection manager for add_if_max command.
     */
    public AddIfMaxCommand(CollectionManager collectionManager) {
        super("add_if_max", "adds Space Marine");
        this.collectionManager = collectionManager;
    }
    /**
     * Adds asked element if its value is more than collections max object.
     *
     * @param argument The argument passed to the command.
     * @return the response of right execution.
     */
    @Override
    public boolean execute(Serializable argument) {
        try {
            if(!(argument instanceof SpaceMarine)) throw new WrongAmountOfArgumentsException();
            SpaceMarine askedMarine = (SpaceMarine) argument;
            if (collectionManager.getSize() == 0) {
                askedMarine.setId(collectionManager.generateNewIdForCollection());
                collectionManager.addToCollection(askedMarine);
                ResponseOutputter.appendLn("Space marine was added successfully");
                return true;
            }
            SpaceMarine maxSpaceMarine = collectionManager.getSpaceMarineCollection()
                    .stream()
                    .max(Comparator.comparing(SpaceMarine::getHeight))
                    .orElseThrow(NoSuchElementException::new);
            if(askedMarine.compareTo(maxSpaceMarine) > 0) {
                askedMarine.setId(collectionManager.generateNewIdForCollection());
                collectionManager.addToCollection(askedMarine);
                ResponseOutputter.appendLn("Space marine added successfully");
            } else {
                ResponseOutputter.appendLn("Height is not enough to add!");
            }
        } catch (WrongAmountOfArgumentsException e) {
            ResponseOutputter.appendLn(e.getMessage());
            return false;
        }
        return false;

    }
}
