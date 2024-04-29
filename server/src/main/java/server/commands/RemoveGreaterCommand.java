package server.commands;



import common.data.SpaceMarine;
import common.exceptions.IncorrectInputInScriptException;
import common.exceptions.WrongAmountOfArgumentsException;
import common.utility.Outputter;
import server.utility.CollectionManager;
import server.utility.ResponseOutputter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Command that removes all elements greater than inputted.
 */
public class RemoveGreaterCommand extends AbstractCommand{
    CollectionManager collectionManager;

    /**
     * Remove_greater command constructor.
     * @param collectionManager Collection manager for remove_greater command.
     */
    public RemoveGreaterCommand(CollectionManager collectionManager) {
        super("remove_greater", "removes all elements greater than input");
        this.collectionManager  = collectionManager;
    }
    /**
     * Removes all elements greater than inputted.
     * @param argument The argument passed to the command.
     * @return the response of right execution.
     */
    @Override
    public boolean execute(Serializable argument) {
        try {
            if(!(argument instanceof SpaceMarine || argument == null)) throw new WrongAmountOfArgumentsException();
            int counter = 0;
            SpaceMarine greaterSpaceMarine = (SpaceMarine) argument;
            ArrayList<SpaceMarine> tempList = new ArrayList<>();
            for (SpaceMarine spaceMarine: collectionManager.getSpaceMarineCollection()) {
                if(spaceMarine.compareTo(greaterSpaceMarine) > 0) {
                    tempList.add(spaceMarine);
                    counter++;
                }
            }
            collectionManager.getSpaceMarineCollection().removeAll(tempList);
            ResponseOutputter.appendLn("Removed "+ counter + " Space marines" );
            return true;
        } catch (WrongAmountOfArgumentsException e) {
            ResponseOutputter.appendError(e.getMessage());
            return false;
        }
    }
}
