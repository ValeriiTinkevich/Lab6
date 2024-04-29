package client.utility;

import client.App;
import common.data.Chapter;
import common.data.SpaceMarine;
import common.exceptions.CommandUsageException;
import common.exceptions.IncorrectInputInScriptException;
import common.exceptions.ScriptRecursionException;
import common.interaction.Request;
import common.interaction.ResponseResult;
import common.utility.Outputter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

public class UserHandler {
    private Scanner userScanner;
    private final Stack<File> scriptStack = new Stack<>();
    private final Stack<Scanner> scannerStack = new Stack<>();

    public UserHandler(Scanner userScanner) {
        this.userScanner = userScanner;
    }

    public Request handle(ResponseResult serverResponseResult) {
        String userInput;
        String[] userCommand;
        ProcessingResult processingResult;
        int rewriteAttempts = 0;
        try{
            do{
                try{
                    if(fileMode() && (serverResponseResult == ResponseResult.ERROR ||
                            serverResponseResult == ResponseResult.SERVER_EXIT))
                        throw new IncorrectInputInScriptException();
                    while(fileMode() && !userScanner.hasNextLine()){
                        userScanner.close();
                        userScanner = scannerStack.pop();
                        Outputter.printLn("Going back to the script '" + scriptStack.pop().getName() + "'...");
                    }
                    if(fileMode()){
                        userInput = userScanner.nextLine();
                        if(!userInput.isEmpty()){
                            Outputter.print(App.PS1);
                            Outputter.printLn(userInput);
                        }
                    } else {
                        Outputter.print(App.PS1);
                        userInput = userScanner.nextLine();
                    }
                    userCommand = (userInput.trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                } catch (NoSuchElementException | IllegalStateException exception) {
                    Outputter.println();
                    Outputter.printError("An error occurred while entering the command!");
                    userCommand = new String[]{"", ""};
                    rewriteAttempts++;
                    int maxRewriteAttempts = 3;
                    if (rewriteAttempts >= maxRewriteAttempts) {
                        Outputter.printError("Exceeded the number of input attempts!");
                        System.exit(0);
                    }
                }
                processingResult = processCommand(userCommand[0], userCommand[1]);
            } while (processingResult == ProcessingResult.ERROR && !fileMode() || userCommand[0].isEmpty());
            try{
                if(fileMode() && (serverResponseResult == ResponseResult.ERROR || processingResult == ProcessingResult.ERROR))
                    throw new IncorrectInputInScriptException();
                switch (processingResult){
                    case CHAPTER:
                        Chapter chapter = generateChapter();
                        return new Request(userCommand[0], chapter);
                    case OBJECT:
                        SpaceMarine spaceMarine = generateSpaceMarine();
                        return new Request(userCommand[0], spaceMarine);
                    case SCRIPT:
                        File scriptFile = new File(userCommand[1]);
                        if(!scriptFile.exists()) throw new FileNotFoundException();
                        if(!scriptStack.isEmpty() && scriptStack.search(scriptFile) != -1)
                            throw new ScriptRecursionException();
                        scannerStack.push(userScanner);
                        scriptStack.push(scriptFile);
                        userScanner = new Scanner(scriptFile);
                        Outputter.printLn("Execute script '" + scriptFile.getName() + "'...");
                        break;
                }
            } catch (FileNotFoundException exception){
                Outputter.printError("The script file was not found!");
            } catch (ScriptRecursionException exception){
                Outputter.printError("Scripts cannot be called recursively!");
                throw new IncorrectInputInScriptException();
            }
        } catch (IncorrectInputInScriptException exception){
            Outputter.printError("Script execution aborted!");
            while (!scannerStack.isEmpty()) {
                userScanner.close();
                userScanner = scannerStack.pop();
            }
            scriptStack.clear();
            return new Request();
        }
        return new Request(userCommand[0], userCommand[1]);
    }

    private ProcessingResult processCommand(String command, String commandArgument) {
        try{
            switch (command){
                case "":
                    return ProcessingResult.ERROR;
                case "remove_greater":
                case "add":
                case "add_if_max":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException("{element}");
                    return ProcessingResult.OBJECT;
                case "exit":
                    System.exit(0);
                case "help":
                case "info":
                case "print_unique_heart_count":
                case "save":
                case "server_exit":
                case "show":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "execute":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<file_name>");
                    return ProcessingResult.SCRIPT;
                case "filter_by_chapter":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException("{element}");
                    return ProcessingResult.CHAPTER;
                case "filter_less_than_health":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<Health>");
                    break;
                case "remove_at":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<Position>");
                    break;
                case "remove_by_id":
                case "update":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<ID>");
                    break;
                default:
                    Outputter.printLn("Command '" + command + "' was not found. Type 'help' for help.");
                    return ProcessingResult.ERROR;
            }
        } catch (CommandUsageException exception){
            if (exception.getMessage() != null) command += " " + exception.getMessage();
            Outputter.printLn("Usage: '" + command + "'");
            return ProcessingResult.ERROR;
        }
        return ProcessingResult.OK;
    }

    private SpaceMarine generateSpaceMarine() throws IncorrectInputInScriptException {
        SpaceMarineInputManager spaceMarineInputManager = new SpaceMarineInputManager(userScanner);
        if(fileMode()) spaceMarineInputManager.setScriptMode();
        return new SpaceMarine(
                spaceMarineInputManager.setId(),
                spaceMarineInputManager.askName(),
                spaceMarineInputManager.askCoordinates(),
                spaceMarineInputManager.askCreationDate(),
                spaceMarineInputManager.askHealth(),
                spaceMarineInputManager.askHeartCount(),
                spaceMarineInputManager.askHeight(),
                spaceMarineInputManager.askMeleeWeapon(),
                spaceMarineInputManager.askChapter()
        );
    }

    private Chapter generateChapter() throws IncorrectInputInScriptException {
        SpaceMarineInputManager spaceMarineInputManager = new SpaceMarineInputManager(userScanner);
        if(fileMode()) spaceMarineInputManager.setScriptMode();
        return spaceMarineInputManager.askChapter();
    }


    private boolean fileMode() {
        return !scannerStack.isEmpty();
    }
}
