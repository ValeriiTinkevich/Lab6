package server.commands;


import common.data.Chapter;
import common.data.SpaceMarine;
import common.exceptions.WrongAmountOfArgumentsException;
import common.utility.Outputter;
import server.utility.CollectionManager;
import server.utility.ResponseOutputter;

import java.io.Serializable;

/**
 * Shows filtered collection by chapter.
 */
public class FilterByChapterCommand extends AbstractCommand{
    CollectionManager collectionManager;

    /**
     * Filter_by_chapter command constructor.
     * @param collectionManager Collection manager for filter_by_chapter command.
     */
    public FilterByChapterCommand(CollectionManager collectionManager) {
        super("filter_by_chapter", "Shows elements with chapter == input");
        this.collectionManager = collectionManager;
    }


    /**
     * Show filtered collection by chapter.
     *
     * @param argument The argument passed to the command.
     * @return the response of right execution.
     */
    @Override
    public boolean execute(Serializable argument) {
        try {
            if (!(argument instanceof Chapter)) throw new WrongAmountOfArgumentsException();
            Chapter askedChapter = (Chapter) argument;
            for(SpaceMarine spaceMarine: collectionManager.getSpaceMarineCollection()) {
                if (spaceMarine.getChapter().equals(askedChapter)) {
                    ResponseOutputter.appendLn(spaceMarine.toString() + "\n===============");
                }
            }
            return true;
        } catch (WrongAmountOfArgumentsException e) {
            ResponseOutputter.appendError(e.getMessage());
            return false;
        }
    }
}
