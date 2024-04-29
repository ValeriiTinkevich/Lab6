package server.commands;


import common.data.SpaceMarine;
import common.exceptions.IncorrectInputInScriptException;
import common.exceptions.WrongAmountOfArgumentsException;
import common.utility.Outputter;
import server.utility.CollectionManager;
import server.utility.ResponseOutputter;

import java.io.Serializable;

/**
 * Command that updates element by id from user input.
 */
public class UpdateByIdCommand extends AbstractCommand {
    CollectionManager collectionManager;

    /**
     * Update command constructor
     * @param collectionManager Collection manager for update command.
     */
    public UpdateByIdCommand(CollectionManager collectionManager) {
        super("update", "Updates element by id");
        this.collectionManager = collectionManager;

    }

    /**
     * Updates element by id from user input.
     * @param argument The argument passed to the command.
     * @return the response of right execution.
     */
    @Override
    public boolean execute(Serializable argument) {
        ResponseOutputter.appendError("Command needs 2 arguments!");
        return false;
    }

    public boolean execute(Serializable argument_id, Serializable argument_Marine) {
        try {
            if (argument_id == null || argument_Marine == null) throw new WrongAmountOfArgumentsException();
            String argument_id_string = (String) argument_id;
            SpaceMarine argument_Marine_cast = (SpaceMarine) argument_Marine;
            long id = Integer.parseInt(argument_id_string);
            collectionManager.update(id, argument_Marine_cast);
            ResponseOutputter.appendLn("Space marine updated successfully!");
            return true;
        } catch (WrongAmountOfArgumentsException e) {
            ResponseOutputter.appendError(e.getMessage());
        } catch (NumberFormatException e) {
            ResponseOutputter.appendError("id must be Integer!");
        } catch (IndexOutOfBoundsException e) {
            ResponseOutputter.appendError("Element with this id does not exist!");
        }
        return false;
    }
    }

