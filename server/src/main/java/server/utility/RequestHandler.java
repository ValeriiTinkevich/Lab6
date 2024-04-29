package server.utility;

import common.interaction.Request;
import common.interaction.Response;
import common.interaction.ResponseResult;
import server.commands.ICommand;

import java.io.Serializable;

public class RequestHandler {
    private final CommandManager commandManager;

    public RequestHandler(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public Response handle(Request request){
        commandManager.addToHistory(request.getCommandName());
        ResponseResult responseResult = executeCommand(
                request.getCommandName(),
                request.getCommandArgument());
        return new Response(responseResult, ResponseOutputter.getAndClear());
    }

    /**
     * Executes a command from a request.
     *
     * @param commandName Name of command.
     * @param commandArgument Serializable argument for command.
     * @return Command execute status.
     */
    public ResponseResult executeCommand(String commandName, Serializable commandArgument) {
        ICommand command = commandManager.commands.get(commandName);
        if(command == null){
            ResponseOutputter.appendLn("Command '" + command + "' was not found. Try to write 'help' for more info.");
            return ResponseResult.ERROR;
        }
        else {
            if(command.execute(commandArgument)) return ResponseResult.OK;
            else return ResponseResult.ERROR;
        }
    }
}
