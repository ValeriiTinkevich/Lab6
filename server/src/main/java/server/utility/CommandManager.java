package server.utility;

import server.commands.*;

import java.util.*;

public class CommandManager {
    public final Map<String, ICommand> commands;
    CollectionManager collectionManager;
    private final int COMMAND_HISTORY_SIZE = 8;

    private LinkedList<String> commandHistory;

    public CommandManager (CollectionManager collectionManager) {
        commandHistory = new LinkedList<String>();
        this.collectionManager = collectionManager;
        commands = new HashMap<>();
        commands.put("help", new HelpCommand(this.commands));
        commands.put("info", new InfoCommand(this.collectionManager));
        commands.put("add", new AddElementCommand(this.collectionManager));
        commands.put("show", new ShowCommand(this.collectionManager));
        commands.put("update", new UpdateByIdCommand(this.collectionManager));
        commands.put("remove_by_id", new RemoveByIdCommand(this.collectionManager));
        commands.put("exit", new ExitCommand());
        commands.put("remove_at", new RemoveAtCommand(this.collectionManager));
        commands.put("add_if_max", new AddIfMaxCommand(this.collectionManager));
        commands.put("remove_greater", new RemoveGreaterCommand(this.collectionManager));
        commands.put("filter_by_chapter", new FilterByChapterCommand(this.collectionManager));
        commands.put("print_unique_heart_count", new PrintUniqueHeartCountCommand(this.collectionManager));
        commands.put("filter_less_than_health", new FilterLessThanHealthCommand(this.collectionManager));
        commands.put("execute", new ExecuteScriptCommand());


    }

    public void addToHistory(String CommandName) {
        commandHistory.addFirst(CommandName);
        if (commandHistory.size() > 5) commandHistory.pollLast();
    }
}
