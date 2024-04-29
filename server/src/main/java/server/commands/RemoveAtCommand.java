package server.commands;



import common.exceptions.WrongAmountOfArgumentsException;
import common.utility.Outputter;
import server.utility.CollectionManager;
import server.utility.ResponseOutputter;

import java.io.Serializable;

/**
 * Command that removes element at given index.
 */
public class RemoveAtCommand extends AbstractCommand{
    CollectionManager collectionManager;

    /**
     * Remove_at command constructor.
     * @param collectionManager Collection manager for remove_at command.
     */
    public RemoveAtCommand(CollectionManager collectionManager) {
        super("remove_at", "Removes an element by its index");
        this.collectionManager = collectionManager;
    }
    /**
     * Removes element at index from collection.
     * @param argument The argument passed to the command.
     * @return the response of right execution.
     */
    @Override
    public boolean execute(Serializable argument) {
        try {
            if (argument == null) throw new WrongAmountOfArgumentsException();
            String argument_str = (String) argument;
            int index = Integer.parseInt(argument_str);
            collectionManager.removeAtIndex(index);
            ResponseOutputter.appendLn("Successfully removed the element");
            return true;

        } catch (WrongAmountOfArgumentsException e) {
            ResponseOutputter.appendError(e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            ResponseOutputter.appendError("Index out of bounds!");
        } catch (NumberFormatException e) {
            ResponseOutputter.appendError("The id value must be int!");
        }
        return false;
    }
}

