package server.commands;


import common.exceptions.MustNotBeEmptyException;
import common.exceptions.WrongAmountOfArgumentsException;
import common.utility.Outputter;
import server.utility.CollectionManager;
import server.utility.ResponseOutputter;

import java.io.Serializable;

/**
 * Command that removes element from collection with given id.
 */
public class RemoveByIdCommand extends AbstractCommand {
    CollectionManager collectionManager;

    /**
     * Remove_by_id command constructor.
     * @param collectionManager Collection manager for remove_by_id command.
     */
    public RemoveByIdCommand(CollectionManager collectionManager) {
        super("remove_by_id", "Removes element by id");
        this.collectionManager = collectionManager;
    }

    /**
     * Removes element by id from collection.
     * @param argument The argument passed to the command.
     * @return the response of right execution.
     */
    @Override
    public boolean execute(Serializable argument) {
        try {
            if (argument == null) throw new WrongAmountOfArgumentsException();
            String argument_str = (String) argument;
            int id = Integer.parseInt(argument_str);
            if (collectionManager.getById(id) == null) throw new MustNotBeEmptyException();
            collectionManager.removeByIDFromCollection(id);
            ResponseOutputter.appendLn("Successfully removed the element");
            return true;

        } catch (WrongAmountOfArgumentsException e) {
            ResponseOutputter.appendError(e.getMessage());
        } catch (MustNotBeEmptyException e) {
            ResponseOutputter.appendError("No space marine with this id");
        } catch (NumberFormatException e) {
            ResponseOutputter.appendError("The id value must be int!");
        }
        return false;
    }
}
